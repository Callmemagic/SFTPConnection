package com.sharingcube.app.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Abbie on 2018/3/15.
 */

public class SCPExchangeData {

    private static final String TAG = "SCPExchangeData";

    /**
     * Response_Code : 0000
     * Training_Mode : true
     * Trans_Amount : 100
     * Trans_Type : 11
     * Pay_Info : ... (ECR)
     * Wallet : ALIPAY_O
     * Order_Id : ...
     */

    @SerializedName("Response_Code")
    private String mResponseCode;
    @SerializedName("Training_Mode")
    private boolean mTrainingMode;
    @SerializedName("Trans_Amount")
    private String mTransAmount;
    @SerializedName("Trans_Type")
    private String mTransType;
    @SerializedName("Pay_Info")
    private String mPayInfo;
    @SerializedName("Wallet")
    private String mWallet;
    @SerializedName("Order_Id")
    private String mOrderId;
    @SerializedName("Txn_Date")
    private String mTxnDate;
    @SerializedName("Txn_Time")
    private String mTxnTime;

    @SerializedName("EDC_Terminal_ID")
    private String mEDCTerminalId;
    @SerializedName("Merchant_ID")
    private String mMerchantId;
    @SerializedName("ScanQRCode")
    private String mScanQRCode;


    public String getResponseCode() {
        Log.d(TAG, "getResponseCode: " + mResponseCode);
        return mResponseCode;
    }

    public void setResponseCode(String responseCode) {
        mResponseCode = responseCode;
    }

    public boolean isTrainingMode() {
        Log.d(TAG, "isTrainingMode: " + mTrainingMode);
        return mTrainingMode;
    }

    public void setTrainingMode(boolean trainingMode) {
        mTrainingMode = trainingMode;
    }

    public String getTransAmount() {
        Log.d(TAG, "getTransAmount: " + mTransAmount);
        return mTransAmount;
    }

    public void setTransAmount(String transAmount) {
        mTransAmount = transAmount;
    }

    public String getTransType() {
        Log.d(TAG, "getTransType: " + mTransType);
        return mTransType;
    }

    public void setTransType(String transType) {
        mTransType = transType;
    }

    public String getPayInfo() {
        Log.d(TAG, "getPayInfo: " + mPayInfo);
        return mPayInfo;
    }

    public void setPayInfo(String payInfo) {
        mPayInfo = payInfo;
    }

    public String getWallet() {
        Log.d(TAG, "getWallet: " + mWallet);
        return mWallet;
    }

    public void setWallet(String wallet) {
        mWallet = wallet;
    }

    public String getOrderId() {
        Log.d(TAG, "getOrderId: " + mOrderId);
        return mOrderId;
    }

    public void setOrderId(String orderId) {
        mOrderId = orderId;
    }

    public String getTxnDate()
    {
        return mTxnDate;
    }

    public String getTxnTime()
    {
        return mTxnTime;
    }

    public String getEDCTerminalId() {
        return mEDCTerminalId;
    }

    public void setEDCTerminalId(String mEDCTerminalId) {
        this.mEDCTerminalId = mEDCTerminalId;
    }

    public String getMerchantId() {
        return mMerchantId;
    }

    public void setMerchantId(String mMerchantId) {
        this.mMerchantId = mMerchantId;
    }

    public String getScanQRCode() {
        return mScanQRCode;
    }

    public void setScanQRCode(String mScanQRCode) {
        this.mScanQRCode = mScanQRCode;
    }
}
