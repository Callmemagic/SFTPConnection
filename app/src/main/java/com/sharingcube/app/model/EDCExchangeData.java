package com.sharingcube.app.model;

import com.google.gson.annotations.SerializedName;

public class EDCExchangeData {

    private static final String TAG = "EDCExchangeData";

    @SerializedName("Trans_Type")
    private String Trans_Type;
    @SerializedName("Trans_Amount")
    private String Trans_Amount;
    @SerializedName("Source_Class")
    private String Source_Class;
    @SerializedName("Source_Package")
    private String Source_Package;
    @SerializedName("POS_REQUEST")
    private String POS_REQUEST;
    @SerializedName("EDC_RESPONSE")
    private String EDC_RESPONSE;
    @SerializedName("ECR_Response_Code")
    private String ECR_Response_Code;
    @SerializedName("Host_ID")
    private String Host_ID;
    @SerializedName("Receipt_No")
    private String Receipt_No;
    @SerializedName("Card_No")
    private String Card_No;
    @SerializedName("Trans_Date")
    private String Trans_Date;
    @SerializedName("Trans_Time")
    private String Trans_Time;
    @SerializedName("Approval_No")
    private String Approval_No;
    @SerializedName("EDC_Terminal_ID")
    private String EDC_Terminal_ID;
    @SerializedName("Order_No")
    private String Order_No;
    @SerializedName("Installment_Total_Amount")
    private String Installment_Total_Amount;
    @SerializedName("Downpayment_Amount")
    private String Downpayment_Amount;
    @SerializedName("Eachpayment_Amount")
    private String Eachpayment_Amount;
    @SerializedName("Installment_Period")
    private String Installment_Period;
    @SerializedName("Real_Amount")
    private String Real_Amount;
    @SerializedName("Rebate_Amount")
    private String Rebate_Amount;
    @SerializedName("Rebate_Point")
    private String Rebate_Point;
    @SerializedName("RRN")
    private String RRN;
    @SerializedName("Card_Type")
    private String Card_Type;
    @SerializedName("CUP_Indicator")
    private String CUP_Indicator;
    @SerializedName("Card_No_Vehicle")
    private String Card_No_Vehicle;
    @SerializedName("Transaction_Finished")
    private String Transaction_Finished;
    @SerializedName("Reference_No")
    private String Reference_No;
    @SerializedName("Store_Id")
    private String Store_Id;
    @SerializedName("Issuer_ID")
    private String Issuer_ID;
    @SerializedName("Merchant_ID")
    private String Merchant_ID;
    @SerializedName("Each_Period_Fee")
    private String Each_Period_Fee;
    @SerializedName("Entry_Mode")
    private String Entry_Mode;
    @SerializedName("Batch_No")
    private String Batch_No;

    public String getTrans_Type() {
        return Trans_Type;
    }

    public void setTrans_Type(String trans_Type) {
        Trans_Type = trans_Type;
    }

    public String getTrans_Amount() {
        return Trans_Amount;
    }

    public void setTrans_Amount(String trans_Amount) {
        Trans_Amount = trans_Amount;
    }

    public String getSource_Class() {
        return Source_Class;
    }

    public void setSource_Class(String source_Class) {
        Source_Class = source_Class;
    }

    public String getSource_Package() {
        return Source_Package;
    }

    public void setSource_Package(String source_Package) {
        Source_Package = source_Package;
    }

    public String getPOS_REQUEST() {
        return POS_REQUEST;
    }

    public void setPOS_REQUEST(String POS_REQUEST) {
        this.POS_REQUEST = POS_REQUEST;
    }

    public String getEDC_RESPONSE() {
        return EDC_RESPONSE;
    }

    public void setEDC_RESPONSE(String EDC_RESPONSE) {
        this.EDC_RESPONSE = EDC_RESPONSE;
    }

    public String getECR_Response_Code() {
        return ECR_Response_Code;
    }

    public void setECR_Response_Code(String ECR_Response_Code) {
        this.ECR_Response_Code = ECR_Response_Code;
    }

    public String getHost_ID() {
        return Host_ID;
    }

    public void setHost_ID(String host_ID) {
        Host_ID = host_ID;
    }

