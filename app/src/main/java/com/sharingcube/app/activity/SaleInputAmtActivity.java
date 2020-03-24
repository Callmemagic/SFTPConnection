package com.sharingcube.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.sharingcube.app.Listener.UploadFinishListener;
import com.sharingcube.app.R;
import com.sharingcube.app.base.BaseActivity;
import com.sharingcube.app.model.SCPExchangeData;
import com.sharingcube.app.utils.ConstantValue;
import com.sharingcube.app.utils.CustomDialogUtility;
import com.sharingcube.app.utils.LogUtil;
import com.sharingcube.app.utils.ProgressDialogUtil;
import com.sharingcube.app.utils.ResourceUtil;
import com.sharingcube.app.utils.WriteFileUtil;
import com.sharingcube.app.view.ChooseCardTypeDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sharingcube.app.utils.ConstantValue.ECR_IPP_REFUND;
import static com.sharingcube.app.utils.ConstantValue.ECR_IPP_SALE;
import static com.sharingcube.app.utils.ConstantValue.ECR_REDEEM_REFUND;
import static com.sharingcube.app.utils.ConstantValue.ECR_REDEEM_SALE;
import static com.sharingcube.app.utils.ConstantValue.ECR_REFUND;
import static com.sharingcube.app.utils.ConstantValue.ECR_SALE;
import static com.sharingcube.app.utils.ConstantValue.ECR_VOID;
import static com.sharingcube.app.utils.ConstantValue.IS_TRAINING_MODE;
import static com.sharingcube.app.utils.ConstantValue.KEY_CLICK_CARD;
import static com.sharingcube.app.utils.ConstantValue.KEY_CLICK_CREDIT_CARD;
import static com.sharingcube.app.utils.ConstantValue.KEY_CLICK_IPP;
import static com.sharingcube.app.utils.ConstantValue.KEY_CLICK_REDEEM;
import static com.sharingcube.app.utils.ConstantValue.KEY_CLICK_REFUND;
import static com.sharingcube.app.utils.ConstantValue.KEY_CLICK_SALE;
import static com.sharingcube.app.utils.ConstantValue.KEY_CLICK_SCAN_CODE;
import static com.sharingcube.app.utils.ConstantValue.KEY_CLICK_SCAN_REFUND;
import static com.sharingcube.app.utils.ConstantValue.KEY_CLICK_VOID;
import static com.sharingcube.app.utils.ConstantValue.STATUS_PAY;
import static com.sharingcube.app.utils.ConstantValue.STATUS_REFUND;
import static com.sharingcube.app.utils.TransferParamsKey.EDC_RESPONSE;
import static com.sharingcube.app.utils.TransferParamsKey.POS_REQUEST;
import static com.sharingcube.app.utils.TransferParamsKey.REQUEST_CODE_EDC;
import static com.sharingcube.app.utils.TransferParamsKey.REQUEST_CODE_SCP;
import static com.sharingcube.app.utils.TransferParamsKey.Receipt_No;
import static com.sharingcube.app.utils.TransferParamsKey.SCP_RESPONSE;
import static com.sharingcube.app.utils.TransferParamsKey.Source_Class;
import static com.sharingcube.app.utils.TransferParamsKey.Source_Package;
import static com.sharingcube.app.utils.TransferParamsKey.Trans_Type;

/**
 * 輸入金額，調閱編號，分期期數的畫面
 */

public class SaleInputAmtActivity extends BaseActivity {
    private Context mContext;
    public String TAG = getClass().getSimpleName();
    private String mStrClickType;
    private String mStrTransType;
    private Gson gson = new Gson();
    private boolean mIsIPP = false;
    private File outputFile;

