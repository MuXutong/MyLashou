package com.example.administrator.mylashou.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.administrator.mylashou.R;
import com.example.administrator.mylashou.entity.City;

import java.util.List;

public class CityAdapter extends ArrayAdapter<City> implements SectionIndexer{

    private int resourceId;
    private List<City> mList;

    public CityAdapter(@NonNull Context context, int resource, @NonNull List<City> objects) {
        super(context, resource, objects);
        resourceId = resource;
        mList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        City city = getItem(position);

        View view;
        ViewHolder viewHolder;

        if(convertView==null){

            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.item_city = view.findViewById(R.id.item_city);
            viewHolder.item_city_section = view.findViewById(R.id.item_city_section);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }


        //viewHolder.item_city_section.setText(city.getSortKey());
        viewHolder.item_city.setText(city.getName());

        int section = getSectionForPosition(position);
        if(getPositionForSection(section)==position){
            viewHolder.item_city_section.setVisibility(View.VISIBLE);
            viewHolder.item_city_section.setText(city.getSortKey());

        }else {
            viewHolder.item_city_section.setVisibility(View.GONE);
        }
        return view;
    }


    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        int length = getCount();
        for (int i = 0; i <length ; i++) {
            char firstChar = mList.get(i).getSortKey().toUpperCase().charAt(0);
            if(firstChar == sectionIndex){
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return mList.get(position).getSortKey().toUpperCase().charAt(0);
    }

    class ViewHolder{

        TextView item_city_section;

        TextView item_city;
    }

}
