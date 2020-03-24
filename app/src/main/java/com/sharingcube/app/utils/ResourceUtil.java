package com.sharingcube.app.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.util.TypedValue;

/**
 * Created by Danielchen on 2017/3/21.
 */

public class ResourceUtil {

    private final static String TAG = ConstantValue.APP_LOG_TAG + ResourceUtil.class.getSimpleName();

    private static ResourceUtil mInstance;
    static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    public static String getString(int id) {
        return mContext.getResources().getString(id);
    }

    public static String getString(String name) {
        String value;
        try {
            value = mContext.getResources().getString(getResourcesIdByName("string", name));
        } catch (Exception e) {
            e.printStackTrace();
            Log.w(TAG, "String is not find:" + name);
            return "";
        }
        return value;
    }

    public static String[] getStringAry(int id) {
        return mContext.getResources().getStringArray(id);
    }

    public static String[] getStringAry(String name) {
        String[] valus = new String[0];
        try {
            valus = mContext.getResources().getStringArray(getResourcesIdByName("array", name));
        } catch (Exception e) {
            e.printStackTrace();
            Log.w(TAG, "StringArray is not find:" + name);
            return valus;
        }
        return valus;
    }

    public static int getInteger(String name) {
        int value;
        try {
            value = mContext.getResources().getInteger(getResourcesIdByName("integer", name));
        } catch (Exception e) {
            e.printStackTrace();
            Log.w(TAG, "String is not find:" + name);
            return 0;
        }
        return value;
    }


    public static int getResourcesIdByName(String packageName, String resourcesName) {
        Resources resources = mContext.getResources();
        int id = resources.getIdentifier(resourcesName, packageName, mContext.getPackageName());
        if (id == 0) {
            Log.e(TAG, "Resource Not Found！resourcesName:" + resourcesName);
        }
        return id;
    }

    public static int dp2Px(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                mContext.getResources().getDisplayMetrics());
    }
}
