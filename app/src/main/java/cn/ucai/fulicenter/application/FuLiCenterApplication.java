package cn.ucai.fulicenter.application;

import android.app.Application;

import java.util.HashMap;

import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.User;

/**
 * Created by Administrator on 2017/1/10.
 */

public class FuLiCenterApplication extends Application {
    public static FuLiCenterApplication insatnce;
    public  static FuLiCenterApplication getInstance(){
        return insatnce;
    }
  /*  public static HashMap<Integer,CartBean> hashMap=new HashMap<>();

    public static HashMap<Integer, CartBean> getHashMap() {
        return hashMap;
    }

    public static void setHashMap(HashMap<Integer, CartBean> hashMap) {
        FuLiCenterApplication.hashMap = hashMap;
    }*/

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
