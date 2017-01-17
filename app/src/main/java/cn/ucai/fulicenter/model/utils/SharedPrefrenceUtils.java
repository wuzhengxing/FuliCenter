package cn.ucai.fulicenter.model.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Administrator on 2017/1/16.
 */

public class SharedPrefrenceUtils {
    private static final String SHARE_PREFRENCES_NAME = "cn.ucai.fulicenter_user";
    private static final String SHARE_PREFRENCES_USER_USERNAME = "cn.ucai.fulicenter_user_username";
    private static SharedPrefrenceUtils instance;
    private static SharedPreferences preferences;

    public SharedPrefrenceUtils(Context context) {
        preferences = context.getSharedPreferences(SHARE_PREFRENCES_NAME, context.MODE_PRIVATE);
    }

    public static SharedPrefrenceUtils getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefrenceUtils(context);
        }
        return instance;
    }

    public static void saveUser(String userName) {
        preferences.edit().putString(SHARE_PREFRENCES_USER_USERNAME, userName).commit();
    }

    public static String getUser() {
        return preferences.getString(SHARE_PREFRENCES_USER_USERNAME, null);
    }
    public static void removeUser(){
        preferences.edit().remove(SHARE_PREFRENCES_USER_USERNAME).commit();
    }
}
