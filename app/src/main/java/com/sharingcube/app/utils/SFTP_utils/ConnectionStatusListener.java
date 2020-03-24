package com.sharingcube.app.utils.SFTP_utils;

/**
 * Interface that must be implemented by any object wishing to Listen
 * to the Jsch connection status (either connected or disconnected).
 * Created by Jon Hough 7/31/14.
 */
public interface ConnectionStatusListener {

    /**
     * Handles event of Session not connected
     */
    public void onDisconnected();

    /**
     * Handles event of Session connected
     */
    public void onConnected();

    public void onConnectedFail();

    public void onAsyncFinished(boolean result);
}
