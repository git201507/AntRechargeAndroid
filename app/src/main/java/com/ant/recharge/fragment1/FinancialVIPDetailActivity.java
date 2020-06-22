package com.ant.recharge.fragment1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.adapter.InvestmentAdapter;
import com.ant.recharge.common.BaseActivity;
import com.ant.recharge.common.NCallback;
import com.ant.recharge.common.NCallbackMsg;
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.StringUtils;
import com.ant.recharge.common.Utility;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.common.pull2fresh.PullToRefreshBase;
import com.ant.recharge.common.pull2fresh.PullToRefreshListView;
import com.ant.recharge.entity.FinancePersonEntity;
import com.ant.recharge.entity.FinanceProductDetail;
import com.ant.recharge.entity.FinanceProductEntity;
import com.ant.recharge.entity.User;
import com.ant.recharge.fragment1.planner.PlannerNetInterface;
import com.ant.recharge.fragment1.planner.PlannerSelectActivity;
import com.ant.recharge.fragment1.planner.PlannerSelectDialog;
import com.ant.recharge.login.NetLoginInterface;
import com.ant.recharge.login.VerifyCode;
import com.ant.recharge.main.NetFianceFragmentInterface;
import com.ant.recharge.verified.VerifiedActivity;
import com.ant.recharge.verified.VerifiedUtil;

import org.codehaus.jackson.type.TypeReference;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit.client.Header;

/**
 * bip标详情
 * Created by kwc on 2016/9/9.
 */
public class FinancialVIPDetailActivity extends BaseActivity {

    private Boolean isCanBuy = false;

    private FinanceProductEntity moneyRecordEntity;
    private String userId = VerifyCode.userid;
    private User user;
    private FinanceProductDetail financeProductDetail;


    private TextView nameTV;
    private TextView expectIncomeTV;//预期年化收益率
    private TextView tempDaysTV;//期限
    private TextView financingTV;//计划金额
    private TextView messageTV;
    private TextView surplusTV;//预期收益

    //tab
    private RadioButton tab1;
    private RadioButton tab2;
    private RadioButton tab3;

    //tab content layout
    private View m1;
    private View m2;
    private View m3;

    //tab 1 content
    private TextView date1;
    private TextView date2;
    private TextView date3;

    private PullToRefreshListView refreshListView;
    private InvestmentAdapter adapter;
    private int pageNo = 1;
    private int pageSize = 100;

    //图片验证码
//    private ImageView imageCodeIV;
//    private EditText imageCodeET;

    //选择理财师
    private TextView plannerTV;

    private CheckBox vip_chipiao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_financial_detail_vip);
        initView(this, R.layout.activity_financial_detail_vip);

        nameTV = (TextView) findViewById(R.id.fd_name);
        expectIncomeTV = (TextView) findViewById(R.id.b_1_value);
        tempDaysTV = (TextView) findViewById(R.id.b_2_value);
        messageTV = (TextView) findViewById(R.id.fd_message);
        financingTV = (TextView) findViewById(R.id.fd_min);
        surplusTV = (TextView) findViewById(R.id.df_money_last);

        tab1 = (RadioButton) findViewById(R.id.a_1);
        tab2 = (RadioButton) findViewById(R.id.a_2);
        tab3 = (RadioButton) findViewById(R.id.a_3);

        m1 = findViewById(R.id.a_1_layout);
        m1.setVisibility(View.VISIBLE);
        m2 = findViewById(R.id.a_2_layout);
        m3 = findViewById(R.id.a_3_layout);

        date1 = (TextView) findViewById(R.id.fd_date_1);
        date2 = (TextView) findViewById(R.id.fd_date_2);
        date3 = (TextView) findViewById(R.id.fd_date_3);

        vip_chipiao = (CheckBox)findViewById(R.id.vip_chipiao);
        //理财师
        plannerTV = (TextView)findViewById(R.id.planner_select);

        //验证码
