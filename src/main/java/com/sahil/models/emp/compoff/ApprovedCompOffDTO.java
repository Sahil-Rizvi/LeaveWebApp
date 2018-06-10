package com.sahil.models.emp.compoff;

import com.sahil.models.EmployeeDTO;
import com.sahil.models.CompOffDTO;

public class ApprovedCompOffDTO extends CompOffDTO{

	private EmployeeDTO approvedBy;
	
	private String approvedOn;

	public EmployeeDTO getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(EmployeeDTO approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getApprovedOn() {
		return approvedOn;
	}

	public void setApprovedOn(String approvedOn) {
		this.approvedOn = approvedOn;
	}

	@Override
	public String toString() {
		return "ApprovedCompOffDTO [approvedBy=" + approvedBy + ", approvedOn=" + approvedOn + "]";
	}

	

	
}
