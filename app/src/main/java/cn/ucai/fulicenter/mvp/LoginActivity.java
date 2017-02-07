package cn.ucai.fulicenter.mvp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.view.DisplayUtils;
import cn.ucai.fulicenter.view.MFGT;

/**
 * Created by Administrator on 2017/2/7.
 */

public class LoginActivity extends AppCompatActivity implements LoginContract.View {
    LoginPresenter presenter;
    ProgressDialog dialog;
    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.etPassword)
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        DisplayUtils.initBackWithTitle(this, "用户登录");
    }

    @OnClick({R.id.btLogin, R.id.btRegisterFree})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btLogin:
                String userName = etUserName.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                presenter=new LoginPresenter(this);
                presenter.login(this,userName,password);
                break;
            case R.id.btRegisterFree:
                MFGT.gotoRegister(this);
                break;
        }
    }


    @Override
    public void loginSuccess() {
        setResult(RESULT_OK);
        MFGT.finish(this);
    }

    @Override
    public void showError(String error) {
        CommonUtils.showShortToast(error);
    }

    @Override
    public void showError(int error) {
        CommonUtils.showShortToast(error);
    }

    @Override
    public void loginFailByUserName() {
        etUserName.setError(getString(R.string.user_name_connot_be_empty));
        etUserName.requestFocus();
    }

    @Override
    public void loginFailByPassword() {
        etPassword.setError(getString(R.string.password_connot_be_empty));
        etPassword.requestFocus();
    }

    @Override
    public void showDialog() {
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.logining));
        dialog.show();
    }

    @Override
    public void dissmissDialog() {
        if(dialog!=null){
            dialog.dismiss();
        }
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {

    }
}
