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
import com.ant.recharge.adapter.RecommendAdapter;
import com.ant.recharge.common.BaseFragment;
import com.ant.recharge.common.DateString;
import com.ant.recharge.common.FontHelper;
import com.ant.recharge.common.NCallback;
import com.ant.recharge.common.NCallbackMsg;
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.StringUtils;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.common.pull2fresh.PullToRefreshBase;
import com.ant.recharge.common.pull2fresh.PullToRefreshListView;
import com.ant.recharge.entity.FinanceProductEntity;
import com.ant.recharge.entity.TelephoneEntity;
import com.ant.recharge.entity.User;
import com.ant.recharge.login.NetLoginInterface;

import org.codehaus.jackson.type.TypeReference;

import java.util.ArrayList;
import java.util.List;

import retrofit.client.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 *
 * 推荐号码
 */
@SuppressLint("ValidFragment")
public class FragmentRecommend extends BaseFragment implements View.OnClickListener{

    private MainTabActivity context;
    //加载提示
//    private View text_loading;
    private View view;

    private PullToRefreshListView refreshListView;
    private ListView listView;
    private RecommendAdapter recommendAdapter;

    private int page = 1;
    private int pageSize = 6;
    private int type = 1;
    private String token;
    private String companyCode;

    public FragmentRecommend() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view != null) {
            return view;
        }
        this.context = (MainTabActivity) getActivity();
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_recommend, container, false);
        FontHelper.applyFont(getActivity(), view);

        //没有返回按钮
        view.findViewById(R.id.btn_back).setVisibility(View.GONE);
        view.findViewById(R.id.text_title_right).setOnClickListener(this);

        //设置系统当前日期
        ((TextView)view.findViewById(R.id.text_title_right)).setText(DateString.StringData());
        ((TextView)view.findViewById(R.id.text_title_right)).setVisibility(View.INVISIBLE);

        //设置标题
        ((TextView)view.findViewById(R.id.text_title)).setText(R.string.recommend_title);
