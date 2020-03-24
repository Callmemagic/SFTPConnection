package com.sharingcube.app.utils;

import com.sharingcube.app.model.EDCExchangeData;
import com.sharingcube.app.model.SCPExchangeData;

import static com.sharingcube.app.utils.ConstantValue.ALIPAY_O;
import static com.sharingcube.app.utils.ConstantValue.ECR_CARD_TYPE_AE;
import static com.sharingcube.app.utils.ConstantValue.ECR_CARD_TYPE_CUP;
import static com.sharingcube.app.utils.ConstantValue.ECR_CARD_TYPE_DISCOVER;
import static com.sharingcube.app.utils.ConstantValue.ECR_CARD_TYPE_EASYCARD;
import static com.sharingcube.app.utils.ConstantValue.ECR_CARD_TYPE_JCB;
import static com.sharingcube.app.utils.ConstantValue.ECR_CARD_TYPE_MASTER;
import static com.sharingcube.app.utils.ConstantValue.ECR_CARD_TYPE_U;
import static com.sharingcube.app.utils.ConstantValue.ECR_CARD_TYPE_VISA;
import static com.sharingcube.app.utils.ConstantValue.ECR_HOST_ID_AMEX;
import static com.sharingcube.app.utils.ConstantValue.ECR_HOST_ID_DINERS;
import static com.sharingcube.app.utils.ConstantValue.ECR_HOST_ID_TSB;
import static com.sharingcube.app.utils.ConstantValue.ECR_HOST_ID_TSB_DCC;
import static com.sharingcube.app.utils.ConstantValue.ECR_HOST_ID_TSB_IN;
import static com.sharingcube.app.utils.ConstantValue.ECR_HOST_ID_TSB_RE;
import static com.sharingcube.app.utils.ConstantValue.ECR_HOST_ID_TSB_SMARTPAY;
import static com.sharingcube.app.utils.ConstantValue.ECR_IPP_REFUND;
import static com.sharingcube.app.utils.ConstantValue.ECR_IPP_SALE;
import static com.sharingcube.app.utils.ConstantValue.ECR_REDEEM_REFUND;
import static com.sharingcube.app.utils.ConstantValue.ECR_REDEEM_SALE;
import static com.sharingcube.app.utils.ConstantValue.ECR_REFUND;
import static com.sharingcube.app.utils.ConstantValue.ECR_SALE;
import static com.sharingcube.app.utils.ConstantValue.ECR_SETTLEMENT;
import static com.sharingcube.app.utils.ConstantValue.ECR_VOID;
import static com.sharingcube.app.utils.ConstantValue.JKOS_I;
import static com.sharingcube.app.utils.ConstantValue.LINEPAY_I;
import static com.sharingcube.app.utils.ConstantValue.LINEPAY_O;
import static com.sharingcube.app.utils.ConstantValue.PI_I;
import static com.sharingcube.app.utils.ConstantValue.WEIXIN_O;

/**
 * 組600 byte資料的工具
 */
public class ByteUtil {

