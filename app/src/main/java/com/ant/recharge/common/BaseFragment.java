package com.ant.recharge.common;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.ant.recharge.R;

/**
 * Created by kwc on 2016/10/22.
 */
public class BaseFragment extends Fragment {


    public static final int type_count = 1;
    public static final int type_list = 2;

    protected FragmentActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (FragmentActivity) activity;
    }

    /**
     *
     * @param type 操作类型
     * @param fragmentListener 回调函数
     */
    public void update(int type, OnFragmentListener fragmentListener, Object... arguments){

    }


    public interface OnFragmentListener {
        void onSuccess();
        void onFail();
    }

    protected void showNext(Fragment fragment, int id) {
        showNext(fragment, id, null, true);
    }
    protected void showNext(Fragment fragment, int id,Bundle b) {
        showNext(fragment, id, b, true);
    }


    protected void showNext(Fragment fragment, int id, Bundle b,boolean isAddBackStack) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out);
        fragmentTransaction.replace(id, fragment);
        if (b != null) {
            fragment.setArguments(b);
        }
        if(isAddBackStack){
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * 返回上一个界面
     */
    protected void back() {
//		showHideFragment();
        getActivity().getSupportFragmentManager().popBackStackImmediate();
    }
}
