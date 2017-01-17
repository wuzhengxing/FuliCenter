package cn.ucai.fulicenter.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.bean.User;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.model.utils.SharedPrefrenceUtils;
import cn.ucai.fulicenter.view.MFGT;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.tv_user_avatar)
    TextView tvUserAvatar;
    @BindView(R.id.activity_setting)
    RelativeLayout activitySetting;
    @BindView(R.id.iv_user_avatar)
    ImageView ivUserAvatar;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_nick)
    TextView tvUserNick;
    @BindView(R.id.bt_quit)
    Button btQuit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            loadUserInfo(user);
        } else {
            MFGT.gotoLogin(this);
        }
    }

    private void loadUserInfo(User user) {
        ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), this, ivUserAvatar);
        tvUserName.setText(user.getMuserName());
        tvUserNick.setText(user.getMuserNick());

    }


    @OnClick(R.id.bt_quit)
    public void onClick() {
        FuLiCenterApplication.setUser(null);
        SharedPrefrenceUtils.getInstance(this).removeUser();
        MFGT.gotoLogin(this);
        MFGT.finish(this);

    }

    @OnClick(R.id.rl_user_nick)
    public void updateNick() {
        String nick = tvUserNick.getText().toString().trim();
        if(TextUtils.isEmpty(nick)){

        }
    }
}
