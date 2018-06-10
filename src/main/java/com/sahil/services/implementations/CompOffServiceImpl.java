package com.sahil.services.implementations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.sahil.entities.CompOffEntity;
import com.sahil.entities.EmployeeEntity;
import com.sahil.enums.CompOffStatus;
import com.sahil.enums.ResponseType;
import com.sahil.models.EmployeeDTO;
import com.sahil.models.PageResponseDTO;
import com.sahil.models.Response;
import com.sahil.models.UpdateResponse;
import com.sahil.models.emp.compoff.ApprovedCompOffDTO;
import com.sahil.models.emp.compoff.PendingCompOffDTO;
import com.sahil.models.emp.compoff.RejectedCompOffDTO;
import com.sahil.models.input.CompOff;
import com.sahil.models.manager.compoff.UpdateCompOffDTO;
import com.sahil.models.manager.compoff.UpdateCompOffRequestListDTO;
import com.sahil.models.manager.compoff.UpdateCompOffResponseDTO;
import com.sahil.models.request.CompOffRequestDTO;
import com.sahil.repositories.CompOffRepository;
import com.sahil.repositories.EmployeeRepository;
import com.sahil.services.CompOffService;
import com.sahil.services.HolidayService;
import com.sahil.services.LeaveCountService;
import com.sahil.services.UploadFileService;
import com.sahil.utils.LeaveUtil;

@Service
public class CompOffServiceImpl implements CompOffService{

	private static final int PAGE_SIZE = 5;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private CompOffRepository compOffRepository;
	
	@Autowired
	private LeaveCountService leaveCountService;
	
	@Autowired
	private UploadFileService uploadFileService;
	
	@Autowired
	private HolidayService holidayService;
	
	@Override
	@Transactional
	public Response addCompOff(CompOff compOff,String empId) {
		System.out.println("in addCompOff"+compOff);
		Response response = validate(compOff,empId);
		System.out.println(response);
		if(response.getCode()==1){
			return response;
		}
		
		EmployeeEntity employeeEntity = employeeRepository.findOne(empId);
		
		CompOffEntity compOffEntity = new CompOffEntity();
		compOffEntity.setEmployeeEntity(employeeEntity);
		compOffEntity.setManagerEntity(employeeEntity.getManager());
		compOffEntity.setCurrentlyManager(employeeEntity.getManager());
		compOffEntity.setForDate(compOff.getForDate());
		compOffEntity.setAppliedOn(LeaveUtil.getTodaysDate());
		compOffEntity.setApprovedOn(null);
		compOffEntity.setRejectionReason(null);
		compOffEntity.setCompOffStatus(CompOffStatus.PENDING);
		employeeEntity.addCompOffs(compOffEntity);
		employeeRepository.save(employeeEntity);
		
		return response;
	}
	
	private Response validate(CompOff compOff,String empId){
		System.out.println("in validate"+compOff);
		
		Response response = new Response();
		response.setCode(1);
		
		
		if(compOff==null || compOff.getForDate() == null){
			System.out.println("invalid data");
			response.setMessage("Invalid Data");
			return response;
		}
		System.out.println("here1");
		
		if(!uploadFileService.isHolidayFileUploadedForCurrentYear()){
			response.setMessage("Holiday file is not uploaded. <br> Please contact the admin.");
			return response;
		}
		
		System.out.println("here1");
		// check whether its a weekend
		// checking for other holidays has to be done		
		if(!LeaveUtil.isWeekend(compOff.getForDate()) && !holidayService.isHoliday(compOff.getForDate())){
			response.setMessage("COMPENSATORY OFFS WILL ONLY BE REGISTERED EITHER FOR WEEKENDS OR HOLIDAYS ");
			System.out.println("only for weekends");
			return response;
		}
		System.out.println("here2");

		if(compOff.getForDate().after(LeaveUtil.getTodaysDate())){
			response.setMessage("COMPENSATORY OFFS CAN'T BE REGISTERED IN ADVANCE");
			System.out.println("not in advance");
			return response;
		}
		
		System.out.println("here3");
		EmployeeEntity employeeEntity = employeeRepository.findOne(empId);
		if (employeeEntity == null) {
			System.out.println("no employee");
			response.setMessage("no employee with employeeId: " + empId + " exists");
			return response;
		}
		if (employeeEntity.getManager() == null) {
			System.out.println("no manager");
			response.setMessage("no manager for employeeId: " + empId + " exists");
			return response;
		}
		
		response.setCode(0);
		response.setMessage("OKAY");
		return response;
	}

