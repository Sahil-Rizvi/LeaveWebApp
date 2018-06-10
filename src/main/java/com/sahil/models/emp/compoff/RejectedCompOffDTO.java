package com.sahil.models.emp.compoff;

import com.sahil.models.EmployeeDTO;
import com.sahil.models.CompOffDTO;

public class RejectedCompOffDTO extends CompOffDTO{

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
		return "RejectedCompOffDTO [rejectedBy=" + rejectedBy + ", rejectedOn=" + rejectedOn + ", rejectionReason="
				+ rejectionReason + "]";
	}

	
}
