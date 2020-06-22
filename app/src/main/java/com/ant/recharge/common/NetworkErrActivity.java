package com.ant.recharge.common;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.ant.recharge.R;

public class NetworkErrActivity extends AppCompatActivity {

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new RelativeLayout(this));

        dialog = new AlertDialog(this)
                .setCustomMessage(this.getString(R.string.error_network_error))
                .setPositiveButton(this.getString(R.string.action_confirm), new View.OnClickListener() {
                    @Override
                    public void onClick(View paramView) {
                        Intent intent = null;
                        //判断手机系统的版本  即API大于10 就是3.0或以上版本
                        if(android.os.Build.VERSION.SDK_INT>10){
                            intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                        }else{
                            intent = new Intent();
                            ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
                            intent.setComponent(component);
                            intent.setAction("android.intent.action.VIEW");
                        }
                        startActivity(intent);
                        dialog.dismiss();
                        finish();
                    }
                })
                .setNegativeButton(this.getString(R.string.action_cancel), new View.OnClickListener() {
                    @Override
                    public void onClick(View paramView) {
                        dialog.dismiss();
                        finish();
                    }
                });

        dialog.setCancelAction(new AlertDialog.CancelAction() {

            @Override
            public void cancel() {
                finish();
            }
        });

        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ((dialog != null && !dialog.isShowing()) || NetworkUtil.isOpenNetwork(this)) {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        dialog.clear();
        dialog = null;
        super.onDestroy();
    }
}
