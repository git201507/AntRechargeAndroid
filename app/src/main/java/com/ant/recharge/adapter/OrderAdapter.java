package com.ant.recharge.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.common.BaseListAdapter;
import com.ant.recharge.common.FontHelper;
import com.ant.recharge.common.NCallback;
import com.ant.recharge.common.NCallbackMsg;
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.StringUtils;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.entity.CouponEntity;
import com.ant.recharge.entity.InvitorEntity;
import com.ant.recharge.entity.OrderEntity;
import com.ant.recharge.entity.User;
import com.ant.recharge.fragment1.FinancialDetailViewActivity;
import com.ant.recharge.login.NetCodeInterface;
import com.ant.recharge.login.NetLoginInterface;
import com.ant.recharge.order.DelegateOrderChangeActivity;
import com.ant.recharge.order.OrderActivity;

import org.codehaus.jackson.type.TypeReference;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by kwc on 2016/9/11.
 */
public class OrderAdapter extends BaseListAdapter<OrderEntity> {


    public OrderAdapter(Context context, List<OrderEntity> list) {
        super(context, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder = null;
        if(view == null){
            view = getmInflater().inflate(R.layout.activity_orders_item, null);
            FontHelper.applyFont(getContext(), view);

            holder = new Holder();
            holder.statusTV = (TextView) view.findViewById(R.id.order_tv_status);
            holder.titleTV = (TextView) view.findViewById(R.id.order_item_title);
            holder.dateTV = (TextView) view.findViewById(R.id.order_happen_time);
            holder.priceTV = (TextView) view.findViewById(R.id.order_name_prize);
            holder.lowTV = (TextView) view.findViewById(R.id.order_name_lowprize);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        final OrderEntity entity = (OrderEntity) getItem(i);
        final String stateStr = entity.getOrderStatusDetails();
        holder.statusTV.setText(stateStr);
        holder.titleTV.setText(entity.getOrderDescription());
//        holder.dateTV.setText("有效期至：" + entity.getShowCreateDate());
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        //String ctime = formatter.format(entity.getCreateDate());
        holder.dateTV.setText("生成时间：" + entity.getCreateDate());
        holder.priceTV.setText("交易价：" + entity.getOrderPrice() + "元");
        //holder.lowTV.setText("" + entity.getOrderPrice() + "元");

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SharedPreferences preferences = getContext().getSharedPreferences(NetLoginInterface.ANT_LOGIN_USER, MODE_PRIVATE);
//                String userStr = preferences.getString("additional", null);
//                try {
//                    if (!StringUtils.isBlank(userStr)){
//                        User user = JsonUtil.decode(userStr, User.class);
//                        if (user.getRoleId().equals("3")){
//                            orderDetail(entity.getId());
//                        }
//                        else if (user.getRoleId().equals("4")){
//                            switch (Integer.parseInt(entity.getOrderStatus()))
//                            {
//                                case 0:
//                                    break;
//                                case 1:
//                                    break;
//                                case 2:
//                                    break;
//                                case 3://审核中
//                                    orderDetail(entity.getId());
//                                    break;
//                                case 4://审核通过
//                                    break;
//                                case 5://已完成
//                                    break;
//                                case 6://退款中
//                                    break;
//                                default:
//                                    break;
//                            }
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        });
        //查看详情
        view.findViewById(R.id.order_btn_detail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getContext().getSharedPreferences(NetLoginInterface.ANT_LOGIN_USER, MODE_PRIVATE);
                String userStr = preferences.getString("additional", null);
                try {
                    if (!StringUtils.isBlank(userStr)){
                        User user = JsonUtil.decode(userStr, User.class);
                        if (user.getRoleId().equals("3")){
                            orderDetail(entity.getId());
                        }
                        else if (user.getRoleId().equals("4")){
                            switch (Integer.parseInt(entity.getOrderStatus()))
                            {
                                case 0:
                                    break;
                                case 1:
                                    break;
                                case 2:
                                    break;
                                case 3://审核中
                                    orderDetail(entity.getId());
                                    break;
                                case 4://审核通过
                                    break;
                                case 5://已完成
                                    break;
                                case 6://退款中
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    private class Holder{

        private TextView statusTV;
        private TextView titleTV;
        private TextView dateTV;
        private TextView priceTV;
        private TextView lowTV;
    }

    public interface NetInterface{
        //头部信息
        @POST("/getOrder.do")
        public void getOrderDetailById(@Query("token") String token,
                                       @Query("id") String id,
                                       NCallback<OrderEntity> callback);
    }

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    public void orderDetail(final String orderId){
        NetInterface netInterface = new NRestAdapter<NetInterface>(getContext(),
                Profile.SERVER_ADDRESS_DEV, NetInterface.class)
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

                            Intent intent = new Intent(getContext(), DelegateOrderChangeActivity.class);
                            intent.putExtra("order", order);
                            int statePage =   Integer.parseInt(order.getOrderStatus());
//                            int max=5;
//                            int min=0;
//                            Random random = new Random();
//
//                            int statePage = random.nextInt(max)%(max-min+1) + min;
//                            statePage = 3;
                            intent.putExtra("stepPage", statePage);

                            getContext().startActivity(intent);
                        }

                        @Override
                        public void onFailure(int statusCode, List<retrofit.client.Header> headers, String infoCode, String infoMessage, Throwable throwable) {

                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
