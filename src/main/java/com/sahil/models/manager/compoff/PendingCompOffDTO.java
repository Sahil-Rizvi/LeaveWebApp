package com.sahil.models.manager.compoff;

import com.sahil.models.EmployeeDTO;
import com.sahil.models.CompOffDTO;

public class PendingCompOffDTO extends CompOffDTO{

	private EmployeeDTO employee;

	public EmployeeDTO getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeDTO employee) {
		this.employee = employee;
	}

	@Override
	public String toString() {
		return "PendingCompOffDTO [employee=" + employee + "]";
	}

}
