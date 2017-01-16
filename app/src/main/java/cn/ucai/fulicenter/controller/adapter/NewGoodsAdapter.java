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
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.view.FooterViewHolder;
import cn.ucai.fulicenter.view.MFGT;

/**
 * Created by Administrator on 2017/1/11.
 */

public class NewGoodsAdapter extends RecyclerView.Adapter {
    final static int TYPE_FOOTER = 0;
    final static int TYPE_Contact = 1;

    Context mContext;
    ArrayList<NewGoodsBean> mList;
    boolean isMore;
    boolean isDragging;
    String footer;

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


    public NewGoodsAdapter(Context mContext, ArrayList<NewGoodsBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View layout;
        switch (viewType) {
            case TYPE_Contact:
                layout = inflater.inflate(R.layout.item_goods, null);
                return new GoodsViewHolder(layout);
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
        GoodsViewHolder vh = (GoodsViewHolder) holder;
        ImageLoader.downloadImg(mContext, vh.ivGoodsThumb, mList.get(position).getGoodsThumb());
        vh.tvGoodsName.setText(mList.get(position).getGoodsName());
        vh.tvGoodsPrice.setText(mList.get(position).getCurrencyPrice());
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MFGT.gotoGoodsDetails(mContext,mList.get(position).getGoodsId());
            }
        });
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

    public void initData(ArrayList<NewGoodsBean> list) {
        if (mList != null) {
            mList.clear();
        }
        addData(list);
    }

    public void addData(ArrayList<NewGoodsBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }


    static class GoodsViewHolder extends RecyclerView.ViewHolder {
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
   public void sortGoods(final int sortBy){
        Collections.sort(mList, new Comparator<NewGoodsBean>() {
            @Override
            public int compare(NewGoodsBean rightBean, NewGoodsBean leftBean) {
                int result=0;
                switch (sortBy){
                    case I.SORT_BY_ADDTIME_ASC:
                        result=(int)(leftBean.getAddTime()-rightBean.getAddTime());
                        break;
                    case I.SORT_BY_ADDTIME_DESC:
                        result=(int)(rightBean.getAddTime()-leftBean.getAddTime());
                        break;
                    case I.SORT_BY_PRICE_ASC:
                        result=getPrice(leftBean.getCurrencyPrice())-getPrice(rightBean.getCurrencyPrice());
                        break;
                    case I.SORT_BY_PRICE_DESC:
                        result=getPrice(rightBean.getCurrencyPrice())-getPrice(leftBean.getCurrencyPrice());
                        break;
                }
                return result;

            }

        });
       notifyDataSetChanged();
    }
    public int getPrice(String price){
        int b=0;
        b=Integer.parseInt(price.substring(price.indexOf("￥")+1));

        return b;
    }

}
