package com.ant.recharge.fragment1.planner;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.common.AlertDialog;
import com.ant.recharge.common.BaseListRecyclerAdapter;
import com.ant.recharge.common.NCallbackMsg;
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.StringUtils;
import com.ant.recharge.fragment1.planner.entity.PlannerEntity;

import java.util.List;

import retrofit.client.Header;

/**
 * Created by kwc on 2016/11/30.
 */
public class PlannerSelectAdapter extends BaseListRecyclerAdapter<PlannerSelectAdapter.MyViewHolder> {

    private String userId;
    public PlannerSelectAdapter(Activity context, List list, String userId) {
        super(context, list);
        this.userId = userId;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(mInflater.inflate(R.layout.planner_list_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder2(PlannerSelectAdapter.MyViewHolder holder, int position) {

        final PlannerEntity entity = (PlannerEntity) list.get(position);
        holder.nameTV.setText("金牌理财师  " + entity.getRealName());
        holder.selectedTV.setText("选他");
        holder.jobTitleTV.setText("职称信息：" + entity.getRealName());
        holder.codeTV.setText("推荐编码：" + entity.getLoginName());
        holder.markTV.setText("个人签名：" + entity.getAdviserIntroduction());

        holder.selectedTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog dialog = new AlertDialog(context);

                dialog.setCustomMessage("理财师一旦选择不允许改变，选择后可以获得年化0.2%的额外增益，但将失去VIP抽奖机会，您确定要选择吗？")
                        .setPositiveButton(context.getString(R.string.action_confirm), new View.OnClickListener() {
                            @Override
                            public void onClick(View paramView) {
                                selectPlanner(entity);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(context.getString(R.string.action_cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View paramView) {
                                dialog.dismiss();
                            }
                        }).show();


            }
        });
    }

    //设定理财师
    private void selectPlanner(final PlannerEntity entity){

        PlannerNetInterface netInterface = new NRestAdapter<PlannerNetInterface>(context,
                Profile.SERVER_ADDRESS, PlannerNetInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(context, "网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }

        netInterface.setMyFinancial(
                userId,
                entity.getId(),
                new NCallbackMsg(){
                    @Override
                    public void onSuccess(int statusCode, List<Header> headers, String result) {
                        Toast.makeText(context, StringUtils.isBlank(result) ? "设定理财师成功!": result, Toast.LENGTH_SHORT).show();
                        context.finish();
                    }

                    @Override
                    public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                        Toast.makeText(context, StringUtils.isBlank(infoMessage) ? "设定理财师失败!": infoMessage, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameTV;//理财师姓名
        TextView selectedTV;//选他（如何判断男女）
        ImageView imgTV;//头像
        TextView jobTitleTV;//职称信息
        TextView codeTV;//推荐编码
        TextView markTV;//个性签名

        public MyViewHolder(View view){
            super(view);
            nameTV = (TextView) view.findViewById(R.id.planner_item_name);
            selectedTV = (TextView) view.findViewById(R.id.planner_item_select);
            imgTV = (ImageView) view.findViewById(R.id.planner_item_icon);
            jobTitleTV = (TextView) view.findViewById(R.id.planner_item_1);
            codeTV = (TextView) view.findViewById(R.id.planner_item_2);
            markTV = (TextView) view.findViewById(R.id.planner_item_3);
        }
    }

}
