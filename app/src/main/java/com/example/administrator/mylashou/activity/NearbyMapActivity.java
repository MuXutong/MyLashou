package com.example.administrator.mylashou.activity;

import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.example.administrator.mylashou.R;
import com.example.administrator.mylashou.entity.Goods;
import com.example.administrator.mylashou.entity.ResponseObject;
import com.example.administrator.mylashou.entity.Shop;
import com.example.administrator.mylashou.util.CONST;
import com.example.administrator.mylashou.util.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NearbyMapActivity extends AppCompatActivity implements AMapLocationListener,LocationSource, AMap.OnInfoWindowClickListener {

    private static final String TAG = "MainActivity";
    private MapView mMapView = null;
    private AMap aMap;
    private ImageView refreshImg;
    private ImageView backImg;
    private Handler mHandler;

    private double latitude = 40.075483;    //纬度
    private double longitude = 116.3676612; //经度

    private LocationManager locationManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_map);

        refreshImg = findViewById(R.id.refreshImg);
        backImg = findViewById(R.id.backImg);

        refreshImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDatas(String.valueOf(latitude),String.valueOf(longitude),"2000");
            }
        });

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //获取地图控件引用
        mMapView = findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);

        if (aMap == null) {
            aMap = mMapView.getMap();

//            aMap.setLocationSource(this);
//            aMap.setMyLocationEnabled(true);
//            aMap.setOnMapLoadedListener(this);
//            aMap.setOnMarkerClickListener(this);
//            aMap.setInfoWindowAdapter(this);
            aMap.setOnInfoWindowClickListener(this);
        }
        aMap.setMapLanguage(AMap.CHINESE);



        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                if (msg.what == 21) {
                    Toast.makeText(NearbyMapActivity.this,"网络走丢了····",Toast.LENGTH_SHORT).show();
                }

                if (msg.what == 22) {

                    //设置镜头自动到缩放级别
                    aMap.animateCamera(
                            CameraUpdateFactory.newCameraPosition(
                                    new CameraPosition(
                                            new LatLng(latitude,longitude), 14,0,0
                                    )
                            )
                    );

                }
            }
        };

        loadDatas(String.valueOf(latitude),String.valueOf(longitude),"2000");
    }

    private void loadDatas(String lat,String lon,String radius){

        Map params = new HashMap<String,String>();

        params.put("lat",lat);
        params.put("lon",lon);
        params.put("radius",radius);

        HttpUtil.sendOkHttpRequest(CONST.GOODS_NEARBY, null,params,new Callback(){

            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendEmptyMessage(21);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
               // Log.i(TAG, "onResponse: "+responseData);
                Gson gson = new GsonBuilder().create();
                ResponseObject<List<Goods>> object =
                        gson.fromJson(responseData, new TypeToken<ResponseObject<List<Goods>>>(){}.getType());
                Log.i(TAG, "onResponse: "+object.getDatas());

                addMarker(object.getDatas());

                //设置镜头自动到缩放级别
                mHandler.sendEmptyMessage(22);


            }

        });
    }


    public void addMarker(List<Goods>list){
        MarkerOptions markerOptions;
        for(Goods goods:list){
            Shop shop = goods.getShop();
            markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(Double.parseDouble(shop.getLat()),Double.parseDouble(shop.getLon())));
            markerOptions.title(shop.getName()).snippet("￥"+goods.getPrice());
            markerOptions.perspective(true);

            if(goods.getCategoryId().equals("3")){
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_landmark_chi));
            }else if(goods.getCategoryId().equals("5")){
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_landmark_movie));
            }else if(goods.getCategoryId().equals("8")){
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_landmark_hotel));
            }else if(goods.getCategoryId().equals("6")){
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_landmark_life));
            }else if(goods.getCategoryId().equals("4")){
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_landmark_wan));
            }else {
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_landmark_default));
            }

            markerOptions.draggable(false);

            aMap.addMarker(markerOptions).setObject(goods);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }







    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

        if(aMapLocation!=null){

            longitude = aMapLocation.getLongitude();
            latitude = aMapLocation.getLatitude();
        }

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

    }

    @Override
    public void deactivate() {

    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        //切换到商品界面
    }
}
