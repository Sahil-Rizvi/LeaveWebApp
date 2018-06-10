package com.sahil.models.input;

import java.util.Date;
import com.sahil.enums.LeaveType;

public class Leave {

	private Date fromDate;
	
	private Date toDate;
	
	private LeaveType leaveType;

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public LeaveType getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(LeaveType leaveType) {
		this.leaveType = leaveType;
	}

	@Override
	public String toString() {
		return "Leave [fromDate=" + fromDate + ", toDate=" + toDate + ", leaveType=" + leaveType + "]";
	}
	
}
