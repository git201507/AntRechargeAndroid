package com.ant.recharge.main;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ant.recharge.R;
import com.ant.recharge.common.BaseFragment;
import com.ant.recharge.common.BaseFragmentActivity;
import com.ant.recharge.common.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 *  主页：登录后显示的界面
 *  由4个tab组成
 *  tab1: 我要理财（Fragment01）
 *  tab2: 我的账户（Fragment02）
 *  tab3: 我有票据（Fragment03）
 *  tab4: 精彩活动（Fragment04）
 */
public class MainActivity extends BaseFragmentActivity implements RadioGroup.OnCheckedChangeListener{


    private int currentPage = 1;//第2位  我的账户
    private ViewPager viewPager;


    private BaseFragment fragment01;
    private BaseFragment fragment02;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        initView(this, R.layout.activity_main);

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
        ((RadioButton)findViewById(R.id.tab_1)).setCompoundDrawables(null, d, null, null);
        ((RadioButton)findViewById(R.id.tab_2)).setCompoundDrawables(null, d2, null, null);
        ((RadioButton)findViewById(R.id.tab_3)).setCompoundDrawables(null, d3, null, null);
        ((RadioButton)findViewById(R.id.tab_4)).setCompoundDrawables(null, d4, null, null);

        //分页
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragment01 = new Fragment01();
        fragment02 = new Fragment02();
        fragmentList.add(fragment01);
        fragmentList.add(fragment02);
        fragmentList.add(new Fragment03());
        fragmentList.add(new Fragment04());
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
                        loadPage((RadioButton)findViewById(R.id.tab_1));
                        break;
                    case 1:
                        loadPage((RadioButton)findViewById(R.id.tab_2));
                        break;
                    case 2:
                        loadPage((RadioButton)findViewById(R.id.tab_3));
                        break;
                    case 3:
                        loadPage((RadioButton)findViewById(R.id.tab_4));
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
            case R.id.tab_1:
                currentPage = 0;
                if(fragment01 == null){
                    return;
                }
                //我要理财
                fragment01.update(BaseFragment.type_list, new BaseFragment.OnFragmentListener() {
                    @Override
                    public void onSuccess() {}
                    @Override
                    public void onFail() {}
                }, 1, 2);
                fragment01.update(BaseFragment.type_count, new BaseFragment.OnFragmentListener() {
                    @Override
                    public void onSuccess() {
                    }
                    @Override
                    public void onFail() {}
                });
                break;
            case R.id.tab_2:
                currentPage = 1;
                //我的账户
                fragment02.update(BaseFragment.type_count, new BaseFragment.OnFragmentListener() {
                    @Override
                    public void onSuccess() { }
                    @Override
                    public void onFail() {}
                });
                break;
            case R.id.tab_3:
                currentPage = 2;
                //我有票据
                break;
            case R.id.tab_4:
                currentPage = 3;
                //精彩活动
                break;
            default:
                break;
        }

        viewPager.setCurrentItem(currentPage);
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
    public void loadPage2(){
        loadPage((RadioButton)findViewById(R.id.tab_2));
    }


    //加载分页
    public void loadPage(RadioButton radioButton){
        radioButton.setChecked(true);
    }

}
