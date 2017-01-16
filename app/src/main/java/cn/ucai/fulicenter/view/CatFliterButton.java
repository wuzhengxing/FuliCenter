package cn.ucai.fulicenter.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.controller.activity.CategoryChildActivity;
import cn.ucai.fulicenter.model.utils.ImageLoader;

/**
 * Created by Administrator on 2017/1/16.
 */

public class CatFliterButton extends Button {
    boolean isExpanded;
    PopupWindow mPopupWindow;
    Context mContext;
    CatFliterAdapter mAdapter;
    GridView mGridView;
    String mGroupName;

    public CatFliterButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void initCatFliterButton(String groupName, ArrayList<CategoryChildBean> list) {
        mGroupName = groupName;
        this.setText(mGroupName);
        setListener();
        mAdapter = new CatFliterAdapter(mContext, list);
        initGridView();
    }

    private void initGridView() {
        mGridView = new GridView(mContext);
        mGridView.setVerticalSpacing(10);
        mGridView.setHorizontalSpacing(10);
        mGridView.setNumColumns(GridView.AUTO_FIT);
        mGridView.setAdapter(mAdapter);

    }

    private void setListener() {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isExpanded) {
                    initPop();
                } else {
                    if (mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                    }
                }
                setArrow();
            }
        });
    }

    private void initPop() {
        mPopupWindow = new PopupWindow();
        mPopupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        mPopupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0xbb000000));
        mPopupWindow.setContentView(mGridView);
        mPopupWindow.showAsDropDown(this);
    }

    private void setArrow() {
        Drawable right;
        if (isExpanded) {
            right = getResources().getDrawable(R.mipmap.arrow2_up);
        } else {
            right = getResources().getDrawable(R.mipmap.arrow2_down);
        }
        right.setBounds(0, 0, right.getIntrinsicWidth(), right.getIntrinsicHeight());
        this.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, right, null);
        isExpanded = !isExpanded;
    }

    class CatFliterAdapter extends BaseAdapter {
        Context context;
        ArrayList<CategoryChildBean> list;

        public CatFliterAdapter(Context context, ArrayList<CategoryChildBean> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CategoryChildBean getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            CatFliterViewHolder holder = null;
            if (view == null) {
                view = View.inflate(context, R.layout.item_cat_fliter, null);
                holder = new CatFliterViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (CatFliterViewHolder) view.getTag();
            }
            holder.bind(i);

            return view;
        }
        class CatFliterViewHolder {
            @BindView(R.id.iv_category_child_thumb)
            ImageView ivCategoryChildThumb;
            @BindView(R.id.tv_category_child_name)
            TextView tvCategoryChildName;
            @BindView(R.id.layout_category_child)
            RelativeLayout layoutCategoryChild;

            CatFliterViewHolder(View view) {
                ButterKnife.bind(this, view);
            }

            public void bind( final int i) {
                ImageLoader.downloadImg(context, ivCategoryChildThumb, list.get(i).getImageUrl());
                tvCategoryChildName.setText(list.get(i).getName());
                layoutCategoryChild.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MFGT.gotoCategoryChild(mContext,
                                list.get(i).getId(),
                                mGroupName,
                                list);
                        MFGT.finish((CategoryChildActivity)mContext);
                    }
                });
            }
        }

    }



}





