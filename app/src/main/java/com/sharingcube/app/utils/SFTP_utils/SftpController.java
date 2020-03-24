package com.sharingcube.app.utils.SFTP_utils;

import android.os.AsyncTask;
import android.util.Log;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.SftpProgressMonitor;
import com.sharingcube.app.Listener.UploadFinishListener;
import com.sharingcube.app.utils.LogUtil;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import static com.sharingcube.app.utils.ConstantValue.SFTP_FOLDER;

/**
 * Controller class for SFTP functions. Performs SFTP
 * ls, get, put commands between local device and remote SSH
 * server. For each process a new sftpchannel is opened and closed
 * after completion.
 */
public class SftpController {

    /**
     * Tag name
     */
    public static final String TAG = "SftpController";

    /**
     * Remote directory path. The path to the current remote directory.
     */
    private String mCurrentPath = "/";


    /**
     * Creates instance of SftpController. Performs SFTP functions.
     */
    public SftpController() {

    }

    /**
     * Creates instance of SftpController. Performs SFTP functions.
     *
     * @param path path to chosen directory on remote host.
     */
    public SftpController(String path) {
        mCurrentPath = path;
    }


    /**
     * Resets the current path on remote server.
     */
    public void resetPathToRoot() {
        mCurrentPath = "/";
    }


    /**
     * Returns the path to the current directory on the
     * remote server.
     *
     * @return
     */
    public String getPath() {
        return mCurrentPath;
    }


    /**
     * Sets the path to current directory on the remote
     * server.
     *
     * @param path
     */
    public void setPath(String path) {
        mCurrentPath = path;
    }


    /**
     * Appends <b>relPath</b> to the current path on remote host.
     *
     * @param relPath relative path
     */
    public void appendToPath(String relPath) {
        if (mCurrentPath == null) {
            mCurrentPath = relPath;
        } else mCurrentPath += relPath;
    }


    /**
     * Disconnects SFTP.
     */
    public void disconnect() {
        //nothing yet
    }


    /**
     * Upload file(s) Task. Aysnc task for uploading local files to remote
     * server.
     */
    public class UploadTask extends AsyncTask<Void, Void, Boolean> {

        /**
         * JSch Session Instance
         */
        private Session mSession;

        /**
         * Progress dialog to monitor upload progress.
         */
//        private SftpProgressMonitor mProgressDialog;

        /**
         * Array of local files to be uploaded
         */
        private File[] mLocalFiles;

        private UploadFinishListener mUploadFinishListener;

        private ConnectionStatusListener mConnectionStatusListener;

        //
        // Constructor
        //

        public UploadTask(Session session, File[] localFiles, UploadFinishListener uploadFinishListener, ConnectionStatusListener connectionStatusListener) {
            mLocalFiles = localFiles;
            mSession = session;
            mUploadFinishListener = uploadFinishListener;
            mConnectionStatusListener = connectionStatusListener;
        }

        @Override
        protected void onPreExecute() {
            //nothing
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            boolean success = true;
            try {
                uploadFiles(mSession, mLocalFiles);
            } catch (JSchException e) {
                e.printStackTrace();
                LogUtil.e(TAG, "JSchException " + e.getMessage());
                success = false;
            } catch (IOException e) {
                e.printStackTrace();
                LogUtil.e(TAG, "IOException " + e.getMessage());
                success = false;
            } catch (SftpException e) {
                e.printStackTrace();
                LogUtil.e(TAG, "SftpException " + e.getMessage());
                success = false;
            } finally {
                return success;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            Log.d(TAG,"上傳成功");
            if(success == false)
            {
                return;
            }
            else
            {
                if(mLocalFiles[0].exists())
                {
                    Log.d(TAG, "[已刪除]");
                    mLocalFiles[0].delete();
                }
            }
//            mUploadFinishListener.uploadFinish(success);
            mConnectionStatusListener.onAsyncFinished(success);
        }

    }


    /**
     * Uploads the files in <b>localFiles</b> to the current directory on
     * remote server.
     *
     * @param session    the Jsch SSH session instance
     * @param localFiles array of files to be uploaded
     * @throws JSchException
     * @throws java.io.IOException
     * @throws SftpException
     */
    public void uploadFiles(Session session, File[] localFiles) throws JSchException, IOException, SftpException {
        if (session == null || !session.isConnected()) {
            session.setTimeout(20000);
            session.connect();
        }

        Channel channel = session.openChannel("sftp");
        channel.setInputStream(null);
        channel.connect();
        ChannelSftp channelSftp = (ChannelSftp) channel;
        channelSftp.cd(SFTP_FOLDER);
        //TODO:
        for (File file : localFiles) {
            Log.d(TAG, "file_path:" + file.getPath());
            Log.d(TAG, "file_name:" + file.getName());
            channelSftp.put(file.getPath(), file.getName(), ChannelSftp.APPEND);
        }

        channelSftp.disconnect();
    }

