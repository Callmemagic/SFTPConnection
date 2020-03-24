package com.sharingcube.app.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.sharingcube.app.R;
import com.sharingcube.app.utils.ConstantValue;
import com.sharingcube.app.utils.CustomDialogUtility;
import com.sharingcube.app.utils.LogUtil;
import com.sharingcube.app.utils.ResourceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sharingcube.app.utils.TransferParamsKey.CUP_Indicator;
import static com.sharingcube.app.utils.TransferParamsKey.Host_ID;
import static com.sharingcube.app.utils.TransferParamsKey.Installment_Period;
import static com.sharingcube.app.utils.TransferParamsKey.Source_Class;
import static com.sharingcube.app.utils.TransferParamsKey.Source_Package;
import static com.sharingcube.app.utils.TransferParamsKey.Trans_Amount;
import static com.sharingcube.app.utils.TransferParamsKey.Trans_Type;

public class ChooseCardTypeDialog extends Dialog {

    private final String TAG = ConstantValue.APP_LOG_TAG + getClass().getSimpleName();
    private String m_finalSum;
    private String m_period;
    private String m_TransType;
    private Gson gson = new Gson();
    private Context mContext;
    private OnClickButtonListener mListener;

    @BindView(R.id.ll_btn_credit_card)
    LinearLayout m_ll_btn_credit_card;
    @BindView(R.id.ll_btn_cup_card)
    LinearLayout m_ll_btn_cup_card;
    @BindView(R.id.ll_btn_smartpay)
    LinearLayout m_ll_btn_smartpay;

    public interface OnClickButtonListener {
        void onChoseCardType(JSONObject jsonObject);
    }

    public void setChoseCardTypeListener(OnClickButtonListener listener)
    {
        mListener = listener;
    }

    public ChooseCardTypeDialog(Context context, String finalSum, String strTransType) {
        super(context);
        mContext = context;
        m_finalSum = finalSum;
        m_TransType = strTransType;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dlg_choose_card_type);

        ButterKnife.bind(this);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ResourceUtil.dp2Px(280), ResourceUtil.dp2Px(200));
        this.setCancelable(true);

        m_ll_btn_credit_card.setOnClickListener(m_CardTypeListener);
        m_ll_btn_cup_card.setOnClickListener(m_CardTypeListener);
        m_ll_btn_smartpay.setOnClickListener(m_CardTypeListener);
    }

    public ChooseCardTypeDialog(Context context, String finalSum, String period, String strTransType) {
        super(context);
        mContext = context;
        m_finalSum = finalSum;
        m_period = period;
        m_TransType = strTransType;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dlg_choose_card_type);

        ButterKnife.bind(this);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ResourceUtil.dp2Px(280), ResourceUtil.dp2Px(200));
        this.setCancelable(true);

        m_ll_btn_credit_card.setOnClickListener(m_CardTypeListener);
        m_ll_btn_cup_card.setOnClickListener(m_CardTypeListener);
        m_ll_btn_smartpay.setOnClickListener(m_CardTypeListener);
    }

    View.OnClickListener m_CardTypeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onClickButton(v);
        }
    };

    public void onClickButton(View v) {

        try {
            JSONObject objParams = new JSONObject();
            objParams.put(Trans_Type, m_TransType);
            objParams.put(Trans_Amount, m_finalSum);
            if (!TextUtils.isEmpty(m_period))
                objParams.put(Installment_Period, m_period.length() == 2 ? "0" + m_period : m_period);
            objParams.put(Source_Package, getContext().getPackageName());
            objParams.put(Source_Class, getClass().getName());

            switch (v.getId()) {
                case R.id.ll_btn_credit_card:
                    objParams.put(CUP_Indicator, "N");
                    break;
                case R.id.ll_btn_cup_card:
                    objParams.put(CUP_Indicator, "C");
                    break;
                case R.id.ll_btn_smartpay:
                    objParams.put(CUP_Indicator, "N");
                    objParams.put(Host_ID, "06");
                    break;
            }
            objParams.put("TIME", CustomDialogUtility.getCurrTimestamp());

            mListener.onChoseCardType(objParams);
            Log.d(TAG, "Json REQ : " + objParams);
            this.dismiss();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            LogUtil.d("Can't find EDC App!");
        }
    }

}
