package cn.ucai.fulicenter.model.net;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by Administrator on 2017/1/16.
 */

public interface IModelUser {
    void login(Context context, String userName, String password,OnCompletionListener<String> list);
    void register(Context context, String userName,String nick, String password,OnCompletionListener<String> list);
}
