package com.sahil.models.hr;

import java.util.List;

public class UpdateEmployeeRightsList {

	private List<UpdateEmployeeRights> employees;

	public List<UpdateEmployeeRights> getEmployees() {
		return employees;
	}

	public void setEmployees(List<UpdateEmployeeRights> employees) {
		this.employees = employees;
	}

	@Override
	public String toString() {
		return "UpdateEmployeeRightsList [employees=" + employees + "]";
	}
	
}
