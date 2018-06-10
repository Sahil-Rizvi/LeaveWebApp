package com.sahil.models.hr;

public class EmployeeForListing {

	private String employeeCode;

	private String employeeName;
	
	private String designation;
	
	private String dateofJoining;
	
	private String dateofBirth;	
	
	private String managerCode;
	
	private String managerName;
	
	private String department;

	private boolean managerStatus;
	
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDateofJoining() {
		return dateofJoining;
	}

	public void setDateofJoining(String dateofJoining) {
		this.dateofJoining = dateofJoining;
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

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public boolean isManagerStatus() {
		return managerStatus;
	}

	public void setManagerStatus(boolean managerStatus) {
		this.managerStatus = managerStatus;
	}

	@Override
	public String toString() {
		return "EmployeeForListing [employeeCode=" + employeeCode + ", employeeName=" + employeeName + ", designation="
				+ designation + ", dateofJoining=" + dateofJoining + ", dateofBirth=" + dateofBirth + ", managerCode="
				+ managerCode + ", managerName=" + managerName + ", department=" + department + ", managerStatus="
				+ managerStatus + "]";
	}
	
}
