package com.sahil.models.hr.report;

import com.sahil.models.EmployeeDTO;

public class Manager extends EmployeeDTO{

	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Manager [email=" + email + ", getEmpCode()=" + getEmpCode() + ", getEmpName()=" + getEmpName()
				+ ", getEmpDepartment()=" + getEmpDepartment() + "]";
	}
	
	
}
