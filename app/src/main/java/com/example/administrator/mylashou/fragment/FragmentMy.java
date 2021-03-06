package com.example.administrator.mylashou.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mylashou.R;
import com.example.administrator.mylashou.activity.AccountActivity;
import com.example.administrator.mylashou.activity.FavoriteActivity;
import com.example.administrator.mylashou.activity.LoginActivity;
import com.example.administrator.mylashou.activity.ShowOrderActivity;
import com.example.administrator.mylashou.activity.ShowUnpayOrderActivity;
import com.example.administrator.mylashou.entity.User;
import com.example.administrator.mylashou.util.ToolKits;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class FragmentMy extends Fragment {

    private Button favorite_btn;
    private Button bt_login;
    private LinearLayout layout_no_login;
    private LinearLayout layout_for_logined;
    private TextView my_userName;
    private TextView my_userMoney;

    private LinearLayout pay_order;
    private LinearLayout unpay_order;

    private User mUser;
    private ImageView accout_manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        bt_login = getActivity().findViewById(R.id.bt_login);
        pay_order = getActivity().findViewById(R.id.pay_order);
        unpay_order = getActivity().findViewById(R.id.unpay_order);

        favorite_btn = getActivity().findViewById(R.id.favorite_btn);
        accout_manager = getActivity().findViewById(R.id.accout_manager);
        layout_no_login = getActivity().findViewById(R.id.layout_no_login);
        layout_for_logined = getActivity().findViewById(R.id.layout_for_logined);
        my_userName = getActivity().findViewById(R.id.my_userName);
        my_userMoney = getActivity().findViewById(R.id.my_userMoney);

        pay_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson=new GsonBuilder().create();
                String userStr= ToolKits.getShareData(getContext(), LoginActivity.LOGIN_USER,null);
                mUser=gson.fromJson(userStr, User.class);
                if(mUser!=null){

                    Intent intent = new Intent(getContext(), ShowOrderActivity.class);
                    intent.putExtra("user_id", mUser.getId());//讲Goods对象通过意图传递
                    startActivity(intent);

                }else{
                    startActivity(new Intent(getContext(),LoginActivity.class));
                    Toast.makeText(getContext(),"请先登录",Toast.LENGTH_SHORT).show();
                }
            }
        });

        unpay_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson=new GsonBuilder().create();
                String userStr= ToolKits.getShareData(getContext(), LoginActivity.LOGIN_USER,null);
                mUser=gson.fromJson(userStr, User.class);
                if(mUser!=null){

                    Intent intent = new Intent(getContext(), ShowUnpayOrderActivity.class);
                    intent.putExtra("user_id", mUser.getId());//讲Goods对象通过意图传递
                    startActivity(intent);

                }else{
                    startActivity(new Intent(getContext(),LoginActivity.class));
                    Toast.makeText(getContext(),"请先登录",Toast.LENGTH_SHORT).show();
                }
            }
        });


        favorite_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Gson gson=new GsonBuilder().create();
                String userStr= ToolKits.getShareData(getContext(), LoginActivity.LOGIN_USER,null);
                mUser=gson.fromJson(userStr, User.class);
                if(mUser!=null){

                    Intent intent = new Intent(getContext(), FavoriteActivity.class);
                    intent.putExtra("user_id", mUser.getId());//讲Goods对象通过意图传递
                    startActivity(intent);

                }else{
                    startActivity(new Intent(getContext(),LoginActivity.class));
                    Toast.makeText(getContext(),"请先登录",Toast.LENGTH_SHORT).show();
                }
            }
        });


        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

        accout_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AccountActivity.class));
            }
        });

    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Gson gson=new GsonBuilder().create();
        String userStr= ToolKits.getShareData(getActivity(), LoginActivity.LOGIN_USER,null);
        User user=gson.fromJson(userStr, User.class);
        if(user!=null){
            layout_for_logined.setVisibility(View.VISIBLE);
            layout_no_login.setVisibility(View.GONE);
            my_userName.setText(user.getName());
            my_userMoney.setText("当前账户余额："+user.getCount_money()+"元");
        }else{
            layout_for_logined.setVisibility(View.GONE);
            layout_no_login.setVisibility(View.VISIBLE);
        }
    }
}