//        imageCodeIV = (ImageView) findViewById(R.id.imagecode_right);
//        imageCodeET = (EditText)findViewById(R.id.get_imagecode);

        refreshListView = (PullToRefreshListView) findViewById(R.id.biao_list);
        refreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        refreshListView.setScrollingWhileRefreshingEnabled(true);
        adapter = new InvestmentAdapter(this,new ArrayList(), true);
        refreshListView.setAdapter(adapter);


        moneyRecordEntity = (FinanceProductEntity) getIntent().getSerializableExtra("financeProductEntity");
        String userStr = readPreferences(NetLoginInterface.ANT_LOGIN_USER, "additional");
        try {
            if (!StringUtils.isBlank(userStr)){
                user = JsonUtil.decode(userStr, User.class);
                userId = user.getLoginName();
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }


        //设置标题
        ((TextView)findViewById(R.id.text_title)).setText(R.string.vip_title);
//        ((Button)findViewById(R.id.fd_buttom)).setText(R.string.vip_btn_);
        findViewById(R.id.fd_buttom).setVisibility(isCanBuy?View.VISIBLE:View.GONE);

        ((RadioGroup)findViewById(R.id.radioTab)).setOnCheckedChangeListener(new SubjectOnCheckedChangeListener());

        //选择理财师
        plannerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FinancialVIPDetailActivity.this, PlannerSelectActivity.class));
            }
        });

