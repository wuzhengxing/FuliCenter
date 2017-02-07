package cn.ucai.fulicenter.controller.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.bean.User;
import cn.ucai.fulicenter.model.dao.UserDao;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompletionListener;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.ResultUtils;
import cn.ucai.fulicenter.model.utils.SharedPrefrenceUtils;
import cn.ucai.fulicenter.view.DisplayUtils;
import cn.ucai.fulicenter.view.MFGT;

public class Login_OActivity extends AppCompatActivity {
    IModelUser model;
    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.etPassword)
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        DisplayUtils.initBackWithTitle(this,"用户登录");
    }

    @OnClick({R.id.btLogin, R.id.btRegisterFree})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btLogin:
                checkInput();
                break;
            case R.id.btRegisterFree:
               // MFGT.gotoRegister(this);
                break;
        }
    }

    private void checkInput() {
        String userName = etUserName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            etUserName.setError(getString(R.string.user_name_connot_be_empty));
            etUserName.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            etPassword.setError(getString(R.string.password_connot_be_empty));
            etPassword.requestFocus();
        } else {
            login(userName, password);
        }
    }

    private void login(final String userName, String password) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.logining));
        dialog.show();
        model = new ModelUser();
        model.login(this, userName, password, new OnCompletionListener<String>() {
            @Override
            public void onSuccess(String str) {
                if (str != null) {
                    Result result = ResultUtils.getResultFromJson(str, User.class);
                    if (result != null) {
                        if (result.getRetData() != null) {
                            User user = (User) result.getRetData();
                            boolean b = UserDao.getInstance().savaUser(user);
                            Log.e("main", "b=" + b);
                            if (b) {
                                SharedPrefrenceUtils.getInstance(Login_OActivity.this).saveUser(user.getMuserName());
                                FuLiCenterApplication.setUser(user);
                                setResult(RESULT_OK);
                                MFGT.finish(Login_OActivity.this);
                            }
                        } else {
                            if (result.getRetCode() == I.MSG_LOGIN_UNKNOW_USER) {
                                CommonUtils.showLongToast(getString(R.string.login_fail_unknow_user));
                            } else if (result.getRetCode() == I.MSG_LOGIN_ERROR_PASSWORD) {
                                CommonUtils.showLongToast(getString(R.string.login_fail_error_password));
                            }
                        }
                    } else {
                        CommonUtils.showLongToast(getString(R.string.login_fail));
                    }
                } else {
                    CommonUtils.showLongToast(getString(R.string.login_fail));
                }
                dialog.dismiss();
            }

            @Override
            public void onError(String error) {
                dialog.dismiss();
                CommonUtils.showLongToast(error);
            }
        });
    }

}
