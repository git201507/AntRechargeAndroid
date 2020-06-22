package com.ant.recharge.order;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.adapter.OrderAdapter;
import com.ant.recharge.common.AlertDialog;
import com.ant.recharge.common.BaseFragment;
import com.ant.recharge.common.FontHelper;
import com.ant.recharge.common.NCallback;
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.PictureUtils;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.StringUtils;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.entity.OrderEntity;
import com.ant.recharge.entity.ResponseEntity;
import com.ant.recharge.entity.UploadPicEntity;
import com.ant.recharge.entity.User;
import com.ant.recharge.login.NetLoginInterface;
import com.ant.recharge.webview.WebviewYLActivity;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Target;
import java.util.List;

import retrofit.client.Header;

import static android.content.Context.MODE_PRIVATE;

/**
 * 上传认证
 */
@SuppressLint("ValidFragment")
public class FragmentWaitUpload extends BaseFragment {
    UploadPicEntity uploadPicEntity;

    private AlertDialog dialog;
    ProgressDialog progressDialog;

    private String zhengImgPath;
    private String fanImgPath;
    private String shouImgPath;
    private int selectedType;
    private Context context;
    private User user;
    private ImageView zhengImg;
    private Button zhengPhotoBtn;
    private Button zhengCameraBtn;
    private ImageView fanImg;
    private Button fanPhotoBtn;
    private Button fanCameraBtn;
    private ImageView shouImg;
    private Button shouPhotoBtn;
    private Button shouCameraBtn;
    //调用系统相册-选择图片
    private static final int IMAGEZHENG = 1;    //调用系统相册-选择图片
    private static final int IMAGEFAN = 2;    //调用系统相册-选择图片
    private static final int IMAGESHOU = 3;    //调用系统相册-选择图片
    private static final int CAMERAZHENG = 11;    //调用照相机-选择图片
    private static final int CAMERAFAN = 12;    //调用照相机-选择图片
    private static final int CAMERASHOU = 13;    //调用照相机-选择图片
    public FragmentWaitUpload() {
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
        View view = inflater.inflate(R.layout.fragment_wait_upload, container, false);
        FontHelper.applyFont(context, view);
        view.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });

        TextView titleTV = (TextView)view.findViewById(R.id.text_title);
        titleTV.setText("资料上传");
        view.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });

        OrderEntity orderEntity = ((DelegateOrderChangeActivity)activity).getOrder();
        TextView telNumberTV = (TextView)view.findViewById(R.id.name_wait_upload);
        telNumberTV.setText("订单号："+orderEntity.getOrderNo());

        TextView stateTV = (TextView)view.findViewById(R.id.wait_upload_state);
        stateTV.setText(orderEntity.getOrderStatusDetails());

        TextView priceTV = (TextView)view.findViewById(R.id.wait_upload_price);
        priceTV.setText(""+orderEntity.getOrderPrice());

        TextView addressTV = (TextView)view.findViewById(R.id.wait_upload_address);
        addressTV.setText(orderEntity.getDeliver_receiverAddress());

        TextView areaTV = (TextView)view.findViewById(R.id.wait_upload_area);
        areaTV.setText("归属地:"+orderEntity.getArea());

        TextView companyTV = (TextView)view.findViewById(R.id.wait_upload_company);
        companyTV.setText("运营商:"+orderEntity.getCompany());

        TextView detailTV = (TextView)view.findViewById(R.id.wait_upload_detail);
        detailTV.setText(orderEntity.getOrderDescription());

        zhengImg = view.findViewById(R.id.zheng_img);
        zhengImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Bitmap bp =BitmapFactory.decodeResource(getResources(),R.drawable.default_zheng);
