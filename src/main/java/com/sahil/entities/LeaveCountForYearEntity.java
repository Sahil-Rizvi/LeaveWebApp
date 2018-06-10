package com.sahil.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="leave_count_for_year_details")
public class LeaveCountForYearEntity {

	@Id
	@Column(name="leave_count_for_year_id")
	private int leaveCountForYearId;
	
	@Column(name="sick_count",nullable=false)
	private double sickCount;
	
	@Column(name="casual_count",nullable=false)
	private double casualCount;
	
	@Column(name="privileged_count",nullable=false)
	private double privilegedCount;
	
	@Column(name="bday_count",nullable=false)
	private double bdayCount;
	
	public int getLeaveCountForYearId() {
		return leaveCountForYearId;
	}

	public void setLeaveCountForYearId(int leaveCountForYearId) {
		this.leaveCountForYearId = leaveCountForYearId;
	}

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

	@Override
	public String toString() {
		return "LeaveCountForYearEntity [leaveCountForYearId=" + leaveCountForYearId + ", sickCount=" + sickCount
				+ ", casualCount=" + casualCount + ", privilegedCount=" + privilegedCount + ", bdayCount=" + bdayCount
				+ "]";
	}

}
