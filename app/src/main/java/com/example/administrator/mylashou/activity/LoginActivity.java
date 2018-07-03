package com.example.administrator.mylashou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.example.administrator.mylashou.R;

public class LoginActivity extends AppCompatActivity {

    private RadioGroup rg_login;
    private View fly_view;
    private Animation move_to_left,move_to_right;
    private LinearLayout layout_for_fast,layout_for_count;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        rg_login = findViewById(R.id.rg_login);
        fly_view = findViewById(R.id.fly_view);
        register = findViewById(R.id.register);

        layout_for_fast=findViewById(R.id.layout_for_fast);
        layout_for_count=findViewById(R.id.layout_for_count);

        move_to_left = AnimationUtils.loadAnimation(this,R.anim.move_to_left);
        move_to_right = AnimationUtils.loadAnimation(this,R.anim.move_to_right);


        rg_login.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){
                    case R.id.rb1:
                        fly_view.startAnimation(move_to_left);
                        layout_for_count.setVisibility(View.VISIBLE);
                        layout_for_fast.setVisibility(View.GONE);

                        break;
                    case R.id.rb2:
                        fly_view.startAnimation(move_to_right);
                        layout_for_fast.setVisibility(View.VISIBLE);
                        layout_for_count.setVisibility(View.GONE);
                        break;
                }
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

    }
}
