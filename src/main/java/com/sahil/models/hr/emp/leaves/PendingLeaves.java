package com.sahil.models.hr.emp.leaves;

import com.sahil.models.EmployeeDTO;

public class PendingLeaves {

	private int leaveId;
	
	private String fromDate;
	
	private String toDate;
	
	private String appliedOn;
	
	private EmployeeDTO pendingWith;
	
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

	public EmployeeDTO getPendingWith() {
		return pendingWith;
	}

	public void setPendingWith(EmployeeDTO pendingWith) {
		this.pendingWith = pendingWith;
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
		return "PendingLeaves [leaveId=" + leaveId + ", fromDate=" + fromDate + ", toDate=" + toDate + ", appliedOn="
				+ appliedOn + ", pendingWith=" + pendingWith + ", leaveType=" + leaveType + ", noOfDays=" + noOfDays
				+ "]";
	}

	
}
