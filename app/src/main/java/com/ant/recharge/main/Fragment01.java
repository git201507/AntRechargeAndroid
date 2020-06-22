package com.ant.recharge.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.adapter.SubjectAdapter;
import com.ant.recharge.common.BaseFragment;
import com.ant.recharge.common.FontHelper;
import com.ant.recharge.common.NCallback;
import com.ant.recharge.common.NCallbackMsg;
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.StringUtils;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.common.pull2fresh.PullToRefreshBase;
import com.ant.recharge.common.pull2fresh.PullToRefreshListView;
import com.ant.recharge.common.ui.VipDialog2;
import com.ant.recharge.entity.FinanceProductEntity;
import com.ant.recharge.entity.User;
import com.ant.recharge.login.NetLoginInterface;

import org.codehaus.jackson.type.TypeReference;

import java.util.ArrayList;
import java.util.List;

import retrofit.client.Header;

/**
 *
 * 标
 */
@SuppressLint("ValidFragment")
public class Fragment01 extends BaseFragment implements View.OnClickListener{

    private MainActivity context;
    //加载提示
//    private View text_loading;
    private View view;

    private PullToRefreshListView refreshListView;
    private ListView listView;
    private SubjectAdapter subjectAdapter;

    private int page = 1;
    private int pageSize = 6;
    private int type = 2;
    private String token;
    //
    private View tab1;
    private View tab2;
    private View tab3;
    private TextView tab1TV;
    private TextView tab2TV;
    private TextView tab3TV;

    private TextView vipTV;

    public Fragment01() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.context = (MainActivity) getActivity();
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_1, container, false);
        FontHelper.applyFont(getActivity(), view);

        //没有返回按钮
        view.findViewById(R.id.btn_back).setOnClickListener(this);
        //设置标题
        ((TextView)view.findViewById(R.id.text_title)).setText("理财产品");
//        text_loading  = view.findViewById(R.id.text_loading);

        refreshListView = (PullToRefreshListView) view.findViewById(R.id.fragment1_list);
        refreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        refreshListView.setScrollingWhileRefreshingEnabled(true);
        listView = refreshListView.getRefreshableView();

        vipTV = (TextView) view.findViewById(R.id.fragment1_vip);
        tab1 = view.findViewById(R.id.a_1);
        tab2 = view.findViewById(R.id.a_2);
        tab3 = view.findViewById(R.id.a_3);

        tab1TV = (TextView) view.findViewById(R.id.a_12);
        tab2TV = (TextView) view.findViewById(R.id.a_22);
        tab3TV = (TextView) view.findViewById(R.id.a_32);

        SharedPreferences preferences = context.getSharedPreferences(NetLoginInterface.ANT_LOGIN_USER, Context.MODE_PRIVATE);
        String userStr = preferences.getString("additional", null);
        User user = null;
        try {
            user = JsonUtil.decode(userStr, User.class);
            token = user.getToken();
//            subjectAdapter.setToken(user.getToken());
        } catch (Exception e) {
//            e.printStackTrace();
        }
//        subjectAdapter.appendPage();
        List list = new ArrayList<FinanceProductEntity>();
        subjectAdapter = new SubjectAdapter(getActivity(), list, user.getLoginName());
        refreshListView.setAdapter(subjectAdapter);

        refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                queryList(page, type, new OnFragmentListener() {
                    @Override
                    public void onSuccess() {
                        refreshListView.onRefreshComplete();
                    }
                    @Override
                    public void onFail() {
                        refreshListView.onRefreshComplete();
                    }
                });
            }
        });

//        ((RadioGroup)view.findViewById(R.id.radioTab)).setOnCheckedChangeListener(new SubjectOnCheckedChangeListener());
        SubjectOnClickListener subjectOnClickListener = new SubjectOnClickListener();
        view.findViewById(R.id.a_1).setOnClickListener(subjectOnClickListener);
        view.findViewById(R.id.a_2).setOnClickListener(subjectOnClickListener);
        view.findViewById(R.id.a_3).setOnClickListener(subjectOnClickListener);

        vipTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VipDialog2 dialog = new VipDialog2(getActivity());
                dialog.show();
            }
        });

