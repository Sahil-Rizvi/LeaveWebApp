package com.sahil.models.emp.leave;

import com.sahil.models.EmployeeDTO;
import com.sahil.models.LeaveDTO;

public class PendingLeaveDTO extends LeaveDTO{

	private EmployeeDTO pendingWith;

	public EmployeeDTO getPendingWith() {
		return pendingWith;
	}

	public void setPendingWith(EmployeeDTO pendingWith) {
		this.pendingWith = pendingWith;
	}

	@Override
	public String toString() {
		return "PendingLeaveDTO [pendingWith=" + pendingWith + "]";
	}
	
}
