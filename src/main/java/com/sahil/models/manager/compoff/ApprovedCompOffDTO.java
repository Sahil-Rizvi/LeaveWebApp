package com.sahil.models.manager.compoff;

import com.sahil.models.EmployeeDTO;
import com.sahil.models.CompOffDTO;

public class ApprovedCompOffDTO extends CompOffDTO{

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
		return "ApprovedCompOffDTO [employee=" + employee + ", approvedOn=" + approvedOn + "]";
	}

	
}
