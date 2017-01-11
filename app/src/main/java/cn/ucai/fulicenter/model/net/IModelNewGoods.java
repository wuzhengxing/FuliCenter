package cn.ucai.fulicenter.model.net;

import android.content.Context;
import android.media.MediaPlayer;

import cn.ucai.fulicenter.bean.NewGoodsBean;
import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2017/1/11.
 */

public interface IModelNewGoods {
    void downData(Context context, int catId, int pageId,OnCompletionListener<NewGoodsBean[]> listener);
}
