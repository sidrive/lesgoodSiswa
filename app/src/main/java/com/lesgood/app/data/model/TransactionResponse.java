package com.lesgood.app.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Agus on 5/13/17.
 */

public class TransactionResponse {
    @SerializedName("merchantCode")
    @Expose
    private String merchantCode;
    @SerializedName("reference")
    @Expose
    private String reference;
    @SerializedName("paymentUrl")
    @Expose
    private String paymentUrl;
    @SerializedName("statusCode")
    @Expose
    private Object statusCode;
    @SerializedName("statusMessage")
    @Expose
    private Object statusMessage;

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    public Object getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Object statusCode) {
        this.statusCode = statusCode;
    }

    public Object getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(Object statusMessage) {
        this.statusMessage = statusMessage;
    }

    @Override
    public String toString() {
        return "TransactionResponse{" +
            "merchantCode='" + merchantCode + '\'' +
            ", reference='" + reference + '\'' +
            ", paymentUrl='" + paymentUrl + '\'' +
            ", statusCode=" + statusCode +
            ", statusMessage=" + statusMessage +
            '}';
    }
}
