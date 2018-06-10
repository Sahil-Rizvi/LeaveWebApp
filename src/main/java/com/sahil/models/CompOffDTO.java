package com.sahil.models;

public abstract class CompOffDTO {

	private int compOffId;
	
	private String forDate;
	
	private String appliedOn;

	public int getCompOffId() {
		return compOffId;
	}

	public void setCompOffId(int compOffId) {
		this.compOffId = compOffId;
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

	@Override
	public String toString() {
		return "CompOffDTO [compOffId=" + compOffId + ", forDate=" + forDate + ", appliedOn=" + appliedOn + "]";
	}
	
}
