package com.example.administrator.mylashou.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mylashou.R;
import com.example.administrator.mylashou.entity.ResponseObject;
import com.example.administrator.mylashou.entity.User;
import com.example.administrator.mylashou.util.CONST;
import com.example.administrator.mylashou.util.HttpUtil;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity implements TextWatcher{

    private Handler mHandler;
    private TextView register_back;
    private EditText register_name;
    private EditText register_Pwd;
    private EditText register_surePwd;
    private Button register_btn;
    private String mName,mPwd,mSurePwd;
    private User user;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_back = findViewById(R.id.register_back);

        register_name = findViewById(R.id.register_name);
        register_Pwd = findViewById(R.id.register_Pwd);
        register_surePwd = findViewById(R.id.register_surePwd);
        register_btn = findViewById(R.id.register_btn);


        register_name.addTextChangedListener(this);
        register_Pwd.addTextChangedListener(this);
        register_surePwd.addTextChangedListener(this);

        register_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mPwd.equals(mSurePwd)){//注册
                    Map params = new HashMap<String,String>();

                    params.put("name", mName);
                    params.put("password",mPwd);
                    params.put("flag","register");

                    HttpUtil.sendOkHttpRequest(CONST.REGISTER_USER, null, params, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            mHandler.sendEmptyMessage(11);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String responseData = response.body().string();

                            ResponseObject<User> object = new GsonBuilder().create().fromJson(responseData, new TypeToken<ResponseObject<User>>() {}.getType());
                            int state = object.getState();
                            if (state == 1) {
                                user = object.getDatas();
                                mHandler.sendEmptyMessage(12);
                                finish();//注册成功，返回
                            } else {
                                mHandler.sendEmptyMessage(13);
                            }
                        }

                    });

                } else{
                    Toast.makeText(RegisterActivity.this,"密码不一致，请重新输入密码！！", Toast.LENGTH_SHORT).show();
                }
            }
        });


        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                if (msg.what == 11) {
                    Toast.makeText(RegisterActivity.this, "连接服务器失败，请重试", Toast.LENGTH_SHORT).show();
                }

                if (msg.what == 12) {
                    Toast.makeText(RegisterActivity.this, "注册成功，请登录！！", Toast.LENGTH_SHORT).show();
                }

                if (msg.what == 13) {
                    Toast.makeText(RegisterActivity.this, "该用户已存在", Toast.LENGTH_SHORT).show();
                }
            }
        };

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mName=register_name.getText().toString().trim();
        mPwd=register_Pwd.getText().toString().trim();
        mSurePwd=register_surePwd.getText().toString().trim();
        if(TextUtils.isEmpty(mName)||TextUtils.isEmpty(mPwd)||TextUtils.isEmpty(mSurePwd)){
            register_btn.setEnabled(false);
        }else{
            register_btn.setEnabled(true);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
