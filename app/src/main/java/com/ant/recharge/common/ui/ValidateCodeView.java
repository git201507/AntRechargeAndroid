package com.ant.recharge.common.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.UiThread;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.common.net.HttpUtils;
import com.ant.recharge.entity.ResponseEntity;
import com.ant.recharge.entity.User;
import com.ant.recharge.login.RegistActivity;
import com.ant.recharge.login.VerifyCode;

/**
 * Created by kwc on 2016/9/1.
 */
public class ValidateCodeView extends FrameLayout {

    private Context context;
    //计时数
    private int currentNum;

    //显示名称
    private View clickView;
    //loading view
    private TextView loadingView;

    private EditText telephoneTV;

    public void setTelephoneTV(EditText telephoneTV) {
        this.telephoneTV = telephoneTV;
    }

    public ValidateCodeView(Context context) {
        super(context);
        init(context);
    }

    public ValidateCodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ValidateCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ValidateCodeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }


    //初始化
    private void init(final Context context){
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.validate_code, null);
        this.addView(view);
        clickView = view.findViewById(R.id.code_right);
        loadingView = (TextView) view.findViewById(R.id.loading_right);
        loadingView.setVisibility(View.GONE);

        //获取验证码
        clickView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(telephoneTV.getText().toString().trim().length() != 11)
                {
                    Toast.makeText(context, "手机号"+telephoneTV.getText().toString().trim()+"不正确", Toast.LENGTH_SHORT).show();
                    return;
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url = Profile.SERVER_ADDRESS_DEV + "/getSms.do?telephone=" + telephoneTV.getText().toString().trim();
                        final String nResponse = HttpUtils.get(url,null);
                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ResponseEntity response = null;
                                try {
                                    response = JsonUtil.decode(nResponse, ResponseEntity.class);
                                    Toast.makeText(context, response.getMsg(), Toast.LENGTH_SHORT).show();
                                    if("1".equals(response.getState())){
                                        clickView();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }).start();
            }
        });
    }


    private Boolean isWaiting = false;
    public synchronized void clickView(){

        if(isWaiting){
            return;
        }

        isWaiting = true;

        currentNum = 60;
        loadingView.setText("60秒后重新发送");
        clickView.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);

        new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = currentNum; i>=1; i--){
                    if (getContext() == null){
                        isWaiting = false;
                        return;
                    }

                    try {
                        Thread.currentThread().sleep(1000);
                    } catch (InterruptedException e) {
//                    e.printStackTrace();
                    }

                    ((Activity)getContext()).runOnUiThread(new Runnable(){
                        @Override
                        public void run() {

                            if(currentNum <= 1){
                                loadingView.setVisibility(View.GONE);
                                clickView.setVisibility(View.VISIBLE);
                                isWaiting = false;
                            }
                            currentNum--;
                            loadingView.setText(currentNum + "秒后重新发送");
                        }
                    });
                }
            }
        }).start();
    }
}
