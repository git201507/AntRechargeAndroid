package com.ant.recharge.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ant.recharge.R;
import com.ant.recharge.adapter.AreaAdapter;
import com.ant.recharge.common.BaseActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static java.lang.System.in;

public class CityPickerActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    ListView lvProvince;
    ListView lvCity;

    String provinceId;
    String provinceName;
    String cityId;
    String cityName;

    ArrayAdapter<CharSequence> adapterPA;
    ArrayAdapter<CharSequence> adapterCA;

    AreaAdapter adapterP;
    AreaAdapter adapterC;

    List<String> listP;
    List<String> listC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_city_picker);

        Intent intent = getIntent();
        provinceId = intent.getStringExtra("provinceId");
        provinceName = intent.getStringExtra("provinceName");
        cityId = intent.getStringExtra("cityId");
        cityName = intent.getStringExtra("cityName");

        initData();
        lvProvince=findViewById(R.id.lvProvince);
        adapterP=new AreaAdapter(listP, this);
        adapterP.setSelectPosition(listP.indexOf(provinceName));//设置默认选中的省份变颜色
        lvProvince.setAdapter(adapterP);
        lvProvince.setOnItemClickListener(this);

        lvCity=findViewById(R.id.lvCity);
        adapterC=new AreaAdapter(listC, this);
        adapterC.setSelectPosition(listC.indexOf(cityName));//设置默认选中的省份变颜色
        lvCity.setAdapter(adapterC);
        lvCity.setOnItemClickListener(this);
    }


    public void initData(){
        adapterPA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.province,
                R.layout.spinner_checked_text);
        listP = new ArrayList<>();
        for(int i=0; i<adapterPA.getCount();i++) {
            listP.add(adapterPA.getItem(i).toString());
        }
        // 处理省的市的显示
        if(Integer.valueOf(provinceId) > 0){
            // 处理省的市的显示

            if (provinceName.equals("直辖市")) {
                adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.直辖市,
                        R.layout.spinner_checked_text);
            }
//            if (pro.equals("北京")) {
//                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.北京,
//                        R.layout.spinner_checked_text);
//            } else if (pro.equals("天津")) {
//                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.天津,
//                        R.layout.spinner_checked_text);
            else if (provinceName.equals("河北")) {
                adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.河北,
                        R.layout.spinner_checked_text);
            } else if (provinceName.equals("山西")) {
                adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.山西,
                        R.layout.spinner_checked_text);
            } else if (provinceName.equals("内蒙古")) {
                adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.内蒙古,
                        R.layout.spinner_checked_text);
            } else if (provinceName.equals("辽宁")) {
                adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.辽宁,
                        R.layout.spinner_checked_text);
            } else if (provinceName.equals("吉林")) {
                adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.吉林,
                        R.layout.spinner_checked_text);
            } else if (provinceName.equals("黑龙江")) {
                adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.黑龙江,
                        R.layout.spinner_checked_text);
//            } else if (pro.equals("上海")) {
//                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.上海,
//                        R.layout.spinner_checked_text);
            } else if (provinceName.equals("江苏")) {
                adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.江苏,
                        R.layout.spinner_checked_text);
            } else if (provinceName.equals("浙江")) {
                adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.浙江,
                        R.layout.spinner_checked_text);
            } else if (provinceName.equals("安徽")) {
                adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.安徽,
                        R.layout.spinner_checked_text);
            } else if (provinceName.equals("福建")) {
                adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.福建,
                        R.layout.spinner_checked_text);
            } else if (provinceName.equals("江西")) {
                adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.江西,
                        R.layout.spinner_checked_text);
            } else if (provinceName.equals("山东")) {
                adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.山东,
                        R.layout.spinner_checked_text);
            } else if (provinceName.equals("河南")) {
                adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.河南,
                        R.layout.spinner_checked_text);
            } else if (provinceName.equals("湖北")) {
                adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.湖北,
                        R.layout.spinner_checked_text);
            } else if (provinceName.equals("湖南")) {
                adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.湖南,
                        R.layout.spinner_checked_text);
            } else if (provinceName.equals("广东")) {
                adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.广东,
                        R.layout.spinner_checked_text);
            } else if (provinceName.equals("广西")) {
                adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.广西,
                        R.layout.spinner_checked_text);
            } else if (provinceName.equals("海南")) {
                adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.海南,
                        R.layout.spinner_checked_text);
