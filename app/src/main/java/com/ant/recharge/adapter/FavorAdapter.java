package com.ant.recharge.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.ant.recharge.R;
import com.ant.recharge.common.BaseListAdapter;
import com.ant.recharge.common.FontHelper;
import com.ant.recharge.common.StringUtils;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.entity.TelephoneEntity;
import com.ant.recharge.entity.User;
import com.ant.recharge.fragment1.FinancialDetailViewActivity;
import com.ant.recharge.login.NetLoginInterface;
import com.ant.recharge.order.DelegateOrderChangeActivity;
import com.ant.recharge.order.OrderActivity;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by kwc on 2016/9/11.
 */
public class FavorAdapter extends BaseListAdapter<TelephoneEntity> {


    public FavorAdapter(Context context, List<TelephoneEntity> list) {
        super(context, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder = null;
        if(view == null){
            view = getmInflater().inflate(R.layout.activity_favor_item, null);
            FontHelper.applyFont(getContext(), view);

            holder = new Holder();
            holder.titleTV = (TextView) view.findViewById(R.id.favor_item_title);
            holder.priceTV = (TextView) view.findViewById(R.id.favor_value_normalprize);
            holder.lowTV = (TextView) view.findViewById(R.id.favor_value_lowprize);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        final TelephoneEntity entity = (TelephoneEntity) getItem(i);
        holder.titleTV.setText(entity.getTelephoneNo());
        holder.priceTV.setText("一口价:" + entity.getPriceOriginal() + "元");
        holder.lowTV.setText("优惠价:" + entity.getPriceAgent() + "元");

        SharedPreferences preferences = getContext().getSharedPreferences(NetLoginInterface.ANT_LOGIN_USER, MODE_PRIVATE);
        String userStr = preferences.getString("additional", null);
        try {
            if (!StringUtils.isBlank(userStr)){
                User user = JsonUtil.decode(userStr, User.class);
                if (user.getUserType().equals("0") || user.getUserType().equals("1")){

                }
                else {
                    holder.lowTV.setVisibility(View.GONE);
                }
            }
            else {
                holder.lowTV.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //查看详情
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DelegateOrderChangeActivity.class);
                intent.putExtra("stepPage", 0);
                intent.putExtra("telephone", entity);
                getContext().startActivity(intent);
            }
        });

        return view;
    }

    private class Holder{
        private TextView titleTV;
        private TextView priceTV;
        private TextView lowTV;
    }
}
