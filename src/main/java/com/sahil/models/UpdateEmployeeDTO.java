package com.sahil.models;

public class UpdateEmployeeDTO {

	private String employeeName;

	private String phoneNo;

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}


	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	@Override
	public String toString() {
		return "UpdateEmployeeDTO [employeeName=" + employeeName + ", phoneNo=" + phoneNo + "]";
	}
	
}
