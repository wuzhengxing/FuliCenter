package cn.ucai.fulicenter.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
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
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.bean.BoutiqueBean;
import cn.ucai.fulicenter.controller.adapter.BoutiqueAdapter;
import cn.ucai.fulicenter.model.net.IModelBoutique;
import cn.ucai.fulicenter.model.net.ModelBoutique;
import cn.ucai.fulicenter.model.net.OnCompletionListener;
import cn.ucai.fulicenter.model.utils.ConvertUtils;
import cn.ucai.fulicenter.model.utils.SpaceItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoutiqueFragment extends Fragment {

    LinearLayoutManager mMnager;
    BoutiqueAdapter mAdapter;
    ArrayList<BoutiqueBean> mList;
    IModelBoutique model;


    @BindView(R.id.tvRefresh)
    TextView mtvRefresh;
    @BindView(R.id.rv)
    RecyclerView mrv;
    @BindView(R.id.srl)
    SwipeRefreshLayout msrl;
    @BindView(R.id.tvMore)
    TextView tvMore;


    public BoutiqueFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_boutique, container, false);
        ButterKnife.bind(this, layout);
        model = new ModelBoutique();
        initView();
        initData();
        setListener();
        return layout;
    }

    private void setListener() {
        pullDownListener();
    }


    private void pullDownListener() {
        msrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mtvRefresh.setVisibility(View.VISIBLE);
                msrl.setRefreshing(true);
                downloadGoodsList(I.ACTION_PULL_DOWN);
            }
        });
    }

    private void initData() {
        downloadGoodsList(I.ACTION_DOWNLOAD);
    }

    private void downloadGoodsList(final int action) {
        model.downData(getActivity(), new OnCompletionListener<BoutiqueBean[]>() {
            @Override
            public void onSuccess(BoutiqueBean[] result) {
                Log.e("main", Arrays.toString(result));
                msrl.setRefreshing(false);
                mtvRefresh.setVisibility(View.GONE);
                mrv.setVisibility(View.VISIBLE);
                tvMore.setVisibility(View.GONE);
                ArrayList<BoutiqueBean> list = ConvertUtils.array2List(result);
                if (result != null && result.length > 0) {
                    if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                        mAdapter.initData(list);
                    } else {
                        mAdapter.addData(list);
                    }

                } else {
                    mrv.setVisibility(View.GONE);
                    tvMore.setVisibility(View.VISIBLE);
                }
                mAdapter.setFooter("没有更多数据");
            }

            @Override
            public void onError(String error) {
                msrl.setRefreshing(false);
                mtvRefresh.setVisibility(View.GONE);
                mrv.setVisibility(View.GONE);
                tvMore.setVisibility(View.VISIBLE);
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
        mMnager = new LinearLayoutManager(getContext());
        mrv.addItemDecoration(new SpaceItemDecoration(30));
        mrv.setLayoutManager(mMnager);
        mrv.setHasFixedSize(true);
        mAdapter = new BoutiqueAdapter(getContext(), mList);
        mrv.setAdapter(mAdapter);
    }

    @OnClick(R.id.tvMore)
    public void onClick() {
        downloadGoodsList(I.ACTION_DOWNLOAD);
    }
}
