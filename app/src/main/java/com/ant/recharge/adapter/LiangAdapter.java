package com.ant.recharge.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.common.BaseListAdapter;
import com.ant.recharge.common.NCallbackMsg;
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.StringUtils;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.entity.FinanceProductEntity;
import com.ant.recharge.entity.TelephoneEntity;
import com.ant.recharge.entity.User;
import com.ant.recharge.fragment1.FinancialDetailViewActivity;
import com.ant.recharge.login.LoginActivity;
import com.ant.recharge.login.NetLoginInterface;
import com.ant.recharge.order.DelegateOrderChangeActivity;

import java.util.List;

import retrofit.client.Header;

import static android.content.Context.MODE_PRIVATE;

/**
 * 靓号
 * Created by wxy on 2017/8/16.
 */
public class LiangAdapter extends BaseListAdapter {

    public LiangAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final TelephoneEntity telephoneEntity = (TelephoneEntity) getItem(i);
        Holder holder = new Holder();

            view = getmInflater().inflate(R.layout.fragment_liang_item, null);
            holder.titleTV = (TextView) view.findViewById(R.id.liang_item_title);
            holder.normalPriceTV = (TextView) view.findViewById(R.id.liang_value_normalprize);
            holder.lowPriceTV = (TextView) view.findViewById(R.id.liang_value_lowprize);
            holder.buyLiang = (Button) view.findViewById(R.id.liang_btn_buy);
            holder.addFavorLiang = (Button) view.findViewById(R.id.liang_btn_addfavor);

            holder.titleTV.setText(telephoneEntity.getTelephoneNo()+"("+telephoneEntity.getCity()+")"+", "+telephoneEntity.getNetworkName()+", "+telephoneEntity.getCompanyName() + ", "+telephoneEntity.getTariff());
//            holder.percentTV.setText(new DecimalFormat("#.00").format(financeProductEntity.getProfit()==null?"0":financeProductEntity.getProfit()) + "%");//格式化小数点后2位
            holder.normalPriceTV.setText(telephoneEntity.getPriceOriginal()==null?"0元":telephoneEntity.getPriceOriginal() + "元");
//            holder.moneyTV.setText(financeProductEntity.getAmount() + "");
            holder.lowPriceTV.setText("优惠价:"+telephoneEntity.getPriceAgent() + "元");
            SharedPreferences preferences = getContext().getSharedPreferences(NetLoginInterface.ANT_LOGIN_USER, MODE_PRIVATE);
            String userStr = preferences.getString("additional", null);
            try {
                if (!StringUtils.isBlank(userStr)){
                    User user = JsonUtil.decode(userStr, User.class);
                    if (!StringUtils.isBlank(user.getToken())){

                    }
                    else {
                        holder.lowPriceTV.setVisibility(View.GONE);
                    }
                }
                else {
                    holder.lowPriceTV.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //查看详情
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                SharedPreferences preferences = getContext().getSharedPreferences(NetLoginInterface.ANT_LOGIN_USER, MODE_PRIVATE);
                String userStr = preferences.getString("additional", null);
                try {
                    if (!StringUtils.isBlank(userStr)){
                        User user = JsonUtil.decode(userStr, User.class);
                        if (!StringUtils.isBlank(user.getToken())){
                            Intent intent = new Intent(getContext(), DelegateOrderChangeActivity.class);
                            intent.putExtra("stepPage", 0);
                            intent.putExtra("telephone", telephoneEntity);
                            ((Activity)getContext()).startActivityForResult(intent, 1);
                        }
                        else {
                            Intent intent = new Intent();
                            intent.setClass((Activity)getContext(), LoginActivity.class);
                            ((Activity)getContext()).startActivity(intent);
                        }
                    }
                    else {
                        Intent intent = new Intent();
                        intent.setClass((Activity)getContext(), LoginActivity.class);
                        ((Activity)getContext()).startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                }
            });

            holder.addFavorLiang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences preferences = getContext().getSharedPreferences(NetLoginInterface.ANT_LOGIN_USER, MODE_PRIVATE);
                    String userStr = preferences.getString("additional", null);
                    try {
                        if (!StringUtils.isBlank(userStr)){
                            User user = JsonUtil.decode(userStr, User.class);
                            if (!StringUtils.isBlank(user.getToken())){
                                validateInvisitCode(telephoneEntity.getId());
                            }
                            else {
                                Intent intent = new Intent();
                                intent.setClass((Activity)getContext(), LoginActivity.class);
                                ((Activity)getContext()).startActivity(intent);
                            }
                        }
                        else {
                            Intent intent = new Intent();
                            intent.setClass((Activity)getContext(), LoginActivity.class);
                            ((Activity)getContext()).startActivity(intent);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        return view;
    }

    /**
     * 添加收藏
     * @param telephoneId
     */
    public void validateInvisitCode(final String telephoneId){

        RecommendAdapter.NeInterface netInterface = new NRestAdapter<RecommendAdapter.NeInterface>(getContext(),
                Profile.SERVER_ADDRESS_DEV, RecommendAdapter.NeInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(getContext(), "网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences preferences = getContext().getSharedPreferences(NetLoginInterface.ANT_LOGIN_USER, MODE_PRIVATE);
        String userStr = preferences.getString("additional", null);
        try {
            if (!StringUtils.isBlank(userStr)){
                User user = JsonUtil.decode(userStr, User.class);
                if (!StringUtils.isBlank(user.getToken())){
                    netInterface.setCollection(user.getToken(), telephoneId,
                    new NCallbackMsg() {
                        @Override
                        public void onSuccess(int statusCode, List<Header> headers, String result) {
                            Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                            Toast.makeText(getContext(), infoMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class Holder{
        TextView titleTV;
        TextView normalPriceTV;
        TextView lowPriceTV;
        Button buyLiang;
        Button addFavorLiang;
    }

}