    /**
     * SHell ls command to list remote host's file list for the given directory.
     *
     * @param session             the session
     * @param taskCallbackHandler handle ls success or failure
     * @param path                relative path from current directory.
     */
    public void lsRemoteFiles(Session session, TaskCallbackHandler taskCallbackHandler, String path) {
        mCurrentPath = path == null || path == "" ? mCurrentPath : mCurrentPath + path + "/";
        new LsTask(session, taskCallbackHandler).execute();
    }


    /**
     * Shows all files (command ls) including directories.
     */
    private class LsTask extends AsyncTask<Void, Void, Boolean> {

        /**
         * Vector of files in the remote server's current
         * directory.
         */
        private Vector<ChannelSftp.LsEntry> mRemoteFiles;

        /**
         * Callback handler for task completion ot failure.
         */
        private TaskCallbackHandler mTaskCallbackHandler;

        /**
         * JSch session instance.
         */
        private Session mSession;


        /**
         * Async Task for listing contents of remote directory.
         *
         * @param session currently connected Jsch session.
         * @param tch     callback handler for completion or failure.
         */
        public LsTask(Session session, TaskCallbackHandler tch) {

            mSession = session;
            mTaskCallbackHandler = tch;
        }

        @Override
        protected void onPreExecute() {
            if(mTaskCallbackHandler != null)
                mTaskCallbackHandler.OnBegin();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            LogUtil.d(TAG, "current path is " + mCurrentPath);

            boolean success = true;
            Channel channel = null;
            try {
                mRemoteFiles = null;
                if (true) {
                    channel = mSession.openChannel("sftp");
                    channel.setInputStream(null);
                    channel.connect();
                    ChannelSftp channelsftp = (ChannelSftp) channel;
                    String path = mCurrentPath == null ? "/" : mCurrentPath;
                    mRemoteFiles = channelsftp.ls(path);
                    if (mRemoteFiles == null) {
                        LogUtil.d(TAG, "remote file list is null");
                    } else {
                        for (ChannelSftp.LsEntry e : mRemoteFiles) {
                            //Log.v(TAG," file "+ e.getFilename());
                        }
                    }
                }
            } catch (Exception e) {
                Log.v(TAG, "sftprunnable exptn " + e.getCause());
                success = false;
                return success;
            }
            if (channel != null) {
                channel.disconnect();
            }

            return true;
        }


        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                if (mTaskCallbackHandler != null) {
                    mTaskCallbackHandler.onTaskFinished(mRemoteFiles);
                }
            } else {
                if (mTaskCallbackHandler != null) {
                    mTaskCallbackHandler.onFail();
                }
            }
        }
    }


    /**
     * Downloads the remote file at srcPath location on remote host, to the out
     * location on local device.
     * Uses SFTP get function.
     *
     * @param session
     * @param srcPath
     * @param out
     * @param spm
     * @throws JSchException
     * @throws SftpException
     */
    public void downloadFile(Session session, String srcPath, String out, SftpProgressMonitor spm, int TransferType) throws JSchException, SftpException {
        if (session == null || !session.isConnected()) {
            session.setTimeout(20000);

            session.connect();
        }

        Channel channel = session.openChannel("sftp");
        ChannelSftp sftpChannel = (ChannelSftp) channel;
        sftpChannel.connect();
//        sftpChannel.get(srcPath, out, spm, ChannelSftp.OVERWRITE);
        sftpChannel.get(srcPath, out, spm, TransferType);
        sftpChannel.disconnect();
    }

    /**
     * Async Task for downloading remote file (sftp get command).
     */
    public class DownloadTask extends AsyncTask<Void, Void, Boolean> {

        Session mSession;
        String mSrcPath;
        String mOut;
        SftpProgressMonitor mSpm;
        ConnectionStatusListener mListener;
        int mTransferMode;

        /**
         * Async Task for downloading remote file to local device (SFTP get command).
         *
         * @param session Current connected JSch Session
         * @param srcPath Path to target file on remote server
         * @param out     Path to output location on local device.
         * @param spm     Progress monitor, to monitor download progress.
         */
        public DownloadTask(Session session, String srcPath, String out, SftpProgressMonitor spm, int TransferMode, ConnectionStatusListener mListener) {
            mSession = session;
            mSrcPath = srcPath;
            mOut = out;
            mSpm = spm;
            mTransferMode = TransferMode;
            this.mListener = mListener;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            Boolean result = false;
            try {
                Log.v(TAG, " path: " + mSrcPath + ", out path: " + mOut);
                //downloadFile(mSession, mCurrentPath + mSrcPath, mOut, mSpm);
                downloadFile(mSession, mSrcPath, mOut, mSpm, mTransferMode);
                result = true;

            }
            catch (JSchException e) {
                LogUtil.e(TAG, "JSchException " + e.getMessage());
            }
            catch (SftpException e) {
                LogUtil.e(TAG, "SftpException " + e.getMessage());
            }
            catch (Exception e) {
                LogUtil.e(TAG, "EXCEPTION " + e.getMessage());

            } finally {
                return result;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success == null){
                return;
            }
            mListener.onAsyncFinished(success);
//            if (success) {
//                LogUtil.i(TAG, "SFTP Download Success!!");
//                if(mSpm != null){
//                    mSpm.end();
//                }
//            }
        }
    }
}