//            } else if (pro.equals("重庆")) {
//                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.重庆,
//                        R.layout.spinner_checked_text);
            } else if (provinceName.equals("四川")) {
                adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.四川,
                        R.layout.spinner_checked_text);
            } else if (provinceName.equals("贵州")) {
                adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.贵州,
                        R.layout.spinner_checked_text);
            } else if (provinceName.equals("云南")) {
                adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.云南,
                        R.layout.spinner_checked_text);
            } else if (provinceName.equals("西藏")) {
                adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.西藏,
                        R.layout.spinner_checked_text);
            } else if (provinceName.equals("陕西")) {
                adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.陕西,
                        R.layout.spinner_checked_text);
            } else if (provinceName.equals("甘肃")) {
                adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.甘肃,
                        R.layout.spinner_checked_text);
            } else if (provinceName.equals("青海")) {
                adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.青海,
                        R.layout.spinner_checked_text);
            } else if (provinceName.equals("宁夏")) {
                adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.宁夏,
                        R.layout.spinner_checked_text);
            } else if (provinceName.equals("新疆")) {
                adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.新疆,
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

        }
        else {
            adapterCA = ArrayAdapter.createFromResource(getApplicationContext(),
                    R.array.def, R.layout.spinner_checked_text);
        }

        listC = new ArrayList<>();
        for(int i=0; i<adapterCA.getCount();i++) {
            listC.add(adapterCA.getItem(i).toString());
        }
    }
    public static String getProvinceIdByName(String name) {
        String proId = "";
        if (name.equals("直辖市")) {
            proId = "10";
        }
        else if (name.equals("河北")) {
            proId = "130000";
        } else if (name.equals("山西")) {
            proId = "140000";
        } else if (name.equals("内蒙古")) {
            proId = "150000";
        } else if (name.equals("辽宁")) {
            proId = "210000";
        } else if (name.equals("吉林")) {
            proId = "220000";
        } else if (name.equals("黑龙江")) {
            proId = "230000";
        } else if (name.equals("江苏")) {
            proId = "320000";
        } else if (name.equals("浙江")) {
            proId = "330000";
        } else if (name.equals("安徽")) {
            proId = "340000";
        } else if (name.equals("福建")) {
            proId = "350000";
        } else if (name.equals("江西")) {
            proId = "360000";
        } else if (name.equals("山东")) {
            proId = "370000";
        } else if (name.equals("河南")) {
            proId = "410000";
        } else if (name.equals("湖北")) {
            proId = "420000";
        } else if (name.equals("湖南")) {
            proId = "430000";
        } else if (name.equals("广东")) {
            proId = "440000";
        } else if (name.equals("广西")) {
            proId = "450000";
        } else if (name.equals("海南")) {
            proId = "460000";
        } else if (name.equals("四川")) {
            proId = "510000";
        } else if (name.equals("贵州")) {
            proId = "520000";
        } else if (name.equals("云南")) {
            proId = "530000";
        } else if (name.equals("西藏")) {
            proId = "540000";
        } else if (name.equals("陕西")) {
            proId = "610000";
        } else if (name.equals("甘肃")) {
            proId = "620000";
        } else if (name.equals("青海")) {
            proId = "630000";
        } else if (name.equals("宁夏")) {
            proId = "640000";
        } else if (name.equals("新疆")) {
            proId = "650000";
        }
        return proId;
    }

    public static String getCityIdByName(String name){
        String retCity = "";

        if (name.equals("北京")) {
            retCity = "110000";
        }
        else if (name.equals("天津")) {
            retCity = "120000";
        }
        else if (name.equals("上海")) {
            retCity = "310000";
        }
        else if (name.equals("重庆")) {
            retCity = "500000";
        }
        else if (name.equals("石家庄")) {
            retCity = "130100";
        }
        else if (name.equals("唐山")) {
            retCity = "130200";
        }
        else if (name.equals("秦皇岛")) {
            retCity = "130300";
        }
        else if (name.equals("邯郸")) {
            retCity = "130400";
        }
        else if (name.equals("邢台")) {
            retCity = "130500";
        }
        else if (name.equals("保定")) {
            retCity = "130600";
        }
        else if (name.equals("张家口")) {
            retCity = "130700";
        }
        else if (name.equals("承德")) {
            retCity = "130800";
        }
        else if (name.equals("沧州")) {
            retCity = "130900";
        }
        else if (name.equals("廊坊")) {
            retCity = "131000";
        }
        else if (name.equals("衡水")) {
            retCity = "131100";
        }
        else if (name.equals("省直辖县级行政区划")) {
            retCity = "139000";
        }
//                    140000     山西省
        else if (name.equals("太原")) {
            retCity = "140100";
        }
        else if (name.equals("大同")) {
            retCity = "140200";
        }
        else if (name.equals("阳泉")) {
            retCity = "140300";
        }
        else if (name.equals("长治")) {
            retCity = "140400";
        }
        else if (name.equals("晋城")) {
            retCity = "140500";
        }
        else if (name.equals("朔州")) {
            retCity = "140600";
        }
        else if (name.equals("晋中")) {
            retCity = "140700";
        }
        else if (name.equals("运城")) {
            retCity = "140800";
        }
        else if (name.equals("忻州")) {
            retCity = "140900";
        }
        else if (name.equals("临汾")) {
            retCity = "141000";
        }
        else if (name.equals("吕梁")) {
            retCity = "141100";
        }
//                    150000     内蒙古自治区
        else if (name.equals("呼和浩特")) {
            retCity = "150100";
        }
        else if (name.equals("包头")) {
            retCity = "150200";
        }
        else if (name.equals("乌海")) {
            retCity = "150300";
        }
        else if (name.equals("赤峰")) {
            retCity = "150400";
        }
        else if (name.equals("通辽")) {
            retCity = "150500";
        }
        else if (name.equals("鄂尔多斯")) {
            retCity = "150600";
        }
        else if (name.equals("呼伦贝尔")) {
            retCity = "150700";
        }
        else if (name.equals("巴彦淖尔")) {
            retCity = "150800";
        }
        else if (name.equals("乌兰察布")) {
            retCity = "150900";
        }
        else if (name.equals("兴安")) {
            retCity = "152200";
        }
        else if (name.equals("锡林郭勒")) {
            retCity = "152500";
        }
        else if (name.equals("阿拉善")) {
            retCity = "152900";
        }
//                    210000     辽宁省
        else if (name.equals("沈阳")) {
            retCity = "210100";
        }
        else if (name.equals("大连")) {
            retCity = "210200";
        }
        else if (name.equals("鞍山")) {
            retCity = "210300";
        }
        else if (name.equals("抚顺")) {
            retCity = "210400";
        }
        else if (name.equals("本溪")) {
            retCity = "210500";
        }
        else if (name.equals("丹东")) {
            retCity = "210600";
        }
        else if (name.equals("锦州")) {
            retCity = "210700";
        }
        else if (name.equals("营口")) {
            retCity = "210800";
        }
        else if (name.equals("阜新")) {
            retCity = "210900";
        }
        else if (name.equals("辽阳")) {
            retCity = "211000";
        }
        else if (name.equals("盘锦")) {
            retCity = "211100";
        }
        else if (name.equals("铁岭")) {
            retCity = "211200";
        }
        else if (name.equals("朝阳")) {
            retCity = "211300";
        }
        else if (name.equals("葫芦岛")) {
            retCity = "211400";
        }
//                    220000     吉林省
        else if (name.equals("长春")) {
            retCity = "220100";
        }
        else if (name.equals("吉林")) {
            retCity = "220200";
        }
        else if (name.equals("四平")) {
            retCity = "220300";
        }
        else if (name.equals("辽源")) {
            retCity = "220400";
        }
        else if (name.equals("通化")) {
            retCity = "220500";
        }
        else if (name.equals("白山")) {
            retCity = "220600";
        }
        else if (name.equals("松原")) {
            retCity = "220700";
        }
        else if (name.equals("白城")) {
            retCity = "220800";
        }
        else if (name.equals("延边")) {
            retCity = "222400";
        }
//                    230000     黑龙江省
        else if (name.equals("哈尔滨")) {
            retCity = "230100";
        }
        else if (name.equals("齐齐哈尔")) {
            retCity = "230200";
        }
        else if (name.equals("鸡西")) {
            retCity = "230300";
        }
        else if (name.equals("鹤岗")) {
            retCity = "230400";
        }
        else if (name.equals("双鸭山")) {
            retCity = "230500";
        }
        else if (name.equals("大庆")) {
            retCity = "230600";
        }
        else if (name.equals("伊春")) {
            retCity = "230700";
        }
        else if (name.equals("佳木斯")) {
            retCity = "230800";
        }
        else if (name.equals("七台河")) {
            retCity = "230900";
        }
        else if (name.equals("牡丹江")) {
            retCity = "231000";
        }
        else if (name.equals("黑河")) {
            retCity = "231100";
        }
        else if (name.equals("绥化")) {
            retCity = "231200";
        }
        else if (name.equals("大兴安岭")) {
            retCity = "232700";
        }
//                    320000     江苏省
        else if (name.equals("南京")) {
            retCity = "320100";
        }
        else if (name.equals("无锡")) {
            retCity = "320200";
        }
        else if (name.equals("徐州")) {
            retCity = "320300";
        }
        else if (name.equals("常州")) {
            retCity = "320400";
        }
        else if (name.equals("苏州")) {
            retCity = "320500";
        }
        else if (name.equals("南通")) {
            retCity = "320600";
        }
        else if (name.equals("连云港")) {
            retCity = "320700";
        }
        else if (name.equals("淮安")) {
            retCity = "320800";
        }
        else if (name.equals("盐城")) {
            retCity = "320900";
        }
        else if (name.equals("扬州")) {
            retCity = "321000";
        }
        else if (name.equals("镇江")) {
            retCity = "321100";
        }
        else if (name.equals("泰州")) {
            retCity = "321200";
        }
        else if (name.equals("宿迁")) {
            retCity = "321300";
        }
//                    330000     浙江省
        else if (name.equals("杭州")) {
            retCity = "330100";
        }
        else if (name.equals("宁波")) {
            retCity = "330200";
        }
        else if (name.equals("温州")) {
            retCity = "330300";
        }
        else if (name.equals("嘉兴")) {
            retCity = "330400";
        }
        else if (name.equals("湖州")) {
            retCity = "330500";
        }
        else if (name.equals("绍兴")) {
            retCity = "330600";
        }
        else if (name.equals("金华")) {
            retCity = "330700";
        }
        else if (name.equals("衢州")) {
            retCity = "330800";
        }
        else if (name.equals("舟山")) {
            retCity = "330900";
        }
        else if (name.equals("台州")) {
            retCity = "331000";
        }
        else if (name.equals("丽水")) {
            retCity = "331100";
        }
//                    340000     安徽省
        else if (name.equals("合肥")) {
            retCity = "340100";
        }
        else if (name.equals("芜湖")) {
            retCity = "340200";
        }
        else if (name.equals("蚌埠")) {
            retCity = "340300";
        }
        else if (name.equals("淮南")) {
            retCity = "340400";
        }
        else if (name.equals("马鞍山")) {
            retCity = "340500";
        }
        else if (name.equals("淮北")) {
            retCity = "340600";
        }
        else if (name.equals("铜陵")) {
            retCity = "340700";
        }
        else if (name.equals("安庆")) {
            retCity = "340800";
        }
        else if (name.equals("黄山")) {
            retCity = "341000";
        }
        else if (name.equals("滁州")) {
            retCity = "341100";
        }
        else if (name.equals("阜阳")) {
            retCity = "341200";
        }
        else if (name.equals("宿州")) {
            retCity = "341300";
        }
        else if (name.equals("六安")) {
            retCity = "341500";
        }
        else if (name.equals("亳州")) {
            retCity = "341600";
        }
        else if (name.equals("池州")) {
            retCity = "341700";
        }
        else if (name.equals("宣城")) {
            retCity = "341800";
        }
//                    350000     福建省
        else if (name.equals("福州")) {
            retCity = "350100";
        }
        else if (name.equals("厦门")) {
            retCity = "350200";
        }
        else if (name.equals("莆田")) {
            retCity = "350300";
        }
        else if (name.equals("三明")) {
            retCity = "350400";
        }
        else if (name.equals("泉州")) {
            retCity = "350500";
        }
        else if (name.equals("漳州")) {
            retCity = "350600";
        }
        else if (name.equals("南平")) {
            retCity = "350700";
        }
        else if (name.equals("龙岩")) {
            retCity = "350800";
        }
        else if (name.equals("宁德")) {
            retCity = "350900";
        }
//                    360000     江西省
        else if (name.equals("南昌")) {
            retCity = "360100";
        }
        else if (name.equals("景德镇")) {
            retCity = "360200";
        }
        else if (name.equals("萍乡")) {
            retCity = "360300";
        }
        else if (name.equals("九江")) {
            retCity = "360400";
        }
        else if (name.equals("新余")) {
            retCity = "360500";
        }
        else if (name.equals("鹰潭")) {
            retCity = "360600";
        }
        else if (name.equals("赣州")) {
            retCity = "360700";
        }
        else if (name.equals("吉安")) {
            retCity = "360800";
        }
        else if (name.equals("宜春")) {
            retCity = "360900";
        }
        else if (name.equals("抚州")) {
            retCity = "361000";
        }
        else if (name.equals("上饶")) {
            retCity = "361100";
        }
//                    370000     山东省
        else if (name.equals("济南")) {
            retCity = "370100";
        }
        else if (name.equals("青岛")) {
            retCity = "370200";
        }
        else if (name.equals("淄博")) {
            retCity = "370300";
        }
        else if (name.equals("枣庄")) {
            retCity = "370400";
        }
        else if (name.equals("东营")) {
            retCity = "370500";
        }
        else if (name.equals("烟台")) {
            retCity = "370600";
        }
        else if (name.equals("潍坊")) {
            retCity = "370700";
        }
        else if (name.equals("济宁")) {
            retCity = "370800";
        }
        else if (name.equals("泰安")) {
            retCity = "370900";
        }
        else if (name.equals("威海")) {
            retCity = "371000";
        }
        else if (name.equals("日照")) {
            retCity = "371100";
        }
        else if (name.equals("莱芜")) {
            retCity = "371200";
        }
        else if (name.equals("临沂")) {
            retCity = "371300";
        }
        else if (name.equals("德州")) {
            retCity = "371400";
        }
        else if (name.equals("聊城")) {
            retCity = "371500";
        }
        else if (name.equals("滨州")) {
            retCity = "371600";
        }
        else if (name.equals("菏泽")) {
            retCity = "371700";
        }
//                    410000     河南省
        else if (name.equals("郑州")) {
            retCity = "410100";
        }
        else if (name.equals("开封")) {
            retCity = "410200";
        }
        else if (name.equals("洛阳")) {
            retCity = "410300";
        }
        else if (name.equals("平顶山")) {
            retCity = "410500";
        }
        else if (name.equals("安阳")) {
            retCity = "410600";
        }
        else if (name.equals("鹤壁")) {
            retCity = "410700";
        }
        else if (name.equals("新乡")) {
            retCity = "410800";
        }
        else if (name.equals("焦作")) {
            retCity = "410900";
        }
        else if (name.equals("濮阳")) {
            retCity = "410900";
        }
        else if (name.equals("许昌")) {
            retCity = "411000";
        }
        else if (name.equals("漯河")) {
            retCity = "411100";
        }
        else if (name.equals("三门峡")) {
            retCity = "411200";
        }
        else if (name.equals("南阳")) {
            retCity = "411300";
        }
        else if (name.equals("商丘")) {
            retCity = "411400";
        }
        else if (name.equals("信阳")) {
            retCity = "411500";
        }
        else if (name.equals("周口")) {
            retCity = "411600";
        }
        else if (name.equals("驻马店")) {
            retCity = "411700";
        }
//                    420000     湖北省
        else if (name.equals("武汉")) {
            retCity = "420100";
        }
        else if (name.equals("黄石")) {
            retCity = "420200";
        }
        else if (name.equals("十堰")) {
            retCity = "420300";
        }
        else if (name.equals("宜昌")) {
            retCity = "420500";
        }
        else if (name.equals("襄阳")) {
            retCity = "420600";
        }
        else if (name.equals("鄂州")) {
            retCity = "420700";
        }
        else if (name.equals("荆门")) {
            retCity = "420800";
        }
        else if (name.equals("孝感")) {
            retCity = "420900";
        }
        else if (name.equals("荆州")) {
            retCity = "421000";
        }
        else if (name.equals("黄冈")) {
            retCity = "421100";
        }
        else if (name.equals("咸宁")) {
            retCity = "421200";
        }
        else if (name.equals("随州")) {
            retCity = "421300";
        }
//                    430000     湖南省
        else if (name.equals("长沙")) {
            retCity = "430100";
        }
        else if (name.equals("株洲")) {
            retCity = "430200";
        }
        else if (name.equals("湘潭")) {
            retCity = "430300";
        }
        else if (name.equals("衡阳")) {
            retCity = "430400";
        }
        else if (name.equals("邵阳")) {
            retCity = "430500";
        }
        else if (name.equals("岳阳")) {
            retCity = "430600";
        }
        else if (name.equals("常德")) {
            retCity = "430700";
        }
        else if (name.equals("张家界")) {
            retCity = "430800";
        }
        else if (name.equals("益阳")) {
            retCity = "430900";
        }
        else if (name.equals("郴州")) {
            retCity = "431000";
        }
        else if (name.equals("永州")) {
            retCity = "431100";
        }
        else if (name.equals("怀化")) {
            retCity = "431200";
        }
        else if (name.equals("洪江")) {
            retCity = "431281";
        }
        else if (name.equals("娄底")) {
            retCity = "431300";
        }
        else if (name.equals("湘西土家族苗族自治州")) {
            retCity = "433100";
        }
//                    440000     广东省
        else if (name.equals("广州")) {
            retCity = "440100";
        }
        else if (name.equals("韶关")) {
            retCity = "440200";
        }
        else if (name.equals("深圳")) {
            retCity = "440300";
        }
        else if (name.equals("珠海")) {
            retCity = "440400";
        }
        else if (name.equals("汕头")) {
            retCity = "440500";
        }
        else if (name.equals("佛山")) {
            retCity = "440600";
        }
        else if (name.equals("江门")) {
            retCity = "440700";
        }
        else if (name.equals("湛江")) {
            retCity = "440800";
        }
        else if (name.equals("茂名")) {
            retCity = "440900";
        }
        else if (name.equals("肇庆")) {
            retCity = "441200";
        }
        else if (name.equals("惠州")) {
            retCity = "441300";
        }
        else if (name.equals("梅州")) {
            retCity = "441400";
        }
        else if (name.equals("汕尾")) {
            retCity = "441500";
        }
        else if (name.equals("河源")) {
            retCity = "441600";
        }
        else if (name.equals("阳江")) {
            retCity = "441700";
        }
        else if (name.equals("清远")) {
            retCity = "441800";
        }
        else if (name.equals("东莞")) {
            retCity = "441900";
        }
        else if (name.equals("中山")) {
            retCity = "442000";
        }
        else if (name.equals("潮州")) {
            retCity = "445100";
        }
        else if (name.equals("揭阳")) {
            retCity = "445200";
        }
        else if (name.equals("云浮")) {
            retCity = "445300";
        }
//                    450000     广西壮族自治区
        else if (name.equals("南宁")) {
            retCity = "450100";
        }
        else if (name.equals("柳州")) {
            retCity = "450200";
        }
        else if (name.equals("桂林")) {
            retCity = "450300";
        }
        else if (name.equals("梧州")) {
            retCity = "450400";
        }
        else if (name.equals("北海")) {
            retCity = "450500";
        }
        else if (name.equals("防城港")) {
            retCity = "450600";
        }
        else if (name.equals("钦州")) {
            retCity = "450700";
        }
        else if (name.equals("贵港")) {
            retCity = "450800";
        }
        else if (name.equals("玉林")) {
            retCity = "450900";
        }
        else if (name.equals("百色")) {
            retCity = "451000";
        }
        else if (name.equals("贺州")) {
            retCity = "451100";
        }
        else if (name.equals("河池")) {
            retCity = "451200";
        }
        else if (name.equals("来宾")) {
            retCity = "451300";
        }
        else if (name.equals("崇左")) {
            retCity = "451400";
        }
//                    460000     海南省
        else if (name.equals("海口")) {
            retCity = "460100";
        }
        else if (name.equals("三亚")) {
            retCity = "460200";
        }
        else if (name.equals("三沙")) {
            retCity = "460300";
        }
        else if (name.equals("儋州")) {
            retCity = "460400";
        }
//
//                    510000     四川省
        else if (name.equals("成都")) {
            retCity = "510100";
        }
        else if (name.equals("自贡")) {
            retCity = "510300";
        }
        else if (name.equals("攀枝花")) {
            retCity = "510400";
        }
        else if (name.equals("泸州")) {
            retCity = "510500";
        }
        else if (name.equals("德阳")) {
            retCity = "510600";
        }
        else if (name.equals("绵阳")) {
            retCity = "510700";
        }
        else if (name.equals("广元")) {
            retCity = "510800";
        }
        else if (name.equals("遂宁")) {
            retCity = "510900";
        }
        else if (name.equals("内江")) {
            retCity = "511000";
        }
        else if (name.equals("乐山")) {
            retCity = "511100";
        }
        else if (name.equals("南充")) {
            retCity = "511300";
        }
        else if (name.equals("眉山")) {
            retCity = "511400";
        }
        else if (name.equals("宜宾")) {
            retCity = "511500";
        }
        else if (name.equals("广安")) {
            retCity = "511600";
        }
        else if (name.equals("达州")) {
            retCity = "511700";
        }
        else if (name.equals("雅安")) {
            retCity = "511800";
        }
        else if (name.equals("巴中")) {
            retCity = "511900";
        }
        else if (name.equals("资阳")) {
            retCity = "512000";
        }
//　513200         　阿坝藏族羌族自治州
//　513300         　甘孜藏族自治州
//　513400         　凉山彝族自治州
//                    520000     贵州省
        else if (name.equals("贵阳")) {
            retCity = "520100";
        }
        else if (name.equals("六盘水")) {
            retCity = "520200";
        }
        else if (name.equals("遵义")) {
            retCity = "520300";
        }
        else if (name.equals("安顺")) {
            retCity = "520400";
        }
        else if (name.equals("毕节")) {
            retCity = "520500";
        }
        else if (name.equals("铜仁")) {
            retCity = "520600";
        }
//　522300         　黔西南布依族苗族自治州
//　522600         　黔东南苗族侗族自治州
//　522700         　黔南布依族苗族自治州
//                    530000     云南省
        else if (name.equals("昆明")) {
            retCity = "530100";
        }
        else if (name.equals("曲靖")) {
            retCity = "530300";
        }
        else if (name.equals("玉溪")) {
            retCity = "530400";
        }
        else if (name.equals("保山")) {
            retCity = "530500";
        }
        else if (name.equals("昭通")) {
            retCity = "530600";
        }
        else if (name.equals("丽江")) {
            retCity = "530700";
        }
        else if (name.equals("普洱")) {
            retCity = "530800";
        }
        else if (name.equals("临沧")) {
            retCity = "530900";
        }
//　532300         　楚雄彝族自治州
//　532500         　红河哈尼族彝族自治州
//　532600         　文山壮族苗族自治州
//　532800         　西双版纳傣族自治州
//　532900         　大理白族自治州
//　533100         　德宏傣族景颇族自治州
//　533300         　怒江傈僳族自治州
//　533400         　迪庆藏族自治州
//                    540000     西藏自治区
        else if (name.equals("拉萨")) {
            retCity = "540100";
        }
        else if (name.equals("日喀则")) {
            retCity = "540200";
        }
        else if (name.equals("昌都")) {
            retCity = "540300";
        }
        else if (name.equals("林芝")) {
            retCity = "540400";
        }
        else if (name.equals("山南")) {
            retCity = "540500";
        }
        else if (name.equals("那曲")) {
            retCity = "542400";
        }
        else if (name.equals("阿里")) {
            retCity = "542500";
        }
//                    610000     陕西省
        else if (name.equals("西安")) {
            retCity = "610100";
        }
        else if (name.equals("铜川")) {
            retCity = "610200";
        }
        else if (name.equals("宝鸡")) {
            retCity = "610300";
        }
        else if (name.equals("咸阳")) {
            retCity = "610400";
        }
        else if (name.equals("渭南")) {
            retCity = "610500";
        }
        else if (name.equals("延安")) {
            retCity = "610600";
        }
        else if (name.equals("汉中")) {
            retCity = "610700";
        }
        else if (name.equals("榆林")) {
            retCity = "610800";
        }
        else if (name.equals("安康")) {
            retCity = "610900";
        }
        else if (name.equals("商洛")) {
            retCity = "611000";
        }
//                    620000     甘肃省
        else if (name.equals("兰州")) {
            retCity = "620100";
        }
        else if (name.equals("嘉峪关")) {
            retCity = "620200";
        }
        else if (name.equals("金昌")) {
            retCity = "620300";
        }
        else if (name.equals("白银")) {
            retCity = "620400";
        }
        else if (name.equals("天水")) {
            retCity = "620500";
        }
        else if (name.equals("武威")) {
            retCity = "620600";
        }
        else if (name.equals("张掖")) {
            retCity = "620700";
        }
        else if (name.equals("平凉")) {
            retCity = "620800";
        }
        else if (name.equals("酒泉")) {
            retCity = "620900";
        }
        else if (name.equals("庆阳")) {
            retCity = "621000";
        }
        else if (name.equals("定西")) {
            retCity = "621100";
        }
        else if (name.equals("陇南")) {
            retCity = "621200";
        }
//　622900         　临夏回族自治州
//　623000         　甘南藏族自治州
//                    630000     青海省
        else if (name.equals("西宁")) {
            retCity = "630100";
        }
        else if (name.equals("海东")) {
            retCity = "630200";
        }
//　632200         　海北藏族自治州
//　632300         　黄南藏族自治州
//　632500         　海南藏族自治州
//　632600         　果洛藏族自治州
//　632700         　玉树藏族自治州
//　632800         　海西蒙古族藏族自治州
//                    640000     宁夏回族自治区
        else if (name.equals("银川")) {
            retCity = "640100";
        }
        else if (name.equals("石嘴山")) {
            retCity = "640200";
        }
        else if (name.equals("吴忠")) {
            retCity = "640300";
        }
        else if (name.equals("固原")) {
            retCity = "640400";
        }
        else if (name.equals("中卫")) {
            retCity = "640500";
        }
//                    650000     新疆维吾尔自治区
        else if (name.equals("乌鲁木齐")) {
            retCity = "650100";
        }
        else if (name.equals("克拉玛依")) {
            retCity = "650200";
        }
        else if (name.equals("吐鲁番")) {
            retCity = "650400";
        }
        else if (name.equals("哈密")) {
            retCity = "650500";
        }
//　652300         　昌吉回族自治州
//　652700         　博尔塔拉蒙古自治州
//　652800         　巴音郭楞蒙古自治州
//　652900         　阿克苏地区
//　653000         　克孜勒苏柯尔克孜自治州
//　653100         　喀什地区
//　653200         　和田地区
//　654000         　伊犁哈萨克自治州
//　654200         　塔城地区
//　654300         　阿勒泰地区
//　659000         　自治区直辖县级行政区划

        return retCity;
    }

    /**
     * 每个list 点击事件
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.lvProvince://点击省列表
                provinceId = String.valueOf(i);
                provinceName = listP.get(i).toString();

                // 处理省的市的显示
                adapterCA = ArrayAdapter.createFromResource(getApplicationContext(),
                        R.array.def, R.layout.spinner_checked_text);
                if (provinceName.equals("直辖市")) {
                    adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.直辖市,
                            R.layout.spinner_checked_text);
                    provinceId = "10";
                }
//            if (pro.equals("北京")) {
//                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.北京,
//                        R.layout.spinner_checked_text);
//            } else if (pro.equals("天津")) {
//                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.天津,
//                        R.layout.spinner_checked_text);
                else if (provinceName.equals("河北")) {
                    adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.河北,
                            R.layout.spinner_checked_text);
                    provinceId = "130000";
                } else if (provinceName.equals("山西")) {
                    adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.山西,
                            R.layout.spinner_checked_text);
                    provinceId = "140000";
                } else if (provinceName.equals("内蒙古")) {
                    adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.内蒙古,
                            R.layout.spinner_checked_text);
                    provinceId = "150000";
                } else if (provinceName.equals("辽宁")) {
                    adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.辽宁,
                            R.layout.spinner_checked_text);
                    provinceId = "210000";
                } else if (provinceName.equals("吉林")) {
                    adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.吉林,
                            R.layout.spinner_checked_text);
                    provinceId = "220000";
                } else if (provinceName.equals("黑龙江")) {
                    adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.黑龙江,
                            R.layout.spinner_checked_text);
                    provinceId = "230000";
//            } else if (pro.equals("上海")) {
//                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.上海,
//                        R.layout.spinner_checked_text);
                } else if (provinceName.equals("江苏")) {
                    adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.江苏,
                            R.layout.spinner_checked_text);
                    provinceId = "320000";
                } else if (provinceName.equals("浙江")) {
                    adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.浙江,
                            R.layout.spinner_checked_text);
                    provinceId = "330000";
                } else if (provinceName.equals("安徽")) {
                    adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.安徽,
                            R.layout.spinner_checked_text);
                    provinceId = "340000";
                } else if (provinceName.equals("福建")) {
                    adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.福建,
                            R.layout.spinner_checked_text);
                    provinceId = "350000";
                } else if (provinceName.equals("江西")) {
                    adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.江西,
                            R.layout.spinner_checked_text);
                    provinceId = "360000";
                } else if (provinceName.equals("山东")) {
                    adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.山东,
                            R.layout.spinner_checked_text);
                    provinceId = "370000";
                } else if (provinceName.equals("河南")) {
                    adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.河南,
                            R.layout.spinner_checked_text);
                    provinceId = "410000";
                } else if (provinceName.equals("湖北")) {
                    adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.湖北,
                            R.layout.spinner_checked_text);
                    provinceId = "420000";
                } else if (provinceName.equals("湖南")) {
                    adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.湖南,
                            R.layout.spinner_checked_text);
                    provinceId = "430000";
                } else if (provinceName.equals("广东")) {
                    adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.广东,
                            R.layout.spinner_checked_text);
                    provinceId = "440000";
                } else if (provinceName.equals("广西")) {
                    adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.广西,
                            R.layout.spinner_checked_text);
                    provinceId = "450000";
                } else if (provinceName.equals("海南")) {
                    adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.海南,
                            R.layout.spinner_checked_text);
                    provinceId = "460000";
//            } else if (pro.equals("重庆")) {
//                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.重庆,
//                        R.layout.spinner_checked_text);
                } else if (provinceName.equals("四川")) {
                    adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.四川,
                            R.layout.spinner_checked_text);
                    provinceId = "510000";
                } else if (provinceName.equals("贵州")) {
                    adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.贵州,
                            R.layout.spinner_checked_text);
                    provinceId = "520000";
                } else if (provinceName.equals("云南")) {
                    adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.云南,
                            R.layout.spinner_checked_text);
                    provinceId = "530000";
                } else if (provinceName.equals("西藏")) {
                    adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.西藏,
                            R.layout.spinner_checked_text);
                    provinceId = "540000";
                } else if (provinceName.equals("陕西")) {
                    adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.陕西,
                            R.layout.spinner_checked_text);
                    provinceId = "610000";
                } else if (provinceName.equals("甘肃")) {
                    adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.甘肃,
                            R.layout.spinner_checked_text);
                    provinceId = "620000";
                } else if (provinceName.equals("青海")) {
                    adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.青海,
                            R.layout.spinner_checked_text);
                    provinceId = "630000";
                } else if (provinceName.equals("宁夏")) {
                    adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.宁夏,
                            R.layout.spinner_checked_text);
                    provinceId = "640000";
                } else if (provinceName.equals("新疆")) {
                    adapterCA = ArrayAdapter.createFromResource(getApplicationContext(), R.array.新疆,
                            R.layout.spinner_checked_text);
                    provinceId = "650000";
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
                listC.removeAll(listC);
                for(int j=0; j<adapterCA.getCount();j++) {
                    listC.add(adapterCA.getItem(j).toString());
                }

                adapterP.setSelectPosition(i);//为了让选择的item显示不同的颜色
                //每一次选择省，都需要把 之前选择的市和区的item初始化为0，默认选择第一个
                adapterC.setSelectPosition(0);//为了让选择的item显示不同的颜色

                //点击省份，需要三个listview都刷新data ，以便执行 setSelectPosition ，刷新选择的项目的颜色
                adapterP.notifyDataSetChanged();
                adapterC.notifyDataSetChanged();
                break;
            case R.id.lvCity:
                cityId = String.valueOf(i);
                cityName = listC.get(i).toString();
                cityId = CityPickerActivity.getCityIdByName(cityName);

                Intent data = new Intent();

                data.putExtra("provinceId", provinceId);
                data.putExtra("provinceName", provinceName);
                data.putExtra("cityId", cityId);
                data.putExtra("cityName", cityName);
                setResult(200, data);
                Toast.makeText(CityPickerActivity.this, provinceId+provinceName+cityId+cityName, Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }
}