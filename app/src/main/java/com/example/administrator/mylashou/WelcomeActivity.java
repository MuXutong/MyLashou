package com.example.administrator.mylashou;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.mylashou.util.ToolKits;

public class WelcomeActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final String IS_FIRST="is_first";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                if(!ToolKits.fetchBooble(WelcomeActivity.this,IS_FIRST,false)){
                    startActivity(new Intent(WelcomeActivity.this,WhatsNewActivity.class));
                    ToolKits.putBooble(WelcomeActivity.this,IS_FIRST,true);
                }else {
                    startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                }
                finish();
                return true;
            }
        }).sendEmptyMessageDelayed(0,2000);

    }
}
