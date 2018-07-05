package com.example.administrator.mylashou.activity;

import android.annotation.SuppressLint;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mylashou.R;
import com.example.administrator.mylashou.entity.Order;
import com.example.administrator.mylashou.entity.ResponseObject;
import com.example.administrator.mylashou.util.CONST;
import com.example.administrator.mylashou.util.HELP;
import com.example.administrator.mylashou.util.HttpUtil;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class OrderDetailActivity extends AppCompatActivity {

    private static final String TAG = "OrderDetailActivity";

    private TextView od_title;
    private TextView od_one_price;
    private TextView od_count;
    private TextView od_all_price;
    private TextView od_time;
    private TextView od_back;
    private ImageView od_photo;
    private Button od_pay_btn;

    private Order order;
    private Handler mHandler;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        od_pay_btn = findViewById(R.id.od_pay_btn);
        od_photo = findViewById(R.id.od_photo);
        od_back = findViewById(R.id.od_back);
        od_time = findViewById(R.id.od_time);
        od_all_price = findViewById(R.id.od_all_price);
        od_count = findViewById(R.id.od_count);
        od_one_price = findViewById(R.id.od_one_price);
        od_title = findViewById(R.id.od_title);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            order =  (Order) bundle.get("order");
            Log.i(TAG, "onCreate: order"+order);
        }

        Picasso.with(OrderDetailActivity.this).load(order.getGoods().getImgUrl()).placeholder(R.drawable.default_pic).into(od_photo);

        od_time.setText(order.getOrdersTime());
        od_title.setText(order.getGoods().getSortTitle());
        od_one_price.setText(String.valueOf("￥"+order.getGoods().getPrice()));
        od_count.setText("x"+order.getOrdersProdouctCount());
        od_all_price.setText("￥"+order.getOrdersAllPrice());

        if(order.getOrdersPaystate().equals("1")){
            od_pay_btn.setVisibility(View.GONE);
        }
        od_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        od_pay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new AlertDialog.Builder(OrderDetailActivity.this)
                        .setTitle("确认付款")  //  ;
                        .setMessage("\n￥"+String.valueOf(order.getOrdersAllPrice())+ HELP.FUKUAN)
                        .setIcon(R.drawable.logo)  //  图标
                        .setPositiveButton("立即付款", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                payOrder();
                                finish();
                            }
                        })
                        .setNegativeButton("稍后付款", null)
                        .create()
                        .show();
            }
        });

        od_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OrderDetailActivity.this, GoodsDetailActivity.class);
                intent.putExtra("goods",order.getGoods());
                startActivity(intent);
            }
        });

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                if (msg.what == 81) {
                    Toast.makeText(OrderDetailActivity.this, "连接服务器失败，请重试", Toast.LENGTH_SHORT).show();
                }

                if (msg.what == 82) {
                    Toast.makeText(OrderDetailActivity.this, "付款成功", Toast.LENGTH_SHORT).show();
                }

                if (msg.what == 83) {
                    Toast.makeText(OrderDetailActivity.this, "订单修改失败", Toast.LENGTH_SHORT).show();
                }
            }
        };

    }

    private void payOrder() {
        Map params = new HashMap<String,String>();

        params.put("orders_id", order.getOrdersId());


        HttpUtil.sendOkHttpRequest(CONST.UPDATE_ORDER, null, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                mHandler.sendEmptyMessage(81);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String responseData = response.body().string();
                Log.i(TAG, "onResponse: "+responseData);
                ResponseObject<Order> object = new GsonBuilder().create().fromJson(responseData, new TypeToken<ResponseObject<Order>>() {}.getType());
                int restate = object.getState();
                if (restate == 1) {
                    mHandler.sendEmptyMessage(82);
                } else {
                    mHandler.sendEmptyMessage(83);
                }
            }
        });

    }
}
