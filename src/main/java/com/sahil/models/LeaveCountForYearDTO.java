package com.sahil.models;

public class LeaveCountForYearDTO extends Response{

	private String year;
	
	private String sickCount;
	
	private String casualCount;
	
	private String privilegedCount;
	
	private String bdayCount;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

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
		return "LeaveCountForYearDTO [year=" + year + ", sickCount=" + sickCount + ", casualCount=" + casualCount
				+ ", privilegedCount=" + privilegedCount + ", bdayCount=" + bdayCount + "]";
	}

	
}
