package com.sahil.models.manager.leave;

import com.sahil.models.EmployeeDTO;
import com.sahil.models.LeaveDTO;

public class RejectedLeaveDTO extends LeaveDTO{

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
		return "RejectedLeaveDTO [employee=" + employee + ", rejectedOn=" + rejectedOn + ", rejectionReason="
				+ rejectionReason + "]";
	}

}
