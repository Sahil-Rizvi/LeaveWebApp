package com.sahil.models.hr.emp;

import com.sahil.models.Response;

public class UpdateEmployeeDTO extends Response{

	private String employeeCode;
	
	private String name;
	
	private String designation; 
	
	private String department;
	
	private String dateofBirth;	
	
	private String managerCode;
	
	private String isManager;
	
	private String emailId;
	
	private String phoneNo;
	
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public String getDateofBirth() {
		return dateofBirth;
	}
	public void setDateofBirth(String dateofBirth) {
		this.dateofBirth = dateofBirth;
	}
	public String getManagerCode() {
		return managerCode;
	}
	public void setManagerCode(String managerCode) {
		this.managerCode = managerCode;
	}
	public String getIsManager() {
		return isManager;
	}
	public void setIsManager(String isManager) {
		this.isManager = isManager;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	@Override
	public String toString() {
		return "UpdateEmployeeDTO [employeeCode=" + employeeCode + ", name=" + name + ", designation=" + designation
				+ ", department=" + department + ", dateofBirth=" + dateofBirth + ", managerCode=" + managerCode
				+ ", isManager=" + isManager + ", emailId=" + emailId + ", phoneNo=" + phoneNo + "]";
	}
	
	
}

