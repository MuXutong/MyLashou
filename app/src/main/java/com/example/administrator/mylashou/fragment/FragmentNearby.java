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
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mylashou.R;
import com.example.administrator.mylashou.adapter.NearbyAdaper;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNearby extends Fragment {

    private TextView nearby_location;
    private GridView nearby_grid_view;
    private LinearLayout nearby_location_group;


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
        nearby_location = getActivity().findViewById(R.id.nearby_location);
        nearby_location_group = getActivity().findViewById(R.id.nearby_location_group);

        nearby_grid_view.setAdapter(new NearbyAdaper());

        nearby_grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),"选中了第"+position+"项",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
