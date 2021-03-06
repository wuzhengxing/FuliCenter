package cn.ucai.fulicenter.model.net;

import android.content.Context;

import java.io.File;

import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.CollectBean;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.model.utils.MD5;
import cn.ucai.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by Administrator on 2017/1/16.
 */

public class ModelUser implements IModelUser {
    @Override
    public void login(Context context, String userName, String password, OnCompletionListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_LOGIN)
                .addParam(I.User.USER_NAME, userName)
                .addParam(I.User.PASSWORD, MD5.getMessageDigest(password))
                .targetClass(String.class)
                .execute(listener);
    }

    @Override
    public void register(Context context, String userName, String nick, String password, OnCompletionListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_REGISTER)
                .addParam(I.User.USER_NAME, userName)
                .addParam(I.User.NICK, nick)
                .addParam(I.User.PASSWORD, MD5.getMessageDigest(password))
                .post()
                .targetClass(String.class)
                .execute(listener);
    }

    @Override
    public void updateNick(Context context, String userName, String nick, OnCompletionListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_USER_NICK)
                .addParam(I.User.USER_NAME, userName)
                .addParam(I.User.NICK, nick)
                .targetClass(String.class)
                .execute(listener);
    }

    @Override
    public void collectCount(Context context, String userName, OnCompletionListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_COLLECT_COUNT)
                .addParam(I.Collect.USER_NAME, userName)
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    @Override
    public void updateAvatar(Context context, String userName, File file, OnCompletionListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_AVATAR)
                .addParam(I.NAME_OR_HXID, userName)
                .addParam(I.AVATAR_TYPE, I.AVATAR_TYPE_USER_PATH)
                .addFile2(file)
                .post()
                .targetClass(String.class)
                .execute(listener);
    }

    @Override
    public void getCollect(Context context, String userName, int pageId, int pageSize, OnCompletionListener<CollectBean[]> listener) {
        OkHttpUtils<CollectBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_COLLECTS)
                .addParam(I.Collect.USER_NAME, userName)
                .addParam(I.PAGE_ID, String.valueOf(pageId))
                .addParam(I.PAGE_SIZE, String.valueOf(pageSize))
                .targetClass(CollectBean[].class)
                .execute(listener);
    }

    @Override
    public void getCart(Context context, String userName, OnCompletionListener<CartBean[]> listener) {
        OkHttpUtils<CartBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CARTS)
                .addParam(I.Cart.USER_NAME, userName)
                .targetClass(CartBean[].class)
                .execute(listener);
    }

    @Override
    public void addCart(Context context, String userName, int goodsId, int count, OnCompletionListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_ADD_CART)
                .addParam(I.Cart.USER_NAME, userName)
                .addParam(I.Cart.GOODS_ID, String.valueOf(goodsId))
                .addParam(I.Cart.COUNT, String.valueOf(count))
                .addParam(I.Cart.IS_CHECKED, String.valueOf(false))
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    @Override
    public void deleteCart(Context context, int cartId, OnCompletionListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_DELETE_CART)
                .addParam(I.Cart.ID, String.valueOf(cartId))
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    @Override
    public void updateCart(Context context, int cartId, int count, OnCompletionListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_CART)
                .addParam(I.Cart.ID, String.valueOf(cartId))
                .addParam(I.Cart.COUNT, String.valueOf(count))
                .addParam(I.Cart.IS_CHECKED, String.valueOf(false))
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    @Override
    public void updateCart(Context context, int action, String userName, int goodsId, int count, int cartId, OnCompletionListener<MessageBean> listener) {
        if (action == I.ACTION_CART_UPDATE) {
            updateCart(context, cartId, count, listener);
        } else if (action == I.ACTION_DELETE_COLLECT) {
            deleteCart(context, cartId, listener);
        } else {
            addCart(context, userName, goodsId, count, listener);
        }
    }
}
