package com.ant.recharge.main;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ant.recharge.R;
import com.ant.recharge.application.MyApplication;
import com.ant.recharge.common.AlertDialog;
import com.ant.recharge.common.BaseFragment;
import com.ant.recharge.common.BaseFragmentActivity;
import com.ant.recharge.common.CrashHandler;
import com.ant.recharge.common.DisplayUtil;
import com.ant.recharge.common.StringUtils;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.entity.User;
import com.ant.recharge.login.LoginActivity;
import com.ant.recharge.login.NetLoginInterface;
import com.ant.recharge.model.FiterBean;

import java.util.ArrayList;
import java.util.List;

/**
 *  主页：登录后显示的界面
 *  由4个tab组成
 *  tab1: 推荐号码（FragmentRecommend）
 *  tab2: 靓号购买（FragmentLiang）
 *  tab3: 在线充值（FragmentRecharge）
 *  tab4: 个人中心（PersonCenterFragment）
 */
public class MainTabActivity extends BaseFragmentActivity implements RadioGroup.OnCheckedChangeListener{

    private DrawerLayout mDrawerLayout;
    private RelativeLayout drawer_content;

    public String[] mString = {"价格", "运营商", "网络"};
    public String[] vString = {"全部", "全部", "全部"};

    public String[] pString = {"辽宁", "吉林", "黑龙江", "直辖市", "广东", "广西", "内蒙古"};
    public String[] cString = {"沈阳", "长春", "哈尔滨", "北京", "广州", "南宁", "呼伦贝尔"};

    public String[] pickedCity = {"0", "省份", "0", "城市"};

    private int currentPage = 0;//第一页推荐号码
    private NoScrollViewPager viewPager;

    private BaseFragment fragmentRecommend;
    private BaseFragment fragmentLiang;

    private AlertDialog dialog;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler());
        MyApplication myApplication = (MyApplication) getApplicationContext();
        myApplication.setMainTabActivity(this);

        initView(this, R.layout.activity_main_tab);

        initView();
        initData();

        //radio图片缩放
        int width = (int) DisplayUtil.dip2px(this, 20);
        Drawable d = getResources().getDrawable(R.drawable.tab_bg_1);
        Drawable d2 = getResources().getDrawable(R.drawable.tab_bg_2);
        Drawable d3 = getResources().getDrawable(R.drawable.tab_bg_3);
        Drawable d4 = getResources().getDrawable(R.drawable.tab_bg_4);
        d.setBounds(0, 0, width, width);
        d2.setBounds(0, 0, width, width);
        d3.setBounds(0, 0, width, width);
        d4.setBounds(0, 0, width, width);
        ((RadioButton)findViewById(R.id.tab_recommend)).setCompoundDrawables(null, d, null, null);
        ((RadioButton)findViewById(R.id.tab_liang)).setCompoundDrawables(null, d2, null, null);
        ((RadioButton)findViewById(R.id.tab_recharge)).setCompoundDrawables(null, d3, null, null);
        ((RadioButton)findViewById(R.id.tab_person_center)).setCompoundDrawables(null, d4, null, null);

        //分页
        viewPager = (NoScrollViewPager)findViewById(R.id.viewPager);
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        //第1个Tab页 推荐号码
        fragmentRecommend = new FragmentRecommend();
        fragmentList.add(fragmentRecommend);

        //第2个Tab页 推荐号码
        fragmentLiang = new FragmentLiang();
        fragmentList.add(fragmentLiang);

        fragmentList.add(new FragmentRecharge());
        fragmentList.add(new PersonCenterFragment());
        viewPager.setAdapter(new TabPagerAdapter(getSupportFragmentManager(), fragmentList));
