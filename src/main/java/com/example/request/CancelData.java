package com.example.request;

import com.google.gson.annotations.SerializedName;

public class CancelData {

	@SerializedName("imp_uid")
	private String imp_uid;
	
	@SerializedName("merchant_uid")
	private String merchant_uid;
	
	@SerializedName("amount")
	private long amount;
	
	@SerializedName("reason")
	private String reason;
	
	@SerializedName("refund_holder")
	private String refund_holder;
	
	@SerializedName("refund_account")
	private String refund_account;
	
	public CancelData(String uid, boolean imp_uid_or_not) {
		if ( imp_uid_or_not ) {
			this.imp_uid = uid;
		} else {
			this.merchant_uid = uid;
		}
	}
	
	public CancelData(String uid, boolean imp_uid_or_not, long amount) {
		this(uid, imp_uid_or_not);
		this.amount = amount;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setRefund_holder(String refund_holder) {
		this.refund_holder = refund_holder;
	}

	public void setRefund_account(String refund_account) {
		this.refund_account = refund_account;
	}
	
}