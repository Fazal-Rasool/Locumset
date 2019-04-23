package com.adaxiom.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefrence {

    private static String IS_LOGIN = "is_login";
    private static String FCM_TOKEN = "fcm_token";
    private static String USER_ID = "user_id";
    private static String SET_FILTER = "filter";

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences("mLocumPref", 0);
    }

    public static void setIsLogin(Context context, String value) {
        getPrefs(context).edit().putString(IS_LOGIN, value).commit();
    }

    public static String getIsLogin(Context context) {
        return getPrefs(context).getString(IS_LOGIN, "0");
    }

    public static void setFcmToken(Context context, String value){
        getPrefs(context).edit().putString(FCM_TOKEN, value).commit();
    }

    public static String getFcmToken(Context context) {
        return getPrefs(context).getString(FCM_TOKEN, "");
    }

    public static void setUserId(Context context, int value){
        getPrefs(context).edit().putInt(USER_ID, value).commit();
    }

    public static int getUserId(Context context) {
        return getPrefs(context).getInt(USER_ID, 0);
    }

    public static void setFilter(Context context, String value){
        getPrefs(context).edit().putString(SET_FILTER, value).commit();
    }

    public static String getFilter(Context context) {
        return getPrefs(context).getString(SET_FILTER, "0");
    }


}
