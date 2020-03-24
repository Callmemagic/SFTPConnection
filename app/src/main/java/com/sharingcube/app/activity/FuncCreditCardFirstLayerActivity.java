package com.sharingcube.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.sharingcube.app.Listener.OnChooseFuncListener;
import com.sharingcube.app.R;
import com.sharingcube.app.adapter.CustomGridViewAdapter;
import com.sharingcube.app.base.BaseActivity;
import com.sharingcube.app.utils.ConstantValue;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sharingcube.app.utils.ConstantValue.CREDIT_CARD;
import static com.sharingcube.app.utils.ConstantValue.IPP;
import static com.sharingcube.app.utils.ConstantValue.REDEEM_DISCOUNT;

/**
 * 信用卡交易 - 第一層
 * 信用卡交易,紅利扣抵,分期付款
 */
public class FuncCreditCardFirstLayerActivity extends BaseActivity implements OnChooseFuncListener {

    @BindView(R.id.view_func_matrix)
    GridView mGvFuncMatrix;
    @BindView(R.id.iv_home)
    ImageView mBtnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_func_credit_card_first_layer);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        CustomGridViewAdapter funcListAdapter = new CustomGridViewAdapter(this,
                Arrays.asList(ConstantValue.CreditCardType.values()), ConstantValue.GRID_TYPE_CREDIT_CARD);
        mGvFuncMatrix.setAdapter(funcListAdapter);
        mGvFuncMatrix.setGravity(View.TEXT_ALIGNMENT_CENTER);
        mGvFuncMatrix.setPadding(10, 0, 10,0);

    }

    @OnClick(R.id.iv_home)
    void onClickHome()
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void OnChooseFunc(String transType) {
        Intent intent;
        Bundle bundle = new Bundle();
        intent = new Intent(this, FuncCreditCardSecondLayerActivity.class);

        switch (transType)
        {
            case CREDIT_CARD:
                //信用卡
                bundle.putString(ConstantValue.KEY_CLICK_TYPE, ConstantValue.KEY_CLICK_CREDIT_CARD);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case REDEEM_DISCOUNT:
                //紅利
                bundle.putString(ConstantValue.KEY_CLICK_TYPE, ConstantValue.KEY_CLICK_REDEEM);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case IPP:
                //分期
                bundle.putString(ConstantValue.KEY_CLICK_TYPE, ConstantValue.KEY_CLICK_IPP);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }
}