	@Override
	public PageResponseDTO<PendingCompOffDTO> findAllPendingCompOffsOfEmployee(CompOffRequestDTO requestDTO) {
		PageResponseDTO<PendingCompOffDTO> pageResponseDTO = new PageResponseDTO<>();
		pageResponseDTO.setCode(1);
		pageResponseDTO.setMessage("NO EMPLOYEE WITH ID"+requestDTO.getEmpId());
		PageRequest pageRequest = new PageRequest(requestDTO.getPageNumber()-1, PAGE_SIZE, Sort.Direction.ASC,"appliedOn");
        Page<CompOffEntity> entityResponses = null;
        String id = requestDTO.getEmpId();
        if(!StringUtils.isEmpty(employeeRepository.findOne(id)) && !StringUtils.isEmpty(employeeRepository.findOne(id).getManager())){
        	Date fromDate = LeaveUtil.convertStringToDateInParticularFormat(requestDTO.getFromWhen());
	        Date toDate = LeaveUtil.convertStringToDateInParticularFormat(requestDTO.getToWhen());
        	entityResponses = compOffRepository.findPendingOrRejectedCompOffsByEmployeeEntityAndCompOffStatus(id,CompOffStatus.PENDING, fromDate, toDate,pageRequest);
		}
		pageResponseDTO.setMessage("EXCEPTION : EITHER EMPLOYEE OR HIS MANAGER DOES NOT EXISTS");
		Page<PendingCompOffDTO> pendingLeaves = null;
		if(entityResponses!=null){
			pageResponseDTO.setCode(0);
			pageResponseDTO.setMessage("SUCCESSFUL");
			pendingLeaves = entityResponses.map(this::convertToPendingCompOffDTO);
		}
		pageResponseDTO.setData(pendingLeaves);
		return pageResponseDTO;
		
	}
	
	private PendingCompOffDTO convertToPendingCompOffDTO(CompOffEntity c){
		PendingCompOffDTO compOffDTO = new PendingCompOffDTO();
		if(Objects.nonNull(c)){
		compOffDTO.setCompOffId(c.getCompOffId());
		compOffDTO.setAppliedOn(LeaveUtil.convertDateToStringInParticularFormat(c.getAppliedOn()));
		compOffDTO.setForDate(LeaveUtil.convertDateToStringInParticularFormat(c.getForDate()));
		EmployeeDTO e = new EmployeeDTO();
		if(Objects.nonNull(c.getCurrentlyManager())){
			e.setEmpCode(c.getCurrentlyManager().getEmployeeCode());
			e.setEmpName(c.getCurrentlyManager().getName());
		}
		compOffDTO.setPendingWith(e);
		}
		return compOffDTO;
	}
	
	