    @BindView(R.id.et_amount)
    EditText mEtAmount;
    @BindView(R.id.et_period)
    EditText mEtPeriod;
    @BindView(R.id.et_ref_no)
    EditText mEtRefNo;
    @BindView(R.id.btn_ok)
    Button mBtnOk;
    @BindView(R.id.btn_cancel)
    Button mBtnBack;
    private InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_input);
        mContext = this;
        ResourceUtil.init(this);
        ButterKnife.bind(this);

        if(getIntent() != null)
        {
            mStrClickType = getIntent().getStringExtra(ConstantValue.KEY_CLICK_TYPE);
            mStrTransType = getIntent().getStringExtra(ConstantValue.KEY_CLICK_TRANS_TYPE);
        }

        initView();
    }

    private void initView() {
        mBtnBack.setVisibility(View.VISIBLE);
        showSoftKeyboard(mEtAmount);

        if(TextUtils.equals(mStrTransType, KEY_CLICK_IPP))
        {
            mIsIPP = true;
            mEtPeriod.setVisibility(View.VISIBLE);
            showSoftKeyboard(mEtPeriod);
        }
        else if(TextUtils.equals(mStrTransType, KEY_CLICK_VOID))
        {
            mEtAmount.setVisibility(View.GONE);
            mEtRefNo.setVisibility(View.VISIBLE);
            showSoftKeyboard(mEtRefNo);
        }
    }

    public void showSoftKeyboard(View view){
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view,InputMethodManager.SHOW_IMPLICIT);
    }

    @OnClick(R.id.btn_cancel)
    void onClickBack()
    {
        if(imm != null)
        {
            if(mEtAmount.getVisibility() == View.VISIBLE)
            {
                imm.hideSoftInputFromWindow(mEtAmount.getWindowToken(), 0);
            }
            else if(mEtPeriod.getVisibility() == View.VISIBLE)
            {
                imm.hideSoftInputFromWindow(mEtPeriod.getWindowToken(), 0);
            }
            else if(mEtRefNo.getVisibility() == View.VISIBLE)
            {
                imm.hideSoftInputFromWindow(mEtRefNo.getWindowToken(), 0);
            }

        }

        finish();
    }

    @OnClick(R.id.btn_ok)
    void onClickOK()
    {
        if(true == isAmtAvailable())
        {
            ChooseCardTypeDialog chooseCardTypeDialog = null;
            switch (mStrClickType)
            {
                case KEY_CLICK_CARD:
                    //銷售
                    chooseCardTypeDialog = new ChooseCardTypeDialog(mContext,mEtAmount.getText().toString(), ECR_SALE);
                    chooseCardTypeDialog.show();
                    chooseCardTypeDialog.setChoseCardTypeListener(mOnClickButtonListener);
                    break;
                case KEY_CLICK_CREDIT_CARD:
                    //信用卡
                    if(mStrTransType.equals(KEY_CLICK_SALE))
                    {
                        chooseCardTypeDialog = new ChooseCardTypeDialog(mContext,mEtAmount.getText().toString(), ECR_SALE);
                    }
                    else if(mStrTransType.equals(KEY_CLICK_REFUND))
                    {
                        chooseCardTypeDialog = new ChooseCardTypeDialog(mContext,mEtAmount.getText().toString(), ECR_REFUND);
                    }
                    else
                    {
                        GoToVoid(ECR_VOID);
                        break;
                    }
                    chooseCardTypeDialog.show();
                    chooseCardTypeDialog.setChoseCardTypeListener(mOnClickButtonListener);
                    break;
                case KEY_CLICK_IPP:
                    //分期
                    if(mStrTransType.equals(KEY_CLICK_SALE))
                    {
                        chooseCardTypeDialog = new ChooseCardTypeDialog(mContext,mEtAmount.getText().toString(), ECR_IPP_SALE);
                    }
                    else if(mStrTransType.equals(KEY_CLICK_REFUND))
                    {
                        chooseCardTypeDialog = new ChooseCardTypeDialog(mContext,mEtAmount.getText().toString(), ECR_IPP_REFUND);
                    }
                    else
                    {
                        GoToVoid(ECR_VOID);
                        break;
                    }
                    chooseCardTypeDialog.show();
                    chooseCardTypeDialog.setChoseCardTypeListener(mOnClickButtonListener);
                    break;
                case KEY_CLICK_REDEEM:
                    //紅利
                    if(mStrTransType.equals(KEY_CLICK_SALE))
                    {
                        chooseCardTypeDialog = new ChooseCardTypeDialog(mContext,mEtAmount.getText().toString(), ECR_REDEEM_SALE);
                        chooseCardTypeDialog.show();
                        chooseCardTypeDialog.setChoseCardTypeListener(mOnClickButtonListener);
                    }
                    else if(mStrTransType.equals(KEY_CLICK_REFUND))
                    {
                        chooseCardTypeDialog = new ChooseCardTypeDialog(mContext,mEtAmount.getText().toString(), ECR_REDEEM_REFUND);
                        chooseCardTypeDialog.show();
                        chooseCardTypeDialog.setChoseCardTypeListener(mOnClickButtonListener);
                    }
                    else
                    {
                        GoToVoid(ECR_VOID);
                        break;
                    }
                    break;
                case KEY_CLICK_VOID:
                    //取消
                    GoToVoid(ECR_VOID);
                    break;
                case KEY_CLICK_REFUND:
                    //退貨
                    chooseCardTypeDialog = new ChooseCardTypeDialog(mContext,mEtAmount.getText().toString(), ECR_REFUND);
                    chooseCardTypeDialog.show();
                    chooseCardTypeDialog.setChoseCardTypeListener(mOnClickButtonListener);
                    break;
                case KEY_CLICK_SCAN_CODE:
                    GoToScanCodePayment(STATUS_PAY);
                    break;
                case KEY_CLICK_SCAN_REFUND:
                    GoToScanCodePayment(STATUS_REFUND);
                    break;
            }
        }
    }

    private void GoToVoid(String transType) {
        try {
            JSONObject objParams = new JSONObject();
            objParams.put(Trans_Type, transType);
            objParams.put(Receipt_No, mEtRefNo.getText());
            objParams.put(Source_Package, getPackageName());
            objParams.put(Source_Class, getClass().getName());

            objParams.put("TIME", CustomDialogUtility.getCurrTimestamp());

            Log.d(TAG, "Json REQ : " + objParams);

            Intent intent = new Intent();
            intent.setClassName("com.cybersoft.a920", "com.cybersoft.a920.activity.MainActivity");
            intent.putExtra(POS_REQUEST, objParams.toString());
            startActivityForResult(intent, REQUEST_CODE_EDC);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            LogUtil.d("Can't find EDC App!");
        }
    }


    public void GoToScanCodePayment(String type)
    {
        //掃碼
        Intent intent = new Intent();
        intent.setClassName("com.cybersoft.a920.scp", "com.cybersoft.a920.scp.activity.MainActivity");

        SCPExchangeData data = new SCPExchangeData();
        data.setTransType(type);
        data.setTransAmount(mEtAmount.getText().toString());
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

    ChooseCardTypeDialog.OnClickButtonListener mOnClickButtonListener = new ChooseCardTypeDialog.OnClickButtonListener() {
        @Override
        public void onChoseCardType(JSONObject jsonObject) {
            Intent intent = new Intent();
            intent.setClassName("com.cybersoft.a920", "com.cybersoft.a920.activity.MainActivity");
            intent.putExtra(POS_REQUEST, jsonObject.toString());
            startActivityForResult(intent, REQUEST_CODE_EDC);
            LogUtil.d(TAG, "json to edc : " + jsonObject.toString());
        }
    };

    private boolean isAmtAvailable() {
        if(((mEtAmount.getVisibility() == View.VISIBLE) && TextUtils.isEmpty(mEtAmount.getText().toString()))
                || (mEtRefNo.getVisibility() == View.VISIBLE) && TextUtils.isEmpty(mEtRefNo.getText().toString())
                || (mIsIPP == true && TextUtils.isEmpty(mEtPeriod.getText().toString())))
        {
            CustomDialogUtility.showDialog(mContext, getString(R.string.error), getString(R.string.sale_empty_amt_alert));
            return false;
        }
        return true;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String response;
        if(data == null)
        {
            BackToMain();
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_EDC:
                response = data.getStringExtra(EDC_RESPONSE);
                Log.d(TAG, "onActivityResult: response=" + response);
                String strResponseCode = "";
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    strResponseCode = jsonObject.getString("ECR_Response_Code");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(strResponseCode.equals(ConstantValue.ERROR))
                {
                    Toast.makeText(mContext, getString(R.string.tx_cancel), Toast.LENGTH_SHORT).show();
                    BackToMain();
                    break;
                }
                if (resultCode == RESULT_OK) {
                    if(!SaleInputAmtActivity.this.isFinishing())
                    {
                        ProgressDialogUtil.showProgressDialog(mContext);
                    }

                    Toast.makeText(mContext, getString(R.string.pay_success), Toast.LENGTH_SHORT).show();

                    //1.解析欄位 2.組600 Byte 3.寫檔
                    outputFile = WriteFileUtil.getInstance(mContext).WriteEDC600ByteFile(data);

                    //順便補傳資料夾內尚未傳送的檔案
                    UploadFilesInDirectory();
                }else if (resultCode == RESULT_CANCELED){
                    //取消
                    Toast.makeText(mContext, getString(R.string.tx_cancel), Toast.LENGTH_SHORT).show();
                    BackToMain();
                }
                break;
            case REQUEST_CODE_SCP:
                response = data.getStringExtra(SCP_RESPONSE);
                Log.d(TAG, "onActivityResult: response=" + response);
                String strSCPResponseCode = "";
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    strSCPResponseCode = jsonObject.getString("Response_Code");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(strSCPResponseCode.equals(ConstantValue.ERROR))
                {
                    Toast.makeText(mContext, getString(R.string.tx_cancel), Toast.LENGTH_SHORT).show();
                    BackToMain();
                    break;
                }
                if (resultCode == RESULT_OK) {
                    if(!SaleInputAmtActivity.this.isFinishing())
                    {
                        ProgressDialogUtil.showProgressDialog(mContext);
                    }

                    Toast.makeText(mContext, getString(R.string.pay_success), Toast.LENGTH_SHORT).show();

                    //1.解析欄位 2.組600 Byte 3.寫檔
                    outputFile = WriteFileUtil.getInstance(mContext).WriteSCP600ByteFile(data);

                    //順便補傳資料夾內尚未傳送的檔案
                    UploadFilesInDirectory();
                }else if (resultCode == RESULT_CANCELED){
                    Toast.makeText(mContext, R.string.tx_cancel, Toast.LENGTH_SHORT).show();
                    BackToMain();
                }
                break;
            default:
                break;
        }
    }

    private void UploadFilesInDirectory() {
        File directory = new File(ConstantValue.sPATH_UPLOAD_DIRECTORY_NAME);
        File[] fList = directory.listFiles();
        Queue<File> uploadQueue = new LinkedList<>();
        Log.d(TAG, "[尚有" + fList.length + "個檔案未上傳]");
        //若資料夾還有檔案未上傳，就上傳
        if(fList.length > 0)
        {
            for (File file : fList)
            {
                uploadQueue.add(file);
            }
            //上傳囉
            WriteFileUtil.getInstance(mContext).UploadFilesBySFTP(mContext, uploadQueue, uploadFinishListener);
        }
    }

    UploadFinishListener uploadFinishListener = new UploadFinishListener() {
        @Override
        public void uploadFinish(boolean bIsSuccess) {
            Log.d(TAG, "[上傳完成]");
            ProgressDialogUtil.dismiss();

            //成功之後轉回首頁
            BackToMain();
        }

        @Override
        public void errorOccurred(String message) {
            ProgressDialogUtil.dismiss();
            Log.d(TAG, "[上傳失敗]" + message);
            Looper.prepare();
            if(mContext != null)
            {
                CustomDialogUtility.showDialog(mContext, getString(R.string.error), message, new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //失敗之後也轉回首頁
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                BackToMain();
                            }
                        });
                    }
                });
            }
            else
            {
                BackToMain();
            }

            Looper.loop();
        }
    };

}
