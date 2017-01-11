package cn.ucai.fulicenter.model.net;

import android.content.Context;

import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.bean.NewGoodsBean;

/**
 * Created by Administrator on 2017/1/11.
 */

public interface IModelBoutique {
    void downData(Context context, OnCompletionListener<BoutiqueBean[]> listener);
}
