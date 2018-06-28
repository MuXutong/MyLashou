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

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class CityActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button city_back;
    private ImageButton city_refresh;
    private ListView city_list_view;
    private EditText city_keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        city_back = findViewById(R.id.city_back);
        city_refresh = findViewById(R.id.city_refresh);
        city_keyword = findViewById(R.id.city_keyword);
        city_list_view = findViewById(R.id.city_list_view);

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Request request = new Request.Builder()
                        .url("http://10.0.2.2:8080/ls_server/CityServlet")
                        .build();
                    Response response= new OkHttpClient().newCall(request).execute();

                    String responseDate =response.body().string();

                    Log.i(TAG, "run: "+responseDate);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
