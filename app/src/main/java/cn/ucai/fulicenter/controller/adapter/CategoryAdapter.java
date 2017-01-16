package cn.ucai.fulicenter.controller.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.CategoryGroupBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.view.MFGT;

/**
 * Created by Administrator on 2017/1/13.
 */

public class CategoryAdapter extends BaseExpandableListAdapter {
    Context mContext;
    ArrayList<CategoryGroupBean> mGroupList;
    ArrayList<ArrayList<CategoryChildBean>> mChildList;

    public CategoryAdapter(Context mContext, ArrayList<CategoryGroupBean> groupList,
                           ArrayList<ArrayList<CategoryChildBean>> childList) {
        this.mContext = mContext;
        mGroupList = new ArrayList<>();
        this.mGroupList.addAll(groupList);
        mChildList = new ArrayList<>();
        this.mChildList.addAll(childList);
    }

    @Override
    public int getGroupCount() {
        return mGroupList != null ? mGroupList.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mGroupList != null && mChildList.get(groupPosition) != null ?
                mChildList.get(groupPosition).size() : 0;
    }

    @Override
    public CategoryGroupBean getGroup(int groupPosition) {
        if (mGroupList != null && mGroupList.get(groupPosition) != null) {
            return mGroupList.get(groupPosition);
        }
        return null;
    }


    @Override
    public CategoryChildBean getChild(int groupPosition, int childPosition) {
        if (mChildList != null && mChildList.get(groupPosition).get(childPosition) != null) {
            return mChildList.get(groupPosition).get(childPosition);
        }
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public void initData(ArrayList<CategoryGroupBean> groupList, ArrayList<ArrayList<CategoryChildBean>> childList) {
        this.mGroupList.clear();
        this.mGroupList.addAll(groupList);
        this.mChildList.clear();
        this.mChildList.addAll(childList);
        notifyDataSetChanged();
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
        GroupViewHolder holder = null;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_category_group, null);
            holder = new GroupViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (GroupViewHolder) view.getTag();
        }
        holder.tvGroupName.setText(mGroupList.get(groupPosition).getName());
        ImageLoader.downloadImg(mContext, holder.ivGroupThumb, getGroup(groupPosition).getImageUrl());
        holder.ivIndicator.setImageResource(isExpanded ? R.mipmap.expand_off : R.mipmap.expand_on);
        return view;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean b, View view, ViewGroup viewGroup) {
        ChildViewHolder holder = null;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_category_child, null);
            holder = new ChildViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ChildViewHolder) view.getTag();
        }
        holder.tvCategoryChildName.setText(getChild(groupPosition, childPosition).getName());
        ImageLoader.downloadImg(mContext, holder.ivCategoryChildThumb, getChild(groupPosition, childPosition).getImageUrl());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MFGT.gotoCategoryChild(mContext,getChild(groupPosition,childPosition).getId(),getGroup(groupPosition).getName(),mChildList.get(groupPosition));
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    static class GroupViewHolder {
        @BindView(R.id.iv_Group_thumb)
        ImageView ivGroupThumb;
        @BindView(R.id.iv_indicator)
        ImageView ivIndicator;
        @BindView(R.id.tv_Group_name)
        TextView tvGroupName;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ChildViewHolder {
        @BindView(R.id.iv_category_child_thumb)
        ImageView ivCategoryChildThumb;
        @BindView(R.id.tv_category_child_name)
        TextView tvCategoryChildName;
        @BindView(R.id.layout_category_child)
        RelativeLayout layoutCategoryChild;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