//                Intent intent=new Intent(activity, PhotoActivity.class);
//                ByteArrayOutputStream baos=new ByteArrayOutputStream();
//                bp.compress(Bitmap.CompressFormat.PNG, 100, baos);
//                byte [] bitmapByte =baos.toByteArray();
//                intent.putExtra("bitmap", bitmapByte);
//                startActivity(intent);
            }
        });
        fanImg = view.findViewById(R.id.fan_img);
        shouImg = view.findViewById(R.id.shou_img);

        zhengPhotoBtn = view.findViewById(R.id.zheng_btn_photo);
        fanPhotoBtn = view.findViewById(R.id.fan_btn_photo);
        shouPhotoBtn = view.findViewById(R.id.shou_btn_photo);

        zhengPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //调用相册
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGEZHENG);
            }
        });
        fanPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //调用相册
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGEFAN);
            }
        });
        shouPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //调用相册
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGESHOU);
            }
        });
        zhengCameraBtn = view.findViewById(R.id.zheng_btn_camera);
        fanCameraBtn = view.findViewById(R.id.fan_btn_camera);
        shouCameraBtn = view.findViewById(R.id.shou_btn_camera);
        zhengCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置Intent的参数为通过摄像头获取的ACTION_IMAGE_CAPTURE
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //启动activity返回照片结果，设置返回的requestCode为1
                startActivityForResult(intent, CAMERAZHENG);
            }
        });
        fanCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置Intent的参数为通过摄像头获取的ACTION_IMAGE_CAPTURE
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //启动activity返回照片结果，设置返回的requestCode为1
                startActivityForResult(intent, CAMERAFAN);
            }
        });
        shouCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置Intent的参数为通过摄像头获取的ACTION_IMAGE_CAPTURE
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //启动activity返回照片结果，设置返回的requestCode为1
                startActivityForResult(intent, CAMERASHOU);
            }
        });

        Button buyBtn = (Button)view.findViewById(R.id.confirm_btn_upload);

        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(zhengImgPath != null && fanImgPath != null && shouImgPath != null) {

                    String[] zhengArray = zhengImgPath.split("/");
                    String zipZhengPath = "/";
                    for(int i = 0; i <zhengArray.length -1; i++) {
                        zipZhengPath += zhengArray[i];
                        zipZhengPath += "/";

                    }
                    zipZhengPath += "zipZhengPath.jpg";

                    //调用压缩图片的方法，返回压缩后的图片path
                    final String compressImage1 = PictureUtils.compressImage(zhengImgPath, zipZhengPath, 30);
                    final File compressedPic1 = new File(compressImage1);
///////////////////
                    String[] fanArray = fanImgPath.split("/");
                    String zipFanPath = "/";
                    for(int i = 0; i <fanArray.length -1; i++) {
                        zipFanPath += fanArray[i];
                        zipFanPath += "/";
                    }
                    zipFanPath += "zipFanPath.jpg";

                    //调用压缩图片的方法，返回压缩后的图片path
                    final String compressImage2 = PictureUtils.compressImage(fanImgPath, zipFanPath, 30);
                    final File compressedPic2 = new File(compressImage2);
//////////////////////
                    String[] shouArray = shouImgPath.split("/");
                    String zipShouPath = "/";
                    for(int i = 0; i <shouArray.length -1; i++) {
                        zipShouPath += shouArray[i];
                        zipShouPath += "/";
                    }
                    zipShouPath += "zipShouPath.jpg";

                    //调用压缩图片的方法，返回压缩后的图片path
                    final String compressImage3 = PictureUtils.compressImage(shouImgPath, zipShouPath, 30);
                    final File compressedPic3 = new File(compressImage3);
///////////////////////
                    if(progressDialog == null) {
                        progressDialog = ProgressDialog.show(activity, "提示", "正在上传");
                    }
                    else {
                        progressDialog.show();
                    }
//参数类型
                    MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
//创建OkHttpClient实例
                    OkHttpClient client = new OkHttpClient();

                    MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);

                    //添加其它信息
