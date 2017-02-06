package cn.ucai.fulicenter.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
        payPrice = getIntent().getIntExtra(I.Cart.PAY_PRICE, 0);
        setView();
    }

    private void setView() {
        tvCartPrice.setText("合计：￥ " + payPrice);
    }

    @OnClick(R.id.tv_cart_buy)
    public void checkOrder() {
        String receiverName=edUserName.getText().toString();
        if(TextUtils.isEmpty(receiverName)){
            edUserName.setError("收货人姓名不能为空");
            edUserName.requestFocus();
            return;
        }
        String mobel=edPhoneNumber.getText().toString();
        if(TextUtils.isEmpty(mobel)){
            edPhoneNumber.setError("手机号码不能为空");
            edPhoneNumber.requestFocus();
            return;
        }
        if (!mobel.matches("[\\d]{11}")) {
            edPhoneNumber.setError("手机号码格式错误");
            edPhoneNumber.requestFocus();
            return;
        }
        String area=spinner.getSelectedItem().toString();
        if(TextUtils.isEmpty(area)){
            Toast.makeText(this, "收货地区不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String address=edAddress.getText().toString();
        if(TextUtils.isEmpty(address)){
            edAddress.setError("街道地址不能为空");
            edAddress.requestFocus();
            return;
        }
    }
}
