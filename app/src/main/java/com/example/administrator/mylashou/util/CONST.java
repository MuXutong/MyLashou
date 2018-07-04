package com.example.administrator.mylashou.util;

public class CONST {

    //http://localhost:8080/joy-manage-controller/getCity


    //public static final String IP="10.0.2.2";
    public static final String IP="10.0.161.94";

    //public static final String HOST = "http://10.0.2.2:8080/joy-manage-controller";

    public static final String CITY_LIST = "http://"+IP+":8080/ls_server/CityServlet";

    public static final String GOODS_LIST = "http://"+IP+":8080/ls_server/GoodsServlet";

    public static final String GOODS_NEARBY = "http://"+IP+":8080/ls_server/NearbyServlet";

    public static final String REGISTER_USER="http://"+IP+":8080/ls_server/UserServlet";

    public static final String LOGIN_USER="http://"+IP+":8080/ls_server/UserServlet";

    public static final String ADD_FAVORITE="http://"+IP+":8080/ls_server/FavoriteServlet";

    public static final String SHOW_FAVORITE="http://"+IP+":8080/ls_server/ShowFavoriteServlet";

    //http://localhost:8080/ls_server/ShowFavoriteServlet?user_id=16
}
