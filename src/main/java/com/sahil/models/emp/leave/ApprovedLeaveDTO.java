package com.sahil.models.emp.leave;

import com.sahil.models.EmployeeDTO;
import com.sahil.models.LeaveDTO;

public class ApprovedLeaveDTO extends LeaveDTO{

	private EmployeeDTO approvedBy;
	
	private String approvedOn;

	public EmployeeDTO getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(EmployeeDTO approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getApprovedOn() {
		return approvedOn;
	}

	public void setApprovedOn(String approvedOn) {
		this.approvedOn = approvedOn;
	}

	@Override
	public String toString() {
		return "ApprovedLeaveDTO [approvedBy=" + approvedBy + ", approvedOn=" + approvedOn + "]";
	}

	
}
