package com.sahil.models.manager.report;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.sahil.models.Response;
import com.sahil.models.hr.report.Manager;

public class ApprovedLeavesReportWrapper extends Response{
	
	private Map<Manager,List<ApprovedLeavesForReport>> leaves = new LinkedHashMap<>();

	
	public Map<Manager, List<ApprovedLeavesForReport>> getLeaves() {
		return leaves;
	}


	public void setLeaves(Map<Manager, List<ApprovedLeavesForReport>> leaves) {
		this.leaves = leaves;
	}


	@Override
	public String toString() {
		return "ApprovedLeavesReportWrapper [leaves=" + leaves + ", getCode()=" + getCode() + ", getMessage()="
				+ getMessage() + "]";
	}
	
	


	
}
