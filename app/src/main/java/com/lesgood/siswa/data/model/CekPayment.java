package com.lesgood.siswa.data.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class CekPayment{

	@SerializedName("merchantCode")
	private String merchantCode;

	@SerializedName("signature")
	private String signature;

	@SerializedName("merchantOrderId")
	private String merchantOrderId;

	public void setMerchantCode(String merchantCode){
		this.merchantCode = merchantCode;
	}

	public String getMerchantCode(){
		return merchantCode;
	}

	public void setSignature(String signature){
		this.signature = signature;
	}

	public String getSignature(){
		return signature;
	}

	public void setMerchantOrderId(String merchantOrderId){
		this.merchantOrderId = merchantOrderId;
	}

	public String getMerchantOrderId(){
		return merchantOrderId;
	}

	@Override
 	public String toString(){
		return 
			"CekPayment{" + 
			"merchantCode = '" + merchantCode + '\'' + 
			",signature = '" + signature + '\'' + 
			",merchantOrderId = '" + merchantOrderId + '\'' + 
			"}";
		}
}