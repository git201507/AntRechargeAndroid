package com.ant.recharge.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.account.AccountInfoActivity;
import com.ant.recharge.common.AlertDialog;
import com.ant.recharge.common.BaseFragment;
import com.ant.recharge.common.FontHelper;
import com.ant.recharge.common.NCallback;
import com.ant.recharge.common.NCallbackMsg;
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.StringUtils;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.common.ui.MessageDialog;
import com.ant.recharge.coupon.CouponActivity;
import com.ant.recharge.entity.MyAccountDetail;
import com.ant.recharge.entity.User;
import com.ant.recharge.financial.FinancialActivity;
import com.ant.recharge.fragment2.memberlevel.MemberLevelBojinDialog;
import com.ant.recharge.fragment2.memberlevel.MemberLevelDialog;
import com.ant.recharge.fragment2.memberlevel.MemberLevelHongDialog;
import com.ant.recharge.fragment2.myshare.MyShareActivity;
import com.ant.recharge.fragment2.planner.PlannerOnClickListener;
import com.ant.recharge.login.LoginActivity;
import com.ant.recharge.login.NetLoginInterface;
import com.ant.recharge.manage.ManageActivity;
import com.ant.recharge.message.MessageActivity;
import com.ant.recharge.message.NetMessageInterface;
import com.ant.recharge.recharge.RechargeActivity;
import com.ant.recharge.salary.SalaryActivity;
import com.ant.recharge.verified.VerifiedActivity;
import com.ant.recharge.verified.VerifiedUtil;
import com.ant.recharge.webview.WebviewZjActivity;
import com.ant.recharge.withdraw.WithdrawActivity;

import java.util.List;

import retrofit.client.Header;

/**
 * 我的理财
 */
@SuppressLint("ValidFragment")
public class Fragment02 extends BaseFragment {

    private Context context;


    //vip图标
//    private ImageView vipIV;
    //会员级别图标
    private ImageView memberLevelIV;
    //理财师图标
    private ImageView plannerIV;


    //消息图片
    private ImageView messageIV;

    //总收益
    private TextView totalIncomeAmountTV;
    //总资产
    private TextView totalAmountTV;
    //
    private TextView freezeAmountTV;
    private TextView financingAmountTV;
    private TextView availableAmountTV;


    private TextView couponAmountTV;
    private TextView salaryAmountTV;

    private MyAccountDetail myAccountDetail;


    private User user;


    //抽奖判断标识
    private String prizeId;

    public Fragment02() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.context = getActivity();
// Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_2, container, false);
        FontHelper.applyFont(context, view);


//        vipIV  = (ImageView) view.findViewById(R.id.user_vip);
        memberLevelIV  = (ImageView) view.findViewById(R.id.user_memberLevel);
        plannerIV = (ImageView) view.findViewById(R.id.user_planner);
        totalIncomeAmountTV = (TextView) view.findViewById(R.id.totalIncomeAmount);
        totalAmountTV = (TextView) view.findViewById(R.id.totalAmount);
        freezeAmountTV  = (TextView) view.findViewById(R.id.user_freezeAmount);
        financingAmountTV  = (TextView) view.findViewById(R.id.user_financingAmount);
        availableAmountTV  = (TextView) view.findViewById(R.id.user_availableAmount);
        couponAmountTV = (TextView) view.findViewById(R.id.couponAmount);
        salaryAmountTV = (TextView) view.findViewById(R.id.salaryAmount);

