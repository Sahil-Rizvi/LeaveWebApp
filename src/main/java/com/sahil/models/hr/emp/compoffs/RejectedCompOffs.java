package com.sahil.models.hr.emp.compoffs;

import com.sahil.models.EmployeeDTO;

public class RejectedCompOffs {

	private int compOffId;
	
	private String appliedOn;
	
	private String forDate;

	private String rejectedOn;
	
	private EmployeeDTO rejectedBy;
	
	private String rejectionReason;

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

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	@Override
	public String toString() {
		return "RejectedCompOffs [compOffId=" + compOffId + ", appliedOn=" + appliedOn + ", forDate=" + forDate
				+ ", rejectedOn=" + rejectedOn + ", rejectedBy=" + rejectedBy + ", rejectionReason=" + rejectionReason
				+ "]";
	}


}
