package com.example.administrator.mylashou.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.mylashou.R;
import com.example.administrator.mylashou.activity.GoodsListActivity;
import com.example.administrator.mylashou.entity.Goods;
import com.example.administrator.mylashou.util.ToolKits;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GoodsAdapter extends BaseAdapter {

    private List<Goods> mList;
    private static final String TAG = "GoodsAdapter";
    
    public GoodsAdapter(List<Goods> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList == null?0:mList.size();
    }

    @Override
    public Goods getItem(int position) {
        return (mList == null || position >= mList.size()?null:mList.get(position-1));
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_list_row,null);

            holder.title = convertView.findViewById(R.id.title);
            holder.tv_content = convertView.findViewById(R.id.tv_content);
            holder.price = convertView.findViewById(R.id.price);
            holder.value = convertView.findViewById(R.id.value);
            holder.distance = convertView.findViewById(R.id.distance);
            holder.photo = convertView.findViewById(R.id.photo);
            holder.appoitment_img = convertView.findViewById(R.id.appoitment_img);

            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Goods goods =mList.get(position);

        holder.title.setText(goods.getSortTitle());
        holder.tv_content.setText(goods.getTitle());
       // holder.distance.setText(goods.getBought());
        holder.price.setText(String.valueOf("￥"+goods.getPrice()));
        holder.value.setText(String.valueOf("￥"+goods.getValue()));

        if (GoodsListActivity.lat != null && GoodsListActivity.lon != null) {
            double distance = ToolKits.getDistance(GoodsListActivity.lat, GoodsListActivity.lon, 
                    Double.parseDouble(goods.getShop().getLat()), Double.parseDouble(goods.getShop().getLon()));
            Log.i(TAG, "getView: distance"+distance+"shop"+GoodsListActivity.lat+"  "+ GoodsListActivity.lon+"  "+
                    Double.parseDouble(goods.getShop().getLat())+"  "+ Double.parseDouble(goods.getShop().getLon()));

            if(distance>1000){
                holder.distance.setText((int)distance/1000+"千米");
            }else{
                holder.distance.setText(distance+"米");
            }
        }

        if(goods.isOp()){
            holder.appoitment_img.setVisibility(View.VISIBLE);
        }else {
            holder.appoitment_img.setVisibility(View.GONE);
        }

        Picasso.with(parent.getContext()).load(goods.getImgUrl()).placeholder(R.drawable.default_pic).into(holder.photo);


        return convertView;

    }


    class ViewHolder{

//        TextView item_city_section;
//        TextView item_city;

        TextView title;
        TextView tv_content;
        TextView price;
        TextView value;
        TextView distance;
        //TextView count;
        ImageView photo;
        ImageView appoitment_img;


    }

}
