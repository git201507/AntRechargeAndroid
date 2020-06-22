package com.ant.recharge.fragment1.planner;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.common.BaseActivity;
import com.ant.recharge.common.NCallback;
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.StringUtils;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.entity.User;
import com.ant.recharge.fragment1.planner.entity.PlannerEntity;
import com.ant.recharge.login.NetLoginInterface;

import org.codehaus.jackson.type.TypeReference;

import java.util.ArrayList;
import java.util.List;

import retrofit.client.Header;

/**
 * 理财师选择
 * Created by kwc on 2016/11/30.
 */
public class PlannerSelectActivity extends BaseActivity {

    //搜索内容：理财师姓名或者手机号
    private EditText searchET;

    private User user;

    private RecyclerView recyclerView;
    private PlannerSelectAdapter adapter;

    //分页
    private int pageNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_financial);
        initView(this, R.layout.planner_list_activity);

        searchET = (EditText)findViewById(R.id.planner_search);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        //设置标题
        ((TextView)findViewById(R.id.text_title)).setText("理财师列表");

        String userStr = readPreferences(NetLoginInterface.ANT_LOGIN_USER, "additional");
        try {
            if (!StringUtils.isBlank(userStr)){
                user = JsonUtil.decode(userStr, User.class);
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PlannerSelectAdapter(this, new ArrayList<PlannerEntity>(), user.getLoginName());
        recyclerView.setAdapter(adapter);

        //初始化全查
        pageNumber = 1;
        search(null);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.icon:
                //搜索
                pageNumber = 1;
                String content = searchET.getText().toString();
                if(StringUtils.isBlank(content)) {
                    Toast.makeText(this, R.string.planner_select_hint_search, Toast.LENGTH_SHORT).show();
                    return;
                }
                search(content);
                return;
        }

        super.onClick(v);
    }

    /**
     * 查询理财师
     */
    public void search(final String content){
        PlannerNetInterface netInterface = new NRestAdapter<PlannerNetInterface>(this,
                Profile.SERVER_ADDRESS, PlannerNetInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(this, "网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(StringUtils.isBlank(content)) {
            netInterface.pageAdvanserList2(
                    user.getLoginName(),
                    pageNumber,
                    "100",
                    new NCallback<List<PlannerEntity>>(PlannerSelectActivity.this,new TypeReference<List<PlannerEntity>>() {} ) {
                        @Override
                        public void onSuccess(int statusCode, List<Header> headers, List<PlannerEntity> oRet) {
                            adapter.replaceList(oRet);
                        }
                        @Override
                        public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                        }
                    });
        } else {
            netInterface.pageAdvanserList(
                    user.getLoginName(),
                    pageNumber,
                    "100", content,
                    new NCallback<List<PlannerEntity>>(PlannerSelectActivity.this,new TypeReference<List<PlannerEntity>>() {} ) {
                        @Override
                        public void onSuccess(int statusCode, List<Header> headers, List<PlannerEntity> oRet) {
                            adapter.replaceList(oRet);
                        }
                        @Override
                        public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                        }
                    });
        }


    }



}