//        queryCount();
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public void queryCount(final OnFragmentListener fragmentListener){
        NetFianceFragmentInterface netInterface = new NRestAdapter<NetFianceFragmentInterface>(context,
                Profile.SERVER_ADDRESS, NetFianceFragmentInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(context,"网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }

        netInterface.getDraftNumber(new NCallbackMsg() {
            @Override
            public void onSuccess(int statusCode, List<Header> headers, String result) {

                if(!StringUtils.isBlank(result)){
                    String c[] = result.split("-");
                    if(Integer.parseInt(c[0]) > 0){
                        tab3TV.setVisibility(View.VISIBLE);
                        tab3TV.setText(c[0]);//vip
                    } else {
                        tab3TV.setVisibility(View.GONE);
                    }

                    if(Integer.parseInt(c[1]) > 0){
                        tab2TV.setVisibility(View.VISIBLE);
                        tab2TV.setText(c[1]);//putong
                    } else {
                        tab2TV.setVisibility(View.GONE);
                    }

                    if(Integer.parseInt(c[2]) > 0){
                        tab1TV.setVisibility(View.VISIBLE);
                        tab1TV.setText(c[2]);//xinshou
                    } else {
                        tab1TV.setVisibility(View.GONE);
                    }
                }
//                queryList(page, type);
                fragmentListener.onSuccess();
            }

            @Override
            public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                fragmentListener.onFail();
                Toast.makeText(context, infoMessage, Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void queryList(final int page,final int currentType, final OnFragmentListener fragmentListener){

        this.page = page;
        NetFianceFragmentInterface netInterface = new NRestAdapter<NetFianceFragmentInterface>(context,
                Profile.SERVER_ADDRESS, NetFianceFragmentInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(context,"网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }

//        text_loading.setVisibility(View.VISIBLE);

        if(1 == currentType){
            try {
                netInterface.fianceNewProductList(page, pageSize, token, new NCallback<List<FinanceProductEntity>>(context, new TypeReference<List<FinanceProductEntity>>() {}) {
                    @Override
                    public void onSuccess(int statusCode, List<Header> headers, List<FinanceProductEntity> moneyRecordEntityList) {
                        fragmentListener.onSuccess();

                        if(type != currentType){
//                            text_loading.setVisibility(View.GONE);
                            return;
                        }

                        for(int i = 0; i < moneyRecordEntityList.size(); i++){
                            moneyRecordEntityList.get(i).setNews(true);
                        }

                        subjectAdapter.appendList(moneyRecordEntityList);
//                        text_loading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                        fragmentListener.onFail();
                        //403定义为过期
//                    if(infoMessage.contains("当前用户登录状态异常") || 403 == statusCode){
//                        exitLogin();
//                    } else {
                        Toast.makeText(context, infoMessage, Toast.LENGTH_SHORT).show();
//                        text_loading.setVisibility(View.GONE);
//                    }
                    }

                });
            } catch (Exception e) {
//            e.printStackTrace();
            }
        } else if (2 == currentType){
            try {
                netInterface.fianceProductList( page, pageSize, token, new NCallback<List<FinanceProductEntity>>(context, new TypeReference<List<FinanceProductEntity>>() {}) {

                    @Override
                    public void onSuccess(int statusCode, List<Header> headers, List<FinanceProductEntity> moneyRecordEntityList) {
                        fragmentListener.onSuccess();
                        if(type != currentType){
//                            text_loading.setVisibility(View.GONE);
                            return;
                        }
                        subjectAdapter.appendList(moneyRecordEntityList);
//                        text_loading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                        fragmentListener.onFail();
                        //403定义为过期
//                    if(infoMessage.contains("当前用户登录状态异常") || 403 == statusCode){
//                        exitLogin();
//                    } else {
                        Toast.makeText(context, infoMessage, Toast.LENGTH_SHORT).show();
//                        text_loading.setVisibility(View.GONE);
//                    }
                    }

                });
            } catch (Exception e) {
//            e.printStackTrace();
            }

        } else if (3 == currentType){
            try {
                netInterface.fianceVipProductList( page, pageSize, token, new NCallback<List<FinanceProductEntity>>(context, new TypeReference<List<FinanceProductEntity>>() {}) {

                    @Override
                    public void onSuccess(int statusCode, List<Header> headers, List<FinanceProductEntity> moneyRecordEntityList) {
                        fragmentListener.onSuccess();
                        if(type != currentType){
//                            text_loading.setVisibility(View.GONE);
                            return;
                        }

                        if(moneyRecordEntityList == null || moneyRecordEntityList.size() <= 0){
//                            text_loading.setVisibility(View.GONE);
                            return;
                        }

                        for(int i = 0; i < moneyRecordEntityList.size(); i++){
                            moneyRecordEntityList.get(i).setVip(true);
                        }

                        subjectAdapter.appendList(moneyRecordEntityList);
//                        text_loading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                        fragmentListener.onFail();
                        //403定义为过期
//                    if(infoMessage.contains("当前用户登录状态异常") || 403 == statusCode){
//                        exitLogin();
//                    } else {
                        Toast.makeText(context, infoMessage, Toast.LENGTH_SHORT).show();
//                        text_loading.setVisibility(View.GONE);
//                    }
                    }
                });
            } catch (Exception e) {
//            e.printStackTrace();
            }

        }

    }

    @Override
    public void onClick(View view) {
        context.loadPage2();
    }

    private class SubjectOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.a_1:
                    onTab1Checked();
                    break;
                case R.id.a_2:
                    onTab2Checked();
                    break;
                case R.id.a_3:
                    onTab3Checked();
                    break;
                default:
                    return;
            }
            subjectAdapter.replaceList(new ArrayList());

            update(BaseFragment.type_count, new BaseFragment.OnFragmentListener() {
                @Override
                public void onSuccess() {
                    update(BaseFragment.type_list, new BaseFragment.OnFragmentListener() {
                        @Override
                        public void onSuccess() {}
                        @Override
                        public void onFail() {}
                    }, 1, type);
                }
                @Override
                public void onFail() {}
            });

        }
    }

    //标签1被选中
    private void onTab1Checked() {
        vipTV.setVisibility(View.GONE);
        setMarginBottom(tab1, 1);
        setMarginBottom(tab2, 0);
        setMarginBottom(tab3, 0);
        type = 1;
    }

    //标签2被选中
    private void onTab2Checked() {
        vipTV.setVisibility(View.GONE);
        setMarginBottom(tab1, 0);
        setMarginBottom(tab2, 1);
        setMarginBottom(tab3, 0);
        type = 2;
    }

    //标签3被选中
    private void onTab3Checked() {
        vipTV.setVisibility(View.VISIBLE);
        setMarginBottom(tab1, 0);
        setMarginBottom(tab2, 0);
        setMarginBottom(tab3, 1);
        type = 3;
    }


    public void setMarginBottom(View view, int bottom){
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.setMargins(0,0,0,bottom);
        view.setLayoutParams(params);
    }



    @Override
    public void update(int type, OnFragmentListener fragmentListener, Object... arguments) {
        switch (type) {
            case type_count:
                queryCount(fragmentListener);
                break;
            case type_list:
                final int page = Integer.parseInt(arguments[0].toString());
                final int currentType = Integer.parseInt(arguments[1].toString());

                switch (currentType) {
                    case 1:
                        onTab1Checked();
                        break;
                    case 2:
                        onTab2Checked();
                        break;
                    case 3:
                        onTab3Checked();
                        break;
                }

                subjectAdapter.replaceList(new ArrayList<FinanceProductEntity>());
                queryList(page, currentType, fragmentListener);
                break;
            default:
                break;
        }

        return;
    }
}
