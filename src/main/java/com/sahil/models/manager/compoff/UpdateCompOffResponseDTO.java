package com.sahil.models.manager.compoff;

import java.util.List;

import com.sahil.models.Response;

public class UpdateCompOffResponseDTO extends Response{

	private List<UpdateCompOffDTO> compOffs;

	public List<UpdateCompOffDTO> getCompOffs() {
		return compOffs;
	}

	public void setCompOffs(List<UpdateCompOffDTO> compOffs) {
		this.compOffs = compOffs;
	}

	@Override
	public String toString() {
		return "UpdateCompOffResponseDTO [compOffs=" + compOffs + "]";
	}

}
