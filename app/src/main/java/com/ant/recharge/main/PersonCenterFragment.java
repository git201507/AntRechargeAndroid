package com.ant.recharge.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.account.AccountInfoActivity;
import com.ant.recharge.common.AlertDialog;
import com.ant.recharge.common.BaseFragment;
import com.ant.recharge.common.FontHelper;
import com.ant.recharge.common.NCallback;
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.entity.MyAccountDetail;
import com.ant.recharge.entity.User;
import com.ant.recharge.favor.FavorsActivity;
import com.ant.recharge.fragment2.myshare.MyShareActivity;
import com.ant.recharge.login.LoginActivity;
import com.ant.recharge.login.NetLoginInterface;
import com.ant.recharge.login.UpdatePasswordActivity;
import com.ant.recharge.manage.ManageActivity;
import com.ant.recharge.member.PersonInfoActivity;
import com.ant.recharge.order.OrderActivity;

import java.util.List;

import retrofit.client.Header;

/**
 * 我的理财
 */
@SuppressLint("ValidFragment")
public class PersonCenterFragment extends BaseFragment {

    private Context context;
    private AlertDialog dialog;

    //总收益
    private TextView totalIncomeAmountTV;
    //
    private TextView personNameTV;
    private TextView personTelTV;
    private TextView personSalerTV;

    private MyAccountDetail myAccountDetail;

    private User user;


    //抽奖判断标识
    private String prizeId;

    public PersonCenterFragment() {
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
        View view = inflater.inflate(R.layout.fragment_person_center, container, false);
        FontHelper.applyFont(context, view);


        personNameTV  = (TextView) view.findViewById(R.id.person_center_name);
        personTelTV = (TextView) view.findViewById(R.id.person_center_tel);
        personSalerTV = (TextView) view.findViewById(R.id.person_center_saler);
        totalIncomeAmountTV = (TextView) view.findViewById(R.id.person_center_amount);

        SharedPreferences preferences = context.getSharedPreferences(NetLoginInterface.ANT_LOGIN_USER, Context.MODE_PRIVATE);
        String userStr = preferences.getString("additional", null);
        try {
            user = JsonUtil.decode(userStr, User.class);
//            personNameTV.setText(StringUtils.isBlank(user.getName())?user.getTelephone():user.getName());
            personNameTV.setText(user.getName());
            personTelTV.setText(user.getLoginName());
            personSalerTV.setText(user.getInvite());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //我的账户
//        view.findViewById(R.id.f2_set).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(context, AccountInfoActivity.class));
//            }
//        });

        //我的订单
        view.findViewById(R.id.person_center_orders).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), OrderActivity.class));
            }
        });

        //我的收藏
        view.findViewById(R.id.person_center_favors).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), FavorsActivity.class));
            }
        });
        //个人信息管理
        view.findViewById(R.id.person_info_management).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), PersonInfoActivity.class), 1);
            }
        });
        //修改密码
        view.findViewById(R.id.update_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UpdatePasswordActivity.class));
            }
        });

        //退出登录
        view.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new AlertDialog(context)
                        .setCustomMessage(context.getString(R.string.logoff_alert))
                        .setPositiveButton(context.getString(R.string.action_confirm), new View.OnClickListener() {
                            @Override
                            public void onClick(View paramView) {
                                dialog.dismiss();

                                exitLogin();
                            }
                        })
                        .setNegativeButton(context.getString(R.string.action_cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View paramView) {
                                dialog.dismiss();
                            }
                        });

                dialog.setCancelAction(new AlertDialog.CancelAction() {

                    @Override
                    public void cancel() {

                    }
                });

                dialog.show();
            }
        });

        return view;

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);  //这个super可不能落下，否则可能回调不了

        if(1 == requestCode){
            if(200 == resultCode){

                SharedPreferences preferences = context.getSharedPreferences(NetLoginInterface.ANT_LOGIN_USER, Context.MODE_PRIVATE);
                String userStr = preferences.getString("additional", null);
                try {
                    user = JsonUtil.decode(userStr, User.class);
//            personNameTV.setText(StringUtils.isBlank(user.getName())?user.getTelephone():user.getName());
                    personNameTV.setText(user.getName());
                    personTelTV.setText(user.getLoginName());
                    personSalerTV.setText(user.getInvite());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
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

            ((MainTabActivity) getActivity()).loadPageRecommend();
            startActivity(new Intent(getActivity(), LoginActivity.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //加载账户信息
        //loadAccount();

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
                PersonCenterFragment.this.myAccountDetail = myAccountDetail;
                try {
                    SharedPreferences preferences = getContext().getSharedPreferences(NetAccountInterface.ANT_ACCOUNT, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(user.getLoginName(), JsonUtil.encode(myAccountDetail));
                    editor.commit();

                } catch (Exception e) {
//                    e.printStackTrace();
                }

                totalIncomeAmountTV.setText("" + myAccountDetail.getTotalIncomeAmount());

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
//                exitLogin();
            }

        });
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
}
