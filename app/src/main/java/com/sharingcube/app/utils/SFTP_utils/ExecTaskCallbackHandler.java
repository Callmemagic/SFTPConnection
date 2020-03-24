package com.sharingcube.app.utils.SFTP_utils;


public interface ExecTaskCallbackHandler {

    void onFail();

    void onComplete(String completeString);
}
