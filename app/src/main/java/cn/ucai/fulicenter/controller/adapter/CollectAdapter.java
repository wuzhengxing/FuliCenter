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
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.bean.CollectBean;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.bean.User;
import cn.ucai.fulicenter.model.net.IModelGoods;
import cn.ucai.fulicenter.model.net.ModelGoods;
import cn.ucai.fulicenter.model.net.OnCompletionListener;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.view.FooterViewHolder;
import cn.ucai.fulicenter.view.MFGT;

/**
 * Created by Administrator on 2017/1/19.
 */

public class CollectAdapter extends RecyclerView.Adapter {
    final static int TYPE_FOOTER = 0;
    final static int TYPE_Contact = 1;

    Context mContext;
    ArrayList<CollectBean> mList;
    boolean isMore;
    boolean isDragging;
    String footer;
    IModelGoods model;
    User user;


    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
        notifyDataSetChanged();
    }

    public boolean isMore() {
        return isMore;
    }

    public boolean isDragging() {
        return isDragging;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    public void setDragging(boolean dragging) {
        isDragging = dragging;
        notifyDataSetChanged();
    }


    public CollectAdapter(Context mContext, ArrayList<CollectBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        model = new ModelGoods();
        user= FuLiCenterApplication.getUser();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View layout;
        switch (viewType) {
            case TYPE_Contact:
                layout = inflater.inflate(R.layout.item_collect, null);
                return new CollectViewHolder(layout);
            case TYPE_FOOTER:
                layout = inflater.inflate(R.layout.item_footer, null);
                return new FooterViewHolder(layout);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_FOOTER) {
            FooterViewHolder vh = (FooterViewHolder) holder;
            vh.tvFooter.setText(getFooter());
            return;
        }
        CollectViewHolder vh = (CollectViewHolder) holder;
        vh.bind(position);
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        return TYPE_Contact;
    }

    public void initData(ArrayList<CollectBean> list) {
        if (mList != null) {
            mList.clear();
        }
        addData(list);
    }

    public void addData(ArrayList<CollectBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }


    class CollectViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_collect_del)
        ImageView ivCollectDel;
        @BindView(R.id.ivGoodsThumb)
        ImageView ivGoodsThumb;
        @BindView(R.id.tvGoodsName)
        TextView tvGoodsName;
        @BindView(R.id.layout_goods)
        LinearLayout layoutGoods;

        int itemPosition;

        CollectViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(final int position) {
            ImageLoader.downloadImg(mContext, ivGoodsThumb, mList.get(position).getGoodsThumb());
            tvGoodsName.setText(mList.get(position).getGoodsName());
            itemPosition = position;
        }

        @OnClick(R.id.layout_goods)
        public void getDetails() {
            MFGT.gotoGoodsDetails(mContext, mList.get(itemPosition).getGoodsId());
        }

        @OnClick(R.id.iv_collect_del)
        public void setCollect() {
            model.setCollect(mContext, mList.get(itemPosition).getGoodsId(), user.getMuserName(), I.ACTION_DELETE_COLLECT, new OnCompletionListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if(result!=null && result.isSuccess()){
                        mList.remove(itemPosition);
                        notifyDataSetChanged();
                    }
                }

                @Override
                public void onError(String error) {

                }
            });
        }

    }
}
