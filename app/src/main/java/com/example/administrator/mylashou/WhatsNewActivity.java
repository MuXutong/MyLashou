package com.example.administrator.mylashou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.mylashou.adapter.GuideAdapter;

import java.util.ArrayList;
import java.util.List;

public class WhatsNewActivity extends AppCompatActivity {

    private ViewPager view_pager;
    private Button start_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whats_new);

        view_pager = findViewById(R.id.view_pager);
        start_btn = findViewById(R.id.start_btn);

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WhatsNewActivity.this,MainActivity.class));
                finish();
            }
        });

        initViewPage();
    }

    private void initViewPage() {

        List<View>list = new ArrayList<>();
        ImageView imageView1 = new ImageView(this);
        imageView1.setImageResource(R.drawable.guide_1);
        list.add(imageView1);

        ImageView imageView2 = new ImageView(this);
        imageView2.setImageResource(R.drawable.guide_2);
        list.add(imageView2);

        ImageView imageView3 = new ImageView(this);
        imageView3.setImageResource(R.drawable.guide_3);
        list.add(imageView3);

        GuideAdapter guideAdapter = new GuideAdapter(list);
        view_pager.setAdapter(guideAdapter);

        view_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {

                if(position==2){
                    start_btn.setVisibility(View.VISIBLE);
                }else {
                    start_btn.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

    }


}
