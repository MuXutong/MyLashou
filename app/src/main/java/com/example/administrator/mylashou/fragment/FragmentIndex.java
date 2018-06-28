package com.example.administrator.mylashou.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.administrator.mylashou.R;
import com.example.administrator.mylashou.activity.CityActivity;


public class FragmentIndex extends Fragment {

    private Button home_city;
    private ImageButton home_map;
    private ImageButton home_search;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_index, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        home_city = getActivity().findViewById(R.id.home_city);
        home_map = getActivity().findViewById(R.id.home_map);
        home_search = getActivity().findViewById(R.id.home_search);

        home_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CityActivity.class));
            }
        });
    }
}