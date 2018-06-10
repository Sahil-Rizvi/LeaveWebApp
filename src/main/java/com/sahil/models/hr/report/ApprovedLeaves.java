package com.sahil.models.hr.report;

import com.sahil.models.EmployeeDTO;

public class ApprovedLeaves {

	private int leaveId;
	
	private EmployeeDTO employee;

	private EmployeeDTO manager;
	
	private EmployeeDTO approvedBy;
	
	private String fromDate;
	
	private String toDate;
	
	private String appliedOn;
	
	private String approvedOn;
	
	private String leaveType;
	
	private int noOfDays;

	public int getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(int leaveId) {
		this.leaveId = leaveId;
	}

	public EmployeeDTO getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeDTO employee) {
		this.employee = employee;
	}

	public EmployeeDTO getManager() {
		return manager;
	}

	public void setManager(EmployeeDTO manager) {
		this.manager = manager;
	}

	public EmployeeDTO getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(EmployeeDTO approvedBy) {
		this.approvedBy = approvedBy;
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

	public String getApprovedOn() {
		return approvedOn;
	}

	public void setApprovedOn(String approvedOn) {
		this.approvedOn = approvedOn;
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
		return "ApprovedLeavesReportDTO [leaveId=" + leaveId + ", employee=" + employee + ", manager=" + manager
				+ ", approvedBy=" + approvedBy + ", fromDate=" + fromDate + ", toDate=" + toDate + ", appliedOn="
				+ appliedOn + ", approvedOn=" + approvedOn + ", leaveType=" + leaveType + ", noOfDays=" + noOfDays
				+ "]";
	}

	
}
