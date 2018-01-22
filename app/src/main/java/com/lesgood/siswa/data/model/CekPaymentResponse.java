package com.lesgood.siswa.data.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class CekPaymentResponse{

	@SerializedName("reference")
	private String reference;

	@SerializedName("amount")
	private String amount;

	@SerializedName("merchantOrderId")
	private String merchantOrderId;

	@SerializedName("statusMessage")
	private String statusMessage;

	@SerializedName("statusCode")
	private String statusCode;

	public void setReference(String reference){
		this.reference = reference;
	}

	public String getReference(){
		return reference;
	}

	public void setAmount(String amount){
		this.amount = amount;
	}

	public String getAmount(){
		return amount;
	}

	public void setMerchantOrderId(String merchantOrderId){
		this.merchantOrderId = merchantOrderId;
	}

	public String getMerchantOrderId(){
		return merchantOrderId;
	}

	public void setStatusMessage(String statusMessage){
		this.statusMessage = statusMessage;
	}

	public String getStatusMessage(){
		return statusMessage;
	}

	public void setStatusCode(String statusCode){
		this.statusCode = statusCode;
	}

	public String getStatusCode(){
		return statusCode;
	}

	@Override
 	public String toString(){
		return 
			"CekPaymentResponse{" + 
			"reference = '" + reference + '\'' + 
			",amount = '" + amount + '\'' + 
			",merchantOrderId = '" + merchantOrderId + '\'' + 
			",statusMessage = '" + statusMessage + '\'' + 
			",statusCode = '" + statusCode + '\'' + 
			"}";
		}
}