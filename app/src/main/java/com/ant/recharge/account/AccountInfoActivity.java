package com.ant.recharge.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.common.BaseActivity;
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.StringUtils;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.entity.MyAccountDetail;
import com.ant.recharge.entity.User;
import com.ant.recharge.common.NCallbackMsg;
import com.ant.recharge.login.NetLoginInterface;
import com.ant.recharge.login.UpdatePasswordActivity;
import com.ant.recharge.main.NetAccountInterface;
import com.ant.recharge.member.MemberActivity;
import com.ant.recharge.verified.VerifiedActivity;
import com.ant.recharge.verified.VerifiedUtil;
import com.ant.recharge.webview.WebviewZjActivity;

import java.util.List;
import java.util.logging.Logger;

import retrofit.client.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * 账户设置
 *
 *  1）实名认真
 *  2）更改密码
 *  3）支付账户签约
 *  4）邀请人员列表
 *  5）我的邀请码
 *
 * Created by kwc on 2016/9/2.
 */
public class AccountInfoActivity extends BaseActivity {
    Logger logger = Logger.getLogger(AccountInfoActivity.class.getSimpleName());

    //二维码
    private WebView scanIV;

    private TextView mycodeTV;
    private MyAccountDetail myAccountDetail;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView(this, R.layout.activity_setting);

        //设置标题
        ((TextView)findViewById(R.id.text_title)).setText(R.string.account_title);

        scanIV = (WebView) findViewById(R.id.setting_erweima);
        mycodeTV = ((TextView)findViewById(R.id.account_my_code));
        String userStr = readPreferences(NetLoginInterface.ANT_LOGIN_USER, "additional");
        try {
            if(!StringUtils.isBlank(userStr)){
                user = JsonUtil.decode(userStr, User.class);
                mycodeTV.setText(user.getInvite());
            }

            String accountInfoStr = readPreferences(NetAccountInterface.ANT_ACCOUNT, user.getLoginName());
            myAccountDetail = JsonUtil.decode(accountInfoStr, MyAccountDetail.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if("yq".equals(myAccountDetail.getIsRealNameVerify())){
            ((TextView)findViewById(R.id.account_pay_icon)).setText("已签");
        } else {
            findViewById(R.id.account_pay_layout).setOnClickListener(this);
        }

        loadCode();

    }

    /**
     * 加载二维码
     */
    private void loadCode(){
        NetInterface netInterface = new NRestAdapter<NetInterface>(this,
                Profile.SERVER_ADDRESS, NetInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(this,"网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }

        netInterface.getMessageNumber(user.getLoginName(), new NCallbackMsg() {
            @Override
            public void onSuccess(int statusCode, List<Header> headers, String result) {

                if (!StringUtils.isBlank(result) ){
                    try {
                        String url = Profile.SERVER_ADDRESS + "/common/img/inviteCode.png?v=" + result;
                        scanIV.setBackgroundColor(2);//透明
                        String content = "<div style=\"text-align:center; width:100%\"><img style=\"display:block margin:0 auto\" src=" + url + " /></div>";
                        scanIV.loadDataWithBaseURL(null, content,"text/html","utf-8", null);
//                        scanIV.setImageBitmap(BitmapFactory.decodeStream(new URL(url).openStream()));
                    } catch (Exception e) {
//                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
//                messageIV.setImageResource(R.drawable.emaili);
            }
        });
    }


    private interface NetInterface {
        //获取二维码url
        @POST("/api/wechat/user/getInviteCode")
        public void getMessageNumber(@Query("memberId") String memberId,
                                     NCallbackMsg callback);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.account_erified:
                //认证
                VerifiedUtil.startVerifiedActivity(this, user.getLoginName());
                finish();
                return;
            case R.id.account_changepassword:
                //修改密码
                startActivity(new Intent(this, UpdatePasswordActivity.class));
                return;
            case R.id.account_pay_layout:

                //是否认证
                if(!VerifiedUtil.isVerified(this, user.getLoginName())){
                    startActivity(new Intent(this, VerifiedActivity.class));
                    finish();
                    return;
                }

                if("yes".equals(myAccountDetail.getIsRealNameVerify())){
                    //签约
                    try {

                        Intent intent = new Intent(this, WebviewZjActivity.class);
                        StringBuilder sb = new StringBuilder();
                        sb.append("agreementType=20&paymentaccountnumber=");
                        //参数: message  signature
                        sb.append(myAccountDetail.getIpsAccount());
                        intent.putExtra(WebviewZjActivity.ZJ_TITLE, "移动用户签约");
                        intent.putExtra(WebviewZjActivity.ZJ_FROM, "cp/member/appQianYue");
                        intent.putExtra(WebviewZjActivity.ZJ_CONTENT, sb.toString().getBytes());
                        startActivity(intent);
                        finish();
                    } catch (Exception e) {
//            e.printStackTrace();
                    }
                } else {
                    startActivity(new Intent(this, VerifiedActivity.class));
                    finish();
                    return;
                }

                return;
            case R.id.account_member:
                //邀请人员列表
                startActivity(new Intent(this, MemberActivity.class));
                return;

            default:
                break;
        }
        super.onClick(v);
    }
}
