package cn.ucai.fulicenter.mvp;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.bean.User;
import cn.ucai.fulicenter.model.dao.UserDao;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompletionListener;
import cn.ucai.fulicenter.model.utils.ResultUtils;
import cn.ucai.fulicenter.model.utils.SharedPrefrenceUtils;

/**
 * Created by Administrator on 2017/2/7.
 */

public class LoginPresenter implements LoginContract.Presenter {
    IModelUser model;
    LoginContract.View view;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        model = new ModelUser();
    }

    @Override
    public void login(final Context context, String userName, String password) {
        if (TextUtils.isEmpty(userName)) {
            view.loginFailByUserName();
        } else if (TextUtils.isEmpty(password)) {
            view.loginFailByPassword();
        } else {
            view.showDialog();
            model.login(context, userName, password, new OnCompletionListener<String>() {
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
                                    SharedPrefrenceUtils.getInstance(context).saveUser(user.getMuserName());
                                    FuLiCenterApplication.setUser(user);
                                    view.loginSuccess();
                                }
                            } else {
                                if (result.getRetCode() == I.MSG_LOGIN_UNKNOW_USER) {
                                    view.showError(R.string.login_fail_unknow_user);
                                } else if (result.getRetCode() == I.MSG_LOGIN_ERROR_PASSWORD) {
                                   view.showError(R.string.login_fail_error_password);
                                }
                            }
                        } else {
                            view.showError(R.string.login_fail);
                        }
                    } else {
                        view.showError(R.string.login_fail);
                    }
                    view.dissmissDialog();
                }

                @Override
                public void onError(String error) {
                    view.dissmissDialog();
                    view.showError(error);
                }
            });
        }

    }

    @Override
    public void start() {

    }
}