    public String getReceipt_No() {
        return Receipt_No;
    }

    public void setReceipt_No(String receipt_No) {
        Receipt_No = receipt_No;
    }

    public String getCard_No() {
        return Card_No;
    }

    public void setCard_No(String card_No) {
        Card_No = card_No;
    }

    public String getTrans_Date() {
        return Trans_Date;
    }

    public void setTrans_Date(String trans_Date) {
        Trans_Date = trans_Date;
    }

    public String getTrans_Time() {
        return Trans_Time;
    }

    public void setTrans_Time(String trans_Time) {
        Trans_Time = trans_Time;
    }

    public String getApproval_No() {
        return Approval_No;
    }

    public void setApproval_No(String approval_No) {
        Approval_No = approval_No;
    }

    public String getEDC_Terminal_ID() {
        return EDC_Terminal_ID;
    }

    public void setEDC_Terminal_ID(String EDC_Terminal_ID) {
        this.EDC_Terminal_ID = EDC_Terminal_ID;
    }

    public String getOrder_No() {
        return Order_No;
    }

    public void setOrder_No(String order_No) {
        Order_No = order_No;
    }

    public String getInstallment_Total_Amount() {
        return Installment_Total_Amount;
    }

    public void setInstallment_Total_Amount(String installment_Total_Amount) {
        Installment_Total_Amount = installment_Total_Amount;
    }

    public String getDownpayment_Amount() {
        return Downpayment_Amount;
    }

    public void setDownpayment_Amount(String downpayment_Amount) {
        Downpayment_Amount = downpayment_Amount;
    }

    public String getEachpayment_Amount() {
        return Eachpayment_Amount;
    }

    public void setEachpayment_Amount(String eachpayment_Amount) {
        Eachpayment_Amount = eachpayment_Amount;
    }

    public String getInstallment_Period() {
        return Installment_Period;
    }

    public void setInstallment_Period(String installment_Period) {
        Installment_Period = installment_Period;
    }

    public String getReal_Amount() {
        return Real_Amount;
    }

    public void setReal_Amount(String real_Amount) {
        Real_Amount = real_Amount;
    }

    public String getRebate_Amount() {
        return Rebate_Amount;
    }

    public void setRebate_Amount(String rebate_Amount) {
        Rebate_Amount = rebate_Amount;
    }

    public String getRebate_Point() {
        return Rebate_Point;
    }

    public void setRebate_Point(String rebate_Point) {
        Rebate_Point = rebate_Point;
    }

    public String getRRN() {
        return RRN;
    }

    public void setRRN(String RRN) {
        this.RRN = RRN;
    }

    public String getCard_Type() {
        return Card_Type;
    }

    public void setCard_Type(String card_Type) {
        Card_Type = card_Type;
    }

    public String getCUP_Indicator() {
        return CUP_Indicator;
    }

    public void setCUP_Indicator(String CUP_Indicator) {
        this.CUP_Indicator = CUP_Indicator;
    }

    public String getCard_No_Vehicle() {
        return Card_No_Vehicle;
    }

    public void setCard_No_Vehicle(String card_No_Vehicle) {
        Card_No_Vehicle = card_No_Vehicle;
    }

    public String getTransaction_Finished() {
        return Transaction_Finished;
    }

    public void setTransaction_Finished(String transaction_Finished) {
        Transaction_Finished = transaction_Finished;
    }

    public String getReference_No() {
        return Reference_No;
    }

    public void setReference_No(String reference_No) {
        Reference_No = reference_No;
    }

    public String getStore_Id() {
        return Store_Id;
    }

    public void setStore_Id(String store_Id) {
        Store_Id = store_Id;
    }

    public String getIssuer_ID() {
        return Issuer_ID;
    }

    public void setIssuer_ID(String issuer_ID) {
        Issuer_ID = issuer_ID;
    }

    public String getMerchant_ID() {
        return Merchant_ID;
    }

    public String getEach_Period_Fee() {
        return Each_Period_Fee;
    }

    public String getEntry_Mode() {
        return Entry_Mode;
    }

    public String getBatch_No() {
        return Batch_No;
    }
}