	@Override
	public PageResponseDTO<ApprovedCompOffDTO> findAllApprovedCompOffsOfEmployee(CompOffRequestDTO requestDTO) {
		PageResponseDTO<ApprovedCompOffDTO> pageResponseDTO = new PageResponseDTO<>();
		pageResponseDTO.setCode(1);
		pageResponseDTO.setMessage("NO EMPLOYEE WITH ID"+requestDTO.getEmpId());
		PageRequest pageRequest = new PageRequest(requestDTO.getPageNumber()-1, PAGE_SIZE, Sort.Direction.ASC,"appliedOn");
        Page<CompOffEntity> entityResponses = null;
        String id = requestDTO.getEmpId();
        if(!StringUtils.isEmpty(employeeRepository.findOne(id)) && !StringUtils.isEmpty(employeeRepository.findOne(id).getManager())){
        	Date fromDate = LeaveUtil.convertStringToDateInParticularFormat(requestDTO.getFromWhen());
	        Date toDate = LeaveUtil.convertStringToDateInParticularFormat(requestDTO.getToWhen());
        	entityResponses = compOffRepository.findApprovedCompOffsByEmployeeEntity(id, fromDate, toDate,pageRequest);
		}
		pageResponseDTO.setMessage("EXCEPTION : EITHER EMPLOYEE OR HIS MANAGER DOES NOT EXISTS");
		Page<ApprovedCompOffDTO> pendingLeaves = null;
		if(entityResponses!=null){
			pageResponseDTO.setCode(0);
			pageResponseDTO.setMessage("SUCCESSFUL");
			pendingLeaves = entityResponses.map(this::convertToApprovedCompOffDTO);
		}
		pageResponseDTO.setData(pendingLeaves);
		return pageResponseDTO;
	
	}

	
	private ApprovedCompOffDTO convertToApprovedCompOffDTO(CompOffEntity c){
		ApprovedCompOffDTO dto = new ApprovedCompOffDTO();
		if(Objects.nonNull(c)){
		dto.setCompOffId(c.getCompOffId());
		dto.setAppliedOn(LeaveUtil.convertDateToStringInParticularFormat(c.getAppliedOn()));
		dto.setForDate(LeaveUtil.convertDateToStringInParticularFormat(c.getForDate()));
		dto.setApprovedOn(LeaveUtil.convertDateToStringInParticularFormat(c.getApprovedOn()));
		EmployeeDTO e = new EmployeeDTO();
		if(Objects.nonNull(c.getCurrentlyManager())){
			e.setEmpCode(c.getCurrentlyManager().getEmployeeCode());
			e.setEmpName(c.getCurrentlyManager().getName());
		}
		dto.setApprovedBy(e);
		}
        return dto;		
	}
	
	@Override
	public PageResponseDTO<RejectedCompOffDTO> findAllRejectedCompOffsOfEmployee(CompOffRequestDTO requestDTO) {
		PageResponseDTO<RejectedCompOffDTO> pageResponseDTO = new PageResponseDTO<>();
		pageResponseDTO.setCode(1);
		pageResponseDTO.setMessage("NO EMPLOYEE WITH ID"+requestDTO.getEmpId());
		PageRequest pageRequest = new PageRequest(requestDTO.getPageNumber()-1, PAGE_SIZE, Sort.Direction.ASC,"appliedOn");
        Page<CompOffEntity> entityResponses = null;
        String id = requestDTO.getEmpId();
        if(!StringUtils.isEmpty(employeeRepository.findOne(id)) && !StringUtils.isEmpty(employeeRepository.findOne(id).getManager())){
        	Date fromDate = LeaveUtil.convertStringToDateInParticularFormat(requestDTO.getFromWhen());
	        Date toDate = LeaveUtil.convertStringToDateInParticularFormat(requestDTO.getToWhen());
        	entityResponses = compOffRepository.findPendingOrRejectedCompOffsByEmployeeEntityAndCompOffStatus(id,CompOffStatus.REJECTED, fromDate, toDate,pageRequest);
		}
		pageResponseDTO.setMessage("EXCEPTION : EITHER EMPLOYEE OR HIS MANAGER DOES NOT EXISTS");
		Page<RejectedCompOffDTO> pendingLeaves = null;
		if(entityResponses!=null){
			pageResponseDTO.setCode(0);
			pageResponseDTO.setMessage("SUCCESSFUL");
			pendingLeaves = entityResponses.map(this::convertToRejectedCompOffDTO);
		}
		pageResponseDTO.setData(pendingLeaves);
		return pageResponseDTO;
	
	}

	
	private RejectedCompOffDTO convertToRejectedCompOffDTO(CompOffEntity c){
		RejectedCompOffDTO dto = new RejectedCompOffDTO();
		if(Objects.nonNull(c)){
			dto.setCompOffId(c.getCompOffId());
			dto.setAppliedOn(LeaveUtil.convertDateToStringInParticularFormat(c.getAppliedOn()));
			dto.setForDate(LeaveUtil.convertDateToStringInParticularFormat(c.getForDate()));
			dto.setRejectedOn(LeaveUtil.convertDateToStringInParticularFormat(c.getApprovedOn()));
			dto.setRejectionReason(c.getRejectionReason());
			EmployeeDTO e = new EmployeeDTO();
			if(Objects.nonNull(c.getCurrentlyManager())){
				e.setEmpCode(c.getCurrentlyManager().getEmployeeCode());
				e.setEmpName(c.getCurrentlyManager().getName());
			}
			dto.setRejectedBy(e);
		}
		return dto;
	}
	
