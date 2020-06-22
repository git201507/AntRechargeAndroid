package com.ant.recharge.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ant.recharge.common.BaseFragment;
import com.ant.recharge.model.FiterBean;
import com.ant.recharge.adapter.FiterAdapter;
import com.ant.recharge.R;

import java.util.ArrayList;

/**
 * @FileName TowFitrerFragment
 * @Description 二级筛选界面
 * @Author zhouyong
 * @Date 2015-08-13 13:37
 * @Version V 1.0
 */
public class TowFitrerFragment extends BaseFragment implements View.OnClickListener {
    private ListView listView;
    private TextView tvCancel;
    private ImageView ivLeft;

    private String[] mString;// = {"宏碁", "华为", "微软", "三星", "联系", "酷比魔方", "戴尔", "七彩虹", "VOYO", "dostyle", "优派"};
    private ArrayList<FiterBean> fiterList;
    private FiterAdapter fiterAdapter;

    public int parentIndex;

    public TowFitrerFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle mBundle = getArguments();
        parentIndex = (Integer) mBundle.get("index");

        switch (parentIndex)
        {
            case 0: {
                String[] groupString = {"10元-30元", "31元-100元", "101元-200元", "201元-500元", "501元-10000元", "10000元以上"};
                mString = groupString;
            }
                break;
            case 1: {
                String[] groupString = {"移动","联通","电信", "蜗牛", "国美", "迪迦", "天音", "青牛", "华翔", "话机世界", "连连科技", "苏宁"};
                mString = groupString;
            }
            break;
            case 2: {
                String[] groupString = {"移动","联通","电信"};
                mString = groupString;
            }
            break;
        }
        View view = inflater.inflate(R.layout.fiter_fragment, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        listView = (ListView) view.findViewById(R.id.listView);
        tvCancel = (TextView) view.findViewById(R.id.tvCancel);
        ivLeft = (ImageView) view.findViewById(R.id.ivLeft);
        tvCancel.setVisibility(View.INVISIBLE);
        ivLeft.setVisibility(View.VISIBLE);
        ivLeft.setOnClickListener(this);

        view.findViewById(R.id.tvRight).setVisibility(view.INVISIBLE);
        view.findViewById(R.id.btnClear).setVisibility(view.INVISIBLE);

        initData();

    }

    private void initData() {
        fiterList = new ArrayList<>();
        for (int i = 0; i < mString.length; i++) {
            fiterList.add(fiterBean(mString[i]));
        }
        fiterAdapter = new FiterAdapter(getActivity(), fiterList);
        listView.setAdapter(fiterAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                (ArrayList<Fragment>) getActivity().getSupportFragmentManager().getFragments();
//                parentFragment = (FitrerFragment) fragmentArrayList.get(0);

                ((MainTabActivity)activity).vString[parentIndex] = mString[position];
                activity.getSupportFragmentManager().popBackStackImmediate();
            }
        });
    }

    private FiterBean fiterBean(String name) {
        FiterBean bean = new FiterBean();
        bean.setFiterName(name);
        return bean;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivLeft:
                back();
                break;
        }
    }
}
