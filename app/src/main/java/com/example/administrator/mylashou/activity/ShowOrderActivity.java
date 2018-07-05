package com.example.administrator.mylashou.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.mylashou.R;
import com.example.administrator.mylashou.adapter.OrderAdapter;
import com.example.administrator.mylashou.entity.Order;
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

public class ShowOrderActivity extends AppCompatActivity {

    private static final String TAG = "ShowOrderActivity";
    private PullToRefreshListView goods_list_view;
    private Handler mHandler;
    private String user_id;
    private List<Order> mList;

    private OrderAdapter mAdapter;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_order);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            user_id =  (String) bundle.get("user_id");
            Log.i(TAG, "onCreate: user_id"+user_id);
        }

        goods_list_view = findViewById(R.id.goods_list_view);

        goods_list_view.setMode(PullToRefreshBase.Mode.BOTH);// 同时支持下拉刷新，上拉更多
        goods_list_view.setScrollingWhileRefreshingEnabled(true);// 不允许加载数据的时候滚动刷新
        goods_list_view.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {// 下拉刷新和上拉更多会回调该方法

            @Override
            public void onRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                // TODO Auto-generated method stub
                loadOrder(refreshView.getScrollY() < 0);// refreshView.getScrollY()<0为true表示下拉刷新，为false表示上拉更多
            }
        });


        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 61) {
                    goods_list_view.setAdapter(mAdapter);
                }

                if (msg.what == 62) {
                    Toast.makeText(ShowOrderActivity.this,"网络走丢了····",Toast.LENGTH_SHORT).show();
                }

            }
        };

        loadOrder(true);

    }

    private void loadOrder(boolean b) {
        Map params = new HashMap<String,String>();

        params.put("user_id",user_id);
        params.put("state","1");

        goods_list_view.onRefreshComplete();

        HttpUtil.sendOkHttpRequest(CONST.SELECT_ORDER, null,params,new Callback(){

            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendEmptyMessage(62);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();

                Log.i(TAG, "onResponse:responseData "+responseData);

                Gson gson = new GsonBuilder().create();

                ResponseObject<List<Order>> object = gson.fromJson(responseData, new TypeToken<ResponseObject<List<Order>>>(){}.getType());

                mList = object.getDatas();

                Log.i(TAG, "onResponse: "+mList);

                mAdapter = new OrderAdapter(mList);
                mHandler.sendEmptyMessage(61);
            }

        });
        goods_list_view.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        goods_list_view.onRefreshComplete();
    }
}