//        builder.addFormDataPart("time",takePicTime);
//        builder.addFormDataPart("mapX", SharedInfoUtils.getLongitude());
//        builder.addFormDataPart("mapY",SharedInfoUtils.getLatitude());
//        builder.addFormDataPart("name",SharedInfoUtils.getUserName());

                    File fZheng = new File(zhengImgPath);
                    if (compressedPic1 != null) {
                        builder.addFormDataPart("pic1", compressedPic1.getName(), RequestBody.create(MEDIA_TYPE_PNG, compressedPic1));
                    }
                    else
                        {
                        builder.addFormDataPart("pic1", fZheng.getName(), RequestBody.create(MEDIA_TYPE_PNG, fZheng));
                    }


                    File fFan = new File(fanImgPath);
                    if (compressedPic2 != null) {
                        builder.addFormDataPart("pic2", compressedPic2.getName(), RequestBody.create(MEDIA_TYPE_PNG, compressedPic2));
                    }
                    else
                        {
                        builder.addFormDataPart("pic2", fFan.getName(), RequestBody.create(MEDIA_TYPE_PNG, fFan));
                    }

                    File fShou = new File(shouImgPath);
                    if (compressedPic3 != null) {
                        builder.addFormDataPart("pic3", compressedPic3.getName(), RequestBody.create(MEDIA_TYPE_PNG, compressedPic3));
                    }
                    else
                        {
                        builder.addFormDataPart("pic3", fShou.getName(), RequestBody.create(MEDIA_TYPE_PNG, fShou));
                    }

                    //构建请求体
                    RequestBody requestBody = builder.build();

                    String url = Profile.SERVER_ADDRESS_DEV + "/common/file/upTriplePics";

                    //构建请求
                    Request request = new Request.Builder()
                            .url(url)//地址
                            .post(requestBody)//添加请求体
                            .build();

