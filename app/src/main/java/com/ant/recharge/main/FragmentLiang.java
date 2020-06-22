package com.ant.recharge.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.adapter.LiangAdapter;
import com.ant.recharge.common.BaseFragment;
import com.ant.recharge.common.FontHelper;
import com.ant.recharge.common.NCallback;
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.Profile;
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

/**
 *
 * 靓号选购
 */
@SuppressLint("ValidFragment")
public class FragmentLiang extends BaseFragment implements View.OnClickListener{

    private MainTabActivity context;
    //加载提示
//    private View text_loading;
    private View view;

    private View conditionV1;
    private View conditionV2;
    private View conditionV3;
    private View conditionV4;
    private View conditionV5;
    private TextView conditionTextV1;
    private TextView conditionTextV2;
    private TextView conditionTextV3;
    private TextView conditionTextV4;
    private TextView conditionTextV5;

    private PullToRefreshListView refreshListView;
    private ListView listView;
    private LiangAdapter liangAdapter;

    private int page = 1;
    private int pageSize = 6;
    private int type = 1;
    private String token;
    private String keyword = "";
    private String network = "";
    private String company = "";
    private String price = "";
    private String provinceCode = "";
    private String cityCode = "";

    public FragmentLiang() {
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
        view = inflater.inflate(R.layout.fragment_liang, container, false);
        FontHelper.applyFont(getActivity(), view);

        //没有返回按钮
        view.findViewById(R.id.btn_screen).setOnClickListener(this);

        view.findViewById(R.id.btn_search).setOnClickListener(this);
//        text_loading  = view.findViewById(R.id.text_loading);

        refreshListView = (PullToRefreshListView) view.findViewById(R.id.fragment_liang_list);
        refreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        refreshListView.setScrollingWhileRefreshingEnabled(true);
        listView = refreshListView.getRefreshableView();

        conditionV1 = view.findViewById(R.id.liang_r1);
        conditionV2 = view.findViewById(R.id.liang_r2);
        conditionV3 = view.findViewById(R.id.liang_r3);
        conditionV4 = view.findViewById(R.id.liang_r4);
        conditionV5 = view.findViewById(R.id.liang_r5);

        conditionTextV1 = (TextView) view.findViewById(R.id.liang_radio_tv1);
        conditionTextV2 = (TextView) view.findViewById(R.id.liang_radio_tv2);
        conditionTextV3 = (TextView) view.findViewById(R.id.liang_radio_tv3);
        conditionTextV4 = (TextView) view.findViewById(R.id.liang_radio_tv4);
        conditionTextV5 = (TextView) view.findViewById(R.id.liang_radio_tv5);

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
        liangAdapter = new LiangAdapter(getActivity(), list);
        refreshListView.setAdapter(liangAdapter);

        refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

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
        view.findViewById(R.id.liang_r1).setOnClickListener(subjectOnClickListener);
        view.findViewById(R.id.liang_r2).setOnClickListener(subjectOnClickListener);
        view.findViewById(R.id.liang_r3).setOnClickListener(subjectOnClickListener);
        view.findViewById(R.id.liang_r4).setOnClickListener(subjectOnClickListener);
        view.findViewById(R.id.liang_r5).setOnClickListener(subjectOnClickListener);

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

    public void queryList(final int page, final int currentType, final OnFragmentListener fragmentListener){

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
                                                        keyword/*电话号码*/,
                                                        network/*网络代码*/,
                                                        company/*运营商代码*/,
                                                        price/*资费*/,
                                                        ""/*属性代码*/,
                                                        ""/*省代码*/,
                                                        cityCode/*市代码*/,
                                                        "1"/*是否上架*/,
                                                        ""/*是否特价*/,
                                                        ""/*是否推荐*/,
                                                        "1"/*是否靓号*/,
                                                        ""/*是否锁定*/,
                                                        ""/*是否结束*/,
                                                        ""/*是否写卡*/,
                        new NCallback<List<TelephoneEntity>>(context, new TypeReference<List<TelephoneEntity>>() {}) {
                    @Override
                    public void onSuccess(int statusCode, List<Header> headers, List<TelephoneEntity> entityList) {
                        fragmentListener.onSuccess();

                        if(type != currentType) {
//                            text_loading.setVisibility(View.GONE);
                            return;
                        }

                        liangAdapter.appendList(entityList);
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

//        text_loading.setVisibility(View.VISIBLE);

        if(((MainTabActivity)activity).vString[0].equals("全部")) {
            price = "";
        }
        else {
            if(((MainTabActivity)activity).vString[0].equals("10元-30元")) {
                price = "0";
            }
            else if(((MainTabActivity)activity).vString[0].equals("31元-100元")) {
                price = "1";
            }
            else if(((MainTabActivity)activity).vString[0].equals("101元-200元")) {
                price = "2";
            }
            else if(((MainTabActivity)activity).vString[0].equals("201元-500元")) {
                price = "3";
            }
            else if(((MainTabActivity)activity).vString[0].equals("501元-10000元")) {
                price = "4";
            }
            else if(((MainTabActivity)activity).vString[0].equals("10000元以上")) {
                price = "5";
            }
            else {
                price = "";
            }
        }
        if(((MainTabActivity)activity).vString[1].equals("全部")) {
            company = "";
        }
        else {
            if(((MainTabActivity)activity).vString[1].equals("移动")) {
                company = "1";
            }
            else if(((MainTabActivity)activity).vString[1].equals("联通")) {
                company = "2";
            }
            else if(((MainTabActivity)activity).vString[1].equals("电信")) {
                company = "3";
            }
            else if(((MainTabActivity)activity).vString[1].equals("蜗牛")) {
                company = "4";
            }
            else if(((MainTabActivity)activity).vString[1].equals("国美")) {
                company = "5";
            }
            else if(((MainTabActivity)activity).vString[1].equals("迪迦")) {
                company = "6";
            }
            else if(((MainTabActivity)activity).vString[1].equals("天音")) {
                company = "7";
            }
            else if(((MainTabActivity)activity).vString[1].equals("青牛")) {
                company = "8";
            }
            else if(((MainTabActivity)activity).vString[1].equals("华翔")) {
                company = "9";
            }
            else if(((MainTabActivity)activity).vString[1].equals("话机世界")) {
                company = "10";
            }
            else if(((MainTabActivity)activity).vString[1].equals("连连科技")) {
                company = "11";
            }
            else if(((MainTabActivity)activity).vString[1].equals("苏宁")) {
                company = "12";
            }
            else {
                company = "";
            }
        }

        if(((MainTabActivity)activity).vString[2].equals("全部")) {
            network = "";
        }
        else {
            if(((MainTabActivity)activity).vString[2].equals("移动")) {
                network = "0";
            }
            else if(((MainTabActivity)activity).vString[2].equals("联通")) {
                network = "1";
            }
            else if(((MainTabActivity)activity).vString[2].equals("电信")) {
                network = "2";
            }
            else {
                network = "";
            }
        }

        provinceCode = ((MainTabActivity)activity).pickedCity[0] == "0" ? "" : ((MainTabActivity)activity).pickedCity[0];
        cityCode = ((MainTabActivity)activity).pickedCity[2]  == "0" ? "" : ((MainTabActivity)activity).pickedCity[2];

        EditText telET= (EditText) this.view.findViewById(R.id.edit_search);
        keyword = telET.getText().toString();
        try {
            netInterface.telNumberList(String.valueOf(page)/*当前页数*/,
                    keyword/*电话号码*/,
                    network/*网络代码*/,
                    company/*运营商代码*/,
                    price/*资费*/,
                    ""/*属性代码*/,
                    ""/*省代码*/,
                    cityCode/*市代码*/,
                    "1"/*是否上架*/,
                    ""/*是否特价*/,
                    ""/*是否推荐*/,
                    "1"/*是否靓号*/,
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

                    liangAdapter.replaceList(entityList);
//                        text_loading.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                    fragmentListener.onFail();
                    liangAdapter.removeList();
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

    @Override
    public void onClick(View view) {

        //context.loadPageRecommend();
        if(view.getId() == R.id.btn_screen) {
            ((MainTabActivity)activity).openFilterDrawer();
        }
        else if(view.getId() == R.id.btn_search) {
            //((MainTabActivity)activity).openAreaDrawer();
            EditText telET= (EditText) this.view.findViewById(R.id.edit_search);
            keyword = telET.getText().toString();

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
    }

    private class SubjectOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.liang_r1:
                    onTab1Checked();
                    break;
                case R.id.liang_r2:
                    onTab2Checked();
                    break;
                case R.id.liang_r3:
                    onTab3Checked();
                    break;
                case R.id.liang_r4:
                    provinceCode = "";//全国
                    cityCode = "";//全国
                    onTab4Checked();
                    break;
                case R.id.liang_r5:
                    onTab5Checked();
                    return;
                default:
                    return;
            }
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
            refreshListView.getRefreshableView().setSelection(0);
        }
    }

    //综合
    private void onTab1Checked() {
        setMarginBottom(conditionV1, 1);
        setMarginBottom(conditionV2, 0);
        setMarginBottom(conditionV3, 0);
        setMarginBottom(conditionV4, 0);
        type = 1;
    }

    //销量
    private void onTab2Checked() {
        setMarginBottom(conditionV1, 0);
        setMarginBottom(conditionV2, 1);
        setMarginBottom(conditionV3, 0);
        setMarginBottom(conditionV4, 0);
        type = 1;
    }

    //价格
    private void onTab3Checked() {
        setMarginBottom(conditionV1, 0);
        setMarginBottom(conditionV2, 0);
        setMarginBottom(conditionV3, 1);
        setMarginBottom(conditionV4, 0);
        type = 1;
    }

    //运行商
    private void onTab4Checked() {
        setMarginBottom(conditionV1, 0);
        setMarginBottom(conditionV2, 0);
        setMarginBottom(conditionV3, 0);
        setMarginBottom(conditionV4, 1);
        type = 1;
    }

    //地区
    private void onTab5Checked() {
//        ((MainTabActivity)activity).openAreaDrawer();
        Intent intent = new Intent(context, CityPickerActivity.class);
        intent.putExtra("provinceId", ((MainTabActivity)activity).pickedCity[0]);
        intent.putExtra("provinceName", ((MainTabActivity)activity).pickedCity[1]);
        intent.putExtra("cityId", ((MainTabActivity)activity).pickedCity[2]);
        intent.putExtra("cityName", ((MainTabActivity)activity).pickedCity[3]);
        startActivityForResult(intent, 1);
    }
    public void setMarginBottom(View view, int bottom){
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.setMargins(0,0,0,bottom);
        view.setLayoutParams(params);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);  //这个super可不能落下，否则可能回调不了

        if(1 == requestCode){
            if(200 == resultCode){

                ((MainTabActivity)activity).pickedCity[0] = data.getStringExtra("provinceId");
                ((MainTabActivity)activity).pickedCity[1] = data.getStringExtra("provinceName");
                ((MainTabActivity)activity).pickedCity[2] = data.getStringExtra("cityId");
                ((MainTabActivity)activity).pickedCity[3] = data.getStringExtra("cityName");

                searchLiangTel();
            }
        }
    }

    @Override
    public void update(int type, OnFragmentListener fragmentListener, Object... arguments) {
		liangAdapter.refreshList();
		/*
        switch (type) {
            case type_count:
                break;
            case type_list:
                final int page = Integer.parseInt(arguments[0].toString());
                final int currentType = Integer.parseInt(arguments[1].toString());

                liangAdapter.replaceList(new ArrayList<FinanceProductEntity>());
                queryList(page, currentType, fragmentListener);
                break;
            default:
                break;
        }

        return;*/
    }

    public void searchLiangTel(){
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
}
