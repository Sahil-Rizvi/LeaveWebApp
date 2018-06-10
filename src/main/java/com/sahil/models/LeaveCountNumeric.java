package com.sahil.models;

public class LeaveCountNumeric {

	private double bdayCount;
	
	private double casualCount;
	
	private double privilegedCount;
	
	private double sickCount;

	public double getBdayCount() {
		return bdayCount;
	}

	public void setBdayCount(double bdayCount) {
		this.bdayCount = bdayCount;
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

	public double getSickCount() {
		return sickCount;
	}

	public void setSickCount(double sickCount) {
		this.sickCount = sickCount;
	}

	@Override
	public String toString() {
		return "LeaveCountNumeric [bdayCount=" + bdayCount + ", casualCount=" + casualCount + ", privilegedCount="
				+ privilegedCount + ", sickCount=" + sickCount + "]";
	}
	
	
}
