package com.sharingcube.app.model;

import com.google.gson.annotations.SerializedName;

/**
 * author: Joe Cheng
 */
public class EcrResponseBean {
    @SerializedName("ECR_Response_Code")
    private String ECR_Response_Code;

    @SerializedName("Response_Code")
    private String Response_Code;

    public String getECR_Response_Code() {
        return ECR_Response_Code;
    }

    public String getResponse_Code() {
        return Response_Code;
    }
}
