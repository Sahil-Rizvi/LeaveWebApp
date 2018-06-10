package com.sahil.models.emp.leave;

import com.sahil.models.EmployeeDTO;
import com.sahil.models.LeaveDTO;

public class RejectedLeaveDTO extends LeaveDTO{

	private EmployeeDTO rejectedBy;
	
	private String rejectedOn;

	private String rejectionReason;

	public EmployeeDTO getRejectedBy() {
		return rejectedBy;
	}

	public void setRejectedBy(EmployeeDTO rejectedBy) {
		this.rejectedBy = rejectedBy;
	}

	public String getRejectedOn() {
		return rejectedOn;
	}

	public void setRejectedOn(String rejectedOn) {
		this.rejectedOn = rejectedOn;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	@Override
	public String toString() {
		return "RejectedLeaveDTO [rejectedBy=" + rejectedBy + ", rejectedOn=" + rejectedOn + ", rejectionReason="
				+ rejectionReason + "]";
	}
	
	
	
}
