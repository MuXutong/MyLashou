package com.example.administrator.mylashou.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mylashou.R;
import com.example.administrator.mylashou.adapter.GoodsAdapter;
import com.example.administrator.mylashou.entity.Goods;
import com.example.administrator.mylashou.entity.ResponseObject;
import com.example.administrator.mylashou.util.CONST;
import com.example.administrator.mylashou.util.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class GoodsListActivity extends AppCompatActivity {

    private static final String TAG = "GoodsListActivity";
    private Handler mHandler;
    private TextView layout_location;
    private TextView goods_labs;
//    private ImageView goods_list_map;
    private ProgressBar layout_progress;
    private PullToRefreshListView goods_list_view;

    public static Double lat, lon;// 经度、纬度
    private String desc = "";// 详细信息
    private String labs = "";// 分类
    private String category ="";// 分类id
    private int page = 1, size = 20, pageCount = 1;
    private double mRadius = 10000;
    private GoodsAdapter mAdapter;
    private List<Goods> mList;

    private ImageView home_map;
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);


        home_map = findViewById(R.id.home_map);
        layout_location = findViewById(R.id.location_text);
        layout_progress = findViewById(R.id.location_progress);
        goods_list_view = findViewById(R.id.goods_list_view);
        goods_labs  = findViewById(R.id.goods_labs);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {// 通过意图得到之前定位的信息
            lat = bundle.getDouble("lat");
            lon = bundle.getDouble("lon");
            desc = bundle.getString("desc");
            labs = bundle.getString("labs");
            category=bundle.getString("category");
        }

        goods_labs.setText(labs);

        // 首次自动加载数据
        new Handler(new Handler.Callback() {

            @Override
            public boolean handleMessage(Message msg) {
                layout_location.setText("太原");
                layout_progress.setVisibility(View.GONE);
                return true;
            }
        }).sendEmptyMessageDelayed(0, 1500);



        goods_labs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        home_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GoodsListActivity.this,NearbyMapActivity.class));
            }
        });

        goods_list_view.setMode(PullToRefreshBase.Mode.BOTH);// 同时支持下拉刷新，上拉更多
        goods_list_view.setScrollingWhileRefreshingEnabled(true);// 不允许加载数据的时候滚动刷新
        goods_list_view.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {// 下拉刷新和上拉更多会回调该方法

            @Override
            public void onRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                // TODO Auto-generated method stub
                loadData(refreshView.getScrollY() < 0);// refreshView.getScrollY()<0为true表示下拉刷新，为false表示上拉更多
            }
        });

        loadData(true);

        goods_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(GoodsListActivity.this, GoodsDetailActivity.class);
                intent.putExtra("goods", mAdapter.getItem(position));//讲Goods对象通过意图传递
                startActivity(intent);
            }
        });
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 31) {
                    goods_list_view.setAdapter(mAdapter);
                }

                if (msg.what == 32) {
                    mAdapter.notifyDataSetChanged();
                }

                if (msg.what == 33) {
                    goods_list_view.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                }
                if (msg.what == 34) {
                    Toast.makeText(GoodsListActivity.this,"网络走丢了····",Toast.LENGTH_SHORT).show();
                }

            }
        };
    }

    private void loadData(final boolean direction){

        if(direction){
            page = 1;
        }else {
            page ++;
        }
        Map params = new HashMap<String,String>();
        params.put("page",String.valueOf(page));
        params.put("size",String.valueOf(size));
        params.put("lat", String.valueOf(lat));
        params.put("lon", String.valueOf(lon));
        params.put("radius", String.valueOf(mRadius));
        params.put("category", String.valueOf(category));

        Log.i(TAG, "loadData: params"+params);
        goods_list_view.onRefreshComplete();

        HttpUtil.sendOkHttpRequest(CONST.GOODS_NEARBY, null,params,new Callback(){

            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendEmptyMessage(34);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                Log.i(TAG, "onResponse: "+responseData);
                Gson gson = new GsonBuilder().create();
                ResponseObject<List<Goods>> object = gson.fromJson(responseData, new TypeToken<ResponseObject<List<Goods>>>(){}.getType());



                page = object.getPage();
                size = object.getSize();
                pageCount = object.getCount();

                if(direction){ //true表示下拉刷新
                    mList = object.getDatas();
                    if(mList!=null){
                        mAdapter = new GoodsAdapter(mList);
                        mHandler.sendEmptyMessage(31);
                    }
                    //  goods_list_view.setAdapter(goodsAdapter);
                }else {//false表示上拉加载更多
                    mList.addAll(object.getDatas());
                    mHandler.sendEmptyMessage(32);

                }
                if(pageCount == page){//下部没有更多数据
                    mHandler.sendEmptyMessage(33);
                    // goods_list_view.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                }

            }

        });
        goods_list_view.onRefreshComplete();
    }

}