//        viewPager.setPageTransformer(true, new MainPageTransformer());

        viewPager.setCurrentItem(currentPage);
        //listener
        ((RadioGroup)findViewById(R.id.radioTab)).setOnCheckedChangeListener(this);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        loadPage((RadioButton)findViewById(R.id.tab_recommend));
                        break;
                    case 1:
                        loadPage((RadioButton)findViewById(R.id.tab_liang));
                        break;
                    case 2:
                        loadPage((RadioButton)findViewById(R.id.tab_recharge));
                        break;
                    case 3:
                        SharedPreferences preferences = getSharedPreferences(NetLoginInterface.ANT_LOGIN_USER, MODE_PRIVATE);
                        String userStr = preferences.getString("additional", null);
                        try {
                            if (!StringUtils.isBlank(userStr)){
                                User user = JsonUtil.decode(userStr, User.class);
                                if (!StringUtils.isBlank(user.getToken())){
                                    loadPage((RadioButton)findViewById(R.id.tab_person_center));
                                    //个人中心
                                }
                                else {
                                    Intent intent = new Intent();
                                    intent.setClass(MainTabActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            }
                            else {
                                Intent intent = new Intent();
                                intent.setClass(MainTabActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId){
            case R.id.tab_recommend:
                currentPage = 0;
                if(fragmentRecommend == null){
                    return;
                }
                //推荐号码
//                fragmentRecommend.update(BaseFragment.type_list, new BaseFragment.OnFragmentListener() {
//                    @Override
//                    public void onSuccess() {}
//                    @Override
//                    public void onFail() {}
//                }, 1, 1);
                break;
            case R.id.tab_liang:
                currentPage = 1;
                //我的账户
//                fragment02.update(BaseFragment.type_count, new BaseFragment.OnFragmentListener() {
//                    @Override
//                    public void onSuccess() { }
//                    @Override
//                    public void onFail() {}
//                });
                break;
            case R.id.tab_recharge:
                currentPage = 2;
                //我有票据
                break;
            case R.id.tab_person_center:

                SharedPreferences preferences = getSharedPreferences(NetLoginInterface.ANT_LOGIN_USER, MODE_PRIVATE);
                String userStr = preferences.getString("additional", null);
                try {
                    if (!StringUtils.isBlank(userStr)){
                        User user = JsonUtil.decode(userStr, User.class);
                        if (!StringUtils.isBlank(user.getToken())){
                            currentPage = 3;
                            //个人中心
                        }
                        else {
                            Intent intent = new Intent();
                            intent.setClass(this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                    else {
                        Intent intent = new Intent();
                        intent.setClass(this, LoginActivity.class);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            default:
                break;
        }

        viewPager.setCurrentItem(currentPage);
    }
    public void updateAllFragment(){
        //推荐号码
        fragmentRecommend.update(BaseFragment.type_list, new BaseFragment.OnFragmentListener() {
            @Override
            public void onSuccess() {}
            @Override
            public void onFail() {}
        }, 1, 1);

        fragmentLiang.update(BaseFragment.type_list, new BaseFragment.OnFragmentListener() {
            @Override
            public void onSuccess() {}
            @Override
            public void onFail() {}
        }, 1, 1);
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

    //加载主页
    public void loadPageRecommend(){
        loadPage((RadioButton)findViewById(R.id.tab_recommend));
    }


    //加载分页
    public void loadPage(RadioButton radioButton){
        radioButton.setChecked(true);
    }

    //加载分页
    public void loadPageMapping(View v){
        if(R.id.recommend_r1 == v.getId()) {
            loadPage((RadioButton) findViewById(R.id.tab_liang));
        }
        if(R.id.recommend_r2 == v.getId()) {
            loadPage((RadioButton) findViewById(R.id.tab_recharge));
        }
        if(R.id.recommend_r3 == v.getId()) {
            loadPage((RadioButton) findViewById(R.id.tab_person_center));
        }
    }

    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer_content = (RelativeLayout) findViewById(R.id.drawer_content);
    }

    private void initData() {
        //显示默认fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.drawer_content, new com.ant.recharge.main.FitrerFragment()).commit();
    }

    public void openFilterDrawer(){
        getSupportFragmentManager().beginTransaction().replace(R.id.drawer_content, new com.ant.recharge.main.FitrerFragment()).commit();
        mDrawerLayout.openDrawer(drawer_content);//打开抽屉内容
    }

    public void searchLiangTel(){
        ((FragmentLiang)fragmentLiang).searchLiangTel();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //
        if(1 == requestCode){
//            Log.d("-----", "-----------resultCode=" + resultCode);
//            Log.d("-----", "-----------data=" + data.toString());
            if(200 == resultCode){
                //推荐号码
                fragmentRecommend.update(BaseFragment.type_list, new BaseFragment.OnFragmentListener() {
                    @Override
                    public void onSuccess() {}
                    @Override
                    public void onFail() {}
                }, 1, 1);
            }
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            dialog = new AlertDialog(MainTabActivity.this)
                    .setCustomMessage(MainTabActivity.this.getString(R.string.exit_alert))
                    .setPositiveButton(MainTabActivity.this.getString(R.string.action_confirm), new View.OnClickListener() {
                        @Override
                        public void onClick(View paramView) {
                            dialog.dismiss();

                            finish();
                        }
                    })
                    .setNegativeButton(MainTabActivity.this.getString(R.string.action_cancel), new View.OnClickListener() {
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

        return false;

    }
    /**监听对话框里面的button点击事件*/
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()
    {
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
                    finish();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                    break;
                default:
                    break;
            }
        }
    };
}
