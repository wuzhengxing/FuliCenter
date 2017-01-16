package cn.ucai.fulicenter.controller.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.bean.CategoryChildBean;
import cn.ucai.fulicenter.controller.fragment.NewGoodsFragment;
import cn.ucai.fulicenter.view.CatFliterButton;
import cn.ucai.fulicenter.view.MFGT;

public class CategoryChildActivity extends AppCompatActivity {
    NewGoodsFragment mFragment;
    boolean priceArc;
    boolean addTimeArc;

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.btn_sort_price)
    Button btnSortPrice;
    @BindView(R.id.btn_sort_addtime)
    Button btnSortAddtime;
    @BindView(R.id.sort_price)
    ImageView sortPrice;
    @BindView(R.id.sort_addTime)
    ImageView sortAddTime;
    @BindView(R.id.cat_fliter_button)
    CatFliterButton catFliterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_child);
        ButterKnife.bind(this);
        mFragment = new NewGoodsFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.layout_category_child_fragment, mFragment)
                .commit();
        String groupName = getIntent().getStringExtra(I.CategoryGroup.NAME);
        ArrayList<CategoryChildBean> list = (ArrayList<CategoryChildBean>) getIntent().getSerializableExtra(I.CategoryChild.DATA);
        catFliterButton.initCatFliterButton(groupName, list);
    }


    @OnClick({R.id.ivBack, R.id.btn_sort_price, R.id.btn_sort_addtime})
    public void onClick(View view) {
        int sort = 0;
        Drawable right;
        switch (view.getId()) {
            case R.id.ivBack:
                MFGT.finish(this);
                break;
            case R.id.btn_sort_price:
                if (priceArc) {
                    sort = I.SORT_BY_PRICE_ASC;
                    right = getResources().getDrawable(R.drawable.arrow_order_up);
                } else {
                    sort = I.SORT_BY_PRICE_DESC;
                    right = getResources().getDrawable(R.drawable.arrow_order_down);
                }
                right.setBounds(0, 0, right.getIntrinsicWidth(), right.getIntrinsicHeight());
                btnSortPrice.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, right, null);
                sortPrice.setVisibility(View.GONE);
                priceArc = !priceArc;
                break;
            case R.id.btn_sort_addtime:
                if (addTimeArc) {
                    sort = I.SORT_BY_ADDTIME_ASC;
                    right = getResources().getDrawable(R.drawable.arrow_order_up);
                } else {
                    sort = I.SORT_BY_ADDTIME_DESC;
                    right = getResources().getDrawable(R.drawable.arrow_order_down);
                }
                right.setBounds(0, 0, right.getIntrinsicWidth(), right.getIntrinsicHeight());
                btnSortAddtime.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, right, null);
                sortAddTime.setVisibility(View.GONE);
                addTimeArc = !addTimeArc;
                break;
        }
        mFragment.sort(sort);
    }
}
