package com.example.administrator.mylashou.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
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

    private TextView myLocation;

    private Handler mHandler;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;


    @SuppressLint("HandlerLeak")
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

        View cityhead = getLayoutInflater().inflate(R.layout.location_city_item,null);
        myLocation = findViewById(R.id.tv_location_city);
        city_list_view.addHeaderView(cityhead);

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


        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 100) {
                    city_list_view.setAdapter(cityAdapter);
                }

            }
        };

        //LocationCity();


        //Toast.makeText(CityActivity.this,"定位成功",Toast.LENGTH_SHORT).show();
        // 首次自动加载数据
        new Handler(new Handler.Callback() {

            @Override
            public boolean handleMessage(Message msg) {
               Toast.makeText(CityActivity.this,"定位城市：太原",Toast.LENGTH_SHORT).show();
               return true;
            }
        }).sendEmptyMessageDelayed(0, 1500);

    }

    private void LocationCity() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());

        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();

        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);

        if(null != mLocationClient){
            mLocationClient.setLocationOption(mLocationOption);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }

        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //设置定位模式为AMapLocationMode.Device_Sensors，仅设备模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(false);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

        //声明定位回调监听器,设置定位回调监听
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {

                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        //可在其中解析aMapLocation获取相应内容。
                        myLocation.setText(aMapLocation.getCity());

                        Toast.makeText(CityActivity.this,aMapLocation.getCity(),Toast.LENGTH_SHORT).show();

                    }else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        Log.i(TAG,"location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());



                    }
                }

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
//        mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。

    }

    public void LoadCity(){
        HttpUtil.sendOkHttpRequest(CONST.CITY_LIST, null,null,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(CityActivity.this,"加载失败",Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onFailure:  加载失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String responseData = response.body().string();

                Gson gson = new GsonBuilder().create();
                ResponseObject<List<City>> result =
                        gson.fromJson(responseData, new TypeToken<ResponseObject<List<City>>>(){}.getType());

                Log.i(TAG, "onResponse: "+result.getDatas());

                Log.i(TAG, "onResponse:  加载成功");

                cityAdapter = new CityAdapter(CityActivity.this,R.layout.city_row,result.getDatas());

                mHandler.sendEmptyMessage(100);

            }
        });

    }


}