    //EDC資料轉600byte格式
    public static String ComposeEDC600ByteData(EDCExchangeData edcExchangeData)
    {
        StringBuilder stringBuilder = new StringBuilder();

        //Trans_Type
        stringBuilder.append(edcExchangeData.getTrans_Type() == null ? String.format("%2s", " ") : mappingEDCTransType(edcExchangeData.getTrans_Type()));
        //stringBuilder.append("-");
        //Host_ID
        stringBuilder.append(edcExchangeData.getHost_ID() == null ? String.format("%2s", " ") : mappingHostId(edcExchangeData.getHost_ID()));
        //stringBuilder.append("-");
        //Invoice_No
        stringBuilder.append(edcExchangeData.getReceipt_No() == null ? String.format("%6s", " ") : String.format("%06d", Integer.parseInt(edcExchangeData.getReceipt_No())));
        //stringBuilder.append("-");
        //Card_No
        stringBuilder.append(edcExchangeData.getCard_No() == null ? String.format("%19s", " ") : String.format("%1$-19s", edcExchangeData.getCard_No()));
        //stringBuilder.append("-");
        //Card_Expire_Date
        stringBuilder.append(String.format("%4s", " "));
        //stringBuilder.append("-");
        //Trans_Amount
        stringBuilder.append(edcExchangeData.getTrans_Amount() == null ? String.format("%12s", " ") : String.format("%012d", Integer.parseInt(edcExchangeData.getTrans_Amount())));
        //stringBuilder.append("-");
        //Trans_Date
        stringBuilder.append(edcExchangeData.getTrans_Date() == null ? String.format("%6s", " ") : edcExchangeData.getTrans_Date());
        //stringBuilder.append("-");
        //Trans_Time
        stringBuilder.append(edcExchangeData.getTrans_Time() == null ? String.format("%6s", " ") : edcExchangeData.getTrans_Time());
        //stringBuilder.append("-");
        //Approval_No
        stringBuilder.append(edcExchangeData.getApproval_No() == null ? String.format("%9s", " ") : String.format("%1$-9s", edcExchangeData.getApproval_No()));
        //stringBuilder.append("-");
        //Auth_Amount
        stringBuilder.append(edcExchangeData.getDownpayment_Amount() == null ? String.format("%12s", " ") : String.format("%012d", Integer.parseInt(edcExchangeData.getDownpayment_Amount())));
        //stringBuilder.append("-");
        //ECR_Response_Code
        stringBuilder.append(edcExchangeData.getECR_Response_Code() == null ? String.format("%4s", " ") : edcExchangeData.getECR_Response_Code());
        //stringBuilder.append("-");
        //EDC_Terminal_ID
        stringBuilder.append(edcExchangeData.getEDC_Terminal_ID() == null ? String.format("%8s", " ") : edcExchangeData.getEDC_Terminal_ID());
        //stringBuilder.append("-");
        //Reference_No
        stringBuilder.append(edcExchangeData.getReference_No() == null ? String.format("%12s", " ") : edcExchangeData.getReference_No());
        //stringBuilder.append("-");
        //Exp_Amount
        stringBuilder.append(edcExchangeData.getEachpayment_Amount() == null ? String.format("%12s", " ") : edcExchangeData.getEachpayment_Amount());
        //stringBuilder.append("-");
        //Store_Id
        stringBuilder.append(edcExchangeData.getStore_Id() == null ? String.format("%18s", " ") : edcExchangeData.getStore_Id());
        //stringBuilder.append("-");
        //Start Get PAN
        stringBuilder.append(edcExchangeData.getTrans_Type() == null ? String.format("%2s", " ") : mappingPAN(edcExchangeData.getTrans_Type()));
        //stringBuilder.append("-");
        //Issuer Id 只有Smart Pay回送發卡單位代號 若無則分期交易會帶上交易期數 再無就帶0
        String strIssuerId = edcExchangeData.getIssuer_ID();
        if(strIssuerId != null)
        {
            stringBuilder.append(String.format("%1$-3s", strIssuerId));
        }
        else
        {
            stringBuilder.append(edcExchangeData.getInstallment_Period() == null ? String.format("%2s", " ") : String.format("%1$-3s", edcExchangeData.getInstallment_Period()));
        }
        //stringBuilder.append("-");
        //Card_Type
        stringBuilder.append(edcExchangeData.getCard_Type() == null ? String.format("%1s", " ") : mappingCardType(edcExchangeData.getCard_Type()));
        //stringBuilder.append("-");
        //Filter
        stringBuilder.append(edcExchangeData.getEach_Period_Fee() == null ? String.format("%6s", " ") : String.format("%06d", Integer.parseInt(edcExchangeData.getEach_Period_Fee())));
        //stringBuilder.append("-");
        //Electronic invoice
        stringBuilder.append(edcExchangeData.getCard_No_Vehicle() == null ? String.format("%50s", " ") : String.format("%1$-50s", edcExchangeData.getCard_No_Vehicle()));
        //stringBuilder.append("-");
        //Merchant ID
        stringBuilder.append(edcExchangeData.getMerchant_ID() == null ? String.format("%15s", " ") : edcExchangeData.getMerchant_ID());
        //stringBuilder.append("-");
        //Batch No -> settle
        stringBuilder.append(edcExchangeData.getBatch_No() == null ? String.format("%6s", " ") : String.format("%06d", Integer.parseInt(edcExchangeData.getBatch_No())));
        //stringBuilder.append("-");
        //Entry mode
        stringBuilder.append(edcExchangeData.getEntry_Mode() == null ? String.format("%2s", " ") : edcExchangeData.getEntry_Mode());
        //stringBuilder.append("-");
        //TC Code
        stringBuilder.append(String.format("%16s", " "));
        //stringBuilder.append("-");
        //CardHolder Name
        stringBuilder.append(String.format("%40s", " "));
        //stringBuilder.append("-");

        //=========================================以下為票證========================================================================
        //Ticket Type
        stringBuilder.append(String.format("%1s", " "));
        //stringBuilder.append("-");
        //Ticket Card Number
        stringBuilder.append(String.format("%19s", " "));
        //stringBuilder.append("-");
        //Ticket Reference Number
        stringBuilder.append(String.format("%10s", " "));
        //stringBuilder.append("-");
        //Ticket Batch Number
        stringBuilder.append(String.format("%10s", " "));
        //stringBuilder.append("-");
        //Ticket Pre Balance
        stringBuilder.append(String.format("%10s", " "));
        //stringBuilder.append("-");
        //Ticket Autoload Amount
        stringBuilder.append(String.format("%10s", " "));
        //stringBuilder.append("-");
        //Ticket Balance
        stringBuilder.append(String.format("%10s", " "));
        //stringBuilder.append("-");
        //Ticket SAM ID
        stringBuilder.append(String.format("%16s", " "));
        //stringBuilder.append("-");
        //Ticket Store ID
        stringBuilder.append(String.format("%10s", " "));
        //stringBuilder.append("-");
        //Ticket Error Code
        stringBuilder.append(String.format("%20s", " "));
        //stringBuilder.append("-");
        //Redemption Data
        stringBuilder.append(String.format("%10s", " "));
        //stringBuilder.append("-");
        //========================================以下為掃碼============================================================================
        //Scan Data
        stringBuilder.append(String.format("%150s", " "));
        //stringBuilder.append("-");
        //Scan Data index
        stringBuilder.append(String.format("%2s", " "));
        //stringBuilder.append("-");
        //Scan Data Order No.
        stringBuilder.append(String.format("%20s", " "));
        //stringBuilder.append("-");
        //Printer Disable -> //不印簽單為1
        stringBuilder.append(String.format("%1s", " "));
        //stringBuilder.append("-");
        //Reserve
        stringBuilder.append(String.format("%28s", " "));
        //stringBuilder.append("-");

        return stringBuilder.toString();
    }

