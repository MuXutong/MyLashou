package com.example.administrator.mylashou.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.mylashou.R;

public class NearbyAdaper extends BaseAdapter{

    private String labels[] = new String[]{"美食","电影","酒店","KTY","火锅","美容美发","休闲娱乐","生活服务","全部"};
    private int colors[] = new int[]{
            R.color.item_0, R.color.item_1,
            R.color.item_2, R.color.item_3,
            R.color.item_4, R.color.item_5,
            R.color.item_6, R.color.item_7,
            R.color.item_8};
    private int icons[] = new int[]{
            R.drawable.food_select,
            R.drawable.movie_select,
            R.drawable.hotel_select,
            R.drawable.ktv_select,
            R.drawable.hot_pot_select,
            R.drawable.hair_select,
            R.drawable.fun_select,
            R.drawable.life_select,
            R.drawable.all_select
    };

    @Override
    public int getCount() {
        return labels.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.nearby_grid_view_item,null);
            holder.textView = convertView.findViewById(R.id.textView);
            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(labels[position]);
        holder.textView.setTextColor(parent.getResources().getColor(colors[position]));

        Drawable drawable = parent.getContext().getResources().getDrawable(icons[position]);
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        holder.textView.setCompoundDrawables(null,drawable,null,null);
        return convertView;
    }

    class ViewHolder{
        TextView textView;
    }
}
