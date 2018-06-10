package com.sahil.models.emp.compoff;

import com.sahil.models.EmployeeDTO;
import com.sahil.models.CompOffDTO;

public class PendingCompOffDTO extends CompOffDTO{

	private EmployeeDTO pendingWith;

	public EmployeeDTO getPendingWith() {
		return pendingWith;
	}

	public void setPendingWith(EmployeeDTO pendingWith) {
		this.pendingWith = pendingWith;
	}

	@Override
	public String toString() {
		return "PendingCompOffDTO [pendingWith=" + pendingWith + "]";
	}
	
}
