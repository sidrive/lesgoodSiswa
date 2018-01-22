package com.lesgood.siswa.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Agus on 5/13/17.
 */

public class Transaction {

    @SerializedName("merchantCode")
    @Expose
    private String merchantCode;
    @SerializedName("paymentAmount")
    @Expose
    private Integer paymentAmount;
    @SerializedName("paymentMethod")
    @Expose
    private String paymentMethod;
    @SerializedName("merchantOrderId")
    @Expose
    private String merchantOrderId;
    @SerializedName("productDetails")
    @Expose
    private String productDetails;
    @SerializedName("merchantUserInfo")
    @Expose
    private String merchantUserInfo;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("itemDetails")
    @Expose
    private List<ItemDetail> itemDetails = null;
    @SerializedName("callbackUrl")
    @Expose
    private String callbackUrl;
    @SerializedName("returnUrl")
    @Expose
    private String returnUrl;
    @SerializedName("signature")
    @Expose
    private String signature;

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public Integer getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Integer paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public String getMerchantUserInfo() {
        return merchantUserInfo;
    }

    public void setMerchantUserInfo(String merchantUserInfo) {
        this.merchantUserInfo = merchantUserInfo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<ItemDetail> getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(List<ItemDetail> itemDetails) {
        this.itemDetails = itemDetails;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

}
