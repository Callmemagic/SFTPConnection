package com.sharingcube.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.sharingcube.app.Listener.OnChooseFuncListener;
import com.sharingcube.app.R;
import com.sharingcube.app.adapter.CustomGridViewAdapter;
import com.sharingcube.app.base.BaseActivity;
import com.sharingcube.app.utils.ConstantValue;
import com.sharingcube.app.utils.CustomDialogUtility;
import com.sharingcube.app.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sharingcube.app.utils.ConstantValue.ABOUT_E_PAY;
import static com.sharingcube.app.utils.ConstantValue.CREDIT_CARD;
import static com.sharingcube.app.utils.ConstantValue.CUP;
import static com.sharingcube.app.utils.ConstantValue.E_PASS;
import static com.sharingcube.app.utils.ConstantValue.REPRINT;
import static com.sharingcube.app.utils.ConstantValue.MOBILE_PAYMENT;
import static com.sharingcube.app.utils.ConstantValue.MORE_FUNC;
import static com.sharingcube.app.utils.ConstantValue.SETTING;
import static com.sharingcube.app.utils.ConstantValue.SETTLE;
import static com.sharingcube.app.utils.ConstantValue.STATUS_CHECKOUT;
import static com.sharingcube.app.utils.ConstantValue.STATUS_REPRINT;
import static com.sharingcube.app.utils.TransferParamsKey.POS_REQUEST;
import static com.sharingcube.app.utils.TransferParamsKey.REQUEST_CODE_EDC;
import static com.sharingcube.app.utils.TransferParamsKey.Source_Class;
import static com.sharingcube.app.utils.TransferParamsKey.Source_Package;
import static com.sharingcube.app.utils.TransferParamsKey.Trans_Type;

/**
 * 第一層功能列表
 */

public class FuncListActivity extends BaseActivity implements OnChooseFuncListener {
    public String TAG = getClass().getSimpleName();
    private Context mContext;

    @BindView(R.id.view_func_matrix)
    GridView mGvFuncMatrix;
    @BindView(R.id.iv_home)
    ImageView mBtnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_func_list);
        ButterKnife.bind(this);
        mContext = this;
        initView();
    }

    private void initView() {
        CustomGridViewAdapter funcListAdapter = new CustomGridViewAdapter(this,
                Arrays.asList(ConstantValue.TransType.values()), ConstantValue.GRID_TYPE_TRANS_TYPE);
        mGvFuncMatrix.setAdapter(funcListAdapter);
        mGvFuncMatrix.setGravity(View.TEXT_ALIGNMENT_CENTER);
        mGvFuncMatrix.setPadding(10, 0, 10,0);
    }

    @OnClick(R.id.iv_home)
    void onClickHome()
    {
        Intent intent = new Intent(FuncListActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void GoToSettle() {
        JSONObject objParams = new JSONObject();
        try {
            objParams.put(Trans_Type, STATUS_CHECKOUT);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            LogUtil.d("Can't find EDC App!");
        }

        Intent intent = new Intent();
        intent.setClassName("com.cybersoft.a920", "com.cybersoft.a920.activity.MainActivity");
        intent.putExtra(POS_REQUEST, objParams.toString());
        startActivityForResult(intent, REQUEST_CODE_EDC);
        LogUtil.d(TAG, "json to edc : " + objParams.toString());
    }

    private void GoToReprint() {
        JSONObject objParams = new JSONObject();
        try {
            objParams.put(Trans_Type, STATUS_REPRINT);
            objParams.put(Source_Package, getPackageName());
            objParams.put(Source_Class, getClass().getName());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            LogUtil.d("Can't find EDC App!");
        }

        Intent intent = new Intent();
        intent.setClassName("com.cybersoft.a920", "com.cybersoft.a920.activity.MainActivity");
        intent.putExtra(POS_REQUEST, objParams.toString());
        startActivityForResult(intent, REQUEST_CODE_EDC);
        LogUtil.d(TAG, "json to edc : " + objParams.toString());
    }

    @Override
    public void OnChooseFunc(String transType) {
        Intent intent;
        switch (transType)
        {
            //信用卡
            case CREDIT_CARD:
                intent = new Intent(this, FuncCreditCardFirstLayerActivity.class);
                startActivity(intent);
                break;
            //電子票證
            case E_PASS:
                break;
            //行動支付
            case MOBILE_PAYMENT:
                intent = new Intent(this, FuncMobilePaymentLayerActivity.class);
                startActivity(intent);
                break;
            //銀聯卡
            case CUP:
                break;
            //重印簽單
            case REPRINT:
                CustomDialogUtility.showDialogWithOKandCancel(mContext, getString(R.string.hint), getString(R.string.go_to_edc_reprint), new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if(which == DialogAction.POSITIVE)
                        {
                            GoToReprint();
                        }
                    }
                });
                break;
            //更多功能
            case MORE_FUNC:
                break;
            //結帳作業
            case SETTLE:
                CustomDialogUtility.showDialogWithOKandCancel(mContext, getString(R.string.hint), getString(R.string.go_to_edc_settle), new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if(which == DialogAction.POSITIVE)
                        {
                            GoToSettle();
                        }
                    }
                });
                break;
            //系統設定
            case SETTING:
                break;
            //關於E-pay
            case ABOUT_E_PAY:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_EDC:
                //結帳回來的
                BackToMain();
                break;
        }
    }
}
