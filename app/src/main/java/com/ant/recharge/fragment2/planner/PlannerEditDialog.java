package com.ant.recharge.fragment2.planner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.common.FontHelper;
import com.ant.recharge.common.NCallbackMsg;
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.StringUtils;
import com.ant.recharge.common.ui.BaseDialog;
import com.ant.recharge.fragment2.planner.entity.FinancePlannerEntity;

import java.util.List;

import retrofit.client.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * 理财师编辑
 * Created by kwc on 2016/11/29.
 */
public class PlannerEditDialog extends BaseDialog {

    private View cancelTV;
    private View okTV;
    private EditText contentTV;


    private TextView planner_describe;//activity中的简介组件
    private String userId;
    private FinancePlannerEntity financePlannerEntity;


    public PlannerEditDialog(Context context) {
        super(context);
    }

    public PlannerEditDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected PlannerEditDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    /**
     * 初始化视图
     * @return
     */
    @Override
    protected View initView() {
        LayoutInflater mLayoutInflater = LayoutInflater.from(getContext());
        View view = mLayoutInflater.inflate(R.layout.planner_edit_dialog, null);
        //微软雅黑
        FontHelper.applyFont(getContext(), view);
        okTV = view.findViewById(R.id.dialog_ok);
        cancelTV = view.findViewById(R.id.dialog_cancel);
        contentTV = (EditText) view.findViewById(R.id.dialog_content);


        initListener();


        setCancelable(false);
        return view;
    }

    /**
     * 设置监听
     */
    protected void initListener() {

        //关闭
        cancelTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        //修改
        okTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String content = contentTV.getText().toString();
                if(StringUtils.isBlank(content)){
                    Toast.makeText(getmContext(), "请填写简介！",Toast.LENGTH_SHORT).show();
                    return;
                }

                NetInterface netInterface = new NRestAdapter<NetInterface>(getmContext(),
                        Profile.SERVER_ADDRESS, NetInterface.class)
                        .create();
                if(netInterface == null){
                    Toast.makeText(getmContext(), "网络正在开小差!", Toast.LENGTH_SHORT).show();
                    return;
                }

                netInterface.updatePlanner(userId, content, new NCallbackMsg() {
                    @Override
                    public void onSuccess(int statusCode, List<Header> headers, String result) {
                        planner_describe.setText(content);
                        dismiss();
                    }

                    @Override
                    public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {

                    }
                });
                dismiss();
            }
        });
    }

    /**
     * 设置需要的参数
     * @param financePlannerEntity 理财师
     * @param userId  用户id
     * @param planner_describe 理财简介
     */
    public void setParams(FinancePlannerEntity financePlannerEntity, String userId, TextView planner_describe) {
        this.financePlannerEntity = financePlannerEntity;
        contentTV.setText(financePlannerEntity.getAdviserIntroduction());
        this.planner_describe = planner_describe;
        this.userId = userId;
    }

    public interface NetInterface {

        //修改理财师数据
        //alert   r.msg
        @POST("/api/wechat/user/updateFinancialIntroduction")
        public void updatePlanner(
                @Query("memberId") String memberId,
                @Query("adviserIntroduction") String adviserIntroduction,
                NCallbackMsg callback);

    }
}
