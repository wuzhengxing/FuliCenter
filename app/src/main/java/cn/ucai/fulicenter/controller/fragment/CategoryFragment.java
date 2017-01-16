package cn.ucai.fulicenter.controller.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.bean.CategoryGroupBean;
import cn.ucai.fulicenter.controller.adapter.CategoryAdapter;
import cn.ucai.fulicenter.model.net.IModelCategory;
import cn.ucai.fulicenter.model.net.ModelCategory;
import cn.ucai.fulicenter.model.net.OnCompletionListener;
import cn.ucai.fulicenter.model.utils.ConvertUtils;


public class CategoryFragment extends Fragment {

    ArrayList<CategoryGroupBean> mGroupList;
    ArrayList<ArrayList<CategoryChildBean>> mChildList;
    IModelCategory model;
    CategoryAdapter mAdapter;
    int groupCount;
    @BindView(R.id.elv_category)
    ExpandableListView elvCategory;
    @BindView(R.id.tv_noMore)
    TextView tvNoMore;

    public CategoryFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);
        mGroupList = new ArrayList<>();
        mChildList = new ArrayList<>();
        mAdapter = new CategoryAdapter(getContext(), mGroupList, mChildList);
        elvCategory.setAdapter(mAdapter);
        elvCategory.setGroupIndicator(null);//去掉ExpandableListView自带的图标
        initView(false);
        initData();
        return view;
    }

    private void initData() {
        model = new ModelCategory();
        model.downData(getContext(), new OnCompletionListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                Log.i("main1", Arrays.toString(result));
                if (result != null) {
                    initView(true);
                    ArrayList<CategoryGroupBean> groupList = ConvertUtils.array2List(result);
                    mGroupList.addAll(groupList);
                    for (int i = 0; i < groupList.size(); i++) {
                        mChildList.add(new ArrayList<CategoryChildBean>());
                        downloadChild(groupList.get(i).getId(), i);
                    }
                } else {
                    initView(false);
                }
            }

            @Override
            public void onError(String error) {
                initView(false);
                Log.e("main", "error=" + error);
            }
        });
    }

   private void downloadChild(int id, final int index) {
        model.downData(getContext(), id, new OnCompletionListener<CategoryChildBean[]>() {
            @Override
            public void onSuccess(CategoryChildBean[] result) {
                groupCount++;
                if (result != null) {
                    ArrayList<CategoryChildBean> childList = ConvertUtils.array2List(result);
                    mChildList.set(index, childList);
                }
                if (groupCount == mGroupList.size()) {
                    mAdapter.initData(mGroupList, mChildList);
                }
            }

            @Override
            public void onError(String error) {
                groupCount++;
                Log.e("main", "error=" + error);
            }
        });
    }

    private void initView(boolean is) {
        elvCategory.setVisibility(is ? View.VISIBLE : View.GONE);
        tvNoMore.setVisibility(is ? View.GONE : View.VISIBLE);

    }


   /* @OnClick(R.id.tv_noMore)
    public void onClick() {
        initData();
    }*/
}
