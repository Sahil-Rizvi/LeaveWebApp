package com.sahil.models.hr.emp.compoffs;

import com.sahil.models.EmployeeDTO;

public class ApprovedCompOffs {

	private int compOffId;
		
	private String appliedOn;
	
	private String forDate;

	private String approvedOn;
	
	private EmployeeDTO approvedBy;

	public int getCompOffId() {
		return compOffId;
	}

	public void setCompOffId(int compOffId) {
		this.compOffId = compOffId;
	}

	public String getAppliedOn() {
		return appliedOn;
	}

	public void setAppliedOn(String appliedOn) {
		this.appliedOn = appliedOn;
	}

	public String getForDate() {
		return forDate;
	}

	public void setForDate(String forDate) {
		this.forDate = forDate;
	}

	public String getApprovedOn() {
		return approvedOn;
	}

	public void setApprovedOn(String approvedOn) {
		this.approvedOn = approvedOn;
	}

	
	public EmployeeDTO getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(EmployeeDTO approvedBy) {
		this.approvedBy = approvedBy;
	}

	@Override
	public String toString() {
		return "ApprovedCompOffs [compOffId=" + compOffId + ", appliedOn=" + appliedOn + ", forDate=" + forDate
				+ ", approvedOn=" + approvedOn + ", approvedBy=" + approvedBy + "]";
	}

	
}
