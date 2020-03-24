package com.sharingcube.app.utils.SFTP_utils;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.sharingcube.app.Listener.UploadFinishListener;
import com.sharingcube.app.R;
import com.sharingcube.app.utils.ConstantValue;
import com.sharingcube.app.utils.ResourceUtil;

import java.io.File;
import java.io.IOException;
import java.util.Queue;

public class UploadFileManager {
    String TAG = getClass().getSimpleName();

    Queue<File> needToUploadQueue;

    String sftp_host = ConstantValue.SFTP_HOST;

    String sftp_port = ConstantValue.SFTP_PORT;

    Context context;

    SessionController mSessionController;

    UploadFinishListener downloadFinishListener = null;

    boolean showProgressDialog = true;

    public UploadFileManager(Context context, Queue<File> queue, UploadFinishListener listener) {
        this.context = context;
        this.needToUploadQueue = queue;
        this.downloadFinishListener = listener;
    }

    public void ConnectToSftp(String userName, String password) {
        Log.d(TAG, "連接Server中...");
        SessionUserInfo mSUI;
        ConnectionStatusListener mListener = new ConnectionStatusListener() {
            @Override
            public void onDisconnected() {
//                dismissProgressDialog();
//                LogUtil.i(TAG, "ConnectToSftp() - SFTP onDisconnected");
                Log.d(TAG, "ConnectToSftp() - SFTP onDisconnected");
            }

            @Override
            public void onConnected() {

//                LogUtil.i(TAG, "ConnectToSftp() - SFTP onConnected");
                Log.d(TAG, "ConnectToSftp() - SFTP onConnected");
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

                // 開始執行任務下載
                //new StartUploadFile().start();
                StartUploadFile();
            }

            @Override
            public void onConnectedFail() {
                Log.d(TAG, "ConnectToSftp() - SFTP Connect Fail");
                if (downloadFinishListener != null) {
                    downloadFinishListener.errorOccurred(ResourceUtil.getString(R.string.fail_to_connect_to_sftp));
                }
                try {
                    mSessionController.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAsyncFinished(boolean result) {

                Log.d(TAG, "ConnectToSftp() - SFTP AsyncFinish");
                // Get Content and remove it from the resourceQueue.
//                File file = needToUploadQueue.poll();

                // 下載成功
//                if (result) {
//                    DatabaseUtilities dbUtil = App.getInstance().getDbUtils();
//                    if (dbUtil.getAppVersionCodeByPackageName(packageName).equals(versionCode)) {
//                        dbUtil.UpdateAPKSavedPathInDB(jsonObject);
//                    }
//                }
                // 下載失敗
//                else {

//                    // 刪除下載失敗資料
//                    deleteDownloadFailRecord(jsonObject);
//
//                    try {
//                        mSessionController.disconnect();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    if (downloadFinishListener != null) {
//                        downloadFinishListener.errorOccurred(ResourceUtil.getString(R.string.fail_to_download_apk_from_sftp));
//
//                        // 下載失敗則不執行任何步驟
//                        return;
////                        // 如果是ManualInstallation呼叫的，則不繼續執行任何步驟。
////                        if (callingClassName.equals(ManualInstallation.class.getSimpleName())) {
////                            return;
////                        }
//                    }
//
//                    UpdateAppUpgradeProcedure.reportAppState(
//                            UpdateState.FAILURE, DOWNLOAD_APK_FAILURE, packageName, Integer.parseInt(versionCode));

//                }

                // 執行完下載任務
                if (needToUploadQueue.size() == 0) {
                    try {
                        mSessionController.disconnect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                // 還有剩餘的下載任務
                else {
                    StartUploadFile();
                }
            }
        };


        mSUI = new SessionUserInfo(userName, sftp_host, password, Integer.parseInt(sftp_port));

        mSessionController = SessionController.getSessionController();
        mSessionController.setUserInfo(mSUI);
        mSessionController.connect();

        if (mListener != null)
            SessionController.getSessionController().setConnectionStatusListener(mListener);
    }

    private void StartUploadFile() {

        Handler handler = new Handler(context.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                Log.d(TAG, "Upload Queue Size: " + needToUploadQueue.size());
                // 還有任務需下載
                if (needToUploadQueue.size() != 0) {
                    Log.d(TAG, "尚有 " + needToUploadQueue.size() + " 個未上傳");
                    File file = needToUploadQueue.poll();

                    File[] files = new File[]{file};
                    mSessionController.uploadFiles(files, downloadFinishListener);

                    Log.d(TAG, "Start to upload apk!");
                }
                // 所有任務已下載完成
                else {
                    Log.d(TAG, "finish!");
                    try {
                        // 斷線
                        mSessionController.disconnect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    downloadFinishListener.uploadFinish(true);
                }
            }
        });

    }

}
