package com.example.administrator.mylashou.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mylashou.R;
import com.example.administrator.mylashou.util.ToolKits;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView acount_back;
    private TextView change_pwd;
    private TextView set_pay_pwd;
    private TextView bind_phone;
    private TextView manage_address;
    private Button exit_for_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        acount_back = findViewById(R.id.acount_back);
        change_pwd = findViewById(R.id.change_pwd);
        set_pay_pwd = findViewById(R.id.set_pay_pwd);
        bind_phone = findViewById(R.id.bind_phone);
        manage_address = findViewById(R.id.manage_address);
        exit_for_login = findViewById(R.id.exit_for_login);

        acount_back.setOnClickListener(this);
        change_pwd.setOnClickListener(this);
        set_pay_pwd.setOnClickListener(this);
        bind_phone.setOnClickListener(this);
        manage_address.setOnClickListener(this);
        exit_for_login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.acount_back:// 返回
                finish();
                break;
            case R.id.change_pwd:// 修改登录密码
                Toast.makeText(this, "修改登录密码", Toast.LENGTH_SHORT).show();
                break;
            case R.id.set_pay_pwd:// 设置支付密码
                Toast.makeText(this, "设置支付密码", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bind_phone:// 已绑定手机号
                Toast.makeText(this, "已绑定手机号", Toast.LENGTH_SHORT).show();
                break;
            case R.id.manage_address:// 管理收获地址
                Toast.makeText(this, "管理收获地址", Toast.LENGTH_SHORT).show();
                break;
            case R.id.exit_for_login:// 退出登录
                ToolKits.cleanShareData(this, LoginActivity.LOGIN_USER);
                finish();
                break;
        }
    }
}
