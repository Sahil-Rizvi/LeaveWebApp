package com.sahil.models.manager.compoff;

import com.sahil.enums.ResponseType;

public class UpdateCompOffDTO {

	private int compOffId;
	
	private String employeeCode;
	
	private String employeeName;
	
	private String forDate; 
	
	private String appliedOn;

	private ResponseType responseType;
	
	private String rejectionReason;

	public int getCompOffId() {
		return compOffId;
	}

	public void setCompOffId(int compOffId) {
		this.compOffId = compOffId;
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

	public String getForDate() {
		return forDate;
	}

	public void setForDate(String forDate) {
		this.forDate = forDate;
	}

	public String getAppliedOn() {
		return appliedOn;
	}

	public void setAppliedOn(String appliedOn) {
		this.appliedOn = appliedOn;
	}

	public ResponseType getResponseType() {
		return responseType;
	}

	public void setResponseType(ResponseType responseType) {
		this.responseType = responseType;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	@Override
	public String toString() {
		return "UpdateCompOffDTO [compOffId=" + compOffId + ", employeeCode=" + employeeCode + ", employeeName="
				+ employeeName + ", forDate=" + forDate + ", appliedOn=" + appliedOn + ", responseType=" + responseType
				+ ", rejectionReason=" + rejectionReason + "]";
	}

}
