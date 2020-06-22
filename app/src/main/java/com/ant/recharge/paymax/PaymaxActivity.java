package com.ant.recharge.paymax;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.common.AlertDialog;
import com.ant.recharge.common.BaseFragmentActivity;
import com.ant.recharge.common.NCallback;
import com.ant.recharge.common.NCallbackMsg;
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.StringUtils;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.entity.Charge;
import com.ant.recharge.entity.OrderEntity;
import com.ant.recharge.entity.TelephoneEntity;
import com.ant.recharge.entity.User;
import com.ant.recharge.login.NetLoginInterface;
import com.ant.recharge.login.UpdatePasswordActivity;
import com.ant.recharge.order.DelegateOrderChangeActivity;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.swwx.paymax.PayLog;
import com.swwx.paymax.PayResult;
import com.swwx.paymax.PaymaxCallback;
import com.swwx.paymax.PaymaxSDK;

import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit.client.Header;
import retrofit.http.POST;
import retrofit.http.Query;

public class PaymaxActivity extends BaseFragmentActivity implements PaymaxCallback {
    private AlertDialog dialog;
    private OrderEntity order;
    private EditText amountEditText;
    private EditText useridEditText;
    private EditText time_expireEditText;

    private String currentAmount = "";

    private ImageButton ibWechat;
    private ImageButton ibAlipay;
    private ImageButton ibLKL;

    protected double amount = 0.0;
    protected String userid = "";
    protected long time_expire ;

    private int channel = PaymaxSDK.CHANNEL_WX;

    /**
     * 支付宝支付渠道
     */
    private static final String CHANNEL_ALIPAY = "alipay_app";

    /**
     * 微信支付渠道
     */
    private static final String CHANNEL_WECHAT = "wechat_app";