	@Override
	public UpdateCompOffResponseDTO findAllCompOffsByManagerForUpdation(String id) {
		UpdateCompOffResponseDTO updateCompOffResponseDTO = new UpdateCompOffResponseDTO();
		updateCompOffResponseDTO.setCode(1);
		updateCompOffResponseDTO.setMessage("NO MANAGER WITH ID"+id);
		List<UpdateCompOffDTO> entityResponses = new ArrayList<>();
		if(!StringUtils.isEmpty(employeeRepository.findOne(id)) && hasRoleManager(employeeRepository.findOne(id))){
			entityResponses = compOffRepository.findByCurrentlyManagerAndCompOffStatus(employeeRepository.findOne(id),CompOffStatus.PENDING)
					          .stream().map(this::convertToUpdateCompOffDTO).collect(Collectors.toList());
		}
		System.out.println(entityResponses);
		if(CollectionUtils.isEmpty(entityResponses)){
			updateCompOffResponseDTO.setMessage("NO PENDING COMP-OFFS");
			return updateCompOffResponseDTO;
		}
		
		updateCompOffResponseDTO.setCode(0);
		updateCompOffResponseDTO.setMessage("SUCCESS");
		updateCompOffResponseDTO.setCompOffs(entityResponses);
	    return updateCompOffResponseDTO;
	}
	
	private UpdateCompOffDTO convertToUpdateCompOffDTO(CompOffEntity c){
		UpdateCompOffDTO dto = new UpdateCompOffDTO();
		if(Objects.nonNull(c)){
	     dto.setCompOffId(c.getCompOffId());
	     dto.setAppliedOn(LeaveUtil.convertDateToStringInParticularFormat(c.getAppliedOn()));
	     dto.setEmployeeCode(c.getEmployeeEntity().getEmployeeCode());
	     dto.setEmployeeName(c.getEmployeeEntity().getName());
	     dto.setForDate(LeaveUtil.convertDateToStringInParticularFormat(c.getForDate()));
	     dto.setResponseType(ResponseType.PENDING);
	     dto.setRejectionReason(null);
		}
		return dto;
	}

