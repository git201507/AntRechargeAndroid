package com.ant.recharge.verified;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.common.BaseActivity;
import com.ant.recharge.common.StringUtils;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.entity.User;
import com.ant.recharge.login.NetLoginInterface;
import com.ant.recharge.main.MainActivity;
import com.ant.recharge.webview.WebviewZjActivity;

/**
 * Created by User on 2016/8/30.
 */
public class VerifiedActivity extends BaseActivity {

    private EditText nameET;
    private EditText idcardET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_verified);
        initView(this, R.layout.activity_verified);

        //设置标题
        ((TextView)findViewById(R.id.text_title)).setText(R.string.verified_title);

        nameET = (EditText)findViewById(R.id.v_nmae);
        idcardET = (EditText)findViewById(R.id.v_idcard);


        //注册过来的，进入认证
        if(getIntent().getBooleanExtra("fromRegist", false)){
            findViewById(R.id.btn_back).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.verified_btn:
                //认证
                verified();
                return;
            case R.id.regist_protocal:
                //先去逛逛再说
                Intent mainIntent = new Intent(VerifiedActivity.this, MainActivity.class);
                startActivity(mainIntent);
                return;
            default:
                break;
        }
        super.onClick(v);
    }

    //认证
    public void verified(){

        String name = nameET.getText().toString();

        if(StringUtils.isBlank(name)){
            Toast.makeText(this, R.string.verified_hint_name, Toast.LENGTH_SHORT).show();
            return;
        }

        String idcard = idcardET.getText().toString();
        if(StringUtils.isBlank(idcard)){
            Toast.makeText(this, R.string.verified_hint_no, Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            String userStr = readPreferences(NetLoginInterface.ANT_LOGIN_USER, "additional");
            User user = JsonUtil.decode(userStr, User.class);

            StringBuilder sb = new StringBuilder();
            sb.append("pRealName=");
            sb.append(name);

            sb.append("&pMobileNo=");
            sb.append(user.getTelephone());//accountinfo中没有

            sb.append("&pIdentNo=");
            sb.append(idcard);

            sb.append("&usertype=");
            sb.append("11");


            Intent intent = new Intent(this, WebviewZjActivity.class);
            intent.putExtra(WebviewZjActivity.ZJ_TITLE, "实名认证");
            intent.putExtra(WebviewZjActivity.ZJ_FROM, "/cp/member/appRenZheng");
            intent.putExtra(WebviewZjActivity.ZJ_CONTENT, sb.toString().getBytes());
            startActivity(intent);
            finish();
        } catch (Exception e) {

        } finally {

        }


    }
}
