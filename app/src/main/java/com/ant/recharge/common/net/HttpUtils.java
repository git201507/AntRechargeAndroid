package com.ant.recharge.common.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.codehaus.jackson.JsonParser;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by kwc on 2016/8/9.
 */
public class HttpUtils {

    private static Logger logger = Logger.getLogger(HttpUtils.class.getSimpleName());

    public static String post(String url, Map<Object, Object> params){

        try {
            logger.warning("-------------url=" + url);
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("POST");
            InputStream in = conn.getInputStream();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1){
                out.write(buffer, 0, len);
            }

//            byte[] data = out.toByteArray();
            return out.toString();
        } catch (Exception e){
            e.printStackTrace();
            logger.warning("-------------e=" + e.getMessage());
        }

        return "";
    }

    public static String get(String url, Map<Object, Object> params){

        try {
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            logger.warning("-------------u2=" + u.toURI());
//            conn.setConnectTimeout(3*1000);
            conn.setRequestMethod("GET");
            conn.connect();
            InputStream in = conn.getInputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1){
                out.write(buffer, 0, len);
            }
            return out.toString();
        } catch (Exception e){
            logger.warning("-------------e=" + e.getMessage());
        }

        return "";
    }


//    public static Boolean post(String address, byte[] content) throws Exception{
//
//        URL url = new URL(address);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setConnectTimeout(3000);
//        conn.setRequestMethod("POST");
//        conn.setRequestProperty("content-Type", "application/x-www-form-urlencoded");
////        conn.setRequestProperty("Content-Length", "" + content.length);
//        conn.setDoOutput(true);//准备写出
//        conn.getOutputStream().write(content);//写出数据
//
//        return conn.getResponseCode() == 200;
//    }

    public static String post(String address, byte[] content) throws Exception{

        URL url = new URL(address);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(3000);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("content-Type", "application/x-www-form-urlencoded");
//        conn.setRequestProperty("Content-Length", "" + content.length);
        conn.setDoOutput(true);//准备写出
        conn.getOutputStream().write(content);//写出数据

        return conn.getResponseMessage();
    }


//    public String doPost(Context context, Map<String, String> postParams)
//            throws Exception {
//        if (!HttpConnectionUtils.isNetworkAvailable(context)) {
//            throw new NetWorkException("请打开网络！");
//        }
//
//        StringBuilder sb = new StringBuilder("http://");
//        sb.append(context.getSharedPreferences(
//                SettingServerActivity.PERFERENCESNAME, context.MODE_PRIVATE)
//                .getString("server", defaultServer));
//        sb.append("/doc/gw.jspr?act=sign");
//        /**
//         * post:参数 gwid：公文id advice:处理内容
//         */
//
//        HttpPost request = new HttpPost(sb.toString());
//        UrlEncodedFormEntity form = new UrlEncodedFormEntity(
//                convertMapToPostPair(postParams));
//        request.setEntity(form);
//        HttpResponse resp = HttpConnectionUtils.execute(context, request);
//        if (HttpStatus.SC_OK == resp.getStatusLine().getStatusCode()) {
//
//            JsonParser jp = new JsonParser();
//            JsonElement je = jp.parse(new InputStreamReader(resp.getEntity()
//                    .getContent()));
//            JsonObject json = je.getAsJsonObject();
//            if (json.get("success").getAsBoolean()) {
//                Log.d("------------msg=", json.get("msg").getAsString());
//                return json.get("msg").getAsString();
//            }
//        } else {
//            throw new ServerException();
//        }
//        return null;
//    }


//    /**
//     * 判断网络是否可用
//     * @param context
//     * @return true 网络已经是开启状态
//     */
//    public static boolean isNetworkAvailable(Context context) {
//        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (connectivity == null) {
//            return false;
//        }
//        /**
//         //mobile 3G Data Network
//         State mobile = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
//         txt3G.setText(mobile.toString()); //显示3G网络连接状态
//         //wifi
//         State wifi = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
//         txtWifi.setText(wifi.toString()); //显示wifi连接状态
//         */
//        NetworkInfo[] allInfo = connectivity.isActiveNetworkMetered().getAllNetworkInfo();
//        if (allInfo != null) {
//            for (int i = 0; i < allInfo.length; i++) {
//                if (allInfo[i].getState() == NetworkInfo.State.CONNECTED) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
}
