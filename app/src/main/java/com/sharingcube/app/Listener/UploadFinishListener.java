package com.sharingcube.app.Listener;

public interface UploadFinishListener {
    void uploadFinish(boolean bIsSuccess);

    void errorOccurred(String message);
}