    //SCP資料轉600byte格式
    public static String ComposeSCP600ByteData(SCPExchangeData scpExchangeData)
    {
        StringBuilder stringBuilder = new StringBuilder();

        //Trans_Type
        stringBuilder.append(scpExchangeData.getTransType() == null ? String.format("%02d", 0) : mappingSCPTransType(scpExchangeData.getTransType()));
        //stringBuilder.append("-");
        //Host_ID -> 掃碼固定15
        stringBuilder.append("15");
        //stringBuilder.append("-");
        //Invoice_No
        stringBuilder.append(String.format("%6s", " "));
        //stringBuilder.append("-");
        //Card_No
        stringBuilder.append(String.format("%19s", " "));
        //stringBuilder.append("-");
        //Card_Expire_Date
        stringBuilder.append(String.format("%4s", " "));
        //stringBuilder.append("-");
        //Trans_Amount
        stringBuilder.append(scpExchangeData.getTransAmount() == null ? String.format("%12s", " ") : String.format("%012d", Integer.parseInt(scpExchangeData.getTransAmount())));
        //stringBuilder.append("-");
        //Trans_Date ex.20201015
        stringBuilder.append(scpExchangeData.getTxnDate() == null ? String.format("%6s", " ") : scpExchangeData.getTxnDate().substring(2));
        //stringBuilder.append("-");
        //Trans_Time
        stringBuilder.append(scpExchangeData.getTxnTime() == null ? String.format("%6s", " ") : scpExchangeData.getTxnTime());
        //stringBuilder.append("-");
        //Approval_No
        stringBuilder.append(String.format("%9s", " "));
        //stringBuilder.append("-");
        //Auth_Amount
        stringBuilder.append(String.format("%12s", " "));
        //stringBuilder.append("-");
        //ECR_Response_Code
        stringBuilder.append(scpExchangeData.getResponseCode() == null ? String.format("%4s", " ") : scpExchangeData.getResponseCode());
        //stringBuilder.append("-");
        //EDC_Terminal_ID
        stringBuilder.append(scpExchangeData.getEDCTerminalId() == null ? String.format("%8s", " ") : scpExchangeData.getEDCTerminalId());
        //stringBuilder.append("-");
        //Reference_No
        stringBuilder.append(String.format("%12s", " "));
        //stringBuilder.append("-");
        //Exp_Amount
        stringBuilder.append(String.format("%12s", " "));
        //stringBuilder.append("-");
        //Store_Id
        stringBuilder.append(String.format("%18s", " "));
        //stringBuilder.append("-");
        //Start Gen PAN
        stringBuilder.append(String.format("%2s", " "));
        //stringBuilder.append("-");
        //Issuer Id
        stringBuilder.append(String.format("%3s", " "));
        //stringBuilder.append("-");
        //Card_Type
        stringBuilder.append(String.format("%1s", " "));
        //stringBuilder.append("-");
        //Filter
        stringBuilder.append(String.format("%6s", " "));
        //stringBuilder.append("-");
        //Electronic invoice
        stringBuilder.append(String.format("%50s", " "));
        //stringBuilder.append("-");
        //Merchant ID
        String strMID = scpExchangeData.getMerchantId();
        StringBuilder sbMID = new StringBuilder();

        //若MID未滿15碼，後補0
        if(strMID == null)
        {
            strMID = String.format("%15s", " ");
        }
        else
        {
            if(scpExchangeData.getMerchantId().length() < 15)
            {

                sbMID.append(strMID);

                while (sbMID.toString().length() < 15)
                {
                    sbMID.append("0");
                }
            }
            else
            {
                strMID = scpExchangeData.getMerchantId();
            }
        }

        stringBuilder.append(strMID.length() < 15 ? sbMID.toString() : strMID);
        //stringBuilder.append("-");
        //Batch No
        stringBuilder.append(String.format("%6s", " "));
        //stringBuilder.append("-");
        //Entry mode
        stringBuilder.append(String.format("%2s", " "));
        //stringBuilder.append("-");
        //TC Code
        stringBuilder.append(String.format("%16s", " "));
        //stringBuilder.append("-");
        //CardHolder Name
        stringBuilder.append(String.format("%40s", " "));
        //stringBuilder.append("-");

        //=========================================以下為票證========================================================================
        //Ticket Type
        stringBuilder.append(String.format("%1s", " "));
        //stringBuilder.append("-");
        //Ticket Card Number
        stringBuilder.append(String.format("%19s", " "));
        //stringBuilder.append("-");
        //Ticket Reference Number
        stringBuilder.append(String.format("%10s", " "));
        //stringBuilder.append("-");
        //Ticket Batch Number
        stringBuilder.append(String.format("%10s", " "));
        //stringBuilder.append("-");
        //Ticket Pre Balance
        stringBuilder.append(String.format("%10s", " "));
        //stringBuilder.append("-");
        //Ticket Autoload Amount
        stringBuilder.append(String.format("%10s", " "));
        //stringBuilder.append("-");
        //Ticket Balance
        stringBuilder.append(String.format("%10s", " "));
        //stringBuilder.append("-");
        //Ticket SAM ID
        stringBuilder.append(String.format("%16s", " "));
        //stringBuilder.append("-");
        //Ticket Store ID
        stringBuilder.append(String.format("%10s", " "));
        //stringBuilder.append("-");
        //Ticket Error Code
        stringBuilder.append(String.format("%20s", " "));
        //stringBuilder.append("-");
        //Redemption Data
        stringBuilder.append(String.format("%10s", " "));
        //stringBuilder.append("-");
        //========================================以下為掃碼============================================================================
        //Scan Data
        stringBuilder.append(scpExchangeData.getScanQRCode() == null ? String.format("%150s", " ") : String.format("%1$-150s", scpExchangeData.getScanQRCode()));
        //stringBuilder.append("-");
        //Scan Data index
        stringBuilder.append(scpExchangeData.getWallet() == null ? String.format("%2s", " ") : mappingScanDataIndex(scpExchangeData.getWallet()));
        //stringBuilder.append("-");
        //Scan Data Order No. TID + DATE + TIME
        stringBuilder.append(scpExchangeData.getOrderId() == null ? String.format("%20s", " ") : trimYearLength(scpExchangeData.getOrderId()));
        //stringBuilder.append("-");
        //Printer Disable -> //不印簽單為1
        stringBuilder.append(String.format("%1s", " "));
        //stringBuilder.append("-");
        //Reserve
        stringBuilder.append(String.format("%28s", " "));
        //stringBuilder.append("-");

        return stringBuilder.toString();
    }


