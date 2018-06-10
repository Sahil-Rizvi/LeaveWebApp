package com.sahil.models;

public class LeaveCountResponse {
	
	private double sickCount;
	
	private double casualCount;
	
	private double privilegedCount;
	
	private double bdayCount;
	
	private double compOffCount;

	public double getSickCount() {
		return sickCount;
	}

	public void setSickCount(double sickCount) {
		this.sickCount = sickCount;
	}

	public double getCasualCount() {
		return casualCount;
	}

	public void setCasualCount(double casualCount) {
		this.casualCount = casualCount;
	}

	public double getPrivilegedCount() {
		return privilegedCount;
	}

	public void setPrivilegedCount(double privilegedCount) {
		this.privilegedCount = privilegedCount;
	}

	public double getBdayCount() {
		return bdayCount;
	}

	public void setBdayCount(double bdayCount) {
		this.bdayCount = bdayCount;
	}

	public double getCompOffCount() {
		return compOffCount;
	}

	public void setCompOffCount(double compOffCount) {
		this.compOffCount = compOffCount;
	}

	@Override
	public String toString() {
		return "LeaveCountResponse [sickCount=" + sickCount + ", casualCount=" + casualCount + ", privilegedCount="
				+ privilegedCount + ", bdayCount=" + bdayCount + ", compOffCount=" + compOffCount + "]";
	}

}
