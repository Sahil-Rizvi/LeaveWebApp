package com.sahil.models.hr.emp.leaves;

import com.sahil.models.EmployeeDTO;

public class RejectedLeaves {

	private int leaveId;
	
	private String fromDate;
	
	private String toDate;
	
	private String appliedOn;
	
	private String rejectedOn;
	
	private EmployeeDTO rejectedBy;
	
	private String leaveType;
	
	private int noOfDays;

	public int getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(int leaveId) {
		this.leaveId = leaveId;
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

	public String getRejectedOn() {
		return rejectedOn;
	}

	public void setRejectedOn(String rejectedOn) {
		this.rejectedOn = rejectedOn;
	}

	public EmployeeDTO getRejectedBy() {
		return rejectedBy;
	}

	public void setRejectedBy(EmployeeDTO rejectedBy) {
		this.rejectedBy = rejectedBy;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public int getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(int noOfDays) {
		this.noOfDays = noOfDays;
	}

	@Override
	public String toString() {
		return "RejectedLeaves [leaveId=" + leaveId + ", fromDate=" + fromDate + ", toDate=" + toDate + ", appliedOn="
				+ appliedOn + ", rejectedOn=" + rejectedOn + ", rejectedBy=" + rejectedBy + ", leaveType=" + leaveType
				+ ", noOfDays=" + noOfDays + "]";
	}

	
}
