package com.sharingcube.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;

import com.sharingcube.app.R;
import com.sharingcube.app.utils.ConstantValue;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainGridViewAdapter extends BaseAdapter {
    List<Map<String, Object>> mItemList;
    Context mContext;
    OnChooseFuncListener mListener;

    public MainGridViewAdapter(Context context, List itemList)
    {
        mItemList = itemList;
        mContext = context;
        if(mListener == null)
        {
            mListener = (MainGridViewAdapter.OnChooseFuncListener) context;
        }
    }

    public interface OnChooseFuncListener
    {
        void OnChooseFunc(ConstantValue.MenuIcons menuIcons);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_view_item, null);
            itemViewHolder = new ItemViewHolder(convertView);
            itemViewHolder.rlFuncItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConstantValue.MenuIcons menuIcon = mappingMainFuncWithIndex(position);
                    mListener.OnChooseFunc(menuIcon);
                }
            });
            convertView.setTag(itemViewHolder);
        } else {
            itemViewHolder = (ItemViewHolder) convertView.getTag();
        }

        ConstantValue.MenuIcons menuIcon = (ConstantValue.MenuIcons) getItem(position);
        itemViewHolder.imgView.setImageDrawable(ContextCompat.getDrawable(mContext, menuIcon.getDrawable()));
//        itemViewHolder.txtView.setText(menuIcon.getChineseName());
//        itemViewHolder.rlFuncItem.setBackground(menuIcon.getIsFuncValid() == false ?
//                mContext.getResources().getDrawable(R.drawable.rect_invalid_with_index) :
//                mContext.getResources().getDrawable(R.drawable.rect_valid_with_index));

        return convertView;
    }

    private ConstantValue.MenuIcons mappingMainFuncWithIndex(int position) {
        switch (position)
        {
            case 0:
                return ConstantValue.MenuIcons.CREDIT_CARD;
            case 1:
                return ConstantValue.MenuIcons.E_PASS;
            case 2:
                return ConstantValue.MenuIcons.MOBILE_PAYMENT;
            case 3:
                return ConstantValue.MenuIcons.POINTS;
            case 4:
                return ConstantValue.MenuIcons.LET_E_GO;
            case 5:
                return ConstantValue.MenuIcons.SHARINGCUBE_POS;
            default:
                return null;
        }
    }

    class ItemViewHolder {

        @BindView(R.id.iv_img)
        ImageView imgView;
        @BindView(R.id.rl_func_item)
        RelativeLayout rlFuncItem;

        ItemViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
