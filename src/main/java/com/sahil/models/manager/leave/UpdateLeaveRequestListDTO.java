package com.sahil.models.manager.leave;

import java.util.List;

public class UpdateLeaveRequestListDTO {

	private List<UpdateLeaveRequestDTO> leaves;

	public List<UpdateLeaveRequestDTO> getLeaves() {
		return leaves;
	}

	public void setLeaves(List<UpdateLeaveRequestDTO> leaves) {
		this.leaves = leaves;
	}

	@Override
	public String toString() {
		return "UpdateLeavesRequestListDTO [leaves=" + leaves + "]";
	}
	
}


