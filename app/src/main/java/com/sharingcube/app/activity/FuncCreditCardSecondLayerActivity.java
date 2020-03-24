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

import static com.sharingcube.app.utils.ConstantValue.KEY_CLICK_TYPE;
import static com.sharingcube.app.utils.ConstantValue.REFUND;
import static com.sharingcube.app.utils.ConstantValue.SALE;
import static com.sharingcube.app.utils.ConstantValue.VOID;

/**
 * 信用卡交易 - 第二層
 * 銷售，退貨，取消
 */

public class FuncCreditCardSecondLayerActivity extends BaseActivity implements OnChooseFuncListener {

    @BindView(R.id.view_func_matrix)
    GridView mGvFuncMatrix;
    @BindView(R.id.iv_home)
    ImageView mBtnHome;

    String strType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_func_credit_card_second_layer);
        ButterKnife.bind(this);

        if(getIntent() != null)
        {
            strType = getIntent().getStringExtra(KEY_CLICK_TYPE);
        }
        initView();
    }

    private void initView() {
        CustomGridViewAdapter funcListAdapter = new CustomGridViewAdapter(this,
                Arrays.asList(ConstantValue.CreditCardTxType.values()), ConstantValue.GRID_TYPE_CREDIT_CARD_TX);
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
        Intent intent = new Intent(this, SaleInputAmtActivity.class);
        Bundle bundle = new Bundle();
        switch (transType)
        {
            case SALE:
                //銷售交易
                bundle.putString(KEY_CLICK_TYPE, strType);
                bundle.putString(ConstantValue.KEY_CLICK_TRANS_TYPE, ConstantValue.KEY_CLICK_SALE);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case VOID:
                //取消交易
                bundle.putString(KEY_CLICK_TYPE, strType);
                bundle.putString(ConstantValue.KEY_CLICK_TRANS_TYPE, ConstantValue.KEY_CLICK_VOID);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case REFUND:
                //退貨交易
                bundle.putString(KEY_CLICK_TYPE, strType);
                bundle.putString(ConstantValue.KEY_CLICK_TRANS_TYPE, ConstantValue.KEY_CLICK_REFUND);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
