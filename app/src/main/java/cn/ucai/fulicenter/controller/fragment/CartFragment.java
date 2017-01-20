package cn.ucai.fulicenter.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
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
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.bean.CartBean;
import cn.ucai.fulicenter.bean.User;
import cn.ucai.fulicenter.controller.adapter.CartAdapter;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompletionListener;
import cn.ucai.fulicenter.model.utils.ConvertUtils;
import cn.ucai.fulicenter.model.utils.SpaceItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {
    LinearLayoutManager mMnager;
    CartAdapter mAdapter;
    ArrayList<CartBean> mList;
    IModelUser model;
    User user;

    int mPageId = 1;

    @BindView(R.id.tvRefresh)
    TextView mtvRefresh;
    @BindView(R.id.rv)
    RecyclerView mrv;
    @BindView(R.id.srl)
    SwipeRefreshLayout msrl;
    @BindView(R.id.tvMore)
    TextView tvMore;


    public CartFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, layout);
        model = new ModelUser();
        user = FuLiCenterApplication.getUser();
        initData();
        initView();
        return layout;
    }


    private void initData() {
        downloadGoodsList(I.ACTION_DOWNLOAD);


    }

    private void downloadGoodsList(final int action) {
        model.getCart(getContext(), user.getMuserName(), new OnCompletionListener<CartBean[]>() {
            @Override
            public void onSuccess(CartBean[] result) {
                Log.e("main", Arrays.toString(result));
                msrl.setRefreshing(false);
                mtvRefresh.setVisibility(View.GONE);
                mrv.setVisibility(View.VISIBLE);
                tvMore.setVisibility(View.GONE);
                ArrayList<CartBean> list = ConvertUtils.array2List(result);
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
            }

            @Override
            public void onError(String error) {

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
        mAdapter = new CartAdapter(getContext(), mList);
        mrv.setAdapter(mAdapter);
    }

}
