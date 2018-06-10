package com.sahil.models.manager.compoff;

import com.sahil.models.CompOffDTO;
import com.sahil.models.EmployeeDTO;

public class RejectedCompOffDTO extends CompOffDTO{

	private EmployeeDTO employee;
	
	private String rejectedOn;
	
	private String rejectionReason;

	public EmployeeDTO getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeDTO employee) {
		this.employee = employee;
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
		return "RejectedCompOffDTO [employee=" + employee + ", rejectedOn=" + rejectedOn + ", rejectionReason="
				+ rejectionReason + "]";
	}
	
}