	@Override
	public UpdateResponse updateCompOffDetails(String managerId, UpdateCompOffRequestListDTO updateCompOffs) {
		UpdateResponse responseDTO = new UpdateResponse();
		List<String> list = new ArrayList<>();
		responseDTO.setList(list);
		responseDTO.setMessage("NOT UPDATED");
		
		if(updateCompOffs!=null && !CollectionUtils.isEmpty(updateCompOffs.getCompOffs())){				
			if(StringUtils.isEmpty(employeeRepository.findOne(managerId))){
				responseDTO.setMessage("INVALID MANAGER ID"+managerId);
				return responseDTO;
			}
			Date onDate = LeaveUtil.getTodaysDate();
			for(int i=0;i<updateCompOffs.getCompOffs().size();i++){
				if(!updateCompOffs.getCompOffs().get(i).getResponseType().toString().toUpperCase().equals(ResponseType.PENDING.toString())){
					
					ResponseType res= updateCompOffs.getCompOffs().get(i).getResponseType();
					System.out.println("res"+res);
					
					CompOffStatus compOffStatus = convertResponseTypeToCompOffStatus(res);
					System.out.println("compOffStatus"+compOffStatus);
					
					compOffRepository.updateCompOffDetails(managerId,compOffStatus,onDate,updateCompOffs.getCompOffs().get(i).getCompOffId(),updateCompOffs.getCompOffs().get(i).getRejectionReason());
					if(ResponseType.APPROVED.toString().equals(updateCompOffs.getCompOffs().get(i).getResponseType().toString().toUpperCase())){
					Response response = leaveCountService.incrementCompOffCountForApproved(updateCompOffs.getCompOffs().get(i).getCompOffId());
						if(response.getCode()==1){
						list.add(String.valueOf(updateCompOffs.getCompOffs().get(i).getCompOffId()));
						}
					}
				}
			}
			if(list.size()>0){
				responseDTO.setMessage("NOT UPDATED FOR THESE COMPOFF IDs");
				return responseDTO;
			}
			responseDTO.setMessage("UPDATED");
			return responseDTO;
		}
		return responseDTO;
	}
		
		
	private CompOffStatus convertResponseTypeToCompOffStatus(ResponseType res){
	
		if(CompOffStatus.APPROVED.toString().equals(res.getValue())){
			return CompOffStatus.APPROVED;
		}
		if(CompOffStatus.PENDING.toString().equals(res.getValue())){
			return CompOffStatus.PENDING;
		}
		if(CompOffStatus.REJECTED.toString().equals(res.getValue())){
			return CompOffStatus.REJECTED;
		}
    	return null;
		
	}

	@Override
	public PageResponseDTO<com.sahil.models.manager.compoff.ApprovedCompOffDTO> findCompOffsApprovedByManager(
			CompOffRequestDTO requestDTO) {
		PageResponseDTO<com.sahil.models.manager.compoff.ApprovedCompOffDTO> pageResponseDTO = new PageResponseDTO<>();
		pageResponseDTO.setCode(1);
		pageResponseDTO.setMessage("NO MANAGER WITH ID"+requestDTO.getEmpId());
		PageRequest pageRequest = new PageRequest(requestDTO.getPageNumber()-1, PAGE_SIZE, Sort.Direction.ASC,"approvedOn");
        Page<CompOffEntity> entityResponses = null;
        String id = requestDTO.getEmpId();
        if(!StringUtils.isEmpty(employeeRepository.findOne(id)) && hasRoleManager(employeeRepository.findOne(id))){
        	Date fromDate = LeaveUtil.convertStringToDateInParticularFormat(requestDTO.getFromWhen());
	        Date toDate = LeaveUtil.convertStringToDateInParticularFormat(requestDTO.getToWhen());
        	entityResponses = compOffRepository.findByCurrentlyManagerAndCompOffStatus(id,CompOffStatus.APPROVED,fromDate,toDate,pageRequest);
		}
		pageResponseDTO.setMessage("NO COMP-OFF AVAILABLE");
		Page<com.sahil.models.manager.compoff.ApprovedCompOffDTO> approvedLeaves = null;
		if(entityResponses!=null){
			pageResponseDTO.setCode(0);
			pageResponseDTO.setMessage("SUCCESSFUL");
			approvedLeaves = entityResponses.map(this::convertToApprovedForManager);
		}
		pageResponseDTO.setData(approvedLeaves);
		return pageResponseDTO;
	}
	
	private com.sahil.models.manager.compoff.ApprovedCompOffDTO convertToApprovedForManager(CompOffEntity c){
		com.sahil.models.manager.compoff.ApprovedCompOffDTO dto = new com.sahil.models.manager.compoff.ApprovedCompOffDTO();
		if(Objects.nonNull(c)){
			dto.setCompOffId(c.getCompOffId());
			dto.setAppliedOn(LeaveUtil.convertDateToStringInParticularFormat(c.getAppliedOn()));
			dto.setApprovedOn(LeaveUtil.convertDateToStringInParticularFormat(c.getApprovedOn()));
			dto.setForDate(LeaveUtil.convertDateToStringInParticularFormat(c.getForDate()));
			EmployeeDTO e = new EmployeeDTO();
			e.setEmpCode(c.getEmployeeEntity().getEmployeeCode());
			e.setEmpName(c.getEmployeeEntity().getName());
			dto.setEmployee(e);
		}
		return dto;
	}

