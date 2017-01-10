package cn.ucai.fulicenter.view;

import android.app.Activity;
import android.content.Intent;

import cn.ucai.fulicenter.R;

/**
 * Created by Administrator on 2017/1/10.
 */

public class MFGT {
    public static void finish(Activity activity){
        activity.finish();
        activity.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }
    public static void startActivity(Activity context,Class<?> cls){
        context.startActivity(new Intent(context,cls));
        context.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);

    }
}
