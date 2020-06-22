package com.ant.recharge.order;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.ant.recharge.R;
import com.ant.recharge.adapter.OrderAdapter;
import com.ant.recharge.common.AlertDialog;
import com.ant.recharge.common.BaseFragment;
import com.ant.recharge.common.FontHelper;
import com.ant.recharge.common.NCallback;
import com.ant.recharge.common.NCallbackMsg;
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.StringUtils;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.entity.MyAccountDetail;
import com.ant.recharge.entity.OrderEntity;
import com.ant.recharge.entity.TelephoneEntity;
import com.ant.recharge.entity.User;
import com.ant.recharge.login.NetLoginInterface;
import com.ant.recharge.paymax.PaymaxActivity;
import com.ant.recharge.webview.WebviewYLActivity;

import java.util.List;

import retrofit.client.Header;
import retrofit.http.POST;
import retrofit.http.Query;

import static android.content.Context.MODE_PRIVATE;

/**
 * 订单支付
 */
@SuppressLint("ValidFragment")
public class FragmentWaitPay extends BaseFragment {
    private TextView titleTV;
    private CheckBox checkBox;
    private LinearLayout addressLayout;
    private AutoCompleteTextView deliverNameEV;
    private AutoCompleteTextView deliverTelEV;
    private AutoCompleteTextView deliverAddressEV;
    private Context context;
    private MyAccountDetail myAccountDetail;
    private User user;

    public FragmentWaitPay() {
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
        View view = inflater.inflate(R.layout.fragment_wait_pay, container, false);
        FontHelper.applyFont(context, view);

        view.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.setResult(200, new Intent());
                activity.finish();
            }
        });
        titleTV = (TextView)view.findViewById(R.id.text_title);

        /////
        TelephoneEntity telephoneEntity = ((DelegateOrderChangeActivity)activity).getTelephone();
        OrderEntity order = ((DelegateOrderChangeActivity) activity).getOrder();

        TextView telTV = (TextView) view.findViewById(R.id.name_wait_pay);
        TextView orgPriceTV = (TextView) view.findViewById(R.id.wait_pay_normalprize);
        TextView agentPriceTV = (TextView) view.findViewById(R.id.wait_pay_lowprize);
        TextView areaTV = (TextView) view.findViewById(R.id.area_text_wait);
        TextView companyTV = (TextView) view.findViewById(R.id.runner_text_wait);
        TextView detailTV = (TextView) view.findViewById(R.id.detail_text_wait);

        if(telephoneEntity != null)
        {
            titleTV.setText("购买详情");
            telTV.setText(telephoneEntity.getTelephoneNo()+"("+telephoneEntity.getCity()+")"+", "+telephoneEntity.getNetworkName()+", "+telephoneEntity.getCompanyName() + ", "+telephoneEntity.getTariff());
            orgPriceTV.setText("" + telephoneEntity.getPriceOriginal() + "元");
            agentPriceTV.setText("" + telephoneEntity.getPriceAgent() + "元");
            areaTV.setText(telephoneEntity.getCity());
            companyTV.setText(telephoneEntity.getCompanyName());
            detailTV.setText(telephoneEntity.getTariff());
        }
        else {
            titleTV.setText("订单详情");
            telTV.setText("订单号："+order.getOrderNo());
            orgPriceTV.setText("" + order.getOrderPrice() + "元");
            agentPriceTV.setText("" + order.getOrderPrice() + "元");
            areaTV.setText(order.getArea());
            companyTV.setText(order.getCompany());
            detailTV.setText(order.getOrderDescription());
        }
