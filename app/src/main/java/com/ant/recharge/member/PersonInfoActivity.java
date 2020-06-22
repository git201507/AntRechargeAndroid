package com.ant.recharge.member;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.common.BaseActivity;
import com.ant.recharge.common.NCallback;
import com.ant.recharge.common.NCallbackMsg;
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.StringUtils;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.entity.OrderEntity;
import com.ant.recharge.entity.User;
import com.ant.recharge.login.NetLoginInterface;
import com.ant.recharge.main.CityPickerActivity;
import com.ant.recharge.welcome.WelcomeActivity;
import com.squareup.okhttp.internal.spdy.Header;

import java.util.List;

import retrofit.http.POST;
import retrofit.http.Query;

public class PersonInfoActivity extends BaseActivity {
    Spinner citySpinner;
    Spinner provinceSpinner;

    String provinceName;
    String cityName;
    String provinceId = "0";
    String cityId = "0";

    EditText personNameET;
    TextView accountNameTV;
    TextView areaTV;

    RadioButton radioButtonMan;
    RadioButton radioButtonWomen;

    EditText mailAddressTV;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView titleTV = (TextView)findViewById(R.id.text_title);
        titleTV.setText(R.string.person_manage_title);

        personNameET = (EditText)findViewById(R.id.personNameET);
        accountNameTV = (TextView)findViewById(R.id.accountNameET);
        areaTV = (TextView)findViewById(R.id.area_text_person);

        radioButtonMan = (RadioButton)findViewById(R.id.radioButtonMan);
        radioButtonWomen = (RadioButton)findViewById(R.id.radioButtonWomen);

        mailAddressTV = (EditText)findViewById(R.id.mailAddressTV);


        SharedPreferences preferences = getSharedPreferences(NetLoginInterface.ANT_LOGIN_USER, MODE_PRIVATE);
        String userStr = preferences.getString("additional", null);
        try {
            if (!StringUtils.isBlank(userStr)){
                user = JsonUtil.decode(userStr, User.class);
                personNameET.setText(user.getName());
                accountNameTV.setText(user.getLoginName());
                if(user.getGender().equals("1")){
                    radioButtonMan.setChecked(true);
                    radioButtonWomen.setChecked(false);
                }
                else {
                    radioButtonMan.setChecked(false);
                    radioButtonWomen.setChecked(true);
                }
                mailAddressTV.setText(user.getEmail());

                areaTV.setText(user.getUserInfoProvinceName() +"|"+ user.getUserInfoCityName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent intent = getIntent();

        provinceSpinner = (Spinner) findViewById(R.id.person_province);
        citySpinner = (Spinner) findViewById(R.id.person_city);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.province,
                R.layout.spinner_checked_text);
        provinceSpinner.setAdapter(adapter);
        provinceSpinner.setOnItemSelectedListener(new spinnerItemSelected());
        citySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cityName = citySpinner.getSelectedItem().toString();
                cityId = CityPickerActivity.getCityIdByName(cityName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        provinceSpinner.setSelection(Integer.valueOf(0));


        ((RadioGroup)findViewById(R.id.sex_radio_group)).setOnCheckedChangeListener(new SubjectOnCheckedChangeListener());
    }

    // 二级联动adapter
    class spinnerItemSelected implements OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Spinner spinner = (Spinner) parent;
            String pro = (String) spinner.getItemAtPosition(position);
            provinceName = provinceSpinner.getSelectedItem().toString();
            provinceId = CityPickerActivity.getProvinceIdByName(provinceName);
            // 处理省的市的显示
            ArrayAdapter<CharSequence> cityadapter = ArrayAdapter.createFromResource(getApplicationContext(),
                    R.array.def, R.layout.spinner_checked_text);
            if (pro.equals("直辖市")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.直辖市,
                        R.layout.spinner_checked_text);
                provinceId = "10";
            }
//            if (pro.equals("北京")) {
//                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.北京,
//                        R.layout.spinner_checked_text);
//            } else if (pro.equals("天津")) {
//                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.天津,
//                        R.layout.spinner_checked_text);
            else if (pro.equals("河北")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.河北,
                        R.layout.spinner_checked_text);
            } else if (pro.equals("山西")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.山西,
                        R.layout.spinner_checked_text);
            } else if (pro.equals("内蒙古")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.内蒙古,
                        R.layout.spinner_checked_text);
            } else if (pro.equals("辽宁")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.辽宁,
                        R.layout.spinner_checked_text);
            } else if (pro.equals("吉林")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.吉林,
                        R.layout.spinner_checked_text);
            } else if (pro.equals("黑龙江")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.黑龙江,
                        R.layout.spinner_checked_text);
