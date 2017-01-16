package cn.ucai.fulicenter.controller.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.controller.fragment.BoutiqueFragment;
import cn.ucai.fulicenter.controller.fragment.CategoryFragment;
import cn.ucai.fulicenter.controller.fragment.NewGoodsFragment;
import cn.ucai.fulicenter.view.MFGT;

public class MainActivity extends AppCompatActivity {
    int index, currentIndex;
    FragmentTransaction ft;

    NewGoodsFragment mNewGoodsFragment;
    BoutiqueFragment mBoutiqueFragment;
    CategoryFragment mCategoryFragment;
    Fragment[] fragments = new Fragment[5];

    RadioButton[] rbs;
    @BindView(R.id.layout_new_good)
    RadioButton layoutNewGood;
    @BindView(R.id.layout_boutique)
    RadioButton layoutBoutique;
    @BindView(R.id.layout_category)
    RadioButton layoutCategory;
    @BindView(R.id.layout_cart)
    RadioButton layoutCart;
    @BindView(R.id.layout_personal_center)
    RadioButton layoutPersonalCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();

        initFragment();
    }

    private void initFragment() {
        ft = getSupportFragmentManager().beginTransaction();
        mNewGoodsFragment = new NewGoodsFragment();
        mBoutiqueFragment = new BoutiqueFragment();
        mCategoryFragment = new CategoryFragment();
        fragments[0] = mNewGoodsFragment;
        fragments[1] = mBoutiqueFragment;
        fragments[2] = mCategoryFragment;
        ft.add(R.id.layout_content, mNewGoodsFragment)
                .add(R.id.layout_content, mBoutiqueFragment)
                .add(R.id.layout_content, mCategoryFragment)
                .show(mNewGoodsFragment)
                .hide(mBoutiqueFragment)
                .hide(mCategoryFragment)
                .commit();
    }

    private void initView() {
        rbs = new RadioButton[5];
        rbs[0] = layoutNewGood;
        rbs[1] = layoutBoutique;
        rbs[2] = layoutCategory;
        rbs[3] = layoutCart;
        rbs[4] = layoutPersonalCenter;
    }

    public void onCheckedChange(View view) {
        switch (view.getId()) {
            case R.id.layout_new_good:
                index = 0;
                break;
            case R.id.layout_boutique:
                index = 1;
                break;
            case R.id.layout_category:
                index = 2;
                break;
            case R.id.layout_cart:
                index = 3;
                break;
            case R.id.layout_personal_center:
                if (FuLiCenterApplication.getUser() == null) {
                    MFGT.gotoLogin(this);
                } else {
                    index = 4;
                }
                break;
        }
        setFragment();
        if (index != currentIndex) {
            setRadioStatus();
        }
    }

    public void setFragment() {
        getSupportFragmentManager().beginTransaction().show(fragments[index]).hide(fragments[currentIndex]).commit();
    }

    public void setRadioStatus() {
        for (int i = 0; i < rbs.length; i++) {
            if (i != index) {
                rbs[i].setChecked(false);
            } else {
                rbs[i].setChecked(true);
            }
        }
        currentIndex = index;
    }
}
