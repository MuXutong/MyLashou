package com.example.administrator.mylashou.fragment;


import android.app.Activity;
import android.app.AlertDialog;
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
import com.example.administrator.mylashou.activity.LoginActivity;
import com.example.administrator.mylashou.util.HELP;
import com.example.administrator.mylashou.util.ToolKits;
import com.umeng.fb.FeedbackAgent;

public class FragmentMore extends Fragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private ToggleButton switch_togg;
    private LinearLayout clean_dat;
    private LinearLayout share_setting;
    private LinearLayout check_update;
    private LinearLayout suggest_back;
    private LinearLayout lashou_help;
    private LinearLayout help;
    private LinearLayout  good_comment;

    private TextView current_vison;
    private TextView lashou_phone;
    private TextView clean_dat_view;

    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_more, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        activity = getActivity();

        good_comment = getActivity().findViewById(R.id.good_comment);
        switch_togg = getActivity().findViewById(R.id.switch_togg);
        clean_dat = getActivity().findViewById(R.id.clean_dat);
        share_setting = getActivity().findViewById(R.id.share_setting);
        check_update = getActivity().findViewById(R.id.check_update);
        current_vison = getActivity().findViewById(R.id.current_vison);
        lashou_phone = getActivity().findViewById(R.id.lashou_phone);
        suggest_back = getActivity().findViewById(R.id.suggest_back);
        lashou_help = getActivity().findViewById(R.id.lashou_help);
        help = getActivity().findViewById(R.id.help);
        clean_dat_view= getActivity().findViewById(R.id.clean_dat_view);

        current_vison.setText("当前版本：V" + getVersion());
        switch_togg.setOnCheckedChangeListener(this);

        check_update.setOnClickListener(this);
        clean_dat.setOnClickListener(this);
        share_setting.setOnClickListener(this);
        suggest_back.setOnClickListener(this);
        lashou_help.setOnClickListener(this);
        help.setOnClickListener(this);
        good_comment.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.check_update:
                Toast.makeText(getActivity(), "正在检测是否有最新版本", Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "当前为最新版本", Toast.LENGTH_SHORT).show();
                break;
            case R.id.suggest_back:// 用户反馈
                FeedbackAgent agent = new FeedbackAgent(getActivity());
                agent.startFeedbackActivity();
                break;

            case R.id.good_comment:
                Toast.makeText(getActivity(), "感谢五星好评", Toast.LENGTH_SHORT).show();
                break;
            case R.id.clean_dat:
                ToolKits.cleanShareData(getContext(), LoginActivity.LOGIN_USER);
                clean_dat_view.setText("0kB");
                break;
            case R.id.share_setting:
                shareText("aaaa","aaa","aaaa");

                break;

            case R.id.lashou_help:
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
                        + lashou_phone.getText().toString().trim())));
                break;
            case R.id.help:
                helpShow();
                Toast.makeText(getActivity(), "帮助", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void helpShow() {
        new AlertDialog.Builder(getContext())
                .setTitle("帮助")  //  ;
                .setMessage(HELP.CONTENT)
                .setIcon(R.drawable.logo)  //  图标
                .setPositiveButton("确定", null)
                .create()
                .show();

    }

    /**
     * 分享文字内容
     *
     * @param dlgTitle
     *            分享对话框标题
     * @param subject
     *            主题
     * @param content
     *            分享内容（文字）
     */
    private void shareText(String dlgTitle, String subject, String content) {
        if (content == null || "".equals(content)) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        if (subject != null && !"".equals(subject)) {
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        }
        intent.putExtra(Intent.EXTRA_TEXT, content);

        // 设置弹出框标题
        if (dlgTitle != null && !"".equals(dlgTitle)) { // 自定义标题
            startActivity(Intent.createChooser(intent, dlgTitle));
        } else { // 系统默认标题
            startActivity(intent);
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
