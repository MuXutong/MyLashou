package com.example.administrator.mylashou.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.mylashou.R;
import com.example.administrator.mylashou.entity.Order;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderAdapter extends BaseAdapter {

    private List<Order> mList;

    public OrderAdapter(List<Order> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList == null?0:mList.size();
    }

    @Override
    public Order getItem(int position) {
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

            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_row,null);

            holder.pay_title = convertView.findViewById(R.id.pay_title);
            holder.pay_content = convertView.findViewById(R.id.pay_content);
            holder.order_oneprice = convertView.findViewById(R.id.order_oneprice);
            holder.order_count = convertView.findViewById(R.id.order_count);
            holder.order_allprice = convertView.findViewById(R.id.order_allprice);

            holder.order_photo = convertView.findViewById(R.id.order_photo);

            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Order order =mList.get(position);

        holder.pay_title.setText(order.getGoods().getSortTitle());
        holder.pay_content.setText(order.getGoods().getTitle());
        holder.order_oneprice.setText(String.valueOf("￥"+order.getGoods().getPrice()));
        holder.order_count.setText("x"+order.getOrdersProdouctCount());
        holder.order_allprice.setText("￥"+order.getOrdersAllPrice());

        Picasso.with(parent.getContext()).load(order.getGoods().getImgUrl()).placeholder(R.drawable.default_pic).into(holder.order_photo);

        return convertView;

    }


    class ViewHolder{

        TextView pay_title;
        TextView pay_content;
        TextView order_oneprice;
        TextView order_count;
        TextView order_allprice;
        ImageView order_photo;

    }

}
