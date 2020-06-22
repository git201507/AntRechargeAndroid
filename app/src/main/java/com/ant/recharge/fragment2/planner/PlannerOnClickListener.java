package com.ant.recharge.fragment2.planner;

import android.content.Context;
import android.content.Intent;
import android.view.View;

/**
 * 理财师图标监听
 * Created by kwc on 2016/11/24.
 */
public class PlannerOnClickListener implements View.OnClickListener {

    private Boolean isPlanner;
    private Context context;

    public PlannerOnClickListener(Context context, Boolean isPlanner){
        this.context = context;
        this.isPlanner = isPlanner;
    }

    @Override
    public void onClick(View view) {
        if (isPlanner){

        } else {
            //不是理财师
            PlannerDialog dialog = new PlannerDialog(context);
            dialog.show();
        }
    }


}
