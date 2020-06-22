package com.ant.recharge.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ant.recharge.R;
import com.ant.recharge.common.BaseListAdapter;
import com.ant.recharge.common.FontHelper;
import com.ant.recharge.common.StringUtils;
import com.ant.recharge.common.ui.RoundProgressView;
import com.ant.recharge.entity.FinanceProductEntity;
import com.ant.recharge.fragment1.FinancialBuyActivity;
import com.ant.recharge.fragment1.FinancialDetailViewActivity;
import com.ant.recharge.fragment1.FinancialVIPDetailActivity;

import java.util.Date;
import java.util.List;

/**
 * 标
 * Created by kwc on 2016/8/16.
 */
public class SubjectAdapter extends BaseListAdapter {

    private String userId;

    public SubjectAdapter(Context context, List list, String userId) {
        super(context, list);
        this.userId = userId;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final FinanceProductEntity financeProductEntity = (FinanceProductEntity) getItem(i);
        Holder holder = new Holder();
        if(financeProductEntity.getVip()){
            view = getmInflater().inflate(R.layout.fragment_1_item2, null);
            holder.vipHolder = new VIPHolder();
            holder.vipHolder.titleTV = (TextView) view.findViewById(R.id.vip_name);
            holder.vipHolder.percentTV = (TextView) view.findViewById(R.id.vip_year_income_value);
            holder.vipHolder.moneyTV = (TextView) view.findViewById(R.id.vip_total_value);
            holder.vipHolder.dayTV = (TextView) view.findViewById(R.id.vip_finance);
            holder.vipHolder.btnTV = (TextView) view.findViewById(R.id.vip_btn);

//            FontHelper.applyTextViewFont(getContext(), holder.vipHolder.titleTV);
//            FontHelper.applyTextViewFont(getContext(), holder.vipHolder.percentTV);
//            FontHelper.applyTextViewFont(getContext(), holder.vipHolder.moneyTV);
//            FontHelper.applyTextViewFont(getContext(), holder.vipHolder.dayTV);

            holder.vipHolder.titleTV.setText(financeProductEntity.getName());
//            holder.vipHolder.percentTV.setText(new DecimalFormat("#.00").format(financeProductEntity.getProfit()==null?"0":financeProductEntity.getProfit()) + "%");
            holder.vipHolder.percentTV.setText(financeProductEntity.getProfit()==null?"0":financeProductEntity.getProfit() + "%");
            holder.vipHolder.moneyTV.setText(financeProductEntity.getFinancing() + "元");
            StringBuilder sb = new StringBuilder();
            sb.append("理财期");
            int first_start = sb.length();
            sb.append(financeProductEntity.getFinanceDays());
            int first_end = sb.length();
            sb.append("天 到期收益");
            int second_start = sb.length();
            sb.append(financeProductEntity.getExpectIncome());
            int second_end = sb.length();
            sb.append("元");

            //            if(Double.parseDouble(financeProductEntity.getProgress()) >= 100){
            if("release".equals(financeProductEntity.getDraftState())){
                holder.vipHolder.btnTV.setText(R.string.vip_btn);
                holder.vipHolder.btnTV.setBackgroundResource(R.drawable.shape_rectangle_rounded_red);
            } else {
                holder.vipHolder.btnTV.setText("已售罄");
                holder.vipHolder.btnTV.setBackgroundResource(R.drawable.shape_rectangle_rounded_hintcolor);
            }


            SpannableStringBuilder builder = new SpannableStringBuilder(sb.toString());
            ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
            ForegroundColorSpan redSpan2 = new ForegroundColorSpan(Color.RED);
            builder.setSpan(redSpan, first_start, first_end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(redSpan2, second_start, second_end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.vipHolder.dayTV.setText(builder);
            holder.vipHolder.btnTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), FinancialVIPDetailActivity.class);
                    intent.putExtra("financeProductEntity", financeProductEntity);
                    getContext().startActivity(intent);
                }
            });
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });
        } else {
            view = getmInflater().inflate(R.layout.fragment_1_item, null);
            holder.roundProgressView = (RoundProgressView) view.findViewById(R.id.circle);
            holder.titleTV = (TextView) view.findViewById(R.id.f1_title);
            holder.percentTV = (TextView) view.findViewById(R.id.f1_percent);
            holder.moneyTV = (TextView) view.findViewById(R.id.f1_money);
            holder.dayTV = (TextView) view.findViewById(R.id.f1_day);
            holder.jiangIV = (ImageView) view.findViewById(R.id.f1_jiang);

//            FontHelper.applyTextViewFont(getContext(), holder.titleTV);
//            FontHelper.applyTextViewFont(getContext(), holder.percentTV);
//            FontHelper.applyTextViewFont(getContext(), holder.moneyTV);
//            FontHelper.applyTextViewFont(getContext(), holder.dayTV);


            holder.roundProgressView.setProgress(Float.parseFloat("" + financeProductEntity.getProgress()));
            holder.titleTV.setText(financeProductEntity.getName());
//            holder.percentTV.setText(new DecimalFormat("#.00").format(financeProductEntity.getProfit()==null?"0":financeProductEntity.getProfit()) + "%");//格式化小数点后2位
            holder.percentTV.setText(financeProductEntity.getProfit()==null?"0":financeProductEntity.getProfit() + "%");
//            holder.moneyTV.setText(financeProductEntity.getAmount() + "");
            holder.moneyTV.setText(financeProductEntity.getFinancing() + "元");
            holder.dayTV.setText("" + financeProductEntity.getFinanceDays() + "天");
            holder.jiangIV.setVisibility("1".equals(financeProductEntity.getRzCoupon()) ? View.VISIBLE:View.GONE);
            //查看详情
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), FinancialDetailViewActivity.class);
                    intent.putExtra("financeProductEntity", financeProductEntity);
                    getContext().startActivity(intent);
                }
            });

            //直接进入购买
            holder.roundProgressView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(!"release".equals(financeProductEntity.getDraftState())){
                        return;
                    }

                    if(!StringUtils.isBlank(financeProductEntity.getProgress()) && Double.parseDouble(financeProductEntity.getProgress()) >= 100 ){
                        return;
                    }

                    Intent intent = new Intent(getContext(), FinancialBuyActivity.class);
                    intent.putExtra("financeProductEntity", financeProductEntity);
                    getContext().startActivity(intent);
                }
            });
        }

//        FontHelper.applyFont(getContext(), view);
        return view;
    }

    private class Holder{
        RoundProgressView roundProgressView = null;
        TextView titleTV;
        TextView percentTV;
        TextView moneyTV;
        TextView dayTV;
        VIPHolder vipHolder = null;

        //有奖图标
        ImageView jiangIV;
    }

    private class VIPHolder{
        TextView titleTV;
        TextView percentTV;
        TextView moneyTV;
        TextView dayTV;
        TextView btnTV;
    }

}
