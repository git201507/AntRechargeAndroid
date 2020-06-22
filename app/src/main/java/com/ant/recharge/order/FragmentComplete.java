package com.ant.recharge.order;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ant.recharge.R;
import com.ant.recharge.common.AlertDialog;
import com.ant.recharge.common.BaseFragment;
import com.ant.recharge.common.FontHelper;
import com.ant.recharge.common.StringUtils;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.entity.User;
import com.ant.recharge.login.NetLoginInterface;

/**
 * 订单完成
 */
@SuppressLint("ValidFragment")
public class FragmentComplete extends BaseFragment {

    private Context context;
    private User user;

    public FragmentComplete() {
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
        View view = inflater.inflate(R.layout.fragment_complete, container, false);
        FontHelper.applyFont(context, view);

        view.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
        TextView titleTV = (TextView)view.findViewById(R.id.text_title);
        titleTV.setText("已完成");

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
