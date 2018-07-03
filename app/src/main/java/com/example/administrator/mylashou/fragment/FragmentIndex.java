package com.example.administrator.mylashou.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.mylashou.R;
import com.example.administrator.mylashou.activity.CityActivity;
import com.example.administrator.mylashou.activity.NearbyMapActivity;
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

public class FragmentIndex extends Fragment {

    private static final String TAG = "MainActivity";
    private Handler mHandler;
    private Button home_city;
    private ImageButton home_map;

    private PullToRefreshListView goods_list_view;

    private int page = 1 ,size = 20, count = 0 ;

    private GoodsAdapter goodsAdapter;
    private List<Goods> mList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        return view;
    }

    //true表示下拉刷新，false表示上拉加载更多
    private void loadDatas(final boolean direction){

        if(direction){
            page = 1;
        }else {
            page ++;
        }
        Map params = new HashMap<String,String>();
        params.put("page",String.valueOf(page));
        params.put("size",String.valueOf(size));

        goods_list_view.onRefreshComplete();

        HttpUtil.sendOkHttpRequest(CONST.GOODS_LIST, null,params,new Callback(){

            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendEmptyMessage(20);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                Log.i(TAG, "onResponse: "+responseData);
                Gson gson = new GsonBuilder().create();
                ResponseObject<List<Goods>> object =
                        gson.fromJson(responseData, new TypeToken<ResponseObject<List<Goods>>>(){}.getType());

                Log.i(TAG, "onResponse: "+object.getDatas());

                page = object.getPage();
                size = object.getSize();
                count = object.getCount();

                if(direction){ //true表示下拉刷新
                    mList = object.getDatas();
                    if(mList!=null){
                        goodsAdapter = new GoodsAdapter(mList);
                        mHandler.sendEmptyMessage(200);
                    }
                    //  goods_list_view.setAdapter(goodsAdapter);
                }else {//false表示上拉加载更多
                    mList.addAll(object.getDatas());
                    mHandler.sendEmptyMessage(300);

                }
                if(count == page){//下部没有更多数据
                    mHandler.sendEmptyMessage(400);
                    // goods_list_view.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                }

            }

        });
        goods_list_view.onRefreshComplete();
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        home_city = getActivity().findViewById(R.id.home_city);
        home_map = getActivity().findViewById(R.id.home_map);
        ImageButton home_search = getActivity().findViewById(R.id.home_search);

        home_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NearbyMapActivity.class));

            }
        });

        home_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CityActivity.class));
            }
        });

        goods_list_view = getActivity().findViewById(R.id.goods_list_view);

        goods_list_view.setMode(PullToRefreshBase.Mode.BOTH);
        goods_list_view.setScrollingWhileRefreshingEnabled(true);
        goods_list_view.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                //进行数据加载

               loadDatas(refreshView.getScrollY()<0);
            }
        });

        loadDatas(true);


        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 200) {
                    goods_list_view.setAdapter(goodsAdapter);
                }

                if (msg.what == 300) {
                    goodsAdapter.notifyDataSetChanged();
                }

                if (msg.what == 400) {
                    goods_list_view.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                }
                if (msg.what == 20) {
                    Toast.makeText(getActivity(),"网络走丢了····",Toast.LENGTH_SHORT).show();
                }

        }
        };
    }




}