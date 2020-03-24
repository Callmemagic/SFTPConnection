package com.sharingcube.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.sharingcube.app.R;
import com.sharingcube.app.adapter.MainGridViewAdapter;
import com.sharingcube.app.base.BaseActivity;
import com.sharingcube.app.utils.ConstantValue;
import com.sharingcube.app.utils.CustomDialogUtility;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sharingcube.app.utils.ConstantValue.IS_TRAINING_MODE;

public class MainActivity extends BaseActivity implements MainGridViewAdapter.OnChooseFuncListener {
    public String TAG = getClass().getSimpleName();
    private Context mContext;

    @BindView(R.id.view_main_matrix)
    GridView mGvMatrix;
    @BindView(R.id.iv_root)
    ImageView mIvRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); //六格
        ButterKnife.bind(this);
        mContext = this;
        initView();
    }

    private void initView() {
        MainGridViewAdapter mainGridViewAdapter = new MainGridViewAdapter(mContext, Arrays.asList(ConstantValue.MenuIcons.values()));
        mGvMatrix.setAdapter(mainGridViewAdapter);
        mGvMatrix.setGravity(View.TEXT_ALIGNMENT_CENTER);
//        mGvMatrix.setPadding(10, 0, 10, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //檢查網路
        checkInternet();
    }

    private void checkInternet() {
        if(isNetworkAvailable() == false && IS_TRAINING_MODE == false)
        {
            CustomDialogUtility.showDialog(mContext, getString(R.string.error_network_unavailable), getString(R.string.error_no_internet), new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    checkInternet();
                }
            });
        }
    }

    @OnClick(R.id.iv_root)
    void onClickFuncList()
    {
        //功能選單
        Intent intent = new Intent(MainActivity.this, FuncListActivity.class);
        startActivity(intent);
    }

    @Override
    public void OnChooseFunc(ConstantValue.MenuIcons menuIcon) {
        switch (menuIcon)
        {
            case CREDIT_CARD:
                //信用卡
                GoToSaleInputAmt(ConstantValue.KEY_CLICK_CARD);
                break;
            case E_PASS:
                //電子票證
                //TODO:
                break;
            case POINTS:
                //點數/兌換
                //TODO:
                break;
            case MOBILE_PAYMENT:
                //行動支付
                GoToSaleInputAmt(ConstantValue.KEY_CLICK_SCAN_CODE);
                break;
            case LET_E_GO:
                //TODO:
                //雷e購
                break;
            case SHARINGCUBE_POS:
                //雷門POS
                //TODO:
                break;
            default:
                Log.d(TAG, "未支援的功能");
                break;
        }
    }

    private void GoToSaleInputAmt(String strType) {
        Intent intent = new Intent(this, SaleInputAmtActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(ConstantValue.KEY_CLICK_TYPE, strType);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