	@Override
	public PageResponseDTO<com.sahil.models.manager.compoff.RejectedCompOffDTO> findCompOffsRejectedByManager(
			CompOffRequestDTO requestDTO) {
		PageResponseDTO<com.sahil.models.manager.compoff.RejectedCompOffDTO> pageResponseDTO = new PageResponseDTO<>();
		pageResponseDTO.setCode(1);
		pageResponseDTO.setMessage("NO MANAGER WITH ID"+requestDTO.getEmpId());
		PageRequest pageRequest = new PageRequest(requestDTO.getPageNumber()-1, PAGE_SIZE, Sort.Direction.ASC,"approvedOn");
        Page<CompOffEntity> entityResponses = null;
        String id = requestDTO.getEmpId();
        if(!StringUtils.isEmpty(employeeRepository.findOne(id)) && hasRoleManager(employeeRepository.findOne(id))){
        	Date fromDate = LeaveUtil.convertStringToDateInParticularFormat(requestDTO.getFromWhen());
	        Date toDate = LeaveUtil.convertStringToDateInParticularFormat(requestDTO.getToWhen());
        	entityResponses = compOffRepository.findByCurrentlyManagerAndCompOffStatus(id,CompOffStatus.REJECTED,fromDate,toDate,pageRequest);
		}
		pageResponseDTO.setMessage("NO COMP-OFF AVAILABLE");
		Page<com.sahil.models.manager.compoff.RejectedCompOffDTO> approvedLeaves = null;
		if(entityResponses!=null){
			pageResponseDTO.setCode(0);
			pageResponseDTO.setMessage("SUCCESSFUL");
			approvedLeaves = entityResponses.map(this::convertToRejectedForManager);
		}
		pageResponseDTO.setData(approvedLeaves);
		return pageResponseDTO;

	}

	private com.sahil.models.manager.compoff.RejectedCompOffDTO convertToRejectedForManager(CompOffEntity c){
		com.sahil.models.manager.compoff.RejectedCompOffDTO dto = new com.sahil.models.manager.compoff.RejectedCompOffDTO();
		if(Objects.nonNull(c)){
			dto.setCompOffId(c.getCompOffId());
			dto.setAppliedOn(LeaveUtil.convertDateToStringInParticularFormat(c.getAppliedOn()));
			dto.setRejectedOn(LeaveUtil.convertDateToStringInParticularFormat(c.getApprovedOn()));
			dto.setForDate(LeaveUtil.convertDateToStringInParticularFormat(c.getForDate()));
			dto.setRejectionReason(c.getRejectionReason());
			EmployeeDTO e = new EmployeeDTO();
			e.setEmpCode(c.getEmployeeEntity().getEmployeeCode());
			e.setEmpName(c.getEmployeeEntity().getName());
			dto.setEmployee(e);
		}
		return dto;
	}
	
	private boolean hasRoleManager(EmployeeEntity e){
		if(Objects.nonNull(e)){
			return e.getUserRoles().stream().map(r->r.getRoleName()).collect(Collectors.toSet()).contains("ROLE_MANAGER");
		}
		return false;
	}

	
	
	@Override
	public Response deleteCompOff(int id){
		Response response = new Response();
		response.setCode(1);
		if(id<=0){
			response.setCode(1);
			response.setMessage("Invalid CompOff Id");
			return response;
		}
		
		CompOffEntity compOffEntity = compOffRepository.findOne(id);
		
		if(Objects.isNull(compOffEntity)){
			response.setCode(1);
			response.setMessage("No CompOff found with CompOffId"+id);
			return response;
		}
		
		if(CompOffStatus.APPROVED.equals(compOffEntity.getCompOffStatus())){
			Response response2 = leaveCountService.decrementCompOffCountForCancellation(id);		
			if(response2.getCode()==1){
		    	return response2;
		    }
		}
		
		compOffRepository.delete(compOffEntity);
		response.setCode(0);
		response.setMessage("Deleted Leave with leaveId"+id);
		return response;
	}

	
	
}
