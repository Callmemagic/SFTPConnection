package com.sharingcube.app.utils;

import android.util.Log;

import static com.sharingcube.app.utils.ConstantValue.APP_LOG_TAG;

/**
 * Created by Danielchen on 2017/8/9.
 */

public class LogUtil {

    private static final String TAG = "ECR";

    private static boolean mDebugLog = false;
    private static boolean mInfo = false;

    public static void enableDebug(boolean debug){
        LogUtil.mDebugLog = debug;
    }

    public static void enableInfo(boolean mInfo){
        LogUtil.mInfo = mInfo;
    }

    public static  void d(String msg) {
        if (mDebugLog) Log.d(TAG, msg);
    }

    public static  void d(String tag, String msg) {
        if (mDebugLog) Log.d(APP_LOG_TAG + tag, msg);
    }

    public static void i(String msg) {
        if (mInfo) Log.i(TAG, msg);
    }

    public static void i(String tag, String msg) {
        if (mInfo) Log.i(APP_LOG_TAG + tag, msg);
    }

    public static void e(String msg) {
        Log.e(TAG,  msg);
    }

    public static void e(String tag, String msg) {
        Log.e(APP_LOG_TAG + tag,  msg);
    }

    public static void e(String tag, String msg, Throwable t) {
        Log.e(APP_LOG_TAG + tag,  msg, t);
    }

    public static void e(Throwable t) {
        Log.e(TAG, t.getMessage(), t);
    }

    public static void w(String msg) {
        Log.w(TAG, msg);
    }

}