//        createCode();
        queryEntity();
    }

    private class SubjectOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            int page = 1;
            switch (i){
                case R.id.a_1:
                    m2.setVisibility(View.GONE);
                    m3.setVisibility(View.GONE);
                    m1.setVisibility(View.VISIBLE);
                    setMarginBottom(tab1, 1);
                    setMarginBottom(tab2, 0);
                    setMarginBottom(tab3, 0);
                    break;
                case R.id.a_2:
                    m1.setVisibility(View.GONE);
                    m3.setVisibility(View.GONE);
                    m2.setVisibility(View.VISIBLE);
                    setMarginBottom(tab1, 0);
                    setMarginBottom(tab2, 1);
                    setMarginBottom(tab3, 0);
                    break;
                case R.id.a_3:
                    m1.setVisibility(View.GONE);
                    m2.setVisibility(View.GONE);
                    m3.setVisibility(View.VISIBLE);
                    setMarginBottom(tab1, 0);
                    setMarginBottom(tab2, 0);
                    setMarginBottom(tab3, 1);
                    break;
                default:
                    return;
            }
        }
    }

    public void setMarginBottom(RadioButton view, int bottom){
        RadioGroup.LayoutParams params = (RadioGroup.LayoutParams) view.getLayoutParams();
        params.setMargins(0,0,0,bottom);
        view.setLayoutParams(params);
    }

    public void queryEntity(){
        NetFianceFragmentInterface netInterface = new NRestAdapter<NetFianceFragmentInterface>(this,
                Profile.SERVER_ADDRESS, NetFianceFragmentInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(this, "网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }

        netInterface.appFinanceDetail(moneyRecordEntity.getId(), user.getLoginName(), new NCallback<FinanceProductDetail>(this, FinanceProductDetail.class) {
            @Override
            public void onSuccess(int statusCode, List<Header> headers, FinanceProductDetail oRet) {
                financeProductDetail = oRet;
                if(financeProductDetail != null){
                    loadData();
                }
                queryList();
            }

            @Override
            public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                Log.d("------infoMessage" , infoMessage + "");
            }
        });

    }

    /**
     * 投标记录
     * 第3个tab页的数据
     */
    public void queryList(){

        NetFianceFragmentInterface netInterface = new NRestAdapter<NetFianceFragmentInterface>(this,
                Profile.SERVER_ADDRESS, NetFianceFragmentInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(this,"网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            netInterface.findApplyList(pageNo, pageSize, moneyRecordEntity.getId(), new NCallback<List<FinancePersonEntity>>(this, new TypeReference<List<FinancePersonEntity>>() {}) {

                @Override
                public void onSuccess(int statusCode, List<Header> headers, List<FinancePersonEntity> list) {
                    adapter.appendList(list);
                    Utility.setListViewHeightBasedOnChildren(refreshListView.getRefreshableView());
                    refreshListView.onRefreshComplete();

                    //是否有理财师
//                    isSetPlanner();
                }

                @Override
                public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                    //403定义为过期
                    if(infoMessage.contains("当前用户登录状态异常") || 403 == statusCode){
                        exitLogin();
                    } else {
//                        Toast.makeText(FinancialVIPDetailActivity.this, infoMessage, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
//            e.printStackTrace();
        }

    }

    private void loadData() {
//        financeProductEntity
        nameTV.setText(financeProductDetail.getName());
        expectIncomeTV.setText(financeProductDetail.getProfit() + "%");
        tempDaysTV.setText(financeProductDetail.getFinanceDays() + "天");
        surplusTV.setText(moneyRecordEntity.getExpectIncome() + "元");
        financingTV.setText("计划金额：" + financeProductDetail.getFinancing() + "元");





        isCanBuy = financeProductDetail.getProgress().doubleValue() >= 100;

        if(financeProductDetail.getProgress().doubleValue() < 100 && "release".equals(moneyRecordEntity.getDraftState())){
            isCanBuy = true;
        } else {
            isCanBuy = false;
        }


        findViewById(R.id.fd_buttom).setVisibility(isCanBuy?View.VISIBLE:View.GONE);
        findViewById(R.id.a_4_layout).setVisibility(isCanBuy?View.VISIBLE:View.GONE);

        String count = financeProductDetail.getCount();
        if (count != null && "1".equals(count)) {
            ((Button)findViewById(R.id.fd_buttom)).setText(R.string.vip_btn_already);
            ((Button)findViewById(R.id.fd_buttom)).setBackgroundResource(R.drawable.shape_rectangle_rounded_hintcolor);
            isCanBuy = false;
        } else {
            ((Button)findViewById(R.id.fd_buttom)).setText(R.string.vip_btn_);
            ((Button)findViewById(R.id.fd_buttom)).setBackgroundResource(R.drawable.shape_rectangle_rounded_red);
            isCanBuy = true;
        }

        //投资信息
        date1.setText(getString(R.string.finance_detail_date1) + "  " + financeProductDetail.getValueDate());
        date2.setText(getString(R.string.finance_detail_date2) + "  " + financeProductDetail.getCeaseDate());
        date3.setText(getString(R.string.finance_detail_date3) + "  " + financeProductDetail.getLatestRepaymentDate());

        //汇票信息
        StringBuilder sb = new StringBuilder();
        if(!StringUtils.isBlank(financeProductDetail.getCouponOne())){
            sb.append("    " + financeProductDetail.getCouponOne() + "\r\n");
        }
        if(!StringUtils.isBlank(financeProductDetail.getCouponTwo())){
            sb.append("    " + financeProductDetail.getCouponTwo() + "\r\n");
        }
        if(!StringUtils.isBlank(financeProductDetail.getCouponThree())){
            sb.append("    " + financeProductDetail.getCouponThree() + "\r\n");
        }
        if(!StringUtils.isBlank(financeProductDetail.getCouponFour())){
            sb.append("    " + financeProductDetail.getCouponFour() + "\r\n");
        }
        if(!StringUtils.isBlank(financeProductDetail.getCouponFive())){
            sb.append("    " + financeProductDetail.getCouponFive() + "\r\n");
        }
//        messageTV.setText(sb.toString());

        if(!StringUtils.isBlank(financeProductDetail.getImgPath())){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final String imgs[] = financeProductDetail.getImgPath().split(",");
                        final int viewids[] = {R.id.fd_message_image1,R.id.fd_message_image2,R.id.fd_message_image3,R.id.fd_message_image4,R.id.fd_message_image5,
                                R.id.fd_message_image6,R.id.fd_message_image7,R.id.fd_message_image8,R.id.fd_message_image9,R.id.fd_message_image10};
                        //多张图片，现在只显示一张
                        final  List<Bitmap> bitmapList = new ArrayList<Bitmap>();
                        final  List<String> imgIdlist = new ArrayList<String>();
                        if (imgs != null && imgs.length > 0) {
                                int i = 0;
                                for (String imgId : imgs) {
                                    if (imgId != null && !"".equals(imgId)) {
                                        Bitmap bitmap = BitmapFactory.decodeStream(new URL(Profile.SERVER_ADDRESS + imgId).openStream());
                                        bitmapList.add(bitmap);
                                        imgIdlist.add(imgId);
                                        i++;
                                        if (i >= 10) {
                                            break;
                                        }
                                    }
                                }
                        }

                        runOnUiThread(new Runnable(){
                            @Override
                            public void run() {
                                if (bitmapList != null && bitmapList.size() > 0) {
                                     int i = 0;
                                     for (Bitmap bitmap : bitmapList) {
                                         final  ImageView iv = (ImageView) findViewById(viewids[i]);
                                         final  String imgPath = imgIdlist.get(i);
                                         iv.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 Intent intent = new Intent();
                                                 intent.setClass(FinancialVIPDetailActivity.this, ImageActivity.class);
                                                 intent.putExtra("imagePath", Profile.SERVER_ADDRESS + imgPath);
                                                 startActivity(intent);
                                             }
                                         });
                                         iv.setImageBitmap(bitmap);
                                         i++;
                                     }
                                }
                            }
                        });
                    } catch (Exception e) {
//            e.printStackTrace();
                    }
                }
            }).start();
        }




    }

    @Override
    public void onClick(View v) {
        if (!isCanBuy) {
            return;
        }
        if(R.id.fd_buttom == v.getId()){
            //是否认证
            if(!VerifiedUtil.isVerified(this, user.getLoginName())){
                startActivity(new Intent(this, VerifiedActivity.class));
                finish();
                return;
            }

            //图片验证码
//            if(StringUtils.isBlank(imageCodeET.getText().toString())){
//                Toast.makeText(this,"请输入图形验证码!", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            if(imageCodeET.getText().toString().length() != 4){
//                Toast.makeText(this,"图形验证码不正确!", Toast.LENGTH_SHORT).show();
//                return;
//            }

            //理财师判断
            isSetPlanner();

            return;
        } else if(R.id.imagecode_right == v.getId()){
            //图片验证码
//            createCode();
            return;
        }

        super.onClick(v);
    }

    //生成验证码
