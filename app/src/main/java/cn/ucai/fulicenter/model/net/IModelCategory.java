package cn.ucai.fulicenter.model.net;

import android.content.Context;

import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.CategoryGroupBean;
import cn.ucai.fulicenter.bean.NewGoodsBean;

/**
 * Created by Administrator on 2017/1/11.
 */

public interface IModelCategory {
    void downData(Context context, OnCompletionListener<CategoryGroupBean[]> listener);

    void downData(Context context,int parentId, OnCompletionListener<CategoryChildBean[]> listener);
}
