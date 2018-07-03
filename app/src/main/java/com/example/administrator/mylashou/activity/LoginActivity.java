package com.example.administrator.mylashou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.example.administrator.mylashou.R;

public class LoginActivity extends AppCompatActivity implements TextWatcher{

    private RadioGroup rg_login;
    private View fly_view;
    private Animation move_to_left,move_to_right;
    private LinearLayout layout_for_fast,layout_for_count;
    private Button register;

    // 用户名、邮箱、手机号
    private EditText count_name;

    // 密码
    private EditText count_password;
    // 手机号

    private EditText fast_tel;
    // 验证码
    private EditText fast_code;
    private Button btn_getCode;
    // 登录
    private Button login_btn;

    // 新浪微博
    private ImageButton weibo_btn;
    // 腾讯微博
    private ImageButton wechat_btn;
    // QQ
    private ImageButton qq_btn;

    private String mName, mPwd, mTel, mCode;
    private boolean mAccount = true;
    public static final String LOGIN_USER = "user";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        rg_login = findViewById(R.id.rg_login);
        fly_view = findViewById(R.id.fly_view);
        register = findViewById(R.id.register);
        count_name = findViewById(R.id.count_name);
        count_password = findViewById(R.id.count_password);
        fast_tel = findViewById(R.id.fast_tel);
        fast_code = findViewById(R.id.fast_code);
        btn_getCode = findViewById(R.id.btn_getCode);
        login_btn = findViewById(R.id.login_btn);
        weibo_btn = findViewById(R.id.weibo_btn);
        wechat_btn = findViewById(R.id.wechat_btn);
        qq_btn = findViewById(R.id.qq_btn);
        layout_for_fast=findViewById(R.id.layout_for_fast);
        layout_for_count=findViewById(R.id.layout_for_count);


        move_to_left = AnimationUtils.loadAnimation(this,R.anim.move_to_left);
        move_to_right = AnimationUtils.loadAnimation(this,R.anim.move_to_right);

        count_name.addTextChangedListener(this);
        count_password.addTextChangedListener(this);
        fast_tel.addTextChangedListener(this);
        fast_code.addTextChangedListener(this);

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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mName = count_name.getText().toString().trim();
        mPwd = count_password.getText().toString().trim();
        mTel = fast_tel.getText().toString().trim();
        mCode = fast_code.getText().toString().trim();
        if ((!TextUtils.isEmpty(mName) && !TextUtils.isEmpty(mPwd))
                || (!TextUtils.isEmpty(mTel) && !TextUtils.isEmpty(mCode))) {
            login_btn.setEnabled(true);

        } else {
            login_btn.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {}


}
