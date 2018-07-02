package com.example.administrator.mylashou.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.mylashou.R;
import com.example.administrator.mylashou.entity.Goods;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GoodsAdapter extends ArrayAdapter<Goods> {

    private int resourceId;
    private List<Goods> mList;

    public GoodsAdapter(@NonNull Context context, int resource, @NonNull List<Goods> objects) {
        super(context, resource, objects);
        resourceId = resource;
        mList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Goods goods = getItem(position);

        View view;
        ViewHolder viewHolder;

        if(convertView==null){

            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();

            viewHolder.title = view.findViewById(R.id.title);
            viewHolder.tv_content = view.findViewById(R.id.tv_content);
            viewHolder.price = view.findViewById(R.id.price);
            viewHolder.value = view.findViewById(R.id.value);
           // viewHolder.count = view.findViewById(R.id.count);
            viewHolder.photo = view.findViewById(R.id.photo);
            viewHolder.appoitment_img = view.findViewById(R.id.appoitment_img);


            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }

        Picasso.with(parent.getContext()).load(goods.getImgUrl()).placeholder(R.drawable.default_pic).into(viewHolder.photo);

        viewHolder.title.setText(goods.getSortTitle());
        viewHolder.tv_content.setText(goods.getTitle());
        //viewHolder.count.setText(goods.getBought());
        viewHolder.price.setText(String.valueOf("￥"+goods.getPrice()));
        viewHolder.value.setText(String.valueOf("￥"+goods.getValue()));

        if(goods.isOp()){
            viewHolder.appoitment_img.setVisibility(View.VISIBLE);
        }else {
            viewHolder.appoitment_img.setVisibility(View.GONE);
        }

        return view;
    }


    class ViewHolder{

//        TextView item_city_section;
//        TextView item_city;

        TextView title;
        TextView tv_content;
        TextView price;
        TextView value;
        //TextView count;
        ImageView photo;
        ImageView appoitment_img;


    }

}
