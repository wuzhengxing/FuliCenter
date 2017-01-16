package cn.ucai.fulicenter.controller.activity;

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
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.bean.User;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompletionListener;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.ResultUtils;
import cn.ucai.fulicenter.view.MFGT;

public class RegisterActivity extends AppCompatActivity {
    IModelUser model;
    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.etNick)
    EditText etNick;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etConfirmPassword)
    EditText etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ivBack, R.id.btnRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                MFGT.finish(this);
                break;
            case R.id.btnRegister:
                checkInput();
                break;
        }
    }

    private void checkInput() {
        String userName = etUserName.getText().toString().trim();//去空格
        String nick = etNick.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            etUserName.setError(getResources().getString(R.string.user_name_connot_be_empty));
            etUserName.requestFocus();
        } else if (!userName.matches("[a-zA-Z]\\w{5,15}")) {
            etUserName.setError(getResources().getString(R.string.illegal_user_name));
            etUserName.requestFocus();
        } else if (TextUtils.isEmpty(nick)) {
            etNick.setError(getResources().getString(R.string.nick_name_connot_be_empty));
            etNick.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            etPassword.setError(getResources().getString(R.string.password_connot_be_empty));
            etPassword.requestFocus();
        } else if (TextUtils.isEmpty(confirmPassword)) {
            etConfirmPassword.setError(getResources().getString(R.string.confirm_password_connot_be_empty));
            etConfirmPassword.requestFocus();
        } else if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError(getResources().getString(R.string.two_input_password));
            etConfirmPassword.requestFocus();
        } else {
            register(userName, nick, password);
        }
    }

    private void register(String userName, String nick, String password) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.register));
        model = new ModelUser();
        model.register(this, userName, nick, password, new OnCompletionListener<String>() {
            @Override
            public void onSuccess(String str) {
                if (str != null) {
                    Result result = ResultUtils.getResultFromJson(str, User.class);
                    if (result.isRetMsg()) {
                        CommonUtils.showLongToast(R.string.register_success);
                    } else {
                        CommonUtils.showLongToast(R.string.register_fail_exists);
                    }
                } else {
                    CommonUtils.showLongToast(R.string.register_fail);
                }
                dialog.dismiss();
            }

            @Override
            public void onError(String error) {
                CommonUtils.showLongToast(R.string.register_fail);
                dialog.dismiss();
            }
        });
    }
}
