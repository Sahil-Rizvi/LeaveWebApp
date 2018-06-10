package com.sahil.models.manager.leave;

import com.sahil.enums.LeaveType;
import com.sahil.enums.ResponseType;

public class UpdateLeaveDTO {

	private int leaveId;
	
	private String employeeCode;
	
	private String employeeName;
	
	private String fromDate; 
	
	private String toDate;
	
	private String appliedOn;

	private LeaveType leaveType;
	
	private ResponseType responseType;
	
	private String rejectionReason;

	public int getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(int leaveId) {
		this.leaveId = leaveId;
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

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getAppliedOn() {
		return appliedOn;
	}

	public void setAppliedOn(String appliedOn) {
		this.appliedOn = appliedOn;
	}

	public LeaveType getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(LeaveType leaveType) {
		this.leaveType = leaveType;
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
		return "UpdateLeavesDTO [leaveId=" + leaveId + ", employeeCode=" + employeeCode + ", employeeName="
				+ employeeName + ", fromDate=" + fromDate + ", toDate=" + toDate + ", appliedOn=" + appliedOn
				+ ", leaveType=" + leaveType + ", responseType=" + responseType + ", rejectionReason=" + rejectionReason
				+ "]";
	}

}
