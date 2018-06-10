package com.sahil.models.hr.emp.compoffs;

import com.sahil.models.EmployeeDTO;

public class PendingCompOffs {

	private int compOffId;
	
	private String appliedOn;
	
	private String forDate;
	
	private EmployeeDTO pendingWith;

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

	public EmployeeDTO getPendingWith() {
		return pendingWith;
	}

	public void setPendingWith(EmployeeDTO pendingWith) {
		this.pendingWith = pendingWith;
	}

	@Override
	public String toString() {
		return "PendingCompOffs [compOffId=" + compOffId + ", appliedOn=" + appliedOn + ", forDate=" + forDate
				+ ", pendingWith=" + pendingWith + "]";
	}

	
}
