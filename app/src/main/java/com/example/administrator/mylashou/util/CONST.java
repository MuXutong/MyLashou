package com.example.administrator.mylashou.util;

public class CONST {

    //http://localhost:8080/joy-manage-controller/getCity


    //public static final String IP="10.0.2.2";
    public static final String IP="10.0.161.94";

    //public static final String HOST = "http://10.0.2.2:8080/joy-manage-controller";

    public static final String CITY_LIST = "http://"+IP+":8080/ls_server/CityServlet";

    public static final String GOODS_LIST = "http://"+IP+":8080/ls_server/GoodsServlet";

    public static final String GOODS_NEARBY = "http://"+IP+":8080/ls_server/NearbyServlet";
    //http://localhost:8080/ls_server/MapServlet?lat=40.075483&lon=116.3676612&radius=500
    public static final String MAP_NEARBY = "http://"+IP+":8080/ls_server/MapServlet";

    public static final String REGISTER_USER="http://"+IP+":8080/ls_server/UserServlet";

    public static final String LOGIN_USER="http://"+IP+":8080/ls_server/UserServlet";

    public static final String ADD_FAVORITE="http://"+IP+":8080/ls_server/FavoriteServlet";

    public static final String SHOW_FAVORITE="http://"+IP+":8080/ls_server/ShowFavoriteServlet";

    //http://localhost:8080/ls_server/ShowFavoriteServlet?user_id=16

    //http://localhost:8080/ls_server/SelectOrderServlet?user_id=16&state=1
    public static final String SELECT_ORDER="http://"+IP+":8080/ls_server/SelectOrderServlet";

   // http://localhost:8080/ls_server/OrderServlet?state=1&allprice=464&count=2&user_id=16&prodouct_id=892856
    public static final String ADD_ORDER="http://"+IP+":8080/ls_server/OrderServlet";
}
