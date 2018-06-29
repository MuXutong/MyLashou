package com.example.administrator.mylashou.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
/**
 * 欢迎页适配器
 */
public class GuideAdapter extends PagerAdapter {

    private List<View> mList;

    public GuideAdapter(List<View>list){
        mList=list;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView(mList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        container.addView(mList.get(position));
        return mList.get(position);

    }
}
