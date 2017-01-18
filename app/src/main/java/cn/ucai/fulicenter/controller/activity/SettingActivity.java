package cn.ucai.fulicenter.controller.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.bean.User;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompletionListener;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.model.utils.OnSetAvatarListener;
import cn.ucai.fulicenter.model.utils.ResultUtils;
import cn.ucai.fulicenter.model.utils.SharedPrefrenceUtils;
import cn.ucai.fulicenter.view.MFGT;

public class SettingActivity extends AppCompatActivity {
    final static String TAG = "SettingActivity";
    IModelUser model;
    OnSetAvatarListener avatarListener;
    @BindView(R.id.tv_user_avatar)
    TextView tvUserAvatar;
    @BindView(R.id.activity_setting)
    RelativeLayout activitySetting;
    @BindView(R.id.iv_user_avatar)
    ImageView ivUserAvatar;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.bt_quit)
    Button btQuit;
    @BindView(R.id.tvNick)
    TextView tvNick;

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
        tvNick.setText(user.getMuserNick());

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
        MFGT.gotoUpdateNick(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == I.REQUEST_CODE_NICK) {
            tvNick.setText(FuLiCenterApplication.getUser().getMuserNick());
        } else if (requestCode == OnSetAvatarListener.REQUEST_CROP_PHOTO) {
            uploadAvatar();
        }
        avatarListener.setAvatar(requestCode, data, ivUserAvatar);

    }


    private void uploadAvatar() {
        User user = FuLiCenterApplication.getUser();
        model = new ModelUser();
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.update_user_avatar));
        dialog.show();
        File file = null;
       /* File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        file = new File(dir, user.getMuserName() + ".jpg");*/
        file = new File(String.valueOf(OnSetAvatarListener.getAvatarFile
                (this, I.AVATAR_TYPE_USER_PATH + "/" + user.getMuserName() + user.getMavatarSuffix())));
        Log.i("main", "SettingActivity.file:" + file.getAbsolutePath());
        model.updateAvatar(this, user.getMuserName(), file, new OnCompletionListener<String>() {
            @Override
            public void onSuccess(String str) {
                Log.i("main", "str=" + str);
                int msg = R.string.update_user_avatar_fail;
                if (str != null) {
                    Result result = ResultUtils.getResultFromJson(str, User.class);
                    if (result != null) {
                        if (result.isRetMsg()) {
                            msg = R.string.update_user_avatar_success;

                        }
                    }
                }
                CommonUtils.showLongToast(msg);
                dialog.dismiss();
            }

            @Override
            public void onError(String error) {
                CommonUtils.showLongToast(R.string.update_user_avatar_fail);
                Log.i("main", "SettingActivity.error:" + error);
                dialog.dismiss();
            }
        });

    }

    @OnClick(R.id.rl_user_name)
    public void onClickName() {
        CommonUtils.showLongToast(R.string.username_connot_be_modify);
    }

    @OnClick(R.id.rl_user_avatar)
    public void updateAvatar() {

        avatarListener = new OnSetAvatarListener(this, R.id.rl_user_avatar, FuLiCenterApplication.getUser().getMuserName(), I.AVATAR_TYPE_USER_PATH);
    }
}
