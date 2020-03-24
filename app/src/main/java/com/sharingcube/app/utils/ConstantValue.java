package com.sharingcube.app.utils;

import android.os.Environment;

import com.sharingcube.app.R;

import java.io.File;

public class ConstantValue {
    public static final String APP_LOG_TAG = "ECR-";
    public static final Boolean IS_TRAINING_MODE = true; //是否為教育訓練模式 -> 出版前記得改

    //SFTP
    public static final String SFTP_ACCT = "updtslog";
    public static final String SFTP_PWD = "cs@RG2020";
    public static final String SFTP_HOST = "220.130.128.76";
    public static final String SFTP_PORT = "16033";
    public static final String SFTP_FOLDER = "/EDCLOG";


    //GridView data type
    public static final String GRID_TYPE_TRANS_TYPE = "transtype";
    public static final String GRID_TYPE_CREDIT_CARD = "creditcard";
    public static final String GRID_TYPE_CREDIT_CARD_TX = "creditcardtx";
    public static final String GRID_TYPE_MOBILE_PAYMENT = "mobilepayment";

    //Type String
    public static final String CREDIT_CARD = "CREDIT_CARD";
    public static final String REDEEM_DISCOUNT = "REDEEM_DISCOUNT";
    public static final String IPP = "IPP";
    public static final String DEBIT_CARD = "DEBIT_CARD";
    public static final String E_PASS = "E_PASS";
    public static final String MOBILE_PAYMENT = "MOBILE_PAYMENT";
    public static final String CUP = "CUP";
    public static final String REPRINT = "REPRINT";
    public static final String MORE_FUNC = "MORE_FUNC";
    public static final String SETTLE = "SETTLE";
    public static final String SETTING = "SETTING";
    public static final String ABOUT_E_PAY = "ABOUT_E_PAY";
    public static final String SALE = "SALE";
    public static final String VOID = "VOID";
    public static final String REFUND = "REFUND";


    public enum MenuIcons {
        CREDIT_CARD("CREDIT_CARD", "信用卡", R.drawable.trans_credit_card, true),
        E_PASS("E_PASS", "電子票證", R.drawable.trans_e_pass, false),
        MOBILE_PAYMENT("MOBILE_PAYMENT", "行動支付", R.drawable.trans_mobile_payment, true),
        POINTS("POINTS", "點數/兌換", R.drawable.trans_points, false),
        LET_E_GO("LET_E_GO", "雷e購", R.drawable.trans_let_it_go, false),
        SHARINGCUBE_POS("SHARINGCUBE_POS", "雷門POS", R.drawable.trans_pos, false);
//        POINTS3("POINTS2", "雷門POS", R.mipmap.ic_launcher, false),
//        POINTS4("POINTS2", "雷門POS", R.mipmap.ic_launcher, false),
//        POINTS5("POINTS2", "雷門POS", R.mipmap.ic_launcher, false);


        private final String name;
        private final String ChineseName;
        private final int drawable;
        private final boolean isValid;

        MenuIcons(String name, String ChineseName, int drawable, boolean bIsValid) {
            this.name = name;
            this.ChineseName = ChineseName;
            this.drawable = drawable;
            this.isValid = bIsValid;
        }

        public String getName() {
            return this.name;
        }

        public String getChineseName() {
            return this.ChineseName;
        }

        public int getDrawable(){
            return this.drawable;
        }

        public boolean getIsFuncValid(){return this.isValid;}

    }

    public enum TransType {
        CREDIT_CARD("1",1,"CREDIT_CARD", "信用卡", R.drawable.func_credit_card, true),
        E_PASS("2",2,"E_PASS", "電子票證", R.drawable.func_e_pass, false),
        MOBILE_PAYMENT("3",3,"MOBILE_PAYMENT", "行動支付", R.drawable.func_mobile_payment, true),
        CUP("4",4,"CUP", "銀聯卡", R.drawable.func_union_pay, false),
        SETTLE("5",5,"SETTLE", "結帳作業", R.drawable.func_settle, true),
        MORE_FUNC("6",6,"MORE_FUNC", "更多功能", R.drawable.func_more, false),
        REPRINT("7",7,"REPRINT", "重印簽單", R.drawable.func_reprint, false),
        SETTING("8",8,"SETTING", "系統設定", R.drawable.func_system_setting, false),
        ABOUT_E_PAY("9",9,"ABOUT_E_PAY", "關於e-Pay", R.drawable.func_about_epay, false);

        private final int index;
        private final String indexStr;
        private final String name;
        private final String dispName;
        private final int drawable;
        private final boolean isValid;

        TransType(String indexStr, int index, String name, String displayName, int drawable, boolean isValid) {
            this.indexStr = indexStr;
            this.index = index;
            this.name = name;
            this.dispName = displayName;
            this.drawable = drawable;
            this.isValid = isValid;
        }

