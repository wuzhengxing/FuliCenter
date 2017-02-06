package cn.ucai.fulicenter.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;

public class OrderActivity extends AppCompatActivity {

    @BindView(R.id.ed_user_name)
    EditText edUserName;
    @BindView(R.id.ed_phone_number)
    EditText edPhoneNumber;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.ed_address)
    EditText edAddress;
    @BindView(R.id.tv_cart_price)
    TextView tvCartPrice;
    int payPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        payPrice=getIntent().getIntExtra(I.Cart.PAY_PRICE,0);
        setView();
    }

    private void setView() {
        tvCartPrice.setText("合计：￥ "+payPrice);
    }
}
