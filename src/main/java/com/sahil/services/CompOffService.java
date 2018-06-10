package com.sahil.services;

import com.sahil.models.PageResponseDTO;
import com.sahil.models.Response;
import com.sahil.models.UpdateResponse;
import com.sahil.models.emp.compoff.ApprovedCompOffDTO;
import com.sahil.models.emp.compoff.PendingCompOffDTO;
import com.sahil.models.emp.compoff.RejectedCompOffDTO;
import com.sahil.models.input.CompOff;
import com.sahil.models.manager.compoff.UpdateCompOffRequestListDTO;
import com.sahil.models.manager.compoff.UpdateCompOffResponseDTO;
import com.sahil.models.request.CompOffRequestDTO;



public interface CompOffService {

	public Response addCompOff(CompOff compOff,String empId);
	
	public PageResponseDTO<PendingCompOffDTO> findAllPendingCompOffsOfEmployee(CompOffRequestDTO requestDTO);

	public PageResponseDTO<ApprovedCompOffDTO> findAllApprovedCompOffsOfEmployee(CompOffRequestDTO requestDTO);
	
	public PageResponseDTO<RejectedCompOffDTO> findAllRejectedCompOffsOfEmployee(CompOffRequestDTO requestDTO);
	
	public PageResponseDTO<com.sahil.models.manager.compoff.ApprovedCompOffDTO> findCompOffsApprovedByManager(CompOffRequestDTO requestDTO);
	
	public PageResponseDTO<com.sahil.models.manager.compoff.RejectedCompOffDTO> findCompOffsRejectedByManager(CompOffRequestDTO requestDTO);
	
	public UpdateCompOffResponseDTO findAllCompOffsByManagerForUpdation(String id);

	public UpdateResponse updateCompOffDetails(String string, UpdateCompOffRequestListDTO updateCompOffs);

	public Response deleteCompOff(int id);
	
}
