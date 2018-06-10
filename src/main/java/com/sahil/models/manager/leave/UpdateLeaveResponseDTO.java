package com.sahil.models.manager.leave;

import java.util.List;
import com.sahil.models.Response;

public class UpdateLeaveResponseDTO extends Response{

	private List<UpdateLeaveDTO> leaves;

	public List<UpdateLeaveDTO> getLeaves() {
		return leaves;
	}

	public void setLeaves(List<UpdateLeaveDTO> leaves) {
		this.leaves = leaves;
	}

	@Override
	public String toString() {
		return "UpdateLeaveResponseDTO [leaves=" + leaves + "]";
	}
	
}
