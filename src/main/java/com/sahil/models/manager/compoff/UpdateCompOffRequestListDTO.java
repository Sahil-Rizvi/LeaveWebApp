package com.sahil.models.manager.compoff;

import java.util.List;

public class UpdateCompOffRequestListDTO {

	private List<UpdateCompOffRequestDTO> compOffs;

	public List<UpdateCompOffRequestDTO> getCompOffs() {
		return compOffs;
	}

	public void setCompOffs(List<UpdateCompOffRequestDTO> compOffs) {
		this.compOffs = compOffs;
	}

	@Override
	public String toString() {
		return "UpdateCompOffRequestListDTO [compOffs=" + compOffs + "]";
	}
	
}
