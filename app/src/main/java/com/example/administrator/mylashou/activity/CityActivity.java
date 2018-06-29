package com.example.administrator.mylashou.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.administrator.mylashou.R;
import com.example.administrator.mylashou.adapter.CityAdapter;
import com.example.administrator.mylashou.entity.City;
import com.example.administrator.mylashou.entity.ResponseObject;
import com.example.administrator.mylashou.util.CONST;
import com.example.administrator.mylashou.util.HttpUtil;
import com.example.administrator.mylashou.widget.SideBar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class CityActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button city_back;
    private ImageButton city_refresh;
    private ListView city_list_view;
    private CityAdapter cityAdapter;
    private SideBar side_bar;
    private EditText city_keyword;

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_city);

        city_back = findViewById(R.id.city_back);
        city_refresh = findViewById(R.id.city_refresh);
        city_keyword = findViewById(R.id.city_keyword);
        city_list_view = findViewById(R.id.city_list_view);
        side_bar = findViewById(R.id.side_bar);

        LoadCity();

        city_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        city_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadCity();
            }
        });

        side_bar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //根据传进来的参数设置listview的选中状态
                int index = cityAdapter.getPositionForSection(s.toUpperCase().charAt(0));
                city_list_view.setSelection(index);

            }
        });

        locationOption = new AMapLocationClientOption();

        initLocation();
    }

    /**
     * 定位
     * 初始化
     */
    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        //设置定位参数
        locationClient.setLocationOption(getDefaultOption());

        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();

        // 设置定位监听
        locationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {

                if (null != aMapLocation && aMapLocation.getErrorCode() == 0) {
                   // aMapLocation.getCity();

                    Log.i(TAG, "********************************: "+aMapLocation.getCity());
                } else {

                    Toast.makeText(CityActivity.this,"定位失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }


    }

    /**
     * 默认的定位参数
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }



    public void LoadCity(){
        HttpUtil.sendOkHttpRequest(CONST.CITY_LIST, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(CityActivity.this,"加载失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String responseData = response.body().string();

                Gson gson = new GsonBuilder().create();
                ResponseObject<List<City>> result =
                        gson.fromJson(responseData, new TypeToken<ResponseObject<List<City>>>(){}.getType());

                Log.i(TAG, "onResponse: "+result.getDatas());

                cityAdapter = new CityAdapter(CityActivity.this,R.layout.city_row,result.getDatas());

                city_list_view.setAdapter(cityAdapter);
            }
        });

    }


}
