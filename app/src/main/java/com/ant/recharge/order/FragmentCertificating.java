package com.ant.recharge.order;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
import com.ant.recharge.common.NCallback;
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.StringUtils;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.entity.OrderEntity;
import com.ant.recharge.entity.User;
import com.ant.recharge.login.NetLoginInterface;
import com.ant.recharge.webview.WebviewYLActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import retrofit.client.Header;

import static android.content.Context.MODE_PRIVATE;
import static com.ant.recharge.common.Profile.SERVER_ADDRESS_DEV;

/**
 * 订单审核中
 */
@SuppressLint("ValidFragment")
public class FragmentCertificating extends BaseFragment {
    String urlStr1;
    String urlStr2;
    String urlStr3;

    private ImageView zhengImage;
    private ImageView fanImage;
    private ImageView shouImage;

    Bitmap  drawableZheng = null;
    Bitmap  drawableFan = null;
    Bitmap  drawableShou = null;

    private Context context;
    private User user;

    public FragmentCertificating() {
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
        View view = inflater.inflate(R.layout.fragment_certificating, container, false);
        FontHelper.applyFont(context, view);

        SharedPreferences preferences = context.getSharedPreferences(NetLoginInterface.ANT_LOGIN_USER, Context.MODE_PRIVATE);
        String userStr = preferences.getString("additional", null);
        try {
            user = JsonUtil.decode(userStr, User.class);
            TextView nameTV = (TextView)view.findViewById(R.id.user_name);
            nameTV.setText(StringUtils.isBlank(user.getName())?user.getTelephone():user.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        view.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
        TextView titleTV = (TextView)view.findViewById(R.id.text_title);
        titleTV.setText("订单审核中");

        OrderEntity order = ((DelegateOrderChangeActivity)activity).getOrder();

        TextView telTV = (TextView) view.findViewById(R.id.name_wait_cert);
        telTV.setText("订单号："+order.getOrderNo());

        TextView priceTV = (TextView) view.findViewById(R.id.wait_cert_normalprize);
        priceTV.setText(""+order.getOrderPrice());

        TextView lowTV = (TextView) view.findViewById(R.id.wait_cert_lowprize);
        lowTV.setText(""+order.getOrderPrice());

        TextView areaTV = (TextView) view.findViewById(R.id.area_wait_cert);
        areaTV.setText("归属地："+ order.getArea());

        TextView companyTV = (TextView) view.findViewById(R.id.runner_wait_cert);
        companyTV.setText("运营商："+ order.getCompany());

        TextView detailTV = (TextView) view.findViewById(R.id.detail_wait_cert);
        detailTV.setText(order.getOrderDescription());

        String pic1=order.getAttachment_pic1(), pic2=order.getAttachment_pic2(), pic3=order.getAttachment_pic3();
        urlStr1 = SERVER_ADDRESS_DEV + "/common/file/showPic?fileId=" + pic1;//"http://pic.qiantucdn.com/58pic/15/36/26/29X58PICDK5_1024.jpg!/fw/780/watermark/url/L3dhdGVybWFyay12MS4zLnBuZw==/align/center";//SERVER_ADDRESS_DEV + "/common/file/showPic?fileId=" + pic1;
        urlStr2 = SERVER_ADDRESS_DEV + "/common/file/showPic?fileId=" + pic2;
        urlStr3 = SERVER_ADDRESS_DEV + "/common/file/showPic?fileId=" + pic3;

        zhengImage = (ImageView)view.findViewById(R.id.zheng_img) ;
        new Thread() {
            @Override
            public void run() {
                // 需要执行的方法
                drawableZheng = loadImageFromNetwork(urlStr1);
                // 执行完毕后给handler发送一个空消息
                handler.sendEmptyMessage(0);
            }
        }.start();
        zhengImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawableZheng == null){
                    return;
                }

                final ProgressDialog progressDialog = ProgressDialog.show(activity, "提示", "正在打开");
                progressDialog.show();
                new Thread() {
                    @Override
                    public void run() {
                        ByteArrayOutputStream baos=new ByteArrayOutputStream();
                        Bitmap bm =((BitmapDrawable) ((ImageView) zhengImage).getDrawable()).getBitmap();

                        final byte [] bitmapByte = WeChatBitmapToByteArray(bm, false);

                        activity.runOnUiThread(new Runnable(){
                            @Override
                            public void run() {
                                progressDialog.hide();
                                Intent intent=new Intent(activity, PhotoActivity.class);
                                intent.putExtra("bitmap", bitmapByte);
                                startActivity(intent);
                            }
                        });

                    }
                }.start();
            }
        });

        fanImage = (ImageView)view.findViewById(R.id.fan_img) ;
        new Thread() {
            @Override
            public void run() {
                // 需要执行的方法
                drawableFan = loadImageFromNetwork(urlStr2);
                // 执行完毕后给handler发送一个空消息
                handler.sendEmptyMessage(0);
            }
        }.start();
        fanImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawableFan == null){
                    return;
                }

                final ProgressDialog progressDialog = ProgressDialog.show(activity, "提示", "正在打开");
                progressDialog.show();
                new Thread() {
                    @Override
                    public void run() {
                        ByteArrayOutputStream baos=new ByteArrayOutputStream();
                        Bitmap bm =((BitmapDrawable) ((ImageView) fanImage).getDrawable()).getBitmap();

                        final byte [] bitmapByte = WeChatBitmapToByteArray(bm, false);

                        activity.runOnUiThread(new Runnable(){
                            @Override
                            public void run() {
                                progressDialog.hide();
                                Intent intent=new Intent(activity, PhotoActivity.class);
                                intent.putExtra("bitmap", bitmapByte);
                                startActivity(intent);
                            }
                        });

                    }
                }.start();
            }
        });
        shouImage = (ImageView)view.findViewById(R.id.shou_img) ;
        new Thread() {
            @Override
            public void run() {
                // 需要执行的方法
                drawableShou = loadImageFromNetwork(urlStr3);
                // 执行完毕后给handler发送一个空消息
                handler.sendEmptyMessage(0);
            }
        }.start();
        shouImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawableShou == null){
                    return;
                }

                final ProgressDialog progressDialog = ProgressDialog.show(activity, "提示", "正在打开");
                progressDialog.show();
                new Thread() {
                    @Override
                    public void run() {
                        ByteArrayOutputStream baos=new ByteArrayOutputStream();
                        Bitmap bm =((BitmapDrawable) ((ImageView) shouImage).getDrawable()).getBitmap();

                        final byte [] bitmapByte = WeChatBitmapToByteArray(bm, false);

                        activity.runOnUiThread(new Runnable(){
                            @Override
                            public void run() {
                                progressDialog.hide();
                                Intent intent=new Intent(activity, PhotoActivity.class);
                                intent.putExtra("bitmap", bitmapByte);
                                startActivity(intent);
                            }
                        });

                    }
                }.start();
            }
        });
        Button doBtn = (Button)view.findViewById(R.id.forward_btn_cert);
        if(user.getRoleId().equals("4")) {
            doBtn.setVisibility(View.VISIBLE);
        }
        else{
            doBtn.setVisibility(View.INVISIBLE);

        }
        doBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOrderStatus("pass");
            }
        });

        Button rollbackBtn = (Button)view.findViewById(R.id.backward_btn_cert);
        if(user.getRoleId().equals("4")) {
            rollbackBtn.setVisibility(View.VISIBLE);
        }
        else{
            rollbackBtn.setVisibility(View.INVISIBLE);

        }
        rollbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOrderStatus("rollback");
            }
        });

        return view;
    }


    //定义Handler对象
    private Handler handler = new Handler() {
        //当有消息发送出来的时候就执行Handler的这个方法来处理消息分发
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //处理UI
            if(drawableZheng != null)
                zhengImage.setImageBitmap(drawableZheng) ;
            if(drawableFan != null)
                fanImage.setImageBitmap(drawableFan) ;
            if(drawableShou != null)
                shouImage.setImageBitmap(drawableShou) ;
        }
    };

    public static byte[] WeChatBitmapToByteArray(Bitmap bmp, boolean needRecycle) {

        // 首先进行一次大范围的压缩

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        float zoom = (float)Math.sqrt(256 * 1024 / (float)output.toByteArray().length); //获取缩放比例

        // 设置矩阵数据
        Matrix matrix = new Matrix();
        matrix.setScale(zoom, zoom);

        // 根据矩阵数据进行新bitmap的创建
        Bitmap resultBitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);

        output.reset();

        resultBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);

        // 如果进行了上面的压缩后，依旧大于256K，就进行小范围的微调压缩
        while(output.toByteArray().length > 256 * 1024){
            matrix.setScale(0.9f, 0.9f);//每次缩小 1/10

            resultBitmap = Bitmap.createBitmap(
                    resultBitmap, 0, 0,
                    resultBitmap.getWidth(), resultBitmap.getHeight(), matrix,true);

            output.reset();
            resultBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
        }

        return output.toByteArray();
    }

    private Bitmap loadImageFromNetwork(String imageUrl)
    {
        URL url = null;
        InputStream is = null;
        Bitmap bm = null;
        try {
            url = new URL(imageUrl);
            is = url.openStream();
            bm = BitmapFactory.decodeStream(is);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return bm ;
    }


    /**
     * 修改订单状态
     * @return
     */
    public void setOrderStatus(String flag){
        DelegateOrderChangeActivity.NetInterface netInterface = new NRestAdapter<DelegateOrderChangeActivity.NetInterface>(getContext(),
                Profile.SERVER_ADDRESS_DEV, DelegateOrderChangeActivity.NetInterface.class)
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
                    OrderEntity order = ((DelegateOrderChangeActivity)activity).getOrder();

                    netInterface.setOrderStatus(user.getToken(), order.getId(), flag, null, null, null,
                            new NCallback<OrderEntity>(getContext(), OrderEntity.class) {
                                @Override
                                public void onSuccess(int statusCode, List<Header> headers, OrderEntity order) {
                                    activity.finish();
                                }

                                @Override
                                public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                                    Toast.makeText(activity, infoMessage, Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
/*
        if (drawableZheng == null) {
            new Thread() {
                @Override
                public void run() {
                    // 需要执行的方法
                    drawableZheng = loadImageFromNetwork(urlStr1);
                    // 执行完毕后给handler发送一个空消息
                    handler.sendEmptyMessage(0);
                }
            }.start();
        }
        if (drawableFan == null) {
            new Thread() {
                @Override
                public void run() {
                    // 需要执行的方法
                    drawableFan = loadImageFromNetwork(urlStr2);
                    // 执行完毕后给handler发送一个空消息
                    handler.sendEmptyMessage(0);
                }
            }.start();
        }
        if (drawableShou == null) {
            new Thread() {
                @Override
                public void run() {
                    // 需要执行的方法
                    drawableShou = loadImageFromNetwork(urlStr3);
                    // 执行完毕后给handler发送一个空消息
                    handler.sendEmptyMessage(0);
                    }
            }.start();
        }
        */
    }

}
