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
import android.widget.ListView;
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

public class FavoriteActivity extends AppCompatActivity {

    private static final String TAG = "FavoriteActivity";
    private PullToRefreshListView goods_list_view;
    private GoodsAdapter mAdapter;
    private List<Goods> mList;
    private Handler mHandler;

    private String user_id;
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            user_id =  (String) bundle.get("user_id");
        }

        goods_list_view = findViewById(R.id.goods_list_view);

        goods_list_view.setMode(PullToRefreshBase.Mode.BOTH);// 同时支持下拉刷新，上拉更多
        goods_list_view.setScrollingWhileRefreshingEnabled(true);// 不允许加载数据的时候滚动刷新
        goods_list_view.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {// 下拉刷新和上拉更多会回调该方法

            @Override
            public void onRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                // TODO Auto-generated method stub
                loadFavorite(refreshView.getScrollY() < 0);// refreshView.getScrollY()<0为true表示下拉刷新，为false表示上拉更多
            }
        });

        loadFavorite(true);

        goods_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(FavoriteActivity.this, GoodsDetailActivity.class);
                intent.putExtra("goods", mAdapter.getItem(position));//讲Goods对象通过意图传递
                startActivity(intent);
            }
        });

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 41) {
                    goods_list_view.setAdapter(mAdapter);
                }

                if (msg.what == 44) {
                    Toast.makeText(FavoriteActivity.this,"网络走丢了····",Toast.LENGTH_SHORT).show();
                }

            }
        };

    }

    private void loadFavorite(final boolean direction){

        Map params = new HashMap<String,String>();


        params.put("user_id",user_id);

        goods_list_view.onRefreshComplete();

        HttpUtil.sendOkHttpRequest(CONST.SHOW_FAVORITE, null,params,new Callback(){

            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendEmptyMessage(44);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                Gson gson = new GsonBuilder().create();
                ResponseObject<List<Goods>> object = gson.fromJson(responseData, new TypeToken<ResponseObject<List<Goods>>>(){}.getType());
                
                mList = object.getDatas();

                Log.i(TAG, "onResponse: "+mList);
                mAdapter = new GoodsAdapter(mList);
                mHandler.sendEmptyMessage(41);
            }

        });
        goods_list_view.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        goods_list_view.onRefreshComplete();
    }
}
