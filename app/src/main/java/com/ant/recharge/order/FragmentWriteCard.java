package com.ant.recharge.order;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.adapter.OrderAdapter;
import com.ant.recharge.common.AlertDialog;
import com.ant.recharge.common.BaseFragment;
import com.ant.recharge.common.FontHelper;
import com.ant.recharge.common.NCallback;
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.StringUtils;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.entity.OrderEntity;
import com.ant.recharge.entity.TelephoneEntity;
import com.ant.recharge.entity.User;
import com.ant.recharge.login.NetLoginInterface;
import com.ant.recharge.webview.WebviewYLActivity;

import java.util.List;

import retrofit.client.Header;

import static android.content.Context.MODE_PRIVATE;

/**
 * 写卡
 */
@SuppressLint("ValidFragment")
public class FragmentWriteCard extends BaseFragment {

    private AlertDialog dialog;
    private Context context;
    private User user;

    public FragmentWriteCard() {
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
        View view = inflater.inflate(R.layout.fragment_write_card, container, false);
        FontHelper.applyFont(context, view);

        view.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });

        TextView titleTV = (TextView)view.findViewById(R.id.text_title);
        titleTV.setText(R.string.write_cart_title);

        Button buyBtn = (Button)view.findViewById(R.id.write_card_btn);

        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new AlertDialog(context)
                        .setCustomMessage(context.getString(R.string.write_cart_alert))
                        .setPositiveButton(context.getString(R.string.action_confirm), new View.OnClickListener() {
                            @Override
                            public void onClick(View paramView) {
                                dialog.dismiss();
                                setOrderStatus();
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

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 修改订单状态
     * @return
     */
    public void setOrderStatus(){
        DelegateOrderChangeActivity.NetInterface netInterface = new NRestAdapter<DelegateOrderChangeActivity.NetInterface>(getContext(),
                Profile.SERVER_ADDRESS_DEV, DelegateOrderChangeActivity.NetInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(getContext(),"网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences preferences = getContext().getSharedPreferences(NetLoginInterface.ANT_LOGIN_USER, MODE_PRIVATE);
        String userStr = preferences.getString("additional", null);
        try {
            if (!StringUtils.isBlank(userStr)){
                User user = JsonUtil.decode(userStr, User.class);
                if (!StringUtils.isBlank(user.getToken())){
                    OrderEntity order = ((DelegateOrderChangeActivity)activity).getOrder();

                    netInterface.setOrderStatus(user.getToken(), order.getId(), "write",null,null,null,
                            new NCallback<OrderEntity>(getContext(), OrderEntity.class) {
                                @Override
                                public void onSuccess(int statusCode, List<Header> headers, OrderEntity order) {
                                    orderDetail(order.getId());
                                }

                                @Override
                                public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                                    Toast.makeText(activity, infoMessage, Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 订单详情
     * @param orderId
     * @return
     */
    public void orderDetail(final String orderId){
        OrderAdapter.NetInterface netInterface = new NRestAdapter<OrderAdapter.NetInterface>(getContext(),
                Profile.SERVER_ADDRESS_DEV, OrderAdapter.NetInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(getContext(),"网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences preferences = getContext().getSharedPreferences(NetLoginInterface.ANT_LOGIN_USER, MODE_PRIVATE);
        String userStr = preferences.getString("additional", null);
        try {
            if (!StringUtils.isBlank(userStr)){
                User user = JsonUtil.decode(userStr, User.class);
                if (!StringUtils.isBlank(user.getToken())){
                    netInterface.getOrderDetailById(user.getToken(), orderId,
                            new NCallback<OrderEntity>(getContext(), OrderEntity.class) {
                                @Override
                                public void onSuccess(int statusCode, List<retrofit.client.Header> headers, OrderEntity order) {
                                    ((DelegateOrderChangeActivity)activity).setOrder(order);

                                    ((DelegateOrderChangeActivity)activity).loadStepPage(2);
                                }

                                @Override
                                public void onFailure(int statusCode, List<retrofit.client.Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                                    Toast.makeText(activity, infoMessage, Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
