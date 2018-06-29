package com.example.administrator.mylashou.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.administrator.mylashou.R;
import com.example.administrator.mylashou.adapter.CityAdapter;
import com.example.administrator.mylashou.entity.City;
import com.example.administrator.mylashou.entity.ResponseObject;
import com.example.administrator.mylashou.util.CONST;
import com.example.administrator.mylashou.util.HttpUtil;
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
    private EditText city_keyword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        city_back = findViewById(R.id.city_back);
        city_refresh = findViewById(R.id.city_refresh);
        city_keyword = findViewById(R.id.city_keyword);
        city_list_view = findViewById(R.id.city_list_view);

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
    }

    public void LoadCity(){
        HttpUtil.sendOkHttpRequest(CONST.CITY_LIST, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String responseData = response.body().string();

                Gson gson = new GsonBuilder().create();
                ResponseObject<List<City>> result =
                        gson.fromJson(responseData, new TypeToken<ResponseObject<List<City>>>(){}.getType());

                Log.i(TAG, "onResponse: "+result.getDatas());

                cityAdapter = new CityAdapter(CityActivity.this,R.layout.city_row,result.getDatas());

                city_list_view.setAdapter(cityAdapter);
            }
        });

    }
}
