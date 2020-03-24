package com.sharingcube.app.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sharingcube.app.Listener.UploadFinishListener;
import com.sharingcube.app.model.EDCExchangeData;
import com.sharingcube.app.model.SCPExchangeData;
import com.sharingcube.app.utils.SFTP_utils.UploadFileManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Queue;

import static com.sharingcube.app.utils.ConstantValue.SFTP_ACCT;
import static com.sharingcube.app.utils.ConstantValue.SFTP_PWD;
import static com.sharingcube.app.utils.TransferParamsKey.EDC_RESPONSE;
import static com.sharingcube.app.utils.TransferParamsKey.SCP_RESPONSE;

public class WriteFileUtil {
    public String TAG = getClass().getSimpleName();
    private static WriteFileUtil mInstance;
    private static Context mContext;
    private Gson gson = new Gson();
    private File outPutFile;
    private String strPathName;

    public static WriteFileUtil getInstance(Context context)
    {
        mContext = context;
        if(mInstance == null)
        {
            mInstance = new WriteFileUtil();
        }
        return mInstance;
    }


    public File WriteEDC600ByteFile(Intent data) {
        String fileName = "";
        String response;

        response = data.getStringExtra(EDC_RESPONSE);
        //解析Response
        EDCExchangeData edcExchangeData = gson.fromJson(response, EDCExchangeData.class);

        //組檔名 = 特店ID(15) + 終端ID(8) + 交易日期(6) + 交易時間(6)
        fileName = edcExchangeData.getMerchant_ID() + edcExchangeData.getEDC_Terminal_ID() +
                edcExchangeData.getTrans_Date() + edcExchangeData.getTrans_Time();
        LogUtil.d("Write fileName：" + fileName);

        //組600 byte 資料
        String str600Byte = ByteUtil.ComposeEDC600ByteData(edcExchangeData);
        LogUtil.d("600Byte Data：" + str600Byte);

        //把600 byte 資料寫入檔案
        strPathName = ConstantValue.sPATH_UPLOAD_DIRECTORY_NAME + fileName +".txt";
        outPutFile = new File(strPathName);
        if(isExtStorageWritable())
        {
            appendStringToFile(str600Byte, outPutFile);
        }

        if(outPutFile.exists())
        {
            Toast.makeText(mContext, "寫檔成功", Toast.LENGTH_SHORT).show();
            LogUtil.d("file exists! Upload!");

            return outPutFile;
        }
        else
        {
            Toast.makeText(mContext, "寫檔失敗", Toast.LENGTH_SHORT).show();
        }

        return null;
    }

    public File WriteSCP600ByteFile(Intent data) {
        String fileName = "";
        String response;

        response = data.getStringExtra(SCP_RESPONSE);
        //解析Response
        SCPExchangeData scpExchangeData = gson.fromJson(response, SCPExchangeData.class);

        String strMID = scpExchangeData.getMerchantId();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(strMID);

        while (stringBuilder.length() < 15)
        {
            stringBuilder.append("0");
        }

        //組檔名 = 特店ID(15) + 終端ID(8) + 交易日期(6) + 交易時間(6)
        fileName = stringBuilder.toString() + scpExchangeData.getEDCTerminalId() +
                scpExchangeData.getTxnDate() + scpExchangeData.getTxnTime();
        LogUtil.d("Write fileName：" + fileName);

        //組600 byte 資料
        String str600Byte = ByteUtil.ComposeSCP600ByteData(scpExchangeData);
        LogUtil.d("600Byte Data：" + str600Byte);

        //把600 byte 資料寫入檔案
        strPathName = ConstantValue.sPATH_UPLOAD_DIRECTORY_NAME + fileName +".txt";
        File outPutFile = new File(strPathName);
        if(isExtStorageWritable())
        {
            appendStringToFile(str600Byte, outPutFile);
        }

        if(outPutFile.exists())
        {
            Toast.makeText(mContext, "[寫檔成功]", Toast.LENGTH_SHORT).show();
            LogUtil.d("file exists! Upload!");

            return outPutFile;

        }
        else
        {
            Toast.makeText(mContext, "[寫檔失敗]", Toast.LENGTH_SHORT).show();
        }
        return null;
    }


    public void UploadFilesBySFTP(Context context, Queue<File> uploadQueue, UploadFinishListener uploadFinishListener) {
        Log.d(TAG, "準備開始上傳");
        UploadFileManager uploadFileManager = new UploadFileManager(context, uploadQueue, uploadFinishListener);
        uploadFileManager.ConnectToSftp(SFTP_ACCT, SFTP_PWD);
    }

    //寫檔
    public void appendStringToFile(final String appendContents, final File file) {

        try {
            if(!file.exists()) {
                if (file.getParentFile() != null)
                    file.getParentFile().mkdirs();
            }

            if (file != null) {
                file.createNewFile(); // ok if returns false, overwrite
                Writer out = new BufferedWriter(new FileWriter(file, true), 1024);
                out.write(appendContents);
                out.close();
            }
        } catch (IOException e) {
            Log.e("ToFile", "Error appending string data to file " + e.getMessage(), e);
        }
    }

    //檢查外部儲存體是否可以進行寫入
    public boolean isExtStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

}
