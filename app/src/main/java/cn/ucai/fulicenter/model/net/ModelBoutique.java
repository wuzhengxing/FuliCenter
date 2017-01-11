package cn.ucai.fulicenter.model.net;

import android.content.Context;

import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by Administrator on 2017/1/11.
 */

public class ModelBoutique implements IModelBoutique {
    @Override
    public void downData(Context context, OnCompletionListener<BoutiqueBean[]> listener) {
        OkHttpUtils<BoutiqueBean[]> utils=new OkHttpUtils<>(context);
       utils.setRequestUrl(I.REQUEST_FIND_BOUTIQUES)
               .targetClass(BoutiqueBean[].class)
               .execute(listener);
    }
}
