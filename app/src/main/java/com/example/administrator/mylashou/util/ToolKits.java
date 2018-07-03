package com.example.administrator.mylashou.util;

import android.content.Context;
import android.content.SharedPreferences;

public class ToolKits {

    public static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences("com.example.administrator.mylashou",Context.MODE_PRIVATE);
    }

    public static void putBooble(Context context,String key,boolean value){
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }

    /**
     * 向SharedPreferences插入数据
     */
    public static void putShareData(Context context, String key, String value) {
        // 获得SharedPreferences对象
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        // 获得SharedPreferences的编辑器
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }


    public static boolean fetchBooble (Context context,String key,boolean defaultValue){

        return getSharedPreferences(context).getBoolean(key,defaultValue);

    }

    /**
     * 获得SharePreferences指定键的值
     */

    public static String getShareData(Context context, String key, String defValue) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getString(key, defValue);

    }

    /**
     * 清除SharePreferences数据
     */
    public static void cleanShareData(Context context,String key){
        SharedPreferences sharedPreferences=getSharedPreferences(context);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * 根据两个点的经度维度，算出这两个点的距离
     */

    public static double getDistance(double lat1,double lon1,double lat2,double lon2){
        double radLat1=lat1*Math.PI/180;
        double radLat2=lat2*Math.PI/180;
        double a=radLat1-radLat2;
        double b=lon1*Math.PI/180-lon2*Math.PI/180;
        double s=2*Math.asin(Math.sqrt(Math.pow(Math.sin(a/2), 2)+
                Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2), 2)));
        s=s*6378137.0;//取WGS84标准参考椭球中的地球长半径（单位：m）
        s=Math.round(s*10000)/10000;
        return s;
    }
}
