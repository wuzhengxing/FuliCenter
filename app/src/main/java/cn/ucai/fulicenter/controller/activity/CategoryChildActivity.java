package cn.ucai.fulicenter.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.fragment.NewGoodsFragment;
import cn.ucai.fulicenter.view.MFGT;

public class CategoryChildActivity extends AppCompatActivity {
    NewGoodsFragment mFragment;
    boolean priceArc;
    boolean addTimeArc;

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.btn_sort_price)
    Button btnSortPrice;
    @BindView(R.id.btn_sort_addtime)
    Button btnSortAddtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_child);
        ButterKnife.bind(this);
        mFragment=new NewGoodsFragment();
       getSupportFragmentManager().beginTransaction()
                .add(R.id.layout_category_child_fragment, mFragment)
                .commit();
    }


    @OnClick({R.id.ivBack, R.id.btn_sort_price, R.id.btn_sort_addtime})
    public void onClick(View view) {
        int sort=0;
        switch (view.getId()) {
            case R.id.ivBack:
                MFGT.finish(this);
                break;
            case R.id.btn_sort_price:
                if(priceArc){
                    sort= I.SORT_BY_PRICE_ASC;
                }else {
                    sort=I.SORT_BY_PRICE_DESC;
                }
                priceArc=!priceArc;
                break;
            case R.id.btn_sort_addtime:
                if(addTimeArc){
                    sort= I.SORT_BY_ADDTIME_ASC;
                }else {
                    sort=I.SORT_BY_ADDTIME_DESC;
                }
                addTimeArc=!addTimeArc;
                break;
        }
        mFragment.sort(sort);
    }
}