//        text_loading  = view.findViewById(R.id.text_loading);

        refreshListView = (PullToRefreshListView) view.findViewById(R.id.fragment_recommend_list);
        refreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        refreshListView.setScrollingWhileRefreshingEnabled(true);
        listView = refreshListView.getRefreshableView();

        SharedPreferences preferences = context.getSharedPreferences(NetLoginInterface.ANT_LOGIN_USER, Context.MODE_PRIVATE);
        String userStr = preferences.getString("additional", null);
        User user = null;
        try {
            user = JsonUtil.decode(userStr, User.class);
            token = user.getToken();
        } catch (Exception e) {
//            e.printStackTrace();
        }
        List list = new ArrayList<FinanceProductEntity>();
        recommendAdapter = new RecommendAdapter(getActivity(), list);
        refreshListView.setAdapter(recommendAdapter);

        refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                companyCode = "";
                refreshList(new OnFragmentListener() {
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
        SubjectOnClickListener subjectOnClickListener = new SubjectOnClickListener();
        view.findViewById(R.id.recommend_r1).setOnClickListener(subjectOnClickListener);
        view.findViewById(R.id.recommend_r2).setOnClickListener(subjectOnClickListener);
        view.findViewById(R.id.recommend_r3).setOnClickListener(subjectOnClickListener);
        view.findViewById(R.id.yun_r1).setOnClickListener(subjectOnClickListener);
        view.findViewById(R.id.yun_r2).setOnClickListener(subjectOnClickListener);
        view.findViewById(R.id.yun_r3).setOnClickListener(subjectOnClickListener);

        refreshList(new OnFragmentListener() {
            @Override
            public void onSuccess() {
                refreshListView.onRefreshComplete();
            }
            @Override
            public void onFail() {
                refreshListView.onRefreshComplete();
            }
        });

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

    public void queryList(final int page,final int currentType, final OnFragmentListener fragmentListener){

        this.page = page;
        NetFianceFragmentInterface netInterface = new NRestAdapter<NetFianceFragmentInterface>(context,
                Profile.SERVER_ADDRESS_DEV, NetFianceFragmentInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(context,"网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }

//        text_loading.setVisibility(View.VISIBLE);

        if(1 == currentType){
            try {
                netInterface.telNumberList(String.valueOf(page)/*当前页数*/,
                                                        ""/*电话号码*/,
                                                        ""/*网络代码*/,
                                                        companyCode/*运营商代码*/,
                                                        ""/*资费*/,
                                                        ""/*属性代码*/,
                                                        ""/*省代码*/,
                                                        ""/*市代码*/,
                                                        "1"/*是否上架*/,
                                                        ""/*是否特价*/,
                                                        "1"/*是否推荐*/,
                                                        ""/*是否靓号*/,
                                                        ""/*是否锁定*/,
                                                        ""/*是否结束*/,
                                                        ""/*是否写卡*/,
                        new NCallback<List<TelephoneEntity>>(context, new TypeReference<List<TelephoneEntity>>() {}) {
                    @Override
                    public void onSuccess(int statusCode, List<Header> headers, List<TelephoneEntity> entityList) {
                        fragmentListener.onSuccess();

                        if(type != currentType){
//                            text_loading.setVisibility(View.GONE);
                            return;
                        }
                        recommendAdapter.appendList(entityList);
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

    public void refreshList(final OnFragmentListener fragmentListener){
        this.page = 1;
        NetFianceFragmentInterface netInterface = new NRestAdapter<NetFianceFragmentInterface>(context,
                Profile.SERVER_ADDRESS_DEV, NetFianceFragmentInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(context,"网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            netInterface.telNumberList(String.valueOf(page)/*当前页数*/,
                    ""/*电话号码*/,
                    ""/*网络代码*/,
                    companyCode/*运营商代码*/,
                    ""/*资费*/,
                    ""/*属性代码*/,
                    ""/*省代码*/,
                    ""/*市代码*/,
                    "1"/*是否上架*/,
                    ""/*是否特价*/,
                    "1"/*是否推荐*/,
                    ""/*是否靓号*/,
                    ""/*是否锁定*/,
                    ""/*是否结束*/,
                    ""/*是否写卡*/,
                    new NCallback<List<TelephoneEntity>>(context, new TypeReference<List<TelephoneEntity>>() {}) {
                        @Override
                        public void onSuccess(int statusCode, List<Header> headers, List<TelephoneEntity> entityList) {
                            fragmentListener.onSuccess();

//                            for(int i = 0; i < entityList.size(); i++){
//                                entityList.get(i).setNews(true);
//                            }

                            recommendAdapter.replaceList(entityList);
                        }

                        @Override
                        public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                            fragmentListener.onFail();

                            recommendAdapter.removeList();
                            //403定义为过期
                            Toast.makeText(context, infoMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        context.loadPageRecommend();
    }

    private class SubjectOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.yun_r1:
                    companyCode = "1";
                    refreshList(new OnFragmentListener() {
                        @Override
                        public void onSuccess() {
                            refreshListView.onRefreshComplete();
                        }
                        @Override
                        public void onFail() {
                            refreshListView.onRefreshComplete();
                        }
                    });
                    break;
                case R.id.yun_r2:
                    companyCode = "2";
                    refreshList(new OnFragmentListener() {
                        @Override
                        public void onSuccess() {
                            refreshListView.onRefreshComplete();
                        }
                        @Override
                        public void onFail() {
                            refreshListView.onRefreshComplete();
                        }
                    });
                    break;
                case R.id.yun_r3:
                    companyCode = "3";
                    refreshList(new OnFragmentListener() {
                        @Override
                        public void onSuccess() {
                            refreshListView.onRefreshComplete();
                        }
                        @Override
                        public void onFail() {
                            refreshListView.onRefreshComplete();
                        }
                    });
                    break;
                default:
                    context.loadPageMapping(view);
                    break;
            }
        }
    }

    public void setMarginBottom(View view, int bottom){
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.setMargins(0,0,0,bottom);
        view.setLayoutParams(params);
    }

    @Override
    public void update(int type, OnFragmentListener fragmentListener, Object... arguments) {
        recommendAdapter.refreshList();
//        switch (type) {
//            case type_count:
//                break;
//            case type_list:
//                final int page = Integer.parseInt(arguments[0].toString());
//                final int currentType = Integer.parseInt(arguments[1].toString());
//                recommendAdapter.replaceList(new ArrayList<FinanceProductEntity>());
//                queryList(page, currentType, fragmentListener);
//                break;
//            default:
//                break;
//        }
//
//        return;
    }
}
