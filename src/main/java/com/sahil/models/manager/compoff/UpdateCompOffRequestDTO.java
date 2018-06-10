package com.sahil.models.manager.compoff;

import com.sahil.enums.ResponseType;

public class UpdateCompOffRequestDTO {

	private int compOffId;
	
	private ResponseType responseType;
	
	private String rejectionReason;

	public int getCompOffId() {
		return compOffId;
	}

	public void setCompOffId(int compOffId) {
		this.compOffId = compOffId;
	}

	public ResponseType getResponseType() {
		return responseType;
	}

	public void setResponseType(ResponseType responseType) {
		this.responseType = responseType;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	@Override
	public String toString() {
		return "UpdateCompOffRequestDTO [compOffId=" + compOffId + ", responseType=" + responseType
				+ ", rejectionReason=" + rejectionReason + "]";
	}

	
}
