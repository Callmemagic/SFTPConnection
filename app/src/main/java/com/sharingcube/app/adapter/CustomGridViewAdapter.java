package com.sharingcube.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sharingcube.app.Listener.OnChooseFuncListener;
import com.sharingcube.app.R;
import com.sharingcube.app.utils.ConstantValue;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sharingcube.app.utils.ConstantValue.GRID_TYPE_CREDIT_CARD;
import static com.sharingcube.app.utils.ConstantValue.GRID_TYPE_CREDIT_CARD_TX;
import static com.sharingcube.app.utils.ConstantValue.GRID_TYPE_MOBILE_PAYMENT;
import static com.sharingcube.app.utils.ConstantValue.GRID_TYPE_TRANS_TYPE;

public class CustomGridViewAdapter extends BaseAdapter {
    List mItemList;
    Context mContext;
    OnChooseFuncListener mListener;
    String mStrGridType;

    public CustomGridViewAdapter(Context context, List dataList, String gridType)
    {
        mContext = context;
        mItemList = dataList;
        mStrGridType = gridType;
        if(mListener == null)
        {
            mListener = (OnChooseFuncListener) context;
        }
    }


    @Override
    public int getCount()
    {
        //取得 GridView 列表 Item 的數量
        if(mItemList == null)
        {
            return 0;
        }
        return mItemList.size();
    }

    @Override
    public Object getItem(int position)
    {
        //取得 GridView列表於 position 位置上的 Item
        if(mItemList == null)
        {
            return null;
        }
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ItemViewHolder itemViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_view_item_func, null);
            itemViewHolder = new ItemViewHolder(convertView);
            itemViewHolder.rlFuncItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NotifyType(position);
                }
            });
            convertView.setTag(itemViewHolder);
        } else {
            itemViewHolder = (ItemViewHolder) convertView.getTag();
        }

        processViews(position, itemViewHolder);

        return convertView;
    }

    private void processViews(int position, ItemViewHolder itemViewHolder) {

        switch (mStrGridType)
        {
            case GRID_TYPE_TRANS_TYPE:
                itemViewHolder.ivFunc.setBackgroundResource(((ConstantValue.TransType) getItem(position)).getDrawable());
                break;
            case GRID_TYPE_CREDIT_CARD:
                itemViewHolder.ivFunc.setBackgroundResource(((ConstantValue.CreditCardType) getItem(position)).getDrawable());
                break;
            case GRID_TYPE_CREDIT_CARD_TX:
                itemViewHolder.ivFunc.setBackgroundResource(((ConstantValue.CreditCardTxType) getItem(position)).getDrawable());
                break;
            case GRID_TYPE_MOBILE_PAYMENT:
                itemViewHolder.ivFunc.setBackgroundResource(((ConstantValue.MobilePaymentType) getItem(position)).getDrawable());
            default:
                break;
        }

    }

    private void NotifyType(int position) {
        switch (mStrGridType)
        {
            case GRID_TYPE_TRANS_TYPE:
                ConstantValue.TransType transType = mappingTransTypeWithIndex(position);
                mListener.OnChooseFunc(transType.getName());
                break;
            case GRID_TYPE_CREDIT_CARD:
                ConstantValue.CreditCardType cardType = mappingTypeWithIndex(position);
                mListener.OnChooseFunc(cardType.getName());
                break;
            case GRID_TYPE_CREDIT_CARD_TX:
                ConstantValue.CreditCardTxType cardTxType = mappingTxTypeWithIndex(position);
                mListener.OnChooseFunc(cardTxType.getName());
                break;
            case GRID_TYPE_MOBILE_PAYMENT:
                ConstantValue.MobilePaymentType mobildePaymentType = mappingMobileTypeWithIndex(position);
                mListener.OnChooseFunc(mobildePaymentType.getName());
            default:
                break;
        }

    }

    private ConstantValue.TransType mappingTransTypeWithIndex(int index) {
        switch (index)
        {
            case 0:
                return ConstantValue.TransType.CREDIT_CARD;
            case 1:
                return ConstantValue.TransType.E_PASS;
            case 2:
                return ConstantValue.TransType.MOBILE_PAYMENT;
            case 3:
                return ConstantValue.TransType.CUP;
            case 4:
                return ConstantValue.TransType.SETTLE;
            case 5:
                return ConstantValue.TransType.MORE_FUNC;
            case 6:
                return ConstantValue.TransType.REPRINT;
            case 7:
                return ConstantValue.TransType.SETTING;
            case 8:
                return ConstantValue.TransType.ABOUT_E_PAY;
            default:
                return null;
        }
    }


    private ConstantValue.CreditCardType mappingTypeWithIndex(int index) {
        switch (index)
        {
            case 0:
                return ConstantValue.CreditCardType.CREDIT_CARD;
            case 1:
                return ConstantValue.CreditCardType.REDEEM_DISCOUNT;
            case 2:
                return ConstantValue.CreditCardType.IPP;
            default:
                return null;
        }
    }

    private ConstantValue.CreditCardTxType mappingTxTypeWithIndex(int index) {
        switch (index)
        {
            case 0:
                return ConstantValue.CreditCardTxType.SALE;
            case 1:
                return ConstantValue.CreditCardTxType.VOID;
            case 2:
                return ConstantValue.CreditCardTxType.REFUND;
            default:
                return null;
        }
    }

    private ConstantValue.MobilePaymentType mappingMobileTypeWithIndex(int index) {
        switch (index)
        {
            case 0:
                return ConstantValue.MobilePaymentType.REFUND;
            case 1:
                return ConstantValue.MobilePaymentType.REPRINT;
            default:
                return null;
        }
    }

    class ItemViewHolder {

        @BindView(R.id.rl_func_item)
        RelativeLayout rlFuncItem;
        @BindView(R.id.iv_img)
        ImageView ivFunc;

        ItemViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
