package com.sharingcube.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.sharingcube.app.Listener.OnChooseFuncListener;
import com.sharingcube.app.R;
import com.sharingcube.app.adapter.CustomGridViewAdapter;
import com.sharingcube.app.base.BaseActivity;
import com.sharingcube.app.model.SCPExchangeData;
import com.sharingcube.app.utils.ConstantValue;
import com.sharingcube.app.utils.LogUtil;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sharingcube.app.utils.ConstantValue.IS_TRAINING_MODE;
import static com.sharingcube.app.utils.ConstantValue.REFUND;
import static com.sharingcube.app.utils.ConstantValue.REPRINT;
import static com.sharingcube.app.utils.ConstantValue.STATUS_REPRINT;
import static com.sharingcube.app.utils.TransferParamsKey.POS_REQUEST;
import static com.sharingcube.app.utils.TransferParamsKey.REQUEST_CODE_SCP;

/**
 * 行動支付 - 第一層
 * 只有掃碼退貨
 */
public class FuncMobilePaymentLayerActivity extends BaseActivity implements OnChooseFuncListener {
    private Gson gson = new Gson();

    @BindView(R.id.view_func_matrix)
    GridView mGvFuncMatrix;
    @BindView(R.id.iv_home)
    ImageView mBtnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_func_mobile_payment_layer);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        CustomGridViewAdapter funcListAdapter = new CustomGridViewAdapter(this,
                Arrays.asList(ConstantValue.MobilePaymentType.values()), ConstantValue.GRID_TYPE_MOBILE_PAYMENT);
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
        switch (transType)
        {
            case REFUND:
                //掃碼退貨
                intent = new Intent(this, SaleInputAmtActivity.class);
                bundle.putString(ConstantValue.KEY_CLICK_TYPE, ConstantValue.KEY_CLICK_SCAN_REFUND);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case REPRINT:
                //重印簽單(掃碼)
                GoToScanReprint();
                break;
        }
    }


    public void GoToScanReprint()
    {
        //掃碼重印簽單
        Intent intent = new Intent();
        intent.setClassName("com.cybersoft.a920.scp", "com.cybersoft.a920.scp.activity.MainActivity");

        SCPExchangeData data = new SCPExchangeData();
        data.setTransType(STATUS_REPRINT);
        data.setTrainingMode(IS_TRAINING_MODE);

        String request = gson.toJson(data);
        LogUtil.d("onClickSCP: send request =" + request);
        try {
            intent.putExtra(POS_REQUEST, request);
            startActivityForResult(intent, REQUEST_CODE_SCP);
        } catch (Exception e) {
            LogUtil.d("Can't find SCP App!");
        }
    }
}