//        TextView telNumberTV = (TextView)view.findViewById(R.id.name_wait_pay);
//        String titleStr =((DelegateOrderChangeActivity)activity).getOrder().getOrderTitle();
//        telNumberTV.setText(titleStr);

        Button buyBtn = (Button)view.findViewById(R.id.buy_btn_wait);

        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getContext().getSharedPreferences(NetLoginInterface.ANT_LOGIN_USER, MODE_PRIVATE);
                String userStr = preferences.getString("additional", null);
                try {
                    if (!StringUtils.isBlank(userStr)){
                        User user = JsonUtil.decode(userStr, User.class);
                        if (!StringUtils.isBlank(user.getToken()) && user.getRoleId().equals("4")){
                            Toast.makeText(context, "销售人员不能购买", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


//                ((DelegateOrderChangeActivity)activity).loadStepPage(1);
                OrderEntity order = ((DelegateOrderChangeActivity)activity).getOrder();

                if(order == null)
                {
                    if (checkBox.isChecked()) {
                        if(deliverNameEV.getText().length() == 0){
                            Toast.makeText(getContext(),"请输入姓名", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(deliverTelEV.getText().length() == 0){
                            Toast.makeText(getContext(),"请输入电话", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(deliverAddressEV.getText().length() == 0){
                            Toast.makeText(getContext(),"请输入地址", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    doBuy();
                }
                else {
                    Intent intent = new Intent(context, PaymaxActivity.class);
                    intent.putExtra("order", order);
                    startActivityForResult(intent, 1);
                }
            }
        });

        addressLayout= (LinearLayout) view.findViewById(R.id.address_info);
        deliverNameEV = (AutoCompleteTextView) view.findViewById(R.id.name_text_wait);
        deliverTelEV = (AutoCompleteTextView) view.findViewById(R.id.tel_text_wait);
        deliverAddressEV = (AutoCompleteTextView) view.findViewById(R.id.address_text_wait);

        checkBox = (CheckBox)view.findViewById(R.id.checkBox);

        if(order != null)
        {
            deliverNameEV.setText(order.getDeliver_receiverName());
            deliverTelEV.setText(order.getDeliver_receiverTel());
            deliverAddressEV.setText(order.getDeliver_receiverAddress());
         }

        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) addressLayout.getLayoutParams(); //取控件当前的布局参数

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) addressLayout.getLayoutParams(); //取控件当前的布局参数

                if(checkBox.isChecked())
                {
                    linearParams.height = 390;// 控件的高强制
                }
                else {
                    linearParams.height = 0;// 控件的高强制设成0
                }
                addressLayout.setLayoutParams(linearParams);
            }
        });
//        checkBox.setVisibility(View.INVISIBLE);
        return view;

    }

    private interface NetInterface{
        //头部信息
        @POST("/doBuy.do")
        public void doBuy(@Query("token") String token,
                          @Query("telephoneId") String telephoneId,
                          @Query("idDeliver") String idDeliver,
                          @Query("receiverAddress") String receiverAddress,
                          @Query("receiverName") String receiverName,
                          @Query("receiverTel") String receiverTel,
                          NCallback<OrderEntity> callback);
    }

    /**
     * 提交订单
     * @return
     */
    public void doBuy(){
        FragmentWaitPay.NetInterface netInterface = new NRestAdapter<FragmentWaitPay.NetInterface>(getContext(),
                Profile.SERVER_ADDRESS_DEV, FragmentWaitPay.NetInterface.class)
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
                    TelephoneEntity telephoneEntity = ((DelegateOrderChangeActivity)activity).getTelephone();
                    netInterface.doBuy(user.getToken(), telephoneEntity.getId(), checkBox.isChecked()?"1":"0" , checkBox.isChecked()?deliverAddressEV.getText().toString():"", checkBox.isChecked()?deliverNameEV.getText().toString():"", checkBox.isChecked()?deliverTelEV.getText().toString():"",
                            new NCallback<OrderEntity>(getContext(), OrderEntity.class) {
                                @Override
                                public void onSuccess(int statusCode, List<Header> headers, OrderEntity order) {
                                    ((DelegateOrderChangeActivity)activity).setOrder(order);

                                    Intent intent = new Intent(context, PaymaxActivity.class);
                                    intent.putExtra("order", order);
                                    startActivityForResult(intent, 1);
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

    @Override
    public void onResume() {
        super.onResume();
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);  //这个super可不能落下，否则可能回调不了

        if(1 == requestCode){
            if(200 == resultCode){
                setOrderStatus();
            }
            if(100 == resultCode){
                //activity.finish();
            }
        }
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

                    netInterface.setOrderStatus(user.getToken(), order.getId(), "pay",null,null,null,
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
                                    if( order.getIsDeliver()!= null && order.getIsDeliver().length() > 0){
                                        //选择邮寄方式时，跳过写卡
                                        ((DelegateOrderChangeActivity)activity).loadStepPage(2);
                                    }
                                    else {
                                        //付款后写卡
                                        ((DelegateOrderChangeActivity)activity).loadStepPage(1);
                                    }
//                                    if(checkBox.isChecked() && addressEV.getText().length() > 0){
//                                        //选择邮寄方式时，跳过写卡
//                                        ((DelegateOrderChangeActivity)activity).loadStepPage(2);
//                                    }
//                                    else {
//                                        ((DelegateOrderChangeActivity)activity).loadStepPage(1);
//                                    }
//                                    Intent intent = new Intent(getContext(), DelegateOrderChangeActivity.class);
//                                    intent.putExtra("order", order);
//                                    intent.putExtra("stepPage", Integer.parseInt(order.getAudit().getOrderStatus()));
//                                    getContext().startActivity(intent);
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
