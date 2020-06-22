package com.ant.recharge.welcome;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.common.BaseActivity;
import com.ant.recharge.common.NCallbackMsg;
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.StringUtils;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.entity.User;
import com.ant.recharge.login.LoginActivity;
import com.ant.recharge.login.NetLoginInterface;
import com.ant.recharge.main.MainTabActivity;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import retrofit.client.Header;
import retrofit.http.POST;

/**
 * 欢迎页 初始化配置
 * 1. 用户是自动登录：MainTabActivity
 * 2. 登录：LoginActivity
 * Created by kwc on 2016/8/10.
 */
public class WelcomeActivity extends BaseActivity {

    private TextView loadingText;
    private boolean downSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.welcome_activity);
        initView(this, R.layout.welcome_activity);
        loadingText = (TextView) findViewById(R.id.text_loading);

//        String userStr = readPreferences(NetLoginInterface.ANT_LOGIN_USER, "additional");
//        try {
//            if (!StringUtils.isBlank(userStr)){
//                User user = JsonUtil.decode(userStr, User.class);
//                if (!StringUtils.isBlank(user.getToken())){
//                    //进入主页
//                    startActivity(new Intent(WelcomeActivity.this, MainTabActivity.class));
//                    WelcomeActivity.this.finish();
//                    return;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        checkVersion();

    }

    private void skipActivity() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Intent intent = new Intent();
//                        intent.setClass(WelcomeActivity.this, LoginActivity.class);
//                        WelcomeActivity.this.startActivity(intent);
//                        WelcomeActivity.this.finish();
                        //进入主页
                        startActivity(new Intent(WelcomeActivity.this, MainTabActivity.class));
                        WelcomeActivity.this.finish();
                    }

                });
            }
        }).start();

    }

    /**
     * 检测新版本
     */
    private void checkVersion(){

        NetInterface netInterface = new NRestAdapter<NetInterface>(this,
                Profile.SERVER_ADDRESS, NetInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(WelcomeActivity.this,"网络正在开小差!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        try {
            final String packageName = WelcomeActivity.this.getPackageName();
            final int versionCode = WelcomeActivity.this.getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;

            netInterface.getAppVersion(new NCallbackMsg() {
                @Override
                public void onSuccess(int statusCode, List<Header> headers, String result) {

                    if(!result.equals(versionCode + "")){
                        //有新版本
                        loadingText.setText("有新版，正在下载更新，请稍等！");
                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                Looper.prepare();

                                File file = new File(getFilePath(packageName, "update")
                                        + "/update.apk");
                                if (file.exists()) {
                                    file.delete();
                                }
                                try {
//                                    String url = Profile.SERVER_ADDRESS + Urls.apk;
                                    String url = Profile.SERVER_ADDRESS + "Urls.apk";
                                    OkHttpClient client = new OkHttpClient();

                                    Response response = client.newCall(
                                            new Request.Builder()
                                                    .url(url)
//                                                    .addHeader("Content-Type",
//                                                            "application/json")
                                                    .addHeader("Content-Type",
                                                            "application/octet-stream")
                                                    .build())
                                            .execute();

                                    InputStream is = response.body().byteStream();

                                    BufferedInputStream input = new BufferedInputStream(is);

                                    OutputStream output = new FileOutputStream(file);

                                    byte[] data = new byte[1024];

                                    int getFileSize = 0;
                                    int count;
                                    while ((count = input.read(data)) != -1) {
                                        output.write(data, 0, count);
                                    }
                                    output.flush();
                                    output.close();
                                    input.close();
                                    is.close();

                                    downSuccess = true;
                                } catch (FileNotFoundException e) {
                                    runOnUiThread(new Runnable(){
                                        @Override
                                        public void run() {
                                            loadingText.setText("下载更新失败，请检查您的网络连接！");
                                        }
                                    });
                                } catch (IOException e) {
                                    runOnUiThread(new Runnable(){
                                        @Override
                                        public void run() {
                                            loadingText.setText("下载更新失败，请检查您的网络连接！");
                                        }
                                    });
                                }

                                if (downSuccess) {
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setDataAndType(Uri.fromFile(file),
                                            "application/vnd.android.package-archive");
                                    WelcomeActivity.this.startActivity(i);

                                }


                                runOnUiThread(new Runnable(){
                                    @Override
                                    public void run() {
                                        if (!downSuccess) {
                                            loadingText.setText("下载更新失败，请检查您的网络连接！");
                                        }
                                        finish();

                                    }
                                });
                                Looper.loop();
                            }
                        }).start();
                    } else {
                        skipActivity();
                    }

                }

                @Override
                public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                    skipActivity();
                }
            });




        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
        }

    }

    //保存路径
    private File getFilePath(String packageName, String saveDir) {
        File userPath = new File(Environment.getExternalStorageDirectory(), packageName);
        if (!userPath.exists())
            userPath.mkdirs();

        userPath = new File(userPath, saveDir);
        if (!userPath.exists())
            userPath.mkdirs();

        return userPath;
    }


    public interface NetInterface {

        //加载理财师数据
        @POST("/getAppVersion")
        public void getAppVersion(NCallbackMsg callback);

    }
}