//            } else if (pro.equals("上海")) {
//                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.上海,
//                        R.layout.spinner_checked_text);
            } else if (pro.equals("江苏")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.江苏,
                        R.layout.spinner_checked_text);
            } else if (pro.equals("浙江")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.浙江,
                        R.layout.spinner_checked_text);
            } else if (pro.equals("安徽")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.安徽,
                        R.layout.spinner_checked_text);
            } else if (pro.equals("福建")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.福建,
                        R.layout.spinner_checked_text);
            } else if (pro.equals("江西")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.江西,
                        R.layout.spinner_checked_text);
            } else if (pro.equals("山东")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.山东,
                        R.layout.spinner_checked_text);
            } else if (pro.equals("河南")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.河南,
                        R.layout.spinner_checked_text);
            } else if (pro.equals("湖北")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.湖北,
                        R.layout.spinner_checked_text);
            } else if (pro.equals("湖南")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.湖南,
                        R.layout.spinner_checked_text);
            } else if (pro.equals("广东")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.广东,
                        R.layout.spinner_checked_text);
            } else if (pro.equals("广西")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.广西,
                        R.layout.spinner_checked_text);
            } else if (pro.equals("海南")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.海南,
                        R.layout.spinner_checked_text);
//            } else if (pro.equals("重庆")) {
//                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.重庆,
//                        R.layout.spinner_checked_text);
            } else if (pro.equals("四川")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.四川,
                        R.layout.spinner_checked_text);
            } else if (pro.equals("贵州")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.贵州,
                        R.layout.spinner_checked_text);
            } else if (pro.equals("云南")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.云南,
                        R.layout.spinner_checked_text);
            } else if (pro.equals("西藏")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.西藏,
                        R.layout.spinner_checked_text);
            } else if (pro.equals("陕西")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.陕西,
                        R.layout.spinner_checked_text);
            } else if (pro.equals("甘肃")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.甘肃,
                        R.layout.spinner_checked_text);
            } else if (pro.equals("青海")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.青海,
                        R.layout.spinner_checked_text);
            } else if (pro.equals("宁夏")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.宁夏,
                        R.layout.spinner_checked_text);
            } else if (pro.equals("新疆")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.新疆,
                        R.layout.spinner_checked_text);
            }
//            } else if (pro.equals("台湾")) {
//                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.台湾,
//                        R.layout.spinner_checked_text);
//            } else if (pro.equals("香港")) {
//                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.香港,
//                        R.layout.spinner_checked_text);
//            } else if (pro.equals("澳门")) {
//                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.澳门,
//                        R.layout.spinner_checked_text);
//            }

            citySpinner.setAdapter(cityadapter);
            citySpinner.setSelection(Integer.valueOf(0));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.confirm_city_picker) {
//            Intent data = new Intent();
//            String selectedProvinceId = String.valueOf(province.getSelectedItemPosition());
//            String selectedProvinceaName = (String) province.getSelectedItem();
//            String selectedCityId = String.valueOf(city.getSelectedItemPosition());
//            String selectedCityName = (String) city.getSelectedItem();
//
//            data.putExtra("provinceId", selectedProvinceId);
//            data.putExtra("provinceName", selectedProvinceaName);
//            data.putExtra("cityId", selectedCityId);
//            data.putExtra("cityName", selectedCityName);
//            setResult(200, data);

            user.setName(personNameET.getText().toString());
            user.setLoginName(accountNameTV.getText().toString());
            user.setUserInfoProvinceCode(provinceId);
            user.setUserInfoProvinceName(provinceName);
            user.setUserInfoCityCode(cityId);
            user.setUserInfoCityName(cityName);
            user.setGender(radioButtonMan.isChecked()?"1":"0");
            user.setEmail(mailAddressTV.getText().toString());
            PersonInfoActivity.NetInterface netInterface = new NRestAdapter<PersonInfoActivity.NetInterface>(this,
                    Profile.SERVER_ADDRESS_DEV, PersonInfoActivity.NetInterface.class)
                    .create();

            try {
                final String userInfo = JsonUtil.encode(user);
                netInterface.savePersonalInfo(user.getToken(), userInfo, new NCallbackMsg() {
                    @Override
                    public void onSuccess(int statusCode, List<retrofit.client.Header> headers, String result) {

                        Toast.makeText(PersonInfoActivity.this, result, Toast.LENGTH_SHORT).show();
                        writePreferences(NetLoginInterface.ANT_LOGIN_USER, "additional", userInfo);
                        setResult(200);
                        finish();
                    }

                    @Override
                    public void onFailure(int statusCode, List<retrofit.client.Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                        Toast.makeText(PersonInfoActivity.this, infoMessage, Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class SubjectOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch (i){
                case R.id.radioButtonMan:
                    Toast.makeText(PersonInfoActivity.this, "男人", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.radioButtonWomen:
                    Toast.makeText(PersonInfoActivity.this, "女人", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    return;
            }
        }
    }

    public interface NetInterface{
        //头部信息
        @POST("/savePersonalInfo.do")
        public void savePersonalInfo(@Query("token") String token,
                                       @Query("user") String user,
                                     NCallbackMsg callback);
    }
}