package cn.ucai.fulicenter.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.bean.User;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompletionListener;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.view.MFGT;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalCenterFragment extends Fragment {
    IModelUser model;

    @BindView(R.id.iv_user_avatar)
    ImageView ivUserAvatar;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_collect_count)
    TextView tvCollectCount;

    public PersonalCenterFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_personal_center, container, false);
        ButterKnife.bind(this, layout);
        return layout;

    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            loadUserInfo(user);
            getCollectCount();
        } else {
            // MFGT.gotoLogin(getActivity());
        }
    }

    private void getCollectCount() {
        model = new ModelUser();
        model.collectCount(getContext(), FuLiCenterApplication.getUser().getMuserName(),
                new OnCompletionListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result != null && result.isSuccess()) {
                          loadCollectCount(result.getMsg());
                        }else {
                            loadCollectCount("0");
                        }
                    }

                    @Override
                    public void onError(String error) {
                        loadCollectCount("0");
                    }
                });
    }

    private void loadUserInfo(User user) {
        ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), getContext(), ivUserAvatar);
        tvUserName.setText(user.getMuserName());
        loadCollectCount("0");

    }

    private void loadCollectCount(String s) {
         tvCollectCount.setText(s);
    }

    @OnClick({R.id.tv_center_settings, R.id.center_user_info})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_center_settings:
            case R.id.center_user_info:
                MFGT.gotoSetting(getActivity());
                break;
        }
    }
}