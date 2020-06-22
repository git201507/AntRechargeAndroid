package com.ant.recharge.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.common.AlertDialog;
import com.ant.recharge.common.BaseFragment;
import com.ant.recharge.common.FontHelper;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.entity.User;
import com.ant.recharge.login.NetLoginInterface;
import com.ant.recharge.order.DelegateOrderChangeActivity;

/**
 *
 */
    @SuppressLint("ValidFragment")
    public class FragmentRecharge extends BaseFragment {
        private AlertDialog dialog;
        private Context context;
        private User user;

        public FragmentRecharge() {
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
            final View view = inflater.inflate(R.layout.fragment_recharge, container, false);
            FontHelper.applyFont(context, view);

            view.findViewById(R.id.btn_back).setVisibility(View.GONE);
            TextView titleTV = (TextView)view.findViewById(R.id.text_title);
            titleTV.setText("手机充值");

            Button confirmBtn = (Button)view.findViewById(R.id.confirm_btn_recharge);

            confirmBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog = new AlertDialog(context)
                            .setCustomMessage(context.getString(R.string.recharge_alert))
                            .setPositiveButton(context.getString(R.string.action_confirm), new View.OnClickListener() {
                                @Override
                                public void onClick(View paramView) {
                                    dialog.dismiss();
                                    Toast toast = Toast.makeText(getActivity(), "充值成功", Toast.LENGTH_LONG);
                                    toast.show();
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

}
