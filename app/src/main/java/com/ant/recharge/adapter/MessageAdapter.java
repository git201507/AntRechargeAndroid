package com.ant.recharge.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ant.recharge.R;
import com.ant.recharge.common.BaseListAdapter;
import com.ant.recharge.common.FontHelper;
import com.ant.recharge.entity.MessageEntity;

import java.util.List;

/**
 * Created by kwc on 2016/9/8.
 */
public class MessageAdapter extends BaseListAdapter {


    public MessageAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder = null;
        if(view == null){
            view = getmInflater().inflate(R.layout.activity_message_item, null);
            FontHelper.applyFont(getContext(), view);

            holder = new Holder();

            holder.numTV = (ImageView) view.findViewById(R.id.message_title_icon);
            holder.titleTV = (TextView) view.findViewById(R.id.message_title);
            holder.dateTV = (TextView) view.findViewById(R.id.message_date);
            holder.detailTV = (TextView) view.findViewById(R.id.message_detail);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        MessageEntity entity = (MessageEntity) getItem(i);


        holder.titleTV.setText(entity.getTitle());
        holder.dateTV.setText(entity.getSendTime());
        holder.detailTV.setText(entity.getContent());

        if("0".equals(entity.getReadState())){
//            view.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.titleTV.setShadowLayer(1,0,0, Color.parseColor("#686868"));
            holder.numTV.setBackgroundResource(R.drawable.message1);
        } else {
            holder.titleTV.setShadowLayer(0,0,0, Color.parseColor("#686868"));
            holder.numTV.setBackgroundResource(R.drawable.message2);
//            view.setBackgroundResource(R.color.main_color);
        }


        return view;
    }

    private class Holder{
        private ImageView numTV;
        private TextView titleTV;
        private TextView dateTV;
        private TextView detailTV;
    }
}
