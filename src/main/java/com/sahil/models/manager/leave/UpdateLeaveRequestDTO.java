package com.sahil.models.manager.leave;

import com.sahil.enums.ResponseType;

public class UpdateLeaveRequestDTO {
	
	private int leaveId;
	
	private ResponseType responseType;
	
	private String rejectionReason;

	public int getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(int leaveId) {
		this.leaveId = leaveId;
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
		return "UpdateLeavesRequestDTO [leaveId=" + leaveId + ", responseType=" + responseType + ", rejectionReason="
				+ rejectionReason + "]";
	}
}
