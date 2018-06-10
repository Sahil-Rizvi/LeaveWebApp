package com.sahil.models.hr;

public class EmployeeForRights{

	private String empCode;
	
	private String name;
	
	private String designation;
	
	private String department;
	
	private boolean managerStatus;
	
	private String dateOfJoining;

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
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

	public boolean isManagerStatus() {
		return managerStatus;
	}

	public void setManagerStatus(boolean managerStatus) {
		this.managerStatus = managerStatus;
	}

	public String getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(String dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	@Override
	public String toString() {
		return "EmployeeForRights [empCode=" + empCode + ", name=" + name + ", designation=" + designation
				+ ", department=" + department + ", managerStatus=" + managerStatus + ", dateOfJoining=" + dateOfJoining
				+ "]";
	}
	
}
