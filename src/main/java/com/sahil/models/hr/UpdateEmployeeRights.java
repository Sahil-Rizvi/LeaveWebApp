package com.sahil.models.hr;

public class UpdateEmployeeRights{
	
	private String empCode;
	
	private boolean managerStatus;

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public boolean isManagerStatus() {
		return managerStatus;
	}

	public void setManagerStatus(boolean managerStatus) {
		this.managerStatus = managerStatus;
	}

	@Override
	public String toString() {
		return "UpdateEmployeeRights [empCode=" + empCode + ", managerStatus=" + managerStatus + "]";
	}


}
