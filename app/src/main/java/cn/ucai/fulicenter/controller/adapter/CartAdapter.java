package cn.ucai.fulicenter.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.bean.MessageBean;
import cn.ucai.fulicenter.bean.User;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompletionListener;
import cn.ucai.fulicenter.model.utils.ImageLoader;

/**
 * Created by Administrator on 2017/1/19.
 */

public class CartAdapter extends RecyclerView.Adapter {

    Context mContext;
    ArrayList<CartBean> mList;
    IModelUser model;
    User user;

    public CartAdapter(Context mContext, ArrayList<CartBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        model=new ModelUser();
        user=FuLiCenterApplication.getUser();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.item_cart, null);
        return new CartViewHolder(layout);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        CartViewHolder vh = (CartViewHolder) holder;
        vh.bind(position);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void initData(ArrayList<CartBean> list) {
        if (mList != null) {
            mList.clear();
        }
        addData(list);
    }

    public void addData(ArrayList<CartBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }




    class CartViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.chkSelect)
        CheckBox chkSelect;
        @BindView(R.id.ivGoodsThumb)
        ImageView ivGoodsThumb;
        @BindView(R.id.tvGoodsName)
        TextView tvGoodsName;
        @BindView(R.id.ivAddCart)
        ImageView ivAddCart;
        @BindView(R.id.tvCartCount)
        TextView tvCartCount;
        @BindView(R.id.ivReduceCart)
        ImageView ivReduceCart;
        @BindView(R.id.tvGoodsPrice)
        TextView tvGoodsPrice;

        int listPosition;

        CartViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(int position) {
            listPosition = position;
            chkSelect.setChecked(mList.get(position).isChecked());
            GoodsDetailsBean detailsBean = mList.get(position).getGoods();
            if (detailsBean != null) {
                ImageLoader.downloadImg(mContext, ivGoodsThumb, detailsBean.getGoodsThumb());
                tvGoodsName.setText(detailsBean.getGoodsName());
                tvGoodsPrice.setText(detailsBean.getCurrencyPrice());
            }
            tvCartCount.setText("(" + mList.get(position).getCount() + ")");
        }

        @OnCheckedChanged(R.id.chkSelect)
        public void setCheck(boolean check) {
            mList.get(listPosition).setChecked(check);
            mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATE_CART));
        }
        @OnClick(R.id.ivAddCart)
        public void addCount() {

             model.updateCart(mContext, I.ACTION_CART_ADD, user.getMuserName(), mList.get(listPosition).getGoodsId(), 1, mList.get(listPosition).getId(), new OnCompletionListener<MessageBean>() {
                 @Override
                 public void onSuccess(MessageBean result) {
                     if(result!=null && result.isSuccess()){
                         mList.get(listPosition).setCount(mList.get(listPosition).getCount()+1);
                         mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATE_CART));
                     }
                 }

                 @Override
                 public void onError(String error) {

                 }
             });
        }

        @OnClick(R.id.ivReduceCart)
        public void deleteCount() {
            int action=I.ACTION_CART_UPDATE;
            final int count=mList.get(listPosition).getCount();
            if(count<=1){
                action=I.ACTION_CART_DELETE;
            }
            model.updateCart(mContext, action, user.getMuserName(),
                    mList.get(listPosition).getGoodsId(), count-1, mList.get(listPosition).getId(), new OnCompletionListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if(result!=null && result.isSuccess()){
                        if(count<=1){
                            mList.remove(listPosition);
                        }else {
                            mList.get(listPosition).setCount(count-1);
                        }
                        mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATE_CART));
                    }
                }

                @Override
                public void onError(String error) {

                }
            });

        }
    }


}
