package com.sahil.models.hr.report;

import java.util.List;

import com.sahil.models.Response;

public class ApprovedLeavesResponseDTO extends Response{
	
	private List<ApprovedLeaves> approvedLeaves;

	public List<ApprovedLeaves> getApprovedLeaves() {
		return approvedLeaves;
	}

	public void setApprovedLeaves(List<ApprovedLeaves> approvedLeaves) {
		this.approvedLeaves = approvedLeaves;
	}

	@Override
	public String toString() {
		return "ApprovedLeavesResponseDTO [approvedLeaves=" + approvedLeaves + ", getCode()=" + getCode()
				+ ", getMessage()=" + getMessage() + "]";
	}

}
