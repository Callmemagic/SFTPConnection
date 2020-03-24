package com.sharingcube.app.utils;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sharingcube.app.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CustomDialogUtility {

    private final static String TAG = ConstantValue.APP_LOG_TAG + "CustomDialogUtility";

    public static void showDialog(Context context, String title, String msg) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(msg)
                .positiveText(R.string.confirm)
                .show();
    }

    public static void showDialog(Context context, String title, String msg, MaterialDialog.SingleButtonCallback callback) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(msg)
                .positiveText(R.string.confirm)
                .onPositive(callback)
                .cancelable(false)
                .show();
    }

    public static void showDialogWithOKandCancel(Context context, String title, String msg, MaterialDialog.SingleButtonCallback callback) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(msg)
                .positiveText(R.string.confirm)
                .negativeText(R.string.cancel)
                .onPositive(callback)
                .onNegative(callback)
                .show();
    }

    public static String getCurrTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Taipei"));
        return sdf.format(new Date());
    }

}
