package com.sahil.models;

public class EmployeeDTO {

	private String empCode;
	
	private String empName;
	
	private String empDepartment;

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpDepartment() {
		return empDepartment;
	}

	public void setEmpDepartment(String empDepartment) {
		this.empDepartment = empDepartment;
	}

	@Override
	public String toString() {
		return "EmployeeDTO [empCode=" + empCode + ", empName=" + empName + ", empDepartment=" + empDepartment + "]";
	}


}
