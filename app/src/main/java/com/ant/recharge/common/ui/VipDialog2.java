package com.ant.recharge.common.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ant.recharge.R;

/**
 * Created by kwc on 2016/9/2.
 */
public class VipDialog2 extends Dialog {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private View mMainView;
    private LinearLayout.LayoutParams mMainViewlp;

    public VipDialog2(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public VipDialog2(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
        initView();
    }

    protected VipDialog2(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
        initView();
    }

    private void initView() {

        mLayoutInflater = LayoutInflater.from(mContext);
        mMainViewlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        mMainView = mLayoutInflater.inflate(
                R.layout.view_vipdialog2, null);

        TextView vip_content = (TextView) mMainView.findViewById(R.id.vip_content);
        vip_content.setText("为什么选VIP标\n" +
                "1）VIP收益高，独享9.6%，10.6%，11.6%收益。\n" +
                "2）可根据您理想的投资额度，投资天数，我们为您选择相应的票源， 您只需填写预购额度就会有客户经理与您联系，为您提供一对一专人服务， 专享通道直接帮您购买成功。同时会在平台VIP标的界面上发布可购买标的，供您选择。\n" +
                "3）购标成功后，签订票据转让协议，直接买断，汇票可带走，可背书， 可银行托收，质押权在手，可以不通过蚂蚁宜票直接在银行行权。\n" +
                "4）亲自审核票据及相关手续票据所有的背书人都有权还款，您可行使背书人一切权益； 等于您借助蚂蚁宜票平台，放债央企国企上市公司，您可以不通过蚂蚁宜票对票据行权。\n" +
                "\n" +
                "购买vip标的好处\n" +
                "1）持票理财.赚取利息\n" +
                "2）购票支付货款，赚取差价\n" +
                "3）大票换小，减少利息支出\n" +
                "\n" +
                "对公账号\n" +
                "账户名：蚂蚁宜票（深圳）票据信息服务有限公司\n" +
                "开户银行：中国邮政储蓄银行股份有限公司北京昌平区龙水路支行\n" +
                "开户银行账号：911005010001047168");
//        vip_content.setText("Vip标的优化\n\n" +
//                "1）Vip收益高，独享9.6%，10%，11.6%收益。\n" +
//                "2）购买Vip标的，为您提供一对一专人服务，专享通道。\n" +
//                "3）购买成功后即可参与幸运大转盘抽奖活动。\n" +
//                "4）您可以选择持票，可以不通过蚂蚁宜票，到期后直接到银行托收行权。");

        //取消
        mMainView.findViewById(R.id.action_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VipDialog2.this.dismiss();
            }
        });

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(mMainView, mMainViewlp);
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.dimAmount = 0.25f;
        lp.alpha = 1.0f;
        lp.width = (int) (dm.widthPixels * 0.85);
        getWindow().setAttributes(lp);
        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawableResource(R.color.transparent);
//        setCanceledOnTouchOutside(touchable);
    }

}
