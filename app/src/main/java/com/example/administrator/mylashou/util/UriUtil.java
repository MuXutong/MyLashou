package com.example.administrator.mylashou.util;

import java.io.UnsupportedEncodingException;

/**
 * url转码、解码
 */
public class UriUtil {
    private final static String ENCODE = "GBK"; 
    /**
     * URL 解码
     */
    public static String getURLDecoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, ENCODE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * URL 转码
     */
    public static String getURLEncoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, ENCODE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

//    /**
//     *
//     * @return void
//     * @author lifq
//     * @date 2015-3-17 下午04:09:16
//     */
//    public static void main(String[] args) {
//        String str = "/document/primary%3AMovies%2Faa.mp4";
//        System.out.println(getURLEncoderString(str));
//        System.out.println(getURLDecoderString(str));
//
//    }

}