    private static String mappingCardType(String card_type) {
        switch (card_type)
        {
            case ECR_CARD_TYPE_VISA:
                return "1";
            case ECR_CARD_TYPE_MASTER:
                return "2";
            case ECR_CARD_TYPE_JCB:
                return "3";
            case ECR_CARD_TYPE_U:
                return "4";
            case ECR_CARD_TYPE_DISCOVER:
                return "5";
            case ECR_CARD_TYPE_AE:
                return "6";
            case ECR_CARD_TYPE_EASYCARD:
                return "7";
            case ECR_CARD_TYPE_CUP:
                return "8";
            default:
                return " ";
        }
    }

    private static String mappingEDCTransType(String transType) {
        switch (transType)
        {
            case ECR_SALE:
                return "01";
            case ECR_REFUND:
                return "02";
            case ECR_IPP_SALE:
                return "03";
            case ECR_IPP_REFUND:
                return "04";
            case ECR_REDEEM_SALE:
                return "05";
            case ECR_REDEEM_REFUND:
                return "06";
            case ECR_VOID:
                return "30";
            case ECR_SETTLEMENT:
                return "50";
            default:
                return "  ";
        }
    }

    private static String mappingHostId(String strHostId)
    {
        switch (strHostId)
        {
            case ECR_HOST_ID_TSB:
            case ECR_HOST_ID_DINERS:
                return "00";
            case ECR_HOST_ID_AMEX:
                return "01";
            case ECR_HOST_ID_TSB_IN:
            case ECR_HOST_ID_TSB_RE:
            case ECR_HOST_ID_TSB_DCC:
                return "02";
            case ECR_HOST_ID_TSB_SMARTPAY:
                return "03";
            default:
                return "  ";
        }
    }

