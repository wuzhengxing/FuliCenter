package cn.ucai.fulicenter.model.net;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.File;

import cn.ucai.fulicenter.bean.MessageBean;

/**
 * Created by Administrator on 2017/1/16.
 */

public interface IModelUser {
    void login(Context context, String userName, String password,OnCompletionListener<String> list);
    void register(Context context, String userName,String nick, String password,OnCompletionListener<String> list);
    void updateNick(Context context, String userName,String nick,OnCompletionListener<String> list);
    void collectCount(Context context, String userName,OnCompletionListener<MessageBean> list);
    void updateAvatar(Context context, String userName, File file, OnCompletionListener<String> list);

}
