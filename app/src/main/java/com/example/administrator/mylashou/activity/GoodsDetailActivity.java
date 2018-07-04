package com.example.administrator.mylashou.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.mylashou.R;
import com.example.administrator.mylashou.entity.Goods;
import com.example.administrator.mylashou.entity.Shop;
import com.squareup.picasso.Picasso;

public class GoodsDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "GoodsDetailActivity";


    private TextView detail_exit;
    private ImageView goods_image;// 商品图片
    private TextView goods_title;// 商品名称
    private TextView goods_desc;// 商品描述信息
    private TextView support;// 商品是否支持随时退货
    private TextView support_back_money;// 商品是否支持过期退款
    private TextView deadline;// 商品结束时间
    private TextView count;// 商品购买人数
    private TextView shop_name;// 商家的名称
    private TextView shop_tel;// 商家的电话
    private TextView shop_address;// 商家的地址
    private TextView shop_distance;// 商家的距离
    private ImageView shop_call;// 拨打商家电话
    private WebView goods_detail_webview;// 本单详情
    private WebView goods_warn_webview;// 温馨提示
    private TextView comment_score;// 商品评价分数
    private ListView comment_list;// 显示每天评论
    private TextView comment_no;// 暂无评论
    private TextView comment_more;// 查看更多评论
    private TextView goods_detail_price;// 商品价格
    private TextView goods_detail_value;// 商品原价

    private Goods goods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);

        Initview();


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            goods = (Goods) bundle.get("goods");
            Log.i(TAG, "onCreate: "+goods);
        }

        if (goods != null) {// 开始渲染商品详情界面的数据
            // 加载商品的图片
            Picasso.with(this).load(goods.getImgUrl())
                    .placeholder(R.drawable.default_pic).into(goods_image);
            // 加载商品的名称
            goods_title.setText(goods.getSortTitle());
            // 加载商品的描述信息
            goods_desc.setText(goods.getTitle());
            // support;//商品是否支持随时退货
            // support_back_money;//商品是否支持过期退款
            deadline.setText(String.valueOf(goods.getEndTime()));// 商品结束时间
            count.setText(goods.getBought() + "人已买");// 商品购买人数

            Shop shop = goods.getShop();
            shop_name.setText(shop.getName());// 商家的名称
            shop_tel.setText(shop.getTel());// 商家的电话
            shop_address.setText(shop.getAddress());// 商家的地址

            // shop_distance.setText();//商家的距离
            //渲染本单详情
            WebSettings detailSettings=goods_detail_webview.getSettings();
            detailSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            //渲染温馨提示
            WebSettings warnSettings=goods_warn_webview.getSettings();
            warnSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

            String[] data=htmlSub(goods.getDetail());

            goods_detail_webview.loadDataWithBaseURL("", data[0], "text/html", "utf-8", "");
            goods_warn_webview.loadDataWithBaseURL("", data[1], "text/html", "utf-8", "");

            comment_score.setText(String.valueOf(goods.getRibat()));// 商品评价分数
            comment_list.setVisibility(View.GONE);//显示每天评论
            comment_no.setVisibility(View.VISIBLE);// 暂无评论
            comment_more.setVisibility(View.GONE);//查看更多评论
            goods_detail_price.setText("￥"+goods.getPrice());// 商品价格
            goods_detail_value.setText("￥"+goods.getValue());// 商品原价
            goods_detail_value.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);// 在原价上面画横线

        }
    }

    private void Initview() {
        shop_address = findViewById(R.id.shop_address);
        goods_image = findViewById(R.id.goods_image);
        goods_title = findViewById(R.id.goods_title);
        goods_desc = findViewById(R.id.goods_desc);
        support = findViewById(R.id.support);
        support_back_money = findViewById(R.id.support_back_money);
        deadline = findViewById(R.id.deadline);
        count = findViewById(R.id.count);
        shop_name = findViewById(R.id.shop_name);
        shop_tel = findViewById(R.id.shop_tel);
        shop_distance = findViewById(R.id.shop_distance);
        shop_call = findViewById(R.id.shop_call);
        goods_detail_webview = findViewById(R.id.goods_detail_webview);
        goods_warn_webview = findViewById(R.id.goods_warn_webview);
        comment_score = findViewById(R.id.comment_score);
        comment_list = findViewById(R.id.comment_list);
        comment_no = findViewById(R.id.comment_no);
        comment_more = findViewById(R.id.comment_more);
        goods_detail_price = findViewById(R.id.goods_detail_price);
        goods_detail_value = findViewById(R.id.goods_detail_value);
        detail_exit= findViewById(R.id.detail_exit);

        shop_call.setOnClickListener(this);
        detail_exit.setOnClickListener(this);
    }



    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.shop_call://拨打商家电话
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+goods.getShop().getTel()));
                startActivity(intent);
                break;
            case R.id.detail_exit:
                finish();
                break;
        }
    }

    /**
     *从一大段的商品描述中截取出本单详情和温馨提示
     * @param html 一大段的商品描述
     *
     */
    public String[] htmlSub(String html){
        char[] str=html.toCharArray();
        int len=str.length;
        System.out.println(len);
        int n=0;
        String[] data=new String[3];
        int oneindex=0;
        int secindex=1;
        int thrindex=2;
        for(int i=0;i<len;i++){
            if(str[i]=='【'){
                n++;
                if(n==1)oneindex=i;
                if(n==2)secindex=i;
                if(n==3)thrindex=i;
            }
        }
        if(oneindex>0&&secindex>1&&thrindex>2){
            data[0]=html.substring(oneindex, secindex);//本单详情
            data[1]=html.substring(secindex, thrindex);//温馨提示
            data[2]=html.substring(thrindex, len-6);//商家产品介绍

        }
        return data;
    }
}
