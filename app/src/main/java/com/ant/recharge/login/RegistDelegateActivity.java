package com.ant.recharge.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.common.BaseActivity;
import com.ant.recharge.common.Md5Util;
import com.ant.recharge.common.NCallback;
import com.ant.recharge.common.NCallbackMsg;
import com.ant.recharge.common.NRestAdapter;
import com.ant.recharge.common.Profile;
import com.ant.recharge.common.json.JsonUtil;
import com.ant.recharge.entity.User;
import com.ant.recharge.main.CityPickerActivity;
import com.ant.recharge.main.MainTabActivity;
import com.ant.recharge.verified.VerifiedActivity;

import org.codehaus.jackson.type.TypeReference;

import java.util.List;

import retrofit.client.Header;
import retrofit.http.Query;

import static com.ant.recharge.R.id.personNameET;
import static com.squareup.okhttp.internal.Internal.logger;

public class RegistDelegateActivity extends BaseActivity {
    Spinner city;
    Spinner province;
    String tProvince;
    String tCity;

    boolean isDirty = false;
    String provinceId = "0";//省代码
    String provinceName = "";
    String cityId = "0";//市代码
    String cityName = "";

    String account = "";//账号
    String password = "";//密码
    String username = "";//用户名
    String inviteById = "";//销售id
    String addressDetail = "";//详细地址
    String sex = "";//性别
    String tel = "";//联系电话
    String fixTel = "";//固定电话
    String weixin = "";
    String qq = "";
    String email = "";
    String registerType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delegate_info);
        findViewById(R.id.btn_back).setVisibility(View.INVISIBLE);

        TextView titleTV = (TextView)findViewById(R.id.text_title);
        titleTV.setText(R.string.regist_delegate_title);

        Intent intent = getIntent();
        password = intent.getStringExtra("password");
        account = intent.getStringExtra("telephone");

        province = (Spinner) findViewById(R.id.person_province);
        city = (Spinner) findViewById(R.id.person_city);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.province,
                R.layout.spinner_checked_text);
        province.setAdapter(adapter);
        province.setOnItemSelectedListener(new spinnerItemSelected());
        city.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tCity = city.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        province.setSelection(Integer.valueOf(provinceId));


        ((RadioGroup)findViewById(R.id.sex_radio_group)).setOnCheckedChangeListener(new SubjectOnCheckedChangeListener());
    }

    // 二级联动adapter
    class spinnerItemSelected implements OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Spinner spinner = (Spinner) parent;
            String pro = (String) spinner.getItemAtPosition(position);
            tProvince = province.getSelectedItem().toString();
            // 处理省的市的显示
            ArrayAdapter<CharSequence> cityadapter = ArrayAdapter.createFromResource(getApplicationContext(),
                    R.array.def, R.layout.spinner_checked_text);
            if (pro.equals("直辖市")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.直辖市,
                        R.layout.spinner_checked_text);
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

            city.setAdapter(cityadapter);
            if(!isDirty){

                city.setSelection(Integer.valueOf(cityId));
                isDirty = true;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.confirm_btn_delegate) {
            EditText personNameET = (EditText)findViewById(R.id.personNameET);
            if(personNameET.getText().toString().trim().length() == 0)
            {
                Toast.makeText(this,"姓名不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if(tProvince.equals("-省份-") || tCity.equals("-城市-") || tCity.equals("-直辖市-"))
            {
                Toast.makeText(this,"请选择地区", Toast.LENGTH_SHORT).show();
                return;
            }
            if(sex.equals(""))
            {
                Toast.makeText(this,"请输入性别", Toast.LENGTH_SHORT).show();
                return;
            }
            EditText mailAddressTV = (EditText)findViewById(R.id.mailAddressTV);
            if(mailAddressTV.getText().toString().trim().length() == 0)
            {
                Toast.makeText(this,"请输入邮箱", Toast.LENGTH_SHORT).show();
                return;
            }
            EditText inviteET = (EditText)findViewById(R.id.invite_ET);
            if(inviteET.getText().toString().trim().length() == 0)
            {
                Toast.makeText(this,"请输入邀请码", Toast.LENGTH_SHORT).show();
                return;
            }

            registerType = "3";
            regist();

        }
        if(v.getId() == R.id.jump_btn_delegate) {
            registerType = "2";
            regist();
        }
    }


    /**
     * login step 1
     *
     */

     public void regist(){

        NetLoginInterface netInterface = new NRestAdapter<NetLoginInterface>(this,
                Profile.SERVER_ADDRESS_DEV, NetLoginInterface.class)
                .create();
        if(netInterface == null){
            Toast.makeText(this,"网络正在开小差!", Toast.LENGTH_SHORT).show();
            return;
        }
        EditText personNameET = (EditText)findViewById(R.id.personNameET);
         username = personNameET.getText().toString().trim();
         provinceId = CityPickerActivity.getProvinceIdByName(tProvince);
         cityId = CityPickerActivity.getCityIdByName(tCity);
         EditText mailAddressET = (EditText)findViewById(R.id.mailAddressTV);
         email = mailAddressET.getText().toString().trim();

         EditText inviteET = (EditText)findViewById(R.id.invite_ET);
         inviteById = inviteET.getText().toString().trim();

         netInterface.register(account, password, username, provinceId, cityId, inviteById, addressDetail, sex, tel, fixTel, weixin, qq, email, registerType,
                new NCallback<User>(this, new TypeReference<User>() {}) {
            @Override
            public void onSuccess(int statusCode, List<Header> headers, User oRet) {
                try {
                    String userStr = JsonUtil.encode(oRet);
                    //保存user附加信息， 使用json
                    writePreferences(NetLoginInterface.ANT_LOGIN_USER, "additional", userStr);

                    //进入主页
                    Intent mainIntent = new Intent(RegistDelegateActivity.this, MainTabActivity.class);
                    startActivity(mainIntent);
                    finish();

                } catch (Exception e) {
                    Toast.makeText(RegistDelegateActivity.this,"系统出错，请重新尝试!", Toast.LENGTH_SHORT).show();
                    logger.warning("list_header error" + e.getMessage());
                }

            }

            @Override
            public void onFailure(int statusCode, List<Header> headers, String infoCode, String infoMessage, Throwable throwable) {
                Toast.makeText(RegistDelegateActivity.this, infoMessage, Toast.LENGTH_SHORT).show();
            }

        });
    }

    private class SubjectOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch (i){
                case R.id.radioButtonMan:
                    sex = "1";
                    Toast.makeText(RegistDelegateActivity.this, "男人", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.radioButtonWomen:
                    sex = "0";
                    Toast.makeText(RegistDelegateActivity.this, "女人", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    return;
            }
        }
    }
}