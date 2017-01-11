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

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.bean.NewGoodsBean;
import cn.ucai.fulicenter.controller.adapter.NewGoodsAdapter;
import cn.ucai.fulicenter.model.net.IModelNewGoods;
import cn.ucai.fulicenter.model.net.ModelNewGoods;
import cn.ucai.fulicenter.model.net.OnCompletionListener;
import cn.ucai.fulicenter.model.utils.ConvertUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewGoodsFragment extends Fragment {
    GridLayoutManager gm;
    NewGoodsAdapter mAdapter;
    ArrayList<NewGoodsBean> mList;
    IModelNewGoods model;
    RecyclerView rv;
    TextView tv;
    SwipeRefreshLayout srl;

    int pageId = 1;


//    @butterknife.BindView(R.id.tvRefresh)
//    android.widget.TextView tvRefresh;
//    @butterknife.BindView(R.id.rv)
//    android.support.v7.widget.RecyclerView rv;
//    @butterknife.BindView(R.id.srl)
//    android.support.v4.widget.SwipeRefreshLayout srl;

    public NewGoodsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_new_goods, container, false);
        model = new ModelNewGoods();
        initData();
        initView(layout);
        return layout;
    }

    private void initData() {
        model.downData(getActivity(), I.CAT_ID, pageId, new OnCompletionListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                Log.e("main", Arrays.toString(result));
                ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                mAdapter.initData(list);
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void initView(View layout) {
        rv= (RecyclerView) layout.findViewById(R.id.rv);
        srl= (SwipeRefreshLayout) layout.findViewById(R.id.srl);
        tv= (TextView) layout.findViewById(R.id.tvRefresh);
        mList = new ArrayList<>();
        srl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_yellow),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red)
        );
        gm = new GridLayoutManager(getContext(), I.COLUM_NUM);
        //LinearLayoutManager lm=new LinearLayoutManager(getContext());
        rv.setLayoutManager(gm);
        rv.setHasFixedSize(true);
        mAdapter = new NewGoodsAdapter(getContext(), mList);
        rv.setAdapter(mAdapter);
    }

}
