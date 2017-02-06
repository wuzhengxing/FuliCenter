package cn.ucai.fulicenter.controller.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.bean.CollectBean;
import cn.ucai.fulicenter.bean.User;
import cn.ucai.fulicenter.controller.adapter.CollectAdapter;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompletionListener;
import cn.ucai.fulicenter.model.utils.ConvertUtils;
import cn.ucai.fulicenter.model.utils.SpaceItemDecoration;
import cn.ucai.fulicenter.view.DisplayUtils;

public class CollectsActivity extends AppCompatActivity {
    User user;
    IModelUser model;
    int pageId = 1;
    GridLayoutManager gm;
    CollectAdapter mAdapter;
    ArrayList<CollectBean> mList;
   UpdateReceiver receiver;

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvRefresh)
    TextView tvRefresh;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collects);
        ButterKnife.bind(this);
        DisplayUtils.initBackWithTitle(this, "个人收藏");
        receiver=new UpdateReceiver();
        user = FuLiCenterApplication.getUser();
        if (user == null) {

        } else {
            initData(I.ACTION_DOWNLOAD);
            initView();
            setListener();
            setReceiver();
        }
    }

    private void setReceiver() {
        IntentFilter filter=new IntentFilter(I.BROADCAST_UPDATE_COLLECT);
        registerReceiver(receiver,filter);
    }


    private void initData(final int action) {
        model = new ModelUser();
        model.getCollect(this, user.getMuserName(), pageId, I.PAGE_SIZE_DEFAULT, new OnCompletionListener<CollectBean[]>() {
            @Override
            public void onSuccess(CollectBean[] result) {
                Log.e("main", Arrays.toString(result));
                ArrayList<CollectBean> list = ConvertUtils.array2List(result);
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
                        srl.setRefreshing(false);
                        tvRefresh.setVisibility(View.GONE);
                        mAdapter.initData(list);
                        break;
                    case I.ACTION_PULL_UP:
                        mAdapter.addData(list);
                        break;
                }
            }

            @Override
            public void onError(String error) {
                Log.i("main", "CollectsActivity.initData()-error:" + error);
            }
        });
    }

    private void initView() {
        mList = new ArrayList<>();
        srl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_yellow),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red)
        );
        gm = new GridLayoutManager(this, I.COLUM_NUM);
        rv.addItemDecoration(new SpaceItemDecoration(30));
        rv.setLayoutManager(gm);
        rv.setHasFixedSize(true);
        mAdapter = new CollectAdapter(this, mList);
        rv.setAdapter(mAdapter);
    }

    private void setListener() {
        pullDownListener();
        pullUpListener();
    }

    private void pullUpListener() {
        rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int position = gm.findLastVisibleItemPosition();
                Log.i("main", "position=" + position + "  count=" + (mAdapter.getItemCount() - 1));

                mAdapter.setDragging(newState == RecyclerView.SCROLL_STATE_DRAGGING);
                if (!mAdapter.isDragging() && mAdapter.isMore() && position == mAdapter.getItemCount() - 1) {
                    pageId++;
                    initData(I.ACTION_PULL_UP);
                }
            }
        });
    }

    private void pullDownListener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tvRefresh.setVisibility(View.VISIBLE);
                srl.setRefreshing(true);
                pageId = 1;
                initData(I.ACTION_PULL_DOWN);
            }
        });
    }

    class UpdateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int goodsId = intent.getIntExtra(I.Collect.GOODS_ID, 0);
            mAdapter.deleteItem(goodsId);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
      unregisterReceiver(receiver);
    }
}
