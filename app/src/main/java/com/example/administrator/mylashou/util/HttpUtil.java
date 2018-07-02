package com.example.administrator.mylashou.util;

import java.io.IOException;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {

    public static void sendOkHttpRequest(String address, final Map<String,String>  head ,final Map<String,String> params,  Callback callback){

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {

                        Request original = chain.request();
                        /**
                         *    添加1   尾部  参数
                         */
                        HttpUrl originalHttpUrl = original.url();
                        HttpUrl.Builder httpBuilder  = originalHttpUrl.newBuilder();
                        if (params!=null) {
                            for (String key : params.keySet()) {
                                httpBuilder = httpBuilder.addQueryParameter(key, params.get(key));
                            }
                        }
                        HttpUrl url = httpBuilder.build();

                        /**
                         *    添加   head  参数
                         */
                        Request.Builder requestBuilder = original.newBuilder().url(url);
                        if (head!=null) {
                            for (String key : head.keySet()) {
                                requestBuilder = requestBuilder.addHeader(key, head.get(key));
                            }
                        }
                        Request request = requestBuilder.build();

                        return chain.proceed(request);
                    }
                }).build();


        Request request = new Request.Builder().url(address).build();

        client.newCall(request).enqueue(callback);
    }

}
