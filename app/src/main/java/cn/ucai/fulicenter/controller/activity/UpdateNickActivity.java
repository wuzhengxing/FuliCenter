package cn.ucai.fulicenter.controller.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
import cn.ucai.fulicenter.view.DisplayUtils;

public class UpdateNickActivity extends AppCompatActivity {
    IModelUser model;
    User user;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.etNick)
    EditText etNick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nick);
        ButterKnife.bind(this);
        DisplayUtils.initBackWithTitle(this,"更新昵称");
        initData();
    }

    private void initData() {
        user = FuLiCenterApplication.getUser();
        if(user==null){
              finish();
        }else {
           etNick.setText(user.getMuserNick());
        }
    }

    @OnClick(R.id.btUpdate)
    public void onClick() {
         user = FuLiCenterApplication.getUser();
         String nick=etNick.getText().toString().trim();
        if(TextUtils.isEmpty(nick)){
            CommonUtils.showLongToast(R.string.nick_name_connot_be_empty);
        }else if(nick.equals(user.getMuserNick())){
            CommonUtils.showLongToast(R.string.update_nick_fail_unmodify);
        }else {
            updateNick(nick);
        }
    }

    private void updateNick(String nick) {
        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setMessage("正在更新");
        dialog.show();
        model=new ModelUser();
        model.updateNick(this, user.getMuserName(), nick, new OnCompletionListener<String>() {
            @Override
            public void onSuccess(String str) {
                int msg=R.string.update_fail;
                if(str!=null){
                    Result result = ResultUtils.getResultFromJson(str, User.class);
                    if(result!=null){
                        if(result.isRetMsg()){
                            msg=R.string.update_user_nick_success;
                            User user = (User) result.getRetData();
                            FuLiCenterApplication.setUser(user);
                            UserDao.getInstance().savaUser(user);
                            setResult(RESULT_OK);
                            finish();
                        }else if(result.getRetCode()== I.MSG_USER_SAME_NICK ||
                                result.getRetCode()==I.MSG_USER_UPDATE_NICK_FAIL){
                            msg=R.string.update_nick_fail_unmodify;
                        }
                    }
                }
                CommonUtils.showLongToast(msg);
            }

            @Override
            public void onError(String error) {
              CommonUtils.showLongToast(R.string.update_fail);
                dialog.dismiss();
            }
        });
    }
}
