package cn.ucai.fulicenter.application;

import android.app.Application;

import cn.ucai.fulicenter.bean.User;

/**
 * Created by Administrator on 2017/1/10.
 */

public class FuLiCenterApplication extends Application {
    public static FuLiCenterApplication insatnce;
    public  static FuLiCenterApplication getInstance(){
        return insatnce;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        insatnce=this;
    }
    public static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        FuLiCenterApplication.user = user;
    }
}
