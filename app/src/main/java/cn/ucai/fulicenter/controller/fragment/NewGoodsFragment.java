package cn.ucai.fulicenter.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.controller.adapter.NewGoodsAdapter;
import cn.ucai.fulicenter.model.net.IModelNewGoods;
import cn.ucai.fulicenter.model.net.ModelNewGoods;
import cn.ucai.fulicenter.model.net.OnCompletionListener;
import cn.ucai.fulicenter.model.utils.ConvertUtils;
import cn.ucai.fulicenter.model.utils.SpaceItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewGoodsFragment extends Fragment {
    GridLayoutManager gm;
    NewGoodsAdapter mAdapter;
    ArrayList<NewGoodsBean> mList;
    IModelNewGoods model;

    int mPageId = 1;

    @BindView(R.id.tvRefresh)
    TextView mtvRefresh;
    @BindView(R.id.rv)
    RecyclerView mrv;
    @BindView(R.id.srl)
    SwipeRefreshLayout msrl;


    public NewGoodsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_new_goods, container, false);
        ButterKnife.bind(this, layout);
        model = new ModelNewGoods();
        initData();
        initView();
        setListener();
        return layout;
    }

    private void setListener() {
        pullDownListener();
        mrv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int position = gm.findLastVisibleItemPosition();
                Log.i("main", "position=" + position + "  count=" + (mAdapter.getItemCount() - 1));

                mAdapter.setDragging(newState == RecyclerView.SCROLL_STATE_DRAGGING);
                if (!mAdapter.isDragging() && mAdapter.isMore() && position == mAdapter.getItemCount() - 1) {
                    mPageId++;
                    downloadGoodsList(I.ACTION_PULL_UP, mPageId);
                }
            }
        });
    }

    private void pullDownListener() {
        msrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mtvRefresh.setVisibility(View.VISIBLE);
                msrl.setRefreshing(true);
                mPageId = 1;
                downloadGoodsList(I.ACTION_PULL_DOWN, mPageId);
            }
        });
    }

    private void initData() {
        downloadGoodsList(I.ACTION_DOWNLOAD, mPageId);
    }

    private void downloadGoodsList(final int action, int pageId) {
        int catId=getActivity().getIntent().getIntExtra(I.NewAndBoutiqueGoods.CAT_ID,I.ID_DEFAULT);
        model.downData(getActivity(), catId, pageId, new OnCompletionListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                Log.e("main", Arrays.toString(result));
                ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                mAdapter.setMore(list.size() > 0 && list != null);
                if (!mAdapter.isMore()) {
                    if (action == I.ACTION_PULL_UP) {
                        mAdapter.setFooter("没有更多商品了");
                    }
                    return;
                }
                mAdapter.setFooter("正在加载数据");
                switch (action) {
                    case I.ACTION_DOWNLOAD:
                        mAdapter.initData(list);
                        break;
                    case I.ACTION_PULL_DOWN:
                        msrl.setRefreshing(false);
                        mtvRefresh.setVisibility(View.GONE);
                        mAdapter.initData(list);
                        break;
                    case I.ACTION_PULL_UP:
                        mAdapter.addData(list);
                        break;
                }
            }

            @Override
            public void onError(String error) {
                Log.e("main", "error" + error);

            }
        });
    }

    private void initView() {
        mList = new ArrayList<>();
        msrl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_yellow),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red)
        );
        gm = new GridLayoutManager(getContext(), I.COLUM_NUM);
        mrv.addItemDecoration(new SpaceItemDecoration(30));
        mrv.setLayoutManager(gm);
        mrv.setHasFixedSize(true);
        mAdapter = new NewGoodsAdapter(getContext(), mList);
        mrv.setAdapter(mAdapter);
    }

}
