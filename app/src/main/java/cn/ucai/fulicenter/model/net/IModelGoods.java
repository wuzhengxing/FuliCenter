package cn.ucai.fulicenter.model.net;

import android.content.Context;

import cn.ucai.fulicenter.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.bean.NewGoodsBean;

/**
 * Created by Administrator on 2017/1/11.
 */

public interface IModelGoods {
    void downData(Context context, int goodsId, OnCompletionListener<GoodsDetailsBean> listener);

    void isCollect(Context context, int goodsId,String userName, OnCompletionListener<MessageBean> listener);
}
