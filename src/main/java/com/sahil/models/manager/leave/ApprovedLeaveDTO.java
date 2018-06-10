package com.sahil.models.manager.leave;

import com.sahil.models.EmployeeDTO;
import com.sahil.models.LeaveDTO;

public class ApprovedLeaveDTO extends LeaveDTO{

	private EmployeeDTO employee;
	
	private String approvedOn;

	public EmployeeDTO getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeDTO employee) {
		this.employee = employee;
	}

	public String getApprovedOn() {
		return approvedOn;
	}

	public void setApprovedOn(String approvedOn) {
		this.approvedOn = approvedOn;
	}

	@Override
	public String toString() {
		return "ApprovedLeaveDTO [employee=" + employee + ", approvedOn=" + approvedOn + "]";
	}


}