        SharedPreferences preferences = context.getSharedPreferences(NetLoginInterface.ANT_LOGIN_USER, Context.MODE_PRIVATE);
        String userStr = preferences.getString("additional", null);
        try {
            user = JsonUtil.decode(userStr, User.class);
            TextView nameTV = (TextView)view.findViewById(R.id.user_name);
            nameTV.setText(StringUtils.isBlank(user.getName())?user.getLoginName():user.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }


        //vip皇冠
//        vipIV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                VipDialog dialog = new VipDialog(getActivity());
//                dialog.show();
//            }
//        });

        //拨打客服电话
        view.findViewById(R.id.f2_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageDialog dialog = new MessageDialog(context);
                dialog.show();
            }
        });

        //我的账户
        view.findViewById(R.id.f2_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, AccountInfoActivity.class));
            }
        });

        //消息
        messageIV = (ImageView) view.findViewById(R.id.f2_message_iv);
        view.findViewById(R.id.f2_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MessageActivity.class));
            }
        });

        //充值
        view.findViewById(R.id.my_recharge).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //是否认证
                if(!VerifiedUtil.isVerified(getActivity(), user.getLoginName())){
                    startActivity(new Intent(getActivity(), VerifiedActivity.class));
                    return;
                }

                Intent rechargeIntent = new Intent(getActivity(), RechargeActivity.class);
                rechargeIntent.putExtra("userId", user.getLoginName());
                startActivity(rechargeIntent);
            }
        });

        //提现
        view.findViewById(R.id.my_withdraw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myAccountDetail == null){
                    return;
                }

                //是否认证
                if(!VerifiedUtil.isVerified(getActivity(), user.getLoginName())){
                    startActivity(new Intent(getActivity(), VerifiedActivity.class));
                    return;
                }

                Intent withdrawActivityIntent = new Intent(getActivity(), WithdrawActivity.class);
                withdrawActivityIntent.putExtra("userId", user.getLoginName());
                startActivity(withdrawActivityIntent);
            }
        });

        //优惠券
        view.findViewById(R.id.f2_coupon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CouponActivity.class));
            }
        });

        //蚂蚁工资
        view.findViewById(R.id.f2_salary).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SalaryActivity.class));
            }
        });

        //我的分享
        view.findViewById(R.id.f2_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MyShareActivity.class));
            }
        });

        //理财记录
        view.findViewById(R.id.f2_manage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ManageActivity.class));
            }
        });

        //资金记录
        view.findViewById(R.id.f2_financial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), FinancialActivity.class));
            }
        });

        //退出登录
        view.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitLogin();
            }
        });

        return view;

    }

    //退出登录
    private void exitLogin() {
        SharedPreferences preferences = context.getSharedPreferences(NetLoginInterface.ANT_LOGIN_USER, Context.MODE_PRIVATE);
        String userStr = preferences.getString("additional", null);
        User user = null;
        try {
            user = JsonUtil.decode(userStr, User.class);
            user.setToken("");
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("additional", JsonUtil.encode(user));
            editor.commit();

            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //加载账户信息
        loadAccount();

    }


    private Boolean isLoading = false;
    /**
     * 加载账户信息
     */
    private void loadAccount() {

        if(isLoading){
            //防止重复提交
            return;
        }

        isLoading = true;
        NetAccountInterface netInterface = new NRestAdapter<NetAccountInterface>(getActivity(),
                Profile.SERVER_ADDRESS, NetAccountInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(getActivity(),"网络正在开小差!", Toast.LENGTH_SHORT).show();
            isLoading = false;
            return;
        }
        netInterface.findAccountInfo(user.getToken(), new NCallback<MyAccountDetail>(getActivity(), MyAccountDetail.class) {
            @Override
            public void onSuccess(int statusCode, List<Header> headers, MyAccountDetail myAccountDetail) {
                isLoading = false;
                Fragment02.this.myAccountDetail = myAccountDetail;
                try {
                    SharedPreferences preferences = getContext().getSharedPreferences(NetAccountInterface.ANT_ACCOUNT, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(user.getLoginName(), JsonUtil.encode(myAccountDetail));
                    editor.commit();

                } catch (Exception e) {
//                    e.printStackTrace();
                }

                totalIncomeAmountTV.setText("" + myAccountDetail.getTotalIncomeAmount());
                totalAmountTV.setText("" + myAccountDetail.getTotalAmount());
                freezeAmountTV.setText("" + myAccountDetail.getFreezeAmount());
                financingAmountTV.setText("" + myAccountDetail.getFinancingAmount());
                availableAmountTV.setText("" + myAccountDetail.getAvailableAmount());
                if(myAccountDetail.getCouponNumber() != null && myAccountDetail.getCouponNumber().intValue() > 0){
                    couponAmountTV.setText("(" + myAccountDetail.getCouponNumber() + ")");
                }
//                if(myAccountDetail.getSalaryAmount() != null && myAccountDetail.getSalaryAmount().intValue() > 0){
//                    salaryAmountTV.setText("(" + myAccountDetail.getSalaryAmount() + ")");
//                }

//                if(1== myAccountDetail.getVip()){
//                    vipIV.setImageResource(R.drawable.icon_vip);
//                    vipIV.setVisibility(View.VISIBLE);
//                } else {
//                    vipIV.setVisibility(View.GONE);
//                }
//                vipIV.setImageResource(1== myAccountDetail.getVip()?R.drawable.icon_vip:R.drawable.icon_vip1);

                final  String currLevel = myAccountDetail.getMemberLevel();
                if (currLevel != null && !"level_4".equals(currLevel)) {
                    memberLevelIV.setImageResource(getMemberLevelResource(myAccountDetail.getMemberLevel()));
                    ViewGroup.LayoutParams params = memberLevelIV.getLayoutParams();
                    params.height=40;
                    params.width =40;
                    memberLevelIV.setLayoutParams(params);
                    //钻石
                    memberLevelIV.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            switch (currLevel) {
                                case "level_3"://铂金
                                    MemberLevelBojinDialog dialogbojin = new MemberLevelBojinDialog(getActivity());
                                    dialogbojin.show();
                                    break;
                                case "level_2"://红钻
                                    MemberLevelHongDialog dialogHong = new MemberLevelHongDialog(getActivity());
                                    dialogHong.show();
                                    break;
                                case "level_1"://黑钻
                                    MemberLevelDialog dialoghei = new MemberLevelDialog(getActivity());
                                    dialoghei.show();
                                    break;
                            }
                        }
                    });
                } else {
                    ViewGroup.LayoutParams params = memberLevelIV.getLayoutParams();
                    params.height=1;
                    params.width =1;
                    memberLevelIV.setLayoutParams(params);
                }

                //理财师
                Boolean isFinancial = MyAccountDetail.financial_2.equals(myAccountDetail.getFinancial());
                plannerIV.setOnClickListener(new PlannerOnClickListener(getActivity(), isFinancial));
                plannerIV.setImageResource(isFinancial ? R.drawable.icon_financia :R.drawable.icon_financia1);

                //加载账户信息后，加载信息是否存在
                loadMessageCount();
            }

            @Override
            public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                isLoading = false;
                //登录过期
//                if(infoMessage.contains("当前用户登录状态异常")){
//                    exitLogin();
//                } else {
//                    Toast.makeText(getActivity(), infoMessage, Toast.LENGTH_SHORT).show();
//                }
                exitLogin();
            }

        });
    }

    /**
     * 加载消息个数
     */
    private void loadMessageCount(){
        NetMessageInterface netInterface = new NRestAdapter<NetMessageInterface>(getActivity(),
                Profile.SERVER_ADDRESS, NetMessageInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(getActivity(),"网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }

        netInterface.getMessageNumber(user.getLoginName(), new NCallbackMsg() {
            @Override
            public void onSuccess(int statusCode, List<Header> headers, String result) {

                if(StringUtils.isBlank(result)){
                    return;
                }

                //下标0 未读消息数  1 普通买标数 2 vip买标数
                String[] counts = result.split("-");
                if(counts.length < 3){
                    return;
                }

                if (Integer.parseInt(counts[0]) >  0){
                    messageIV.setImageResource(R.drawable.emaili_1);
                } else {
                    messageIV.setImageResource(R.drawable.emaili);
                }

                //抽奖
                if (Integer.parseInt(counts[2]) >  0){
                    appGetPrizeId();
                } else if(Integer.parseInt(counts[1]) >  0 ){
                    lottery(1);
                }


            }

            @Override
            public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                messageIV.setImageResource(R.drawable.emaili);
            }
        });
    }

    /**
     * 抽奖
     * @param type
     */
    private void lottery(final int type) {


    }


    private int getMemberLevelResource(String level){
        switch (level){
            case "level_4"://普通
                return R.drawable.icon_putong;
            case "level_3"://铂金
                return R.drawable.icon_baizuan;
            case "level_2"://红钻
                return R.drawable.icon_hongzuan;
            case "level_1"://黑钻
                return R.drawable.icon_heizuan;
            case "level_0":
                return R.drawable.icon_putong;
        }
        return R.drawable.icon_putong;
    }

    @Override
    public void update(int type, OnFragmentListener fragmentListener, Object... arguments) {
        switch (type) {
            case type_count:
                loadAccount();
                break;
            case type_list:
//                final int page = Integer.parseInt(arguments[0].toString());
//                final int currentType = Integer.parseInt(arguments[1].toString());
//
//                switch (currentType) {
//                    case 1:
//                        onTab1Checked();
//                        break;
//                    case 2:
//                        onTab2Checked();
//                        break;
//                    case 3:
//                        onTab3Checked();
//                        break;
//                }
//
//                subjectAdapter.replaceList(new ArrayList<FinanceProductEntity>());
//                queryList(page, currentType, fragmentListener);
                break;
            default:
                break;
        }

        return;
    }





    //如果有优选标抽奖次数，调用接口
    private void appGetPrizeId(){
        NetAccountInterface netInterface = new NRestAdapter<NetAccountInterface>(getActivity(),
                Profile.SERVER_ADDRESS, NetAccountInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(getActivity(),"网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }
        netInterface.appGetPrizeId(user.getLoginName(), new NCallbackMsg() {
            @Override
            public void onSuccess(int statusCode, List<Header> headers, final String prizeId) {
                Fragment02.this.prizeId = prizeId;
                if(!StringUtils.isBlank(prizeId)){
                    lottery(2);
                }
            }

            @Override
            public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {

            }
        });
    }

}
