package cn.ucai.fulicenter.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.controller.fragment.NewGoodsFragment;

public class CategoryChildActivity extends AppCompatActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.layout_content)
    FrameLayout layoutContent;
    @BindView(R.id.activity_boutique)
    LinearLayout activityBoutique;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_child);
        ButterKnife.bind(this);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.layout_content, new NewGoodsFragment())
                .commit();
        String name = getIntent().getStringExtra("name");
        tvTitle.setText(name);
    }

    @OnClick(R.id.ivBack)
    public void onClick() {
        finish();
    }
}
