package cn.ucai.fulicenter.model.net;

import android.content.Context;

import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by Administrator on 2017/1/12.
 */

public class ModelGoods implements IModelGoods {
    @Override
    public void downData(Context context, int goodsId, OnCompletionListener<GoodsDetailsBean> listener) {
        OkHttpUtils<GoodsDetailsBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOOD_DETAILS)
                .addParam(I.Goods.KEY_GOODS_ID, String.valueOf(goodsId))
                .targetClass(GoodsDetailsBean.class)
                .execute(listener);
    }

    @Override
    public void isCollect(Context context, int goodsId, String userName, OnCompletionListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_IS_COLLECT)
                .addParam(I.Goods.KEY_GOODS_ID, String.valueOf(goodsId))
                .addParam(I.Collect.USER_NAME, userName)
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    @Override
    public void setCollect(Context context, int goodsId, String userName, int action, OnCompletionListener<MessageBean> listener) {
        String url = I.REQUEST_ADD_COLLECT;
        if (action == I.ACTION_DELETE_COLLECT) {
            url = I.REQUEST_DELETE_COLLECT;
        }
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(url)
                .addParam(I.Goods.KEY_GOODS_ID, String.valueOf(goodsId))
                .addParam(I.Collect.USER_NAME, userName)
                .targetClass(MessageBean.class)
                .execute(listener);
    }
}
