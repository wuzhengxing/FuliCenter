package cn.ucai.fulicenter.controller.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.bean.User;
import cn.ucai.fulicenter.model.dao.UserDao;
import cn.ucai.fulicenter.model.utils.SharedPrefrenceUtils;
import cn.ucai.fulicenter.view.MFGT;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String userName = SharedPrefrenceUtils.getInstance(SplashActivity.this).getUser();
                Log.e("main","SplashActivity.userName:"+userName);
                if(userName!=null){
                    User user = UserDao.getInstance().getUser(userName);
                    Log.e("main","SplashActivity.user:"+user);
                    if(user!=null){
                        FuLiCenterApplication.setUser(user);
                    }
                }
                MFGT.startActivity(SplashActivity.this, MainActivity.class);
                MFGT.finish(SplashActivity.this);
            }
        }, 2000);

    }
}