//    private void createCode(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    final Bitmap bitmap = VerifyCode.getCodeBitmap(userId);
//                    runOnUiThread(new Runnable(){
//                        @Override
//                        public void run() {
//                            imageCodeIV.setImageBitmap(bitmap);
//                        }
//                    });
//                } catch (Exception e) {
////            e.printStackTrace();
//                }
//            }
//        }).start();
//    }

    public void appVerifyVipCode(){
        NetFianceFragmentInterface netInterface = new NRestAdapter<NetFianceFragmentInterface>(this,
                Profile.SERVER_ADDRESS, NetFianceFragmentInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(this, "网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }

        netInterface.appVerifyVipCode(moneyRecordEntity.getId(),
                user.getLoginName(),
                "",
                "",
                "2",
                (vip_chipiao.isChecked()) ? "1":"0",
                new NCallback<String>(this, String.class) {
            @Override
            public void onSuccess(int statusCode, List<Header> headers, String oRet) {
                Toast.makeText(FinancialVIPDetailActivity.this, "申请成功!稍后我们客服会和您联系，请保持电话畅通!", Toast.LENGTH_SHORT).show();
                findViewById(R.id.fd_buttom).setVisibility(View.GONE);
            }

            @Override
            public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                if("错误".equals(infoMessage)){
                    Toast.makeText(FinancialVIPDetailActivity.this, "验证码错误!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if("重复".equals(infoMessage)){
                    Toast.makeText(FinancialVIPDetailActivity.this, "您已经提交过申请!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }

    /**
     * 是否设定了理财师
     * 未设定理财师，给出提示：设定理财师对话框
     */
    public void isSetPlanner(){

        PlannerNetInterface netInterface = new NRestAdapter<PlannerNetInterface>(this,
                Profile.SERVER_ADDRESS, PlannerNetInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(this, "网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }

        netInterface.getMyAdviser(
                user.getLoginName(),
                new NCallbackMsg() {

                    @Override
                    public void onSuccess(int statusCode, List<Header> headers, String result) {

                        if("0".equals(result)){
                            //设定理财师对话框
                            new PlannerSelectDialog(FinancialVIPDetailActivity.this).show();
                        } else {
                            //立即申请
                            appVerifyVipCode();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                        //立即申请
                        appVerifyVipCode();
                    }
                });
    }

}
