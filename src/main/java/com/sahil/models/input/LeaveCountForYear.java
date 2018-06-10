package com.sahil.models.input;

public class LeaveCountForYear {
	
	private String sickCount;
	
	private String casualCount;
	
	private String privilegedCount;
	
	private String bdayCount;

	public String getSickCount() {
		return sickCount;
	}

	public void setSickCount(String sickCount) {
		this.sickCount = sickCount;
	}

	public String getCasualCount() {
		return casualCount;
	}

	public void setCasualCount(String casualCount) {
		this.casualCount = casualCount;
	}

	public String getPrivilegedCount() {
		return privilegedCount;
	}

	public void setPrivilegedCount(String privilegedCount) {
		this.privilegedCount = privilegedCount;
	}

	public String getBdayCount() {
		return bdayCount;
	}

	public void setBdayCount(String bdayCount) {
		this.bdayCount = bdayCount;
	}

	@Override
	public String toString() {
		return "LeaveCountForYear [sickCount=" + sickCount + ", casualCount=" + casualCount + ", privilegedCount="
				+ privilegedCount + ", bdayCount=" + bdayCount + "]";
	}

}