    /**
     * 微信支付渠道
     */
    protected static final String CHANNEL_LKL = "lakala_app";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymax);
        Intent intent = getIntent();
        order = (OrderEntity) intent.getSerializableExtra("order");

        TextView titleTV = (TextView)findViewById(R.id.text_title);
        titleTV.setText("选择支付方式");

        ImageView ivWX = (ImageView) findViewById(R.id.iv_wx);
        ivWX.setImageResource(R.drawable.wx);
        ImageView ivAli = (ImageView) findViewById(R.id.iv_ali);
        ivAli.setImageResource(R.drawable.ali);
        ImageView ivLkl = (ImageView) findViewById(R.id.iv_lkl);
        ivLkl.setImageResource(R.drawable.lkl);


        // select channel button
        ibWechat = (ImageButton) findViewById(R.id.ibWechat);
        ibAlipay = (ImageButton) findViewById(R.id.ibAlipay);
        ibLKL = (ImageButton) findViewById(R.id.ibLKL);
        onChannelClick(ibWechat);

        useridEditText = (EditText) findViewById(R.id.edit_userid);
        amountEditText = (EditText) findViewById(R.id.et_right);
        amountEditText.setText("￥"+order.getOrderPrice());

        time_expireEditText = (EditText) findViewById(R.id.edit_time_expire);

        amountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(currentAmount)) {
                    amountEditText.removeTextChangedListener(this);
                    String replaceable = String.format("[%s, \\s.]", NumberFormat.getCurrencyInstance(Locale.CHINA).getCurrency().getSymbol(Locale.CHINA));
                    String cleanString = s.toString().replaceAll(replaceable, "");

                    if (cleanString.equals("") || new BigDecimal(cleanString).toString().equals("0")) {
                        amountEditText.setText(null);
                    } else {
                        double parsed = Double.parseDouble(cleanString);
                        String formatted = NumberFormat.getCurrencyInstance(Locale.CHINA).format((parsed / 100));
                        currentAmount = formatted;
                        amountEditText.setText(formatted);
                        amountEditText.setSelection(formatted.length());
                    }
                    amountEditText.addTextChangedListener(this);
                }
            }
        });

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public interface NetInterface {
        //验证码是否正确
        @POST("/doCharge.do")
        public void doCharge(@Query("token") String token,
                             @Query("orderId") String orderId,
                             @Query("channel") String channel,
                             NCallback<Charge> callback);
    }

    public void doCharge(View view){
        PaymaxActivity.NetInterface netInterface = new NRestAdapter<PaymaxActivity.NetInterface>(this,
                Profile.SERVER_ADDRESS_DEV, PaymaxActivity.NetInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(this,"网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences preferences = getSharedPreferences(NetLoginInterface.ANT_LOGIN_USER, MODE_PRIVATE);
        String userStr = preferences.getString("additional", null);
        try {
            if (!StringUtils.isBlank(userStr)){
                User user = JsonUtil.decode(userStr, User.class);
                if (!StringUtils.isBlank(user.getToken())){

                    String flag;
                    if(channel == PaymaxSDK.CHANNEL_WX){
                        flag = CHANNEL_WECHAT;
                    }
                    else {
                        flag = CHANNEL_ALIPAY;
                    }

                    netInterface.doCharge(user.getToken(), order.getId(), flag,
                        new NCallback<Charge>(this, Charge.class) {
                            @Override
                            public void onSuccess(int statusCode, List<Header> headers, Charge charge) {

                                try {
                                    String data = JsonUtil.encode(charge);

                                    switch (channel) {
                                        case PaymaxSDK.CHANNEL_WX:
                                            PaymaxSDK.pay(PaymaxActivity.this, data, PaymaxActivity.this);
                                            break;
                                        case PaymaxSDK.CHANNEL_ALIPAY:
                                            PaymaxSDK.pay(PaymaxActivity.this, data, PaymaxActivity.this);
                                            //new PaymentTask(PaymaxActivity.this, PaymaxActivity.this).execute(new PaymentRequest(CHANNEL_ALIPAY, amount, "测试商品007", "测试商品Body", userid,time_expire));
                                            break;
                                        default:
                                            break;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                                Toast.makeText(PaymaxActivity.this, infoMessage, Toast.LENGTH_SHORT).show();

//                                Intent data = new Intent();
//                                setResult(100, data);
//                                finish();
                            }
                        });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onCharge(View view) {
        String amountText = amountEditText.getText().toString();
        userid = useridEditText.getText().toString();
        time_expire=Long.parseLong(time_expireEditText.getText().toString())* 1000+System.currentTimeMillis();
        Log.d("FaceRecoSDK", "time_expire=" + time_expire);

        if (checkInputValid(amountText)) {
            amount = parseInputTxt(amountText);
            amount /= 100;

            switch (channel) {
                case PaymaxSDK.CHANNEL_WX:
                    new PaymentTask(PaymaxActivity.this, PaymaxActivity.this).execute(new PaymentRequest(CHANNEL_WECHAT, amount, "测试商品007", "测试商品Body", userid,time_expire));
                    break;

                case PaymaxSDK.CHANNEL_ALIPAY:
                    new PaymentTask(PaymaxActivity.this, PaymaxActivity.this).execute(new PaymentRequest(CHANNEL_ALIPAY, amount, "测试商品007", "测试商品Body", userid,time_expire));
                    break;

                case PaymaxSDK.CHANNEL_LKL: {
                    new FaceTask().execute(new FaceRequest("123", "123", userid));
                }
                break;
            }

        }
    }

    public void onChannelClick(View v) {
        switch (v.getId()) {
            case R.id.ibAlipay:
                channel = PaymaxSDK.CHANNEL_ALIPAY;
                ibAlipay.setBackgroundResource(R.drawable.selected);
                ibWechat.setBackgroundResource(R.drawable.unselected);
                ibLKL.setBackgroundResource(R.drawable.unselected);
                break;

            case R.id.ibWechat:
                channel = PaymaxSDK.CHANNEL_WX;
                ibAlipay.setBackgroundResource(R.drawable.unselected);
                ibWechat.setBackgroundResource(R.drawable.selected);
                ibLKL.setBackgroundResource(R.drawable.unselected);
                break;

            case R.id.ibLKL:
                channel = PaymaxSDK.CHANNEL_LKL;
                ibAlipay.setBackgroundResource(R.drawable.unselected);
                ibWechat.setBackgroundResource(R.drawable.unselected);
                ibLKL.setBackgroundResource(R.drawable.selected);
                break;

            default:
                break;
        }
    }

    @Override
    public void onPayFinished(PayResult result) {
        String msg = "Unknow";
        switch (result.getCode()) {
            case PaymaxSDK.CODE_SUCCESS:
                msg = "Complete, Success!.";

                //支付成功的提示
                Intent data = new Intent();
                setResult(200, data);
                finish();
                break;

            case PaymaxSDK.CODE_ERROR_CHARGE_JSON:
                msg = "Json error.";
                break;

            case PaymaxSDK.CODE_FAIL_CANCEL:
                msg = "cancel pay.";
//                //假数据
//                //支付成功的提示
//                Intent data1 = new Intent();
//                setResult(200, data1);
//                finish();
                break;

            case PaymaxSDK.CODE_ERROR_CHARGE_PARAMETER:
                msg = "appid error.";
                break;

            case PaymaxSDK.CODE_ERROR_WX_NOT_INSTALL:
                msg = "wx not install.";
                break;

            case PaymaxSDK.CODE_ERROR_WX_NOT_SUPPORT_PAY:
                msg = "ex not support pay.";
                break;

            case PaymaxSDK.CODE_ERROR_WX_UNKNOW:
                msg = "wechat failed.";
                break;

            case PaymaxSDK.CODE_ERROR_ALI_DEAL:
                msg = "alipay dealing.";
                break;

            case PaymaxSDK.CODE_ERROR_ALI_CONNECT:
                msg = "alipay network connection failed.";
                break;

            case PaymaxSDK.CODE_ERROR_CHANNEL:
                msg = "channel error.";
                break;

            case PaymaxSDK.CODE_ERROR_LAK_USER_NO_NULL:
                msg = "lklpay user no is null.";
                break;

        }
        Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG)
                .setAction("Close", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
    }

    private boolean checkInputValid(String amountText) {
        return !(null == amountText || amountText.length() == 0);
    }

    private double parseInputTxt(String amountText) {
        String replaceable = String.format("[%s, \\s.]", NumberFormat.getCurrencyInstance(Locale.CHINA).getCurrency().getSymbol(Locale.CHINA));
        String cleanString = amountText.replaceAll(replaceable, "");
        return Double.valueOf(new BigDecimal(cleanString).toString());
    }

    class FaceTask extends AsyncTask<FaceRequest, Void, String> {


        public FaceTask() {
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(FaceRequest... pr) {
            FaceRequest faceRequest = pr[0];
            String data = null;

            String json = null;
            try {
                json = JsonUtil.encode(faceRequest);
                Log.d("FaceRecoSDK", "json=" + json);
                // 向 PaymaxSDK Server SDK请求数据
                String url = String.format("https://www.paymax.cc/mock_merchant_server/v1/face/auth/%s/product", pr[0].uId);
                data = postJson(url, json);
                Log.d("FaceRecoSDK", "data=" + data);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return data;
        }


        @Override
        protected void onPostExecute(String data) {
            if (null == data || data.length() == 0) {
                Snackbar.make(findViewById(android.R.id.content), "no face data", Snackbar.LENGTH_LONG)
                        .setAction("Close", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        }).show();
            } else if (getRtn(data)) {
                new PaymentTask(PaymaxActivity.this, PaymaxActivity.this).execute(new PaymentRequest(CHANNEL_LKL, amount, "测试商品007", "测试商品Body", userid,time_expire));
            } else {
//                Intent intent = new Intent(PaymaxActivity.this, InputActivity.class);
//                intent.putExtra("amount", amount);
//                intent.putExtra("userid", userid);
//                intent.putExtra("time_expire", time_expire);
//
//                startActivity(intent);
            }
        }
    }

    class FaceRequest {
        String key;//商户key
        String merchantNo;//商户号
        String uId;//商户用户id， 在商户系统能唯一标示某个用户商户用户名

        public FaceRequest(String key, String merchantNo, String uId) {
            this.key = key;
            this.merchantNo = merchantNo;
            this.uId = uId;
        }
    }


    /**
     * 获得返回结果码,来判断此用户是否通过人脸识别
     *
     * @param data
     * @return
     */
    private boolean getRtn(String data) {
        boolean flag = false;
        try {
            JSONObject jsonObject = new JSONObject(data);
            flag = jsonObject.optBoolean("authValid");
        } catch (Exception e) {
            PayLog.e(e);
        }
        return flag;
    }


    private String postJson(String url, String json) throws IOException {
        MediaType type = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(type, json);
        Request request = new Request.Builder().url(url).get().build();
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(5, TimeUnit.SECONDS);
        client.setReadTimeout(5, TimeUnit.SECONDS);
        Response response = client.newCall(request).execute();
        Log.d("PaymaxSDK", "response code = " + response.code());
        return response.body().string();

    }

}




