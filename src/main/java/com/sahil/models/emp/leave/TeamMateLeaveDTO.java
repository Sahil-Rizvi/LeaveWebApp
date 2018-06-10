package com.sahil.models.emp.leave;

import com.sahil.models.EmployeeDTO;
import com.sahil.models.LeaveDTO;

public class TeamMateLeaveDTO extends LeaveDTO{

	private EmployeeDTO employee;

	public EmployeeDTO getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeDTO employee) {
		this.employee = employee;
	}

	@Override
	public String toString() {
		return "TeamMateLeaveDTO [employee=" + employee + "]";
	}

	
	
}
