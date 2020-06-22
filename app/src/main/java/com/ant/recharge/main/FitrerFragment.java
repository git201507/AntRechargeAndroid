package com.ant.recharge.main;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ant.recharge.common.BaseFragment;
import com.ant.recharge.model.FiterBean;
import com.ant.recharge.adapter.FiterAdapter;
import com.ant.recharge.R;
import java.util.ArrayList;

/**
 * @FileName FitrerFragment
 * @Description 一级筛选界面
 * @Author
 * @Date 2015-08-13 13:36
 * @Version V 1.0
 */
public class FitrerFragment extends BaseFragment implements View.OnClickListener {

    private ListView listView;
    private Button btnClear;
    private TextView confirmTV;
    private RelativeLayout drawer_content;
    private DrawerLayout mDrawerLayout;

    private String[] mString = {"品牌", "价格", "尺寸", "特色", "系统", "硬盘", "大家说"};
    private String[] vString = {"全部", "全部", "全部", "全部", "全部", "全部", "全部"};
    private ArrayList<FiterBean> fiterList;
    private FiterAdapter fiterAdapter;

    public FitrerFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fiter_fragment, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        listView = (ListView) view.findViewById(R.id.listView);
        btnClear = (Button) view.findViewById(R.id.btnClear);
        confirmTV = (TextView) view.findViewById(R.id.tvRight);

        mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawer_content = (RelativeLayout) getActivity().findViewById(R.id.drawer_content);

        btnClear.setOnClickListener(this);
        confirmTV.setOnClickListener(this);
        initData();

    }

    private void initData() {
        this.mString = ((MainTabActivity)getActivity()).mString;
        this.vString = ((MainTabActivity)getActivity()).vString;

        fiterList = new ArrayList<>();
        for (int i = 0; i < mString.length; i++) {
            fiterList.add(fiterBean(mString[i],vString[i]));
        }

        fiterAdapter = new FiterAdapter(getActivity(), fiterList);
        listView.setAdapter(fiterAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle mBundle = new Bundle();
                mBundle.putInt("index", position);//压入数据
                showNext(new TowFitrerFragment(),R.id.drawer_content,mBundle);
            }
        });
    }

    private FiterBean fiterBean(String name, String value) {
        FiterBean bean = new FiterBean();
        bean.setFiterName(name);
        bean.setFiterValue(value);
        return bean;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnClear:
                String[] clearString = {"全部", "全部", "全部"};
                ((MainTabActivity)activity).vString = clearString;
                this.vString = ((MainTabActivity)getActivity()).vString;

                fiterList = new ArrayList<>();
                for (int i = 0; i < mString.length; i++) {
                    fiterList.add(fiterBean(mString[i],vString[i]));
                }

                fiterAdapter = new FiterAdapter(getActivity(), fiterList);
                listView.setAdapter(fiterAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Bundle mBundle = new Bundle();
                        mBundle.putInt("index", position);//压入数据
                        showNext(new TowFitrerFragment(),R.id.drawer_content,mBundle);
                    }
                });
                break;
            case R.id.tvRight:
                ((MainTabActivity)activity).searchLiangTel();
                mDrawerLayout.closeDrawer(drawer_content);
                break;
        }
    }

    public void updateDataSource(){
        fiterAdapter.notifyDataSetChanged();
    }

    public ArrayList<FiterBean> getFiterList(){
        return this.fiterList;
    };
    public void setFiterList(ArrayList<FiterBean> list){
        this.fiterList = list;
    };
}