        public String getName() {
            return this.name;
        }

        public String getDisplayName() {
            return this.dispName;
        }

        public int getDrawable(){
            return this.drawable;
        }

        public int getIndex(){return this.index;}

        public String getIndexStr(){return this.indexStr;}

        public boolean getFuncValid(){return this.isValid;}
    }

    public enum CreditCardType {
        CREDIT_CARD("1", "CREDIT_CARD", "信用卡", R.drawable.func_credit_card, true),
        REDEEM_DISCOUNT("2","REDEEM_DISCOUNT", "紅利扣抵", R.drawable.func_redeem, true),
        IPP("3","IPP", "分期付款", R.drawable.func_installment, true);

        private final String indexStr;
        private final String name;
        private final String ChineseName;
        private final int drawable;
        private final boolean isValid;

        CreditCardType(String indexStr, String name, String ChineseName, int drawable, boolean bIsValid) {
            this.indexStr = indexStr;
            this.name = name;
            this.ChineseName = ChineseName;
            this.drawable = drawable;
            this.isValid = bIsValid;
        }

        public String getIndexStr()
        {
            return this.indexStr;
        }

        public String getName() {
            return this.name;
        }

        public String getChineseName() {
            return this.ChineseName;
        }

        public int getDrawable(){
            return this.drawable;
        }

        public boolean getFuncIsValid(){return isValid;}

    }

    public enum CreditCardTxType {
        SALE("1", "SALE", "銷售交易", R.drawable.func_sale, true),
        VOID("2","VOID", "取消交易", R.drawable.func_void, true),
        REFUND("3","REFUND", "退貨交易", R.drawable.func_refund, true);

        private final String indexStr;
        private final String name;
        private final String ChineseName;
        private final int drawable;
        private final boolean isValid;

        CreditCardTxType(String indexStr, String name, String ChineseName, int drawable, boolean bIsValid) {
            this.indexStr = indexStr;
            this.name = name;
            this.ChineseName = ChineseName;
            this.drawable = drawable;
            this.isValid = bIsValid;
        }

        public String getIndexStr()
        {
            return this.indexStr;
        }

        public String getName() {
            return this.name;
        }

        public String getChineseName() {
            return this.ChineseName;
        }

        public int getDrawable(){
            return this.drawable;
        }

        public boolean getFuncIsValid(){return this.isValid;}

    }

    public enum MobilePaymentType {
        REFUND("1", "REFUND", "掃碼退貨", R.drawable.func_scan_refund, true),
        REPRINT("2", "REPRINT", "重印簽單", R.drawable.func_reprint, true);

        private final String indexStr;
        private final String name;
        private final String ChineseName;
        private final int drawable;
        private final boolean isValid;

        MobilePaymentType(String indexStr, String name, String ChineseName, int drawable, boolean isValid) {
            this.indexStr = indexStr;
            this.name = name;
            this.ChineseName = ChineseName;
            this.drawable = drawable;
            this.isValid = isValid;
        }

        public String getIndexStr()
        {
            return this.indexStr;
        }

        public String getName() {
            return this.name;
        }

        public String getChineseName() {
            return this.ChineseName;
        }

        public int getDrawable(){
            return this.drawable;
        }

        public boolean getFuncIsValid(){return this.isValid;}

    }

    //INTENT_KEY
    public static final String KEY_CLICK_TYPE = "KEY_CLICK_TYPE";
    public static final String KEY_CLICK_TRANS_TYPE = "KEY_CLICK_TRANS_TYPE";
    public static final String KEY_CLICK_CARD = "KEY_CLICK_CARD"; //快捷-信用卡
    public static final String KEY_CLICK_SCAN_CODE = "KEY_CLICK_SCAN_CODE"; //快捷-掃碼
    public static final String KEY_CLICK_SCAN_REFUND = "KEY_CLICK_SCAN_REFUND"; //掃碼退貨
    public static final String KEY_CLICK_CREDIT_CARD = "KEY_CLICK_CREDIT_CARD"; //信用卡交易
    public static final String KEY_CLICK_REDEEM = "KEY_CLICK_REDEEM"; //紅利扣抵
    public static final String KEY_CLICK_IPP = "KEY_CLICK_IPP"; //分期付款
    public static final String KEY_CLICK_SALE = "KEY_CLICK_SALE"; //銷售
    public static final String KEY_CLICK_VOID = "KEY_CLICK_VOID"; //取消
    public static final String KEY_CLICK_REFUND = "KEY_CLICK_REFUND"; //退貨
    public static final String KEY_EDC_RESPONSE = "KEY_EDC_RESPONSE"; //EDC結果
    public static final String KEY_SCP_RESPONSE = "KEY_SCP_RESPONSE"; //SCP結果
    public static final String KEY_RESULT_CODE = "KEY_RESULT_CODE"; //Result code
    public static final String KEY_RESULT_SCAN_REPRINT = "KEY_RESULT_SCAN_REPRINT"; //重印簽單

