package cn.ucai.fulicenter.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;

/**
 * Created by Administrator on 2017/1/11.
 */

public class NewGoodsAdapter extends RecyclerView.Adapter {
    Context mContext;
    ArrayList<NewGoodsBean> mList;

    public NewGoodsAdapter(Context mContext, ArrayList<NewGoodsBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.item_goods, null);
        return new GoodsViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GoodsViewHolder vh = (GoodsViewHolder) holder;
        ImageLoader.downloadImg(mContext, vh.ivGoodsThumb, mList.get(position).getGoodsThumb());
        vh.tvGoodsName.setText(mList.get(position).getGoodsName());
        vh.tvGoodsPrice.setText(mList.get(position).getCurrencyPrice());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void initData(ArrayList<NewGoodsBean> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }


    static class GoodsViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ivGoodsThumb)
        ImageView ivGoodsThumb;
        @BindView(R.id.tvGoodsName)
        TextView tvGoodsName;
        @BindView(R.id.tvGoodsPrice)
        TextView tvGoodsPrice;
        @BindView(R.id.layout_goods)
        LinearLayout layoutGoods;

        GoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
