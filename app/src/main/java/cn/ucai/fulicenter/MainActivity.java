package cn.ucai.fulicenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {
    RadioButton rbNewGood, rbBoutique, rbCart, rbCategory, rbPersonal;
    int index,currentIndex;
    RadioButton[] rbs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        rbNewGood= (RadioButton) findViewById(R.id.layout_new_good);
        rbBoutique= (RadioButton) findViewById(R.id.layout_boutique);
        rbCart= (RadioButton) findViewById(R.id.layout_cart);
        rbCategory= (RadioButton) findViewById(R.id.layout_category);
        rbPersonal= (RadioButton) findViewById(R.id.layout_personal_center);
        rbs=new RadioButton[5];
        rbs[0]=rbNewGood;
        rbs[1]=rbBoutique;
        rbs[2]=rbCategory;
        rbs[3]=rbCart;
        rbs[4]=rbPersonal;
    }

    public void onCheckedChange(View view) {
        switch (view.getId()){
            case R.id.layout_new_good:
                index=0;
                break;
            case R.id.layout_boutique:
                index=1;
                break;
            case R.id.layout_category:
                index=2;
                break;
            case R.id.layout_cart:
                index=3;
                break;
            case R.id.layout_personal_center:
                index=4;
                break;
        }
        if(index!=currentIndex){
            setRadioStatus();
        }
    }
    public void setRadioStatus(){
        for(int i=0;i<rbs.length;i++){
            if(i!=index){
                rbs[i].setChecked(false);
            }else {
                rbs[i].setChecked(true);
            }
        }
        currentIndex=index;
    }
}
