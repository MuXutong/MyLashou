package com.example.administrator.mylashou.fragment;


import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.administrator.mylashou.R;


public class FragmentMore extends Fragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private ToggleButton switch_togg;
    private LinearLayout clean_dat;
    private LinearLayout share_setting;
    private LinearLayout check_update;
    private TextView current_vison;
    private TextView lashou_phone;
    private LinearLayout suggest_back;
    private LinearLayout lashou_help;
    private LinearLayout help;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_more, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        switch_togg = getActivity().findViewById(R.id.switch_togg);
        clean_dat = getActivity().findViewById(R.id.clean_dat);
        share_setting = getActivity().findViewById(R.id.share_setting);
        check_update = getActivity().findViewById(R.id.check_update);
        current_vison = getActivity().findViewById(R.id.current_vison);
        lashou_phone = getActivity().findViewById(R.id.lashou_phone);
        suggest_back = getActivity().findViewById(R.id.suggest_back);
        lashou_help = getActivity().findViewById(R.id.lashou_help);
        help = getActivity().findViewById(R.id.help);

        current_vison.setText("当前版本：V" + getVersion());
        switch_togg.setOnCheckedChangeListener(this);

        check_update.setOnClickListener(this);
        clean_dat.setOnClickListener(this);
        share_setting.setOnClickListener(this);
        suggest_back.setOnClickListener(this);
        lashou_help.setOnClickListener(this);
        help.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.check_update:
            //    UmengUpdateAgent.forceUpdate(getActivity());// 手动自动更新
                break;
            case R.id.suggest_back:// 用户反馈
            //    FeedbackAgent agent = new FeedbackAgent(getActivity());
            //    agent.startFeedbackActivity();
                break;
            case R.id.clean_dat:
                Toast.makeText(getActivity(), "清除缓存", Toast.LENGTH_SHORT).show();
                break;
            case R.id.share_setting:
                Toast.makeText(getActivity(), "分享设置", Toast.LENGTH_SHORT).show();
                break;

            case R.id.lashou_help:
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
                        + lashou_phone.getText().toString().trim())));
                break;
            case R.id.help:
                Toast.makeText(getActivity(), "帮助", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    @Override
    public void setMenuVisibility(boolean menuVisible) {// 重写这函数，为了防止显示的界面内容重叠
        // TODO Auto-generated method stub
        super.setMenuVisibility(menuVisible);
        if (this.getView() != null) {
            this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            Toast.makeText(getActivity(), "开", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "关", Toast.LENGTH_SHORT).show();
        }
    }

    private String getVersion() {
        try {
            PackageInfo manage = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            return manage.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "NoKnowVersion";
    }


}
