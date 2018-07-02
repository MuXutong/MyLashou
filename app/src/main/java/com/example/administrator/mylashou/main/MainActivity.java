package com.example.administrator.mylashou.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.administrator.mylashou.R;
import com.example.administrator.mylashou.fragment.FragmentIndex;
import com.example.administrator.mylashou.fragment.FragmentMore;
import com.example.administrator.mylashou.fragment.FragmentMy;
import com.example.administrator.mylashou.fragment.FragmentNearby;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private String mTitleArray[] = {"团购", "附近", "我的", "更多"};
    private int mImageViewArray[] = {R.drawable.ic_tab_artists_selected, R.drawable.ic_tab_albums_selected,
            R.drawable.ic_tab_songs_selected, R.drawable.ic_tab_playlists_selected};

    private BottomNavigationBar mBottomNavigationBar;

    private List<Fragment> allFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setBottomNavigationBar();

        setDefaultFragment();

    }

    private void setDefaultFragment() {

        allFragments=new ArrayList<>();
        allFragments.add(new FragmentIndex());
        allFragments.add(new FragmentNearby());
        allFragments.add(new FragmentMy());
        allFragments.add(new FragmentMore());

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frameLayout,allFragments.get(0));
        transaction.commit();
    }
    private void setBottomNavigationBar() {

        mBottomNavigationBar = findViewById(R.id.bottom_navigation_bar);

        mBottomNavigationBar//设置模式
                .setMode(BottomNavigationBar.MODE_FIXED)
                //设置背景颜色
                .setBarBackgroundColor(R.color.white)
                //设置每个item的颜色
                .setInActiveColor(R.color.gray)
                .setActiveColor(R.color.orange)
                //
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                //
                .addItem(new BottomNavigationItem(mImageViewArray[0], mTitleArray[0]))
                .addItem(new BottomNavigationItem(mImageViewArray[1], mTitleArray[1]))
                .addItem(new BottomNavigationItem(mImageViewArray[2], mTitleArray[2]))
                .addItem(new BottomNavigationItem(mImageViewArray[3], mTitleArray[3]))
                .initialise();

        mBottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                //未选中->选中
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frameLayout,allFragments.get(position));
                transaction.commit();

            }
            @Override
            public void onTabUnselected(int position) {
                //选中->未选中
            }
            @Override
            public void onTabReselected(int position) {
                //选中->选中
            }
        });

    }

}
