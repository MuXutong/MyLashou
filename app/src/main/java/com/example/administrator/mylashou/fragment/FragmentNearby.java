package com.example.administrator.mylashou.fragment;


import android.os.Bundle;
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
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.example.administrator.mylashou.R;
import com.example.administrator.mylashou.adapter.NearbyAdaper;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNearby extends Fragment implements AMapLocationListener{

    private ProgressBar location_progress;
    private TextView location_text;
    private GridView nearby_grid_view;
    private LinearLayout location_group;
    private double geoLat,geoLng;

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

        nearby_grid_view.setAdapter(new NearbyAdaper());

        nearby_grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),"选中了第"+position+"项",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if(aMapLocation!=null){

            geoLat = aMapLocation.getLatitude();
            geoLng = aMapLocation.getLongitude();
            Bundle locBundle = aMapLocation.getExtras();
            String desc = "";
            if(locBundle != null){
                desc = locBundle.getString("desc");
                location_text.setText(desc);
                location_progress.setVisibility(View.GONE);
            }

            stopLocation();
        }

    }

    private void stopLocation() {


    }
}
