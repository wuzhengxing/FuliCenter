package cn.ucai.fulicenter.application;

import android.app.Application;

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
}
