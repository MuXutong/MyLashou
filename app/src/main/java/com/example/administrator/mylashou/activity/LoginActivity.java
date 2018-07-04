package com.example.administrator.mylashou.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mylashou.R;
import com.example.administrator.mylashou.entity.ResponseObject;
import com.example.administrator.mylashou.entity.User;
import com.example.administrator.mylashou.util.CONST;
import com.example.administrator.mylashou.util.HttpUtil;
import com.example.administrator.mylashou.util.ToolKits;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements TextWatcher {

    //26ae6fafc72db
    private String msag;
    private Handler mHandler;
    private RadioGroup rg_login;
    private RadioButton rb1,rb2;
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

    private TextView login_back;
    String mName, mPwd, mTel, mCode;
    private boolean mAccount = true;
    public static final String LOGIN_USER = "user";


    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();


        rg_login.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){
                    case R.id.rb1:
                        fly_view.startAnimation(move_to_left);
                        layout_for_count.setVisibility(View.VISIBLE);
                        layout_for_fast.setVisibility(View.GONE);
                        if (!TextUtils.isEmpty(mName) && !TextUtils.isEmpty(mPwd) ) {
                            login_btn.setEnabled(true);

                        }  else {
                            login_btn.setEnabled(false);
                        }
                        break;
                    case R.id.rb2:
                        fly_view.startAnimation(move_to_right);
                        layout_for_fast.setVisibility(View.VISIBLE);
                        layout_for_count.setVisibility(View.GONE);
                        if (!TextUtils.isEmpty(mTel) && !TextUtils.isEmpty(mCode)) {
                            login_btn.setEnabled(true);

                        } else {
                            login_btn.setEnabled(false);
                        }
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

        login_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAccount) {// 以账号的形式登录
                    Login(mName, mPwd, "accout_login");
                } else {// 以手机号码的形式登录
                    Login(mTel, null, "phone_login");
                }
            }
        });

        weibo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Platform platform = ShareSDK.getPlatform(SinaWeibo.NAME);// 得到平台
                platform.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {}

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {}

                    @Override
                    public void onCancel(Platform platform, int i) {}
                });
                if (platform.isAuthValid()) {// 如果已经授权过，则直接登录
                    String nickName = platform.getDb().getUserName();
                    String Uid = platform.getDb().getUserId();
                    Login(nickName, Uid, "social_login");
                    return;
                } else {
                    platform.showUser(null);// 弹出授权界面
                }
            }
        });
        wechat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        qq_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                if (msg.what == 15) {
                    Toast.makeText(LoginActivity.this, "连接服务器失败，请重试", Toast.LENGTH_SHORT).show();

                }

                if (msg.what == 16) {
                    Toast.makeText(LoginActivity.this, msag, Toast.LENGTH_SHORT).show();

                }

                if (msg.what == 17) {
                }
            }
        };

    }

    private void initView() {


        weibo_btn = findViewById(R.id.weibo_btn);
        wechat_btn = findViewById(R.id.wechat_btn);
        qq_btn = findViewById(R.id.qq_btn);

        rb2 = findViewById(R.id.rb2);
        rb1 = findViewById(R.id.rb1);
        login_back= findViewById(R.id.login_back);
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

    }

    private void Login(String name, String pwd, String flag) {

        Map params = new HashMap<String,String>();

        params.put("name", name);
        params.put("password",pwd);
        params.put("flag",flag);


        HttpUtil.sendOkHttpRequest(CONST.LOGIN_USER, null, params, new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendEmptyMessage(15);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String responseData = response.body().string();

                ResponseObject<User> object = new GsonBuilder().create().fromJson(responseData, new TypeToken<ResponseObject<User>>() {}.getType());

                int state = object.getState();
                msag = object.getMsg();

                if (state == 1) {
                    // 使用Gson将json数据转换成String类型，并通过SharePreferences将登录数据存储起来
                    Gson gson = new GsonBuilder().create();
                    ToolKits.putShareData(LoginActivity.this, LOGIN_USER, gson.toJson(object.getDatas()));
                    finish();// 登录成功，返回
                }
                mHandler.sendEmptyMessage(16);
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

        if (!TextUtils.isEmpty(mName) && !TextUtils.isEmpty(mPwd) && rb1.isChecked()) {
            login_btn.setEnabled(true);

        } else if (!TextUtils.isEmpty(mTel) && !TextUtils.isEmpty(mCode)&& rb2.isChecked()) {
            login_btn.setEnabled(true);

        } else {
            login_btn.setEnabled(false);
        }

    }

    @Override
    public void afterTextChanged(Editable s) {}


}
