package com.sahil.models;

import java.util.List;

public class LeaveCounts extends Response{

	private List<LeaveCountForYearDTO> leaveCounts ;

	public List<LeaveCountForYearDTO> getLeaveCounts() {
		return leaveCounts;
	}

	public void setLeaveCounts(List<LeaveCountForYearDTO> leaveCounts) {
		this.leaveCounts = leaveCounts;
	}

	@Override
	public String toString() {
		return "LeaveCounts [leaveCounts=" + leaveCounts + ", getCode()=" + getCode() + ", getMessage()=" + getMessage()
				+ "]";
	}

}