//发送异步请求，同步会报错，Android4.0以后禁止在主线程中进行耗时操作
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, final IOException e) {

                            System.out.println("request = " + request.urlString());
                            System.out.println("e.getLocalizedMessage() = " + e.getLocalizedMessage());
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.hide();
                                    Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            //看清楚是response.body().string()不是response.body().toString()
                            final String toastStr = response.body().string();
                            try {
                                uploadPicEntity = JsonUtil.decode(toastStr, UploadPicEntity.class);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(activity, toastStr, Toast.LENGTH_SHORT).show();
                                    if (toastStr.contains("上传成功")) {
                                        setOrderStatus(uploadPicEntity);
                                    }
                                }
                            });
                        }
                    });
                }
                else {
                    Toast.makeText(getContext(),"请选择要上传的图片!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button cancelBtn = (Button)view.findViewById(R.id.cancel_btn_upload);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });

        return view;
    }


    /**
     * 修改订单状态
     * @return
     */
    public void setOrderStatus(UploadPicEntity uploadPicEntity){
        DelegateOrderChangeActivity.NetInterface netInterface = new NRestAdapter<DelegateOrderChangeActivity.NetInterface>(getContext(),
                Profile.SERVER_ADDRESS_DEV, DelegateOrderChangeActivity.NetInterface.class)
                .create();
        if(netInterface == null){
            progressDialog.hide();
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

                    netInterface.setOrderStatus(user.getToken(), order.getId(), "upload", uploadPicEntity.getData().getPic1(),uploadPicEntity.getData().getPic2(),uploadPicEntity.getData().getPic3(),
                            new NCallback<OrderEntity>(getContext(), OrderEntity.class) {
                                @Override
                                public void onSuccess(int statusCode, List<Header> headers, OrderEntity order) {
                                    orderDetail(order.getId());
                                }

                                @Override
                                public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                                    progressDialog.hide();
                                    Toast.makeText(activity, infoMessage, Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            progressDialog.hide();
        }
    }

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    public void orderDetail(final String orderId){
        OrderAdapter.NetInterface netInterface = new NRestAdapter<OrderAdapter.NetInterface>(getContext(),
                Profile.SERVER_ADDRESS_DEV, OrderAdapter.NetInterface.class)
                .create();
        if(netInterface == null){
            progressDialog.hide();
            Toast.makeText(getContext(),"网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences preferences = getContext().getSharedPreferences(NetLoginInterface.ANT_LOGIN_USER, MODE_PRIVATE);
        String userStr = preferences.getString("additional", null);
        try {
            if (!StringUtils.isBlank(userStr)){
                User user = JsonUtil.decode(userStr, User.class);
                if (!StringUtils.isBlank(user.getToken())){

                    netInterface.getOrderDetailById(user.getToken(), orderId,
                            new NCallback<OrderEntity>(getContext(), OrderEntity.class) {
                                @Override
                                public void onSuccess(int statusCode, List<retrofit.client.Header> headers, OrderEntity order) {
                                    ((DelegateOrderChangeActivity)activity).setOrder(order);

                                    ((DelegateOrderChangeActivity)activity).loadStepPage(3);
                                }

                                @Override
                                public void onFailure(int statusCode, List<retrofit.client.Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                                    Toast.makeText(activity, infoMessage, Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
                progressDialog.hide();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    //所需权限
//    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>

    public void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);  //这个super可不能落下，否则可能回调不了

        //获取图片路径
        if ((requestCode == IMAGEZHENG || requestCode == IMAGEFAN || requestCode == IMAGESHOU ) && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = activity.getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            switch (requestCode)
            {
                case IMAGEZHENG:
                    this.zhengImgPath = c.getString(columnIndex);
                    break;
                case IMAGEFAN:
                    this.fanImgPath = c.getString(columnIndex);
                    break;
                case IMAGESHOU:
                    this.shouImgPath = c.getString(columnIndex);
                    break;
            }
            this.selectedType = requestCode;
            showImage();
            c.close();
        }

        //如果请求的requestCode为0的话，进行处理
        if ((requestCode == CAMERAZHENG || requestCode == CAMERAFAN || requestCode == CAMERASHOU ) && resultCode == Activity.RESULT_OK && data != null) {
            //成功得到照片后，得到data对象中的data值，并且转换为Bitmap对象
            Bitmap bmPhoto = (Bitmap) data.getExtras().get("data");
            //设置Iv的显示对象为此Bitmap
            showImageFromCamera(bmPhoto, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            if (requestCode == 1) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    setImage();
                } else {
                    // Permission Denied
                }
            }

    }

    //加载图片
    private void showImage(){
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请 READ_EXTERNAL_STORAGE 权限
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        }
        // Permission Granted
        setImage();
    }

    private void setImage(){
        Bitmap bm = null;
        InputStream inputStream = null ;
        File file = null;
        try {
            AssetManager assetManager = activity.getAssets();

            switch(this.selectedType) {
                case IMAGEZHENG:
//                        inputStream = assetManager.open(this.zhengImgPath);
                    file = new File(zhengImgPath);
                    inputStream = new FileInputStream(file);
                    bm = BitmapFactory.decodeStream(inputStream);
                    zhengImg.setImageBitmap(bm);
                    break;
                case IMAGEFAN:
//                        inputStream = assetManager.open(this.fanImgPath);
                    file = new File(fanImgPath);
                    inputStream = new FileInputStream(file);
                    bm = BitmapFactory.decodeStream(inputStream);
                    fanImg.setImageBitmap(bm);
                    break;
                case IMAGESHOU:
//                        inputStream = assetManager.open(this.shouImgPath);
                    file = new File(shouImgPath);
                    inputStream = new FileInputStream(file);
                    bm = BitmapFactory.decodeStream(inputStream);
                    shouImg.setImageBitmap(bm);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //加载图片
    private void showImageFromCamera(Bitmap bmPhoto, int type){
        String name = "mian";
        switch(type) {
            case CAMERAZHENG:
                zhengImg.setImageBitmap(bmPhoto);
                name = "zhengmian";
                break;
            case CAMERAFAN:
                fanImg.setImageBitmap(bmPhoto);
                name = "fanmian";
                break;
            case CAMERASHOU:
                shouImg.setImageBitmap(bmPhoto);
                name = "shoumian";
                break;
        }
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File file = null;

        try {
            file = File.createTempFile(
                    name,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );

            FileOutputStream out=new FileOutputStream(file);
            bmPhoto.compress(Bitmap.CompressFormat.JPEG, 20, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        switch(type) {
            case CAMERAZHENG:
                zhengImgPath = file.getAbsolutePath();
                break;
            case CAMERAFAN:
                fanImgPath = file.getAbsolutePath();
                break;
            case CAMERASHOU:
                shouImgPath = file.getAbsolutePath();
                break;
        }
    }
}