    //PATH
    public static final String sPACKAGE_NAME = "com.sharingcube.app";
    public static final String sPATH_PARENT_DIRECTORY_NAME = Environment.getExternalStorageDirectory() + File.separator + "A920" + File.separator;
    public static final String sPATH_CRASH_LOG_DIRECTORY_NAME = sPATH_PARENT_DIRECTORY_NAME + "CrashLog" + File.separator + sPACKAGE_NAME + File.separator;
    public static final String sPATH_LOGCAT_DIRECTORY_NAME = sPATH_PARENT_DIRECTORY_NAME + "Logcat" + File.separator + sPACKAGE_NAME + File.separator;
    public static final String sPATH_UPLOAD_DIRECTORY_NAME = sPATH_LOGCAT_DIRECTORY_NAME + "UploadTx" + File.separator;
    public static final String sPATH_BACKUP_DIRECTORY_NAME = sPATH_PARENT_DIRECTORY_NAME + File.separator + sPACKAGE_NAME + File.separator;

    //START STATUS
    public static final String STATUS_PAY = "11"; //付款
    public static final String STATUS_CAPTURE = "31"; //請款
    public static final String STATUS_REFUND = "12"; //退款
    public static final String STATUS_CHECKOUT = "50"; //結帳
    public static final String STATUS_REPRINT = "63"; //重印簽單

    //ECT Trans Type
    public static final String ECR_SALE_ALL_IN_ONE = "01";
    public static final String ECR_SALE = "11";
    public static final String ECR_REFUND_ALL_IN_ONE = "02";
    public static final String ECR_OFFLINE = "03";
    public static final String ECR_IPP_SALE = "04";
    public static final String ECR_REDEEM_SALE = "05";
    public static final String ECR_REFUND = "12";
    public static final String ECR_REDEEM_REFUND = "22";
    public static final String ECR_IPP_REFUND = "32";
    public static final String ECR_VOID = "30";
    public static final String ECR_SETTLEMENT = "50";


    /*  ECR HOST ID：
        03 台新銀行(一般信用卡/銀聯卡)主機、花旗銀行(大來卡)
        04 台新銀行(分期付款)主機
        05 台新銀行(紅利抵用)主機
        06 台新銀行(SmartPay)主機
        07 台新銀行(DCC)主機
        01 美國運通 */
    public static final String ECR_HOST_ID_TSB = "03";
    public static final String ECR_HOST_ID_DINERS = "02";
    public static final String ECR_HOST_ID_TSB_IN = "04";
    public static final String ECR_HOST_ID_TSB_RE = "05";
    public static final String ECR_HOST_ID_TSB_SMARTPAY = "06";
    public static final String ECR_HOST_ID_TSB_DCC = "07";
    public static final String ECR_HOST_ID_AMEX = "01";

    //OTHER APP RESPONSE CODE
    public final static String APPROVED = "0000";
    public final static String ERROR = "0001";
    public final static String CALL_BANK = "0002";
    public final static String TIME_OUT = "0003";
    public final static String FALL_BACK = "0004";
    public final static String APPROVED_FAIL = "0006";
    public final static String AMOUNT_ERROR = "0007";
    public final static String CONTACTLESS_ERROR = "0008";

    //ECR Card type
    public static final String ECR_CARD_TYPE_CUP = "01";
    public static final String ECR_CARD_TYPE_JCB = "03";
    public static final String ECR_CARD_TYPE_VISA = "04";
    public static final String ECR_CARD_TYPE_MASTER = "05";
    public static final String ECR_CARD_TYPE_AE = "06";
    public static final String ECR_CARD_TYPE_DISCOVER = "07";
    public static final String ECR_CARD_TYPE_U = "08";
    public static final String ECR_CARD_TYPE_SMARTPAY = "09";
    public static final String ECR_CARD_TYPE_EASYCARD = "10";
    public static final String ECR_CARD_TYPE_IPASS = "11";

    //Wallet Name
    public static final String ALIPAY_O = "ALIPAY_O";
    public static final String WEIXIN_O = "WEIXIN_O";
    public static final String LINEPAY_O = "LINEPAY_O";
    public static final String LINEPAY_I = "LINEPAY_I";
    public static final String PAYPLUS_I = "PAYPLUS_I";
    public static final String PI_I = "PI_I";
    public static final String ALLPAY_I = "ALLPAY_I";
    public static final String JKOS_I = "JKOS_I";
    public static final String LETSPAY_I = "LETSPAY_I";
    public static final String SKMPAY_I = "SKMPAY_I";
    public static final String TAIWANPAY_I = "TAIWAN_I";
}
