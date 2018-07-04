package com.example.administrator.mylashou.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.mylashou.R;
import com.example.administrator.mylashou.activity.GoodsListActivity;
import com.example.administrator.mylashou.adapter.NearbyAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNearby extends Fragment {

    private NearbyAdapter adapter = new NearbyAdapter();
    private ProgressBar location_progress;
    private TextView location_text;
    private GridView nearby_grid_view;
    private LinearLayout location_group;
    private double getLat = 39.993456, getLon = 116.3154950;
    private String desc = "";
    private String[] labs = new String[] { "美食", "电影", "酒店", "KTV", "火锅",
            "美容美发", "休闲娱乐", "生活服务", "全部" };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nearby, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        nearby_grid_view = getActivity().findViewById(R.id.nearby_grid_view);
        location_text = getActivity().findViewById(R.id.location_text);
        location_group = getActivity().findViewById(R.id.location_group);
        location_progress = getActivity().findViewById(R.id.location_progress);

        nearby_grid_view.setAdapter(adapter);

        nearby_grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), GoodsListActivity.class);
                intent.putExtra("lat", getLat);
                intent.putExtra("lon", getLon);
                intent.putExtra("desc",desc);
                intent.putExtra("labs",labs[position]);
                intent.putExtra("category",adapter.getItem(position));
                startActivity(intent);
               // Toast.makeText(getActivity(),"选中了第"+position+"项",Toast.LENGTH_SHORT).show();
            }
        });

        // 首次自动加载数据
        new Handler(new Handler.Callback() {

            @Override
            public boolean handleMessage(Message msg) {
                location_text.setText("太原");
                location_progress.setVisibility(View.GONE);
                return true;
            }
        }).sendEmptyMessageDelayed(0, 1500);

    }


}
