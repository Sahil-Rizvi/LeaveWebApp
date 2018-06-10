package com.sahil.models.manager.leave;

import com.sahil.models.EmployeeDTO;
import com.sahil.models.LeaveDTO;

public class PendingLeaveDTO extends LeaveDTO{
	
	private EmployeeDTO employee;

	public EmployeeDTO getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeDTO employee) {
		this.employee = employee;
	}

	@Override
	public String toString() {
		return "PendingLeavesDTO [employee=" + employee + "]";
	}

	
	
}
