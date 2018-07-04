package com.example.administrator.mylashou.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mylashou.R;
import com.example.administrator.mylashou.entity.Goods;
import com.example.administrator.mylashou.entity.User;
import com.example.administrator.mylashou.util.CONST;
import com.example.administrator.mylashou.util.HELP;
import com.example.administrator.mylashou.util.HttpUtil;
import com.example.administrator.mylashou.util.ToolKits;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "OrderActivity";
    private Handler mHandler;
    private Goods goods;

    private Button order_next;
    private Button add_count;
    private Button reduce_count;
    private TextView order_title;
    private TextView order_price;
    private TextView product_count;
    private TextView order_Allprice;
    private TextView order_back;
    private int state = 0;
    private int count = 1;
    private double price;
    private double allprice;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initView();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            goods = (Goods) bundle.get("goods");
        }

        price = goods.getPrice();
        order_price.setText("￥"+String.valueOf(price));
        order_title.setText(goods.getSortTitle());
        allprice = count*price;
        order_Allprice.setText("￥"+String.valueOf(allprice));

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                if (msg.what == 51) {
                    Toast.makeText(OrderActivity.this, "连接服务器失败，请重试", Toast.LENGTH_SHORT).show();
                }

                if (msg.what == 52) {
                    Toast.makeText(OrderActivity.this, "订单添加成功", Toast.LENGTH_SHORT).show();
                }

                if (msg.what == 53) {
                    Toast.makeText(OrderActivity.this, "订单添加失败", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private void initView() {
        order_title = findViewById(R.id.order_title);
        order_back = findViewById(R.id.order_back);
        order_next = findViewById(R.id.order_next);
        add_count = findViewById(R.id.add_count);
        reduce_count = findViewById(R.id.reduce_count);
        order_price = findViewById(R.id.order_price);
        product_count = findViewById(R.id.product_count);
        order_Allprice = findViewById(R.id.order_Allprice);

        order_back.setOnClickListener(this);
        order_next.setOnClickListener(this);

        add_count.setOnClickListener(this);
        reduce_count.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_back:
                finish();
                break;

            case R.id.order_next:
                nextOrder();
                break;

            case R.id.add_count:
                count++;
                product_count.setText(String.valueOf(count));
                allprice = count*price;
                order_Allprice.setText("￥"+String.valueOf(allprice));
                break;

            case R.id.reduce_count:
                count--;
                if(count<=1){count=1;}
                product_count.setText(String.valueOf(count));
                allprice = count*price;
                order_Allprice.setText("￥"+String.valueOf(allprice));
                break;

        }
    }

    private void nextOrder() {
        Gson gson=new GsonBuilder().create();
        String userStr= ToolKits.getShareData(OrderActivity.this, LoginActivity.LOGIN_USER,null);
        user=gson.fromJson(userStr, User.class);
        if(user!=null){
            new AlertDialog.Builder(OrderActivity.this)
                    .setTitle("确认付款")  //  ;
                    .setMessage("\n￥"+String.valueOf(allprice)+ HELP.FUKUAN)
                    .setIcon(R.drawable.logo)  //  图标
                    .setPositiveButton("立即付款", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            state=1;
                            insertOrder(state);
                            finish();
                        }
                    })
                    .setNegativeButton("稍后付款", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            state=0;
                            insertOrder(state);
                        }
                    })
                    .create()
                    .show();
        }else{
            startActivity(new Intent(OrderActivity.this,LoginActivity.class));
            Toast.makeText(OrderActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
        }

    }

    private void insertOrder(int state) {

        Map params = new HashMap<String,String>();

        params.put("state", String.valueOf(state));
        params.put("allprice",String.valueOf(allprice));
        params.put("count", String.valueOf(count));
        params.put("user_id", user.getId());
        params.put("prodouct_id",goods.getId());

        HttpUtil.sendOkHttpRequest(CONST.ADD_ORDER, null, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendEmptyMessage(51);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String responseData = response.body().string();
                Log.i(TAG, "onResponse: "+responseData);
//                ResponseObject<Order> object = new GsonBuilder().create().fromJson(responseData, new TypeToken<ResponseObject<Order>>() {}.getType());
//                int restate = object.getState();
//                if (restate == 1) {
//                    mHandler.sendEmptyMessage(52);
//                } else {
//                    mHandler.sendEmptyMessage(53);
//                }
            }
        });
    }
}
