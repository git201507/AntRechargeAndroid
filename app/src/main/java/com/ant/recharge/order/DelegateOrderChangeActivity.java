package com.ant.recharge.order;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.common.BaseFragment;
import com.ant.recharge.common.BaseFragmentActivity;
import com.ant.recharge.common.BaseViewPager;
import com.ant.recharge.common.NCallback;
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.StringUtils;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.entity.OrderEntity;
import com.ant.recharge.entity.TelephoneEntity;
import com.ant.recharge.entity.User;
import com.ant.recharge.login.NetLoginInterface;
import com.ant.recharge.webview.WebviewYLActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit.client.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 *  主页：订单状态改变过程
 *  由7个Fragment组成
 *  step1: 未付款
 *  step2: 已付款（写卡）
 *  step3: 未认证
 *  step4: 审核中
 *  step5: 已认证（可确认完成或退款）
 *  step6: 已完成
 *  step7: 退款中
 */
public class DelegateOrderChangeActivity extends BaseFragmentActivity {

    private DrawerLayout mDrawerLayout;
    private RelativeLayout drawer_content;

    private int currentPage = 0;//第一页
    private ViewPager viewPager;

    private BaseFragment fragmentWaitPay;
    private BaseFragment fragmentWriteCard;
    private BaseFragment fragmentWaitUpload;
    private BaseFragment fragmentCertificating;
    private BaseFragment fragmentConfirm;
    private BaseFragment fragmentRollBack;
    private BaseFragment fragmentComplete;

    private OrderEntity order;
    private TelephoneEntity telephone;

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public TelephoneEntity getTelephone() {
        return telephone;
    }

    public void setOrder(TelephoneEntity telephone) {
        this.telephone = telephone;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(this, R.layout.activity_delegate_order_change);

        Intent intent = getIntent();
        currentPage = intent.getIntExtra("stepPage", 0);
        order = (OrderEntity) intent.getSerializableExtra("order");
        telephone = (TelephoneEntity) intent.getSerializableExtra("telephone");
        //分页
        viewPager = (BaseViewPager) findViewById(R.id.viewPager);
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        //第1步 等待支付
        fragmentWaitPay = new FragmentWaitPay();
        fragmentList.add(fragmentWaitPay);

        //第2步 写卡
        fragmentWriteCard = new FragmentWriteCard();
        fragmentList.add(fragmentWriteCard);

        //第3步 去认证
        fragmentWaitUpload = new FragmentWaitUpload();
        fragmentList.add(fragmentWaitUpload);

        //第4步 审核中
        fragmentCertificating = new FragmentCertificating();
        fragmentList.add(fragmentCertificating);

        //第5步 已认证
        fragmentConfirm = new FragmentConfirm();
        fragmentList.add(fragmentConfirm);

        //第6步 已完成
        fragmentComplete = new FragmentComplete();
        fragmentList.add(fragmentComplete);

        //第7步 退款中
        fragmentRollBack = new FragmentRollback();
        fragmentList.add(fragmentRollBack);

        viewPager.setAdapter(new TabPagerAdapter(getSupportFragmentManager(), fragmentList));
//        viewPager.setPageTransformer(true, new MainPageTransformer());

        viewPager.setCurrentItem(currentPage);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
//                switch (position){
//                    case 0:
//                        loadPage((RadioButton)findViewById(R.id.tab_recommend));
//                        break;
//                    case 1:
//                        loadPage((RadioButton)findViewById(R.id.tab_liang));
//                        break;
//                    case 2:
//                        loadPage((RadioButton)findViewById(R.id.tab_3));
//                        break;
//                    case 3:
//                        loadPage((RadioButton)findViewById(R.id.tab_person_center));
//                        break;
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class TabPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> list;

        public TabPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

    //加载分页
    public void loadStepPage(int pageNumber){
        currentPage = pageNumber;

        viewPager.setCurrentItem(currentPage);

    }

    public interface NetInterface{
        //头部信息
        @POST("/setOrderStatus.do")
        public void setOrderStatus(@Query("token") String token,
                                   @Query("orderId") String orderId,
                                   @Query("action") String action,
                                   @Query("pic1") String pic1,
                                   @Query("pic2") String pic2,
                                   @Query("pic3") String pic3,
                          NCallback<OrderEntity> callback);
    }
}
