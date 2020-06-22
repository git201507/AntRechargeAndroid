package com.ant.recharge.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ant.recharge.R;
import com.ant.recharge.common.BaseActivity;
import com.ant.recharge.common.DoubleDatePickerDialog;
import com.ant.recharge.common.SpinnerOption;

import java.util.ArrayList;
import java.util.Calendar;

public class ScreenOrderActivity extends BaseActivity {
    ArrayList<SpinnerOption> orderStateOption;
    Spinner spinnerOption;
    EditText fromDateTV;
    EditText toDateTV;
    TextView stateTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_order);
        fromDateTV = (EditText)findViewById(R.id.fromdate_edit_screen_order);
        toDateTV = (EditText)findViewById(R.id.todate_edit_screen_order);
        stateTV = (TextView) findViewById(R.id.state_content_screen_order);
//准备好下拉框内容
        orderStateOption = new ArrayList<SpinnerOption>();
        SpinnerOption s0 = new SpinnerOption("0", "未付款");
        orderStateOption.add(s0);
        SpinnerOption s1 = new SpinnerOption("1", "已付款");
        orderStateOption.add(s1);
        SpinnerOption s2 = new SpinnerOption("2", "待上传");
        orderStateOption.add(s2);
        SpinnerOption s3 = new SpinnerOption("3", "待审核");
        orderStateOption.add(s3);
        SpinnerOption s4 = new SpinnerOption("4", "审核通过");
        orderStateOption.add(s4);
        SpinnerOption s5 = new SpinnerOption("5", "已完成");
        orderStateOption.add(s5);
        SpinnerOption s6 = new SpinnerOption("6", "退款中");
        orderStateOption.add(s6);
        SpinnerOption s7 = new SpinnerOption("7", "退款完成");
        orderStateOption.add(s7);

        spinnerOption = (Spinner)findViewById(R.id.spinner_screen_order);
//设置Adapter
        ArrayAdapter<SpinnerOption> adapter = new ArrayAdapter<SpinnerOption>(this,android.R.layout.simple_spinner_item, orderStateOption);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOption.setAdapter(adapter);
        spinnerOption.setSelection(0);
        spinnerOption.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                //Spinner spinner = (Spinner) arg0;
                //shopname = (String) spinner.getItemAtPosition(arg2);
                stateTV.setText(arg0.getItemAtPosition(arg2).toString());
                //((Declare)getApplication()).setShopname(shopname);
                //arg0.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
//取下拉框的值
        TextView orderStateTV = findViewById(R.id.state_content_screen_order);
        orderStateTV.setText(((SpinnerOption)spinnerOption.getSelectedItem()).getValue());
    }

    @Override
    public void onClick(View v) {

        Calendar c = Calendar.getInstance();
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                return;
            case R.id.search_btn_screen_order:
                Intent data = new Intent();
//                    data.putExtra("userName", userName);
//                    data.putExtra("password", password);
                setResult(200, data);
                finish();
                return;
            case  R.id.fromdate_edit_screen_order:
                // 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
                new DoubleDatePickerDialog(this, 0, new DoubleDatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                          int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
                                          int endDayOfMonth) {
//                        String textString = String.format("开始时间：%d-%d-%d\n结束时间：%d-%d-%d\n", startYear,
//                                startMonthOfYear + 1, startDayOfMonth, endYear, endMonthOfYear + 1, endDayOfMonth);
                        String textString = String.format("%d-%d-%d", startYear,
                                startMonthOfYear + 1, startDayOfMonth);

                        fromDateTV.setText(textString);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
                return;
            case  R.id.todate_edit_screen_order:
                // 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
                new DoubleDatePickerDialog(this, 0, new DoubleDatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                          int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
                                          int endDayOfMonth) {
//                        String textString = String.format("开始时间：%d-%d-%d\n结束时间：%d-%d-%d\n", startYear,
//                                startMonthOfYear + 1, startDayOfMonth, endYear, endMonthOfYear + 1, endDayOfMonth);
                        String textString = String.format("%d-%d-%d", endYear,
                                endMonthOfYear + 1, endDayOfMonth);

                        toDateTV.setText(textString);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
                return;
            default:
                break;
        }
    }
}