    private static String mappingSCPTransType(String transType) {
        switch (transType)
        {
            case ECR_SALE:
                return "22";
            case ECR_REFUND:
                return "23";
            default:
                return "  ";
        }
    }

    private static String mappingPAN(String transType) {
        switch (transType)
        {
            case ECR_SALE:
                return "01";
            case ECR_REFUND:
                return "02";
            case ECR_IPP_SALE:
                return "03";
            case ECR_IPP_REFUND:
                return "04";
            case ECR_REDEEM_SALE:
                return "05";
            case ECR_REDEEM_REFUND:
                return "06";
            default:
                return "  ";
        }
    }

    private static String mappingScanDataIndex(String walletName)
    {
        switch (walletName)
        {
            case ALIPAY_O:
                return "Al";
            case LINEPAY_I:
            case LINEPAY_O:
                return "Li";
            case JKOS_I:
                return "JK";
            case WEIXIN_O:
                return "We";
            case PI_I:
                return "Pi";
            default:
                return "  ";
        }
    }

    private static String trimYearLength(String scandata)
    {
        if (scandata.length() > 20 && scandata.length() == 22)
        {
            //形如T0000004 20200116134418，把年份前兩位拿掉使其成為20碼
            String TID = scandata.substring(0, 8);
            String Date = scandata.substring(8, 16);
            String Time = scandata.substring(16, 22);

            return TID + Date.substring(2) + Time;
        }
        return scandata;
    }

}
