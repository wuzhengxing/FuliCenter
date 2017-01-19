package cn.ucai.fulicenter.model.net;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.File;

import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.CollectBean;
import cn.ucai.fulicenter.bean.MessageBean;

/**
 * Created by Administrator on 2017/1/16.
 */

public interface IModelUser {
    void login(Context context, String userName, String password,OnCompletionListener<String> listener);
    void register(Context context, String userName,String nick, String password,OnCompletionListener<String> listener);
    void updateNick(Context context, String userName,String nick,OnCompletionListener<String> listener);
    void collectCount(Context context, String userName,OnCompletionListener<MessageBean> list);
    void updateAvatar(Context context, String userName, File file, OnCompletionListener<String> listener);
    void getCollect(Context context, String userName,int pageId,int pageSize, OnCompletionListener<CollectBean[]> listener);
    void getCart(Context context, String userName, OnCompletionListener<CartBean[]> listener);
    void addCart(Context context, String userName,int goodsId,int count, OnCompletionListener<MessageBean> listener);
    void deleteCart(Context context,int cartId, OnCompletionListener<MessageBean> listener);
    void updateCart(Context context,int cartId,int count, OnCompletionListener<MessageBean> listener);
    void updateCart(Context context,int action,String userName,int goodsId,int count, int cartId,OnCompletionListener<MessageBean> listener);
}
