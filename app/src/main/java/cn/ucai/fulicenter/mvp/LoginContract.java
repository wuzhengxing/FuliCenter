package cn.ucai.fulicenter.mvp;

import android.content.Context;
import android.provider.ContactsContract;

/**
 * Created by Administrator on 2017/2/7.
 */

public interface LoginContract {
    interface View extends BaseView<Presenter> {
        void loginSuccess();
        void showError(String error);
        void showError(int error);
        void loginFailByUserName();
        void loginFailByPassword();
        void showDialog();
        void dissmissDialog();
    }

    interface Presenter extends BasePresenter {
        void login(Context context, String userName, String password);
    }
}
