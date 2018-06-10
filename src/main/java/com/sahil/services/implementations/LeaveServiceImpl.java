package com.sahil.services.implementations;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.sahil.entities.EmployeeEntity;
import com.sahil.entities.LeaveEntity;
import com.sahil.enums.LeaveStatus;
import com.sahil.enums.ResponseType;
import com.sahil.models.EmployeeDTO;
import com.sahil.models.LeaveDetailsDayWise;
import com.sahil.models.LeaveDetailsDeptWiseDayWise;
import com.sahil.models.LeaveDetailsDeptWiseDayWiseConverted;
import com.sahil.models.PageResponseDTO;
import com.sahil.models.Response;
import com.sahil.models.UpdateResponse;
import com.sahil.models.emp.leave.ApprovedLeaveDTO;
import com.sahil.models.emp.leave.PendingLeaveDTO;
import com.sahil.models.emp.leave.RejectedLeaveDTO;
import com.sahil.models.emp.leave.TeamMateLeaveDTO;
import com.sahil.models.hr.report.ApprovedLeaves;
import com.sahil.models.hr.report.ApprovedLeavesResponseDTO;
import com.sahil.models.hr.report.Manager;
import com.sahil.models.input.Leave;
import com.sahil.models.manager.leave.UpdateLeaveDTO;
import com.sahil.models.manager.leave.UpdateLeaveRequestListDTO;
import com.sahil.models.manager.leave.UpdateLeaveResponseDTO;
import com.sahil.models.manager.report.ApprovedLeavesForReport;
import com.sahil.models.manager.report.ApprovedLeavesReportWrapper;
import com.sahil.models.request.LeaveRequestDTO;
import com.sahil.repositories.ContactRepository;
import com.sahil.repositories.EmployeeRepository;
import com.sahil.repositories.LeaveRepository;
import com.sahil.services.HolidayService;
import com.sahil.services.LeaveCountService;
import com.sahil.services.LeaveService;
import com.sahil.services.UploadFileService;
import com.sahil.services.WorkingDaysCalculatorService;
import com.sahil.utils.LeaveUtil;



@Service
public class LeaveServiceImpl implements LeaveService{
	
	private static final int PAGE_SIZE = 5;
	
	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private LeaveRepository leaveRepository;
	
	@Autowired
	private LeaveCountService leaveCountService;

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private UploadFileService uploadFileService;
	
	@Autowired
	private HolidayService holidayService;
	
	@Autowired
	private WorkingDaysCalculatorService workingDaysCalculatorService;

	@Override
	@Transactional
	public Response addLeave(Leave leave, String empId) {
		Response response = validate(leave, empId);
		System.out.println("code:"+response.getCode());
		System.out.println("message:"+response.getMessage());
		if (response.getCode() == 1) {
			return response;
		}
		EmployeeEntity employeeEntity = employeeRepository.findOne(empId);
		LeaveEntity leaveEntity = new LeaveEntity();
	    leaveEntity.setEmployeeEntity(employeeEntity);
		leaveEntity.setFromDate(leave.getFromDate());
		leaveEntity.setLeaveStatus(LeaveStatus.PENDING);
		leaveEntity.setLeaveType(leave.getLeaveType());
		leaveEntity.setToDate(leave.getToDate());
		leaveEntity.setAppliedOn(LeaveUtil.getTodaysDate());
		leaveEntity.setApprovedOn(null);
	    leaveEntity.setManagerEntity(employeeEntity.getManager());
	    leaveEntity.setCurrentlyManager(employeeEntity.getManager());
		//employeeEntity.getLeaves().add(leaveEntity);
        employeeEntity.addLeaves(leaveEntity);
	    employeeRepository.save(employeeEntity);
		
		//sendEmail(leave);
		
		response.setMessage("Leave forwarded to your manager");
		return response;
	}
	

	private Response validate(Leave leave, String empId) {
		Response response = new Response();
		response.setCode(1);
		
		if(!uploadFileService.isHolidayFileUploadedForCurrentYear()){
			response.setMessage("Holiday file is not uploaded. <br> Please contact the admin.");
			return response;
		}
		
		if(StringUtils.isEmpty(leave) || leave==null || leave.getFromDate()==null || leave.getToDate()==null){
			response.setMessage("Invalid Data");
			return response;
		}
		
	    if(leave.getFromDate().before(LeaveUtil.getTodaysDate()) && !leave.getFromDate().equals(LeaveUtil.getTodaysDate())){
			response.setMessage("fromDate has already gone");
			return response;
		}
	    if(leave.getToDate().before(LeaveUtil.getTodaysDate()) && !leave.getToDate().equals(LeaveUtil.getTodaysDate())){
	    	response.setMessage("toDate has already gone");
			return response;
	    }
	    if(leave.getFromDate().after(leave.getToDate())){
	    	response.setMessage("fromDate is less than toDate");
			return response;
	    }
		EmployeeEntity employeeEntity = employeeRepository.findOne(empId);
		if (employeeEntity == null) {
			response.setMessage("no employee with employeeId: " + empId + " exists");
			return response;
		}
		if (employeeEntity.getManager() == null) {
			response.setMessage("no manager for employeeId: " + empId + " exists");
			return response;
		}
		if (!employeeEntity.getManager().getUserRoles().stream().map(e->e.getRoleName()).collect(Collectors.toSet()).contains("ROLE_MANAGER")) {
			response.setMessage("employeeId: " + empId + " does not have manager rights");
			return response;
		}
		if(!isLeaveWithinLimit(leave,empId)){
			response.setMessage("Enough leaves are not available");
			return response;
		}
		
		if(workingDaysCalculatorService.getWorkingDaysBetweenTwoDatesIncludingBothDaysIncludingHolidays(leave.getFromDate(),leave.getToDate())==0){
			response.setMessage("There is no working day between the two dates");
			return response;
		}
		/*
		if(isClashingWithAnyApprovedLeave(employeeEntity,leave.getFromDate(),leave.getToDate())){
			response.setMessage("Dates are clashing with some already approved leave");
			return response;
		}
		*/
		response.setCode(0);
		response.setMessage("OKAY");
		return response;
	}
	
	
	
	@SuppressWarnings("unused")
	private boolean isLeaveAvailable(Leave leave,String id){
		System.out.println(leave);
		switch(leave.getLeaveType()){
		       case SICK : if(employeeRepository.findOne(id).getLeaveCountEntity().getSickCount()>0){
			          			return true;
							}
		            		return false;       
		       case BDAY : if(employeeRepository.findOne(id).getLeaveCountEntity().getBdayCount()>0){
		    	               return true;
		       				}
		                    return false;
		       case CASUAL : if(employeeRepository.findOne(id).getLeaveCountEntity().getCasualCount()>0){
		    	               return true;
		       				}
		                    return false;
		       case PRIVILEGED : if(employeeRepository.findOne(id).getLeaveCountEntity().getPrivilegedCount()>0){
		    	                 return true;
		       					}
		                        return false;
		       case COMPOFF : if(employeeRepository.findOne(id).getLeaveCountEntity().getCompOffCount()>0){
		    	                 return true;
		       					}
		                        return false;
		       default : break;
		}
		return false;
	}
	
	
	private boolean isLeaveWithinLimit(Leave leave,String id){
		double left = -1.0;
		double diff = -1.0;	
		
		if(!uploadFileService.isHolidayFileUploadedForCurrentYear()){
			System.out.println("Holiday file not uploaded");
			return false;
		}
		
		switch(leave.getLeaveType()){
	       case SICK : left = employeeRepository.findOne(id).getLeaveCountEntity().getSickCount();
	                   diff = workingDaysCalculatorService.getWorkingDaysBetweenTwoDatesIncludingBothDaysIncludingHolidays(leave.getFromDate(),leave.getToDate());
	                   if(diff<=left){
	                	   return true;
	                   }
	                   return false;
	       case BDAY : left = employeeRepository.findOne(id).getLeaveCountEntity().getBdayCount();
           			   diff = workingDaysCalculatorService.getWorkingDaysBetweenTwoDatesIncludingBothDaysIncludingHolidays(leave.getFromDate(),leave.getToDate());
           			   if(diff<=left){
        	              return true;
                       }
                       return false;
	       case CASUAL :left = employeeRepository.findOne(id).getLeaveCountEntity().getCasualCount();
	       				diff = workingDaysCalculatorService.getWorkingDaysBetweenTwoDatesIncludingBothDaysIncludingHolidays(leave.getFromDate(),leave.getToDate());
	       				if(diff<=left){
	       					return true;
	       				}
	       				return false;
	       case PRIVILEGED : left = employeeRepository.findOne(id).getLeaveCountEntity().getPrivilegedCount();
	       					 diff = workingDaysCalculatorService.getWorkingDaysBetweenTwoDatesIncludingBothDaysIncludingHolidays(leave.getFromDate(),leave.getToDate());
	       					 System.out.println(diff);
	       					 System.out.println(left);
	       					 if(diff<=left){
	       						 return true;
	       					 }
	       					 return false;
	       case COMPOFF : left = employeeRepository.findOne(id).getLeaveCountEntity().getCompOffCount();
	       				  diff = workingDaysCalculatorService.getWorkingDaysBetweenTwoDatesIncludingBothDaysIncludingHolidays(leave.getFromDate(),leave.getToDate());
	       				  if(diff<=left){
	       					  return true;
	       				  }
	       				  return false;
	       default : break;
	}
	return false;
	}
	
	
	@SuppressWarnings("unused")
	private boolean sendEmail(Leave leave){
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom("sender's email");
		simpleMailMessage.setTo("receiver's mail");
		simpleMailMessage.setSubject("Leave Mail");
		simpleMailMessage.setText("text");
		try{
			mailSender.send(simpleMailMessage);
			return true;
		}
		catch(MailException e){
			System.out.println(e);
		}
		catch(Exception e){
			System.out.println(e);
		}
		return false;
	}
	
	
	private PendingLeaveDTO convertToPending(LeaveEntity le){
		PendingLeaveDTO pendingLeavesDTO = new PendingLeaveDTO();
		if(!StringUtils.isEmpty(le)){
			pendingLeavesDTO.setLeaveId(le.getLeaveId());
			pendingLeavesDTO.setAppliedOn(LeaveUtil.convertDateToStringInParticularFormat(le.getAppliedOn()));
			pendingLeavesDTO.setFromDate(LeaveUtil.convertDateToStringInParticularFormat(le.getFromDate()));
			pendingLeavesDTO.setToDate(LeaveUtil.convertDateToStringInParticularFormat(le.getToDate()));
			pendingLeavesDTO.setLeaveType(le.getLeaveType());
			
			EmployeeDTO managerDTO = new EmployeeDTO();
			if(Objects.nonNull(le.getCurrentlyManager())){
				managerDTO.setEmpCode(le.getCurrentlyManager().getEmployeeCode());
				managerDTO.setEmpName(le.getCurrentlyManager().getName());
			}
			pendingLeavesDTO.setPendingWith(managerDTO);
		}
		return pendingLeavesDTO;
	}
	
	
	private ApprovedLeaveDTO convertToApproved(LeaveEntity le){
		ApprovedLeaveDTO approvedLeavesDTO = new ApprovedLeaveDTO();
		if(!StringUtils.isEmpty(le)){
			approvedLeavesDTO.setLeaveId(le.getLeaveId());
			approvedLeavesDTO.setFromDate(LeaveUtil.convertDateToStringInParticularFormat(le.getFromDate()));
			approvedLeavesDTO.setToDate(LeaveUtil.convertDateToStringInParticularFormat(le.getToDate()));
			approvedLeavesDTO.setAppliedOn(LeaveUtil.convertDateToStringInParticularFormat(le.getAppliedOn()));
			approvedLeavesDTO.setApprovedOn(LeaveUtil.convertDateToStringInParticularFormat(le.getApprovedOn()));
			approvedLeavesDTO.setLeaveType(le.getLeaveType());
			EmployeeDTO leaveManagerDTO = new EmployeeDTO();
			if(Objects.nonNull(le.getCurrentlyManager())){
				leaveManagerDTO.setEmpCode(le.getCurrentlyManager().getEmployeeCode());
				leaveManagerDTO.setEmpName(le.getCurrentlyManager().getName());
			}
			approvedLeavesDTO.setApprovedBy(leaveManagerDTO);
		}
		return approvedLeavesDTO;
	}
	
	
	private RejectedLeaveDTO convertToRejected(LeaveEntity le){
		RejectedLeaveDTO rejectedLeavesDTO = new RejectedLeaveDTO();
		if(!StringUtils.isEmpty(le)){
			rejectedLeavesDTO.setLeaveId(le.getLeaveId());
			rejectedLeavesDTO.setFromDate(LeaveUtil.convertDateToStringInParticularFormat(le.getFromDate()));
			rejectedLeavesDTO.setToDate(LeaveUtil.convertDateToStringInParticularFormat(le.getToDate()));
			rejectedLeavesDTO.setAppliedOn(LeaveUtil.convertDateToStringInParticularFormat(le.getAppliedOn()));
			rejectedLeavesDTO.setRejectedOn(LeaveUtil.convertDateToStringInParticularFormat(le.getApprovedOn()));
			rejectedLeavesDTO.setLeaveType(le.getLeaveType());
			EmployeeDTO leaveManagerDTO = new EmployeeDTO();
			if(Objects.nonNull(le.getCurrentlyManager())){
				leaveManagerDTO.setEmpCode(le.getCurrentlyManager().getEmployeeCode());
				leaveManagerDTO.setEmpName(le.getCurrentlyManager().getName());
			}
			rejectedLeavesDTO.setRejectedBy(leaveManagerDTO);
			rejectedLeavesDTO.setRejectionReason(le.getRejectionReason());
			
		}
		return rejectedLeavesDTO;
	}
	
	
	
	private UpdateLeaveDTO convertToUpdateLeaveDTO(LeaveEntity le){
		UpdateLeaveDTO dto = new UpdateLeaveDTO();
		if(Objects.nonNull(le)){
	       		dto.setLeaveId(le.getLeaveId());
	       		dto.setEmployeeCode(le.getEmployeeEntity().getEmployeeCode());
	       		dto.setEmployeeName(le.getEmployeeEntity().getName());
	       		dto.setFromDate(LeaveUtil.convertDateToStringInParticularFormat(le.getFromDate()));
	       		dto.setToDate(LeaveUtil.convertDateToStringInParticularFormat(le.getToDate()));
	       		dto.setAppliedOn(LeaveUtil.convertDateToStringInParticularFormat(le.getAppliedOn()));
	       		dto.setLeaveType(le.getLeaveType());
	       		dto.setResponseType(ResponseType.PENDING);
	       		dto.setRejectionReason(null);
		}
		return dto;
	}
	
	@Override
	@Transactional
	public UpdateResponse updateLeaveDetails(String managerId,UpdateLeaveRequestListDTO updateLeavesDTO){
		UpdateResponse responseDTO = new UpdateResponse();
		List<String> list = new ArrayList<>();
		responseDTO.setList(list);
		responseDTO.setMessage("NOT UPDATED");
		
		if(updateLeavesDTO!=null && !CollectionUtils.isEmpty(updateLeavesDTO.getLeaves())){				
			if(StringUtils.isEmpty(employeeRepository.findOne(managerId))){
				responseDTO.setMessage("INVALID MANAGER ID"+managerId);
				return responseDTO;
			}
			if(!uploadFileService.isHolidayFileUploadedForCurrentYear()){
				responseDTO.setMessage("NO HOLIDAY FILE UPLOADED FOR CURRENT YEAR"+Calendar.getInstance().get(Calendar.YEAR));
				return responseDTO;
			}
			
			Date onDate = LeaveUtil.getTodaysDate();
			for(int i=0;i<updateLeavesDTO.getLeaves().size();i++){
				if(!updateLeavesDTO.getLeaves().get(i).getResponseType().toString().toUpperCase().equals(ResponseType.PENDING.toString())){
					
					ResponseType res= updateLeavesDTO.getLeaves().get(i).getResponseType();
					System.out.println("res"+res);
					
					LeaveStatus leaveStatus = convertResponseTypeToLeaveStatus(res);
					System.out.println("leaveStatus"+leaveStatus);
					
					leaveRepository.updateLeaveDetails(managerId,leaveStatus,onDate,updateLeavesDTO.getLeaves().get(i).getLeaveId(),updateLeavesDTO.getLeaves().get(i).getRejectionReason());
					if(ResponseType.APPROVED.toString().equals(updateLeavesDTO.getLeaves().get(i).getResponseType().toString().toUpperCase())){
					Response response = leaveCountService.decrementLeaveCountForApproved(updateLeavesDTO.getLeaves().get(i).getLeaveId());
						if(response.getCode()==1){
						list.add(String.valueOf(updateLeavesDTO.getLeaves().get(i).getLeaveId()));
						}
					}
				}
			}
			if(list.size()>0){
				responseDTO.setMessage("NOT UPDATED FOR THESE LEAVE IDs");
				return responseDTO;
			}
			responseDTO.setMessage("UPDATED");
			return responseDTO;	
		}
		
		return responseDTO;
	}
	
	
    private LeaveStatus convertResponseTypeToLeaveStatus(ResponseType res) {
		// TODO Auto-generated method stub
		if(LeaveStatus.APPROVED.toString().equals(res.getValue())){
			return LeaveStatus.APPROVED;
		}
		if(LeaveStatus.PENDING.toString().equals(res.getValue())){
			return LeaveStatus.PENDING;
		}
		if(LeaveStatus.REJECTED.toString().equals(res.getValue())){
			return LeaveStatus.REJECTED;
		}
    	return null;
	}


	// considering team mates have same manager for employees
	@Override
	public PageResponseDTO<TeamMateLeaveDTO> findLeavesOfTeamMates(LeaveRequestDTO requestDTO){
		PageResponseDTO<TeamMateLeaveDTO> pageResponseDTO = new PageResponseDTO<>();
		pageResponseDTO.setCode(1);
		pageResponseDTO.setMessage("NO EMPLOYEE WITH ID"+requestDTO.getEmpId());
		PageRequest pageRequest = new PageRequest(requestDTO.getPageNumber()-1, PAGE_SIZE, Sort.Direction.ASC,"appliedOn");
        Page<LeaveEntity> entityResponses = null;
        String id = requestDTO.getEmpId();
		if(!StringUtils.isEmpty(employeeRepository.findOne(id)) && !StringUtils.isEmpty(employeeRepository.findOne(id).getManager()) ){
	        Date fromDate = LeaveUtil.convertStringToDateInParticularFormat(requestDTO.getFromWhen());
	        Date toDate = LeaveUtil.convertStringToDateInParticularFormat(requestDTO.getToWhen());
	        entityResponses = leaveRepository.findApprovedLeavesOfTeamMates(id,employeeRepository.findOne(id).getManager().getEmployeeCode(),fromDate,toDate,pageRequest);
		}
		pageResponseDTO.setMessage("EXCEPTION");
		Page<TeamMateLeaveDTO> teamMatesLeaves = null;
		if(entityResponses!=null){
			pageResponseDTO.setCode(0);
			pageResponseDTO.setMessage("SUCCESSFUL");
			teamMatesLeaves = entityResponses.map(this::convertToTeamMateLeaveDTO);
		}
		pageResponseDTO.setData(teamMatesLeaves);
		return pageResponseDTO;	
	}
	
	
	private TeamMateLeaveDTO convertToTeamMateLeaveDTO(LeaveEntity l){
		TeamMateLeaveDTO dto = new TeamMateLeaveDTO();
		dto.setLeaveId(l.getLeaveId());
		dto.setFromDate(LeaveUtil.convertDateToStringInParticularFormat(l.getFromDate()));
		dto.setToDate(LeaveUtil.convertDateToStringInParticularFormat(l.getToDate()));
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setEmpCode(l.getEmployeeEntity().getEmployeeCode());
		employeeDTO.setEmpName(l.getEmployeeEntity().getName());
		dto.setEmployee(employeeDTO);
		dto.setLeaveType(l.getLeaveType());
		return dto;
	}
	
	@Override
	public LeaveDetailsDeptWiseDayWise findLeaveDetailsBetween(Date fromDate,Date toDate){
		
		LeaveDetailsDeptWiseDayWise deptWise = new LeaveDetailsDeptWiseDayWise();
		HashMap<Date, HashMap<String, Set<EmployeeDTO>>> hashMap = new LinkedHashMap<>();
		deptWise.setHashMap(hashMap);
		
		if(!StringUtils.isEmpty(fromDate) && !StringUtils.isEmpty(toDate)){
			List<ApprovedLeaveDTO> leaveDetails =leaveRepository.findApprovedLeaveEntitiesBetween(fromDate, toDate).stream().map(this::convertToApproved).collect(Collectors.toList());
			
			LeaveDetailsDayWise leaveDetailsDayWise = getDetailsDayWise(fromDate,toDate,leaveDetails);
			
			System.out.println("showing leaveDetails Day Wise");
			for(Map.Entry<Date,Set<EmployeeDTO>> me: leaveDetailsDayWise.getHashMap().entrySet()){
				System.out.println("Date"+me.getKey());
				System.out.println("Value"+me.getValue());
				System.out.println("**************");
			}
			
			
			deptWise = getDeptWiseDetailsFromDateWiseDetails(leaveDetailsDayWise);
			System.out.println("showing leaveDetails Dept and Day Wise");
			
			for(Map.Entry<Date,HashMap<String,Set<EmployeeDTO>>> me:deptWise.getHashMap().entrySet()){
				System.out.println("Date "+me.getKey());
				
				for(Map.Entry<String,Set<EmployeeDTO>> m : me.getValue().entrySet()){
					System.out.println("Department"+m.getKey());
					
					System.out.println("SET of employees");
					System.out.println(m.getValue());
				}
			}
		}
		return deptWise;
	}
	
	
	private LeaveDetailsDeptWiseDayWise getDeptWiseDetailsFromDateWiseDetails(
			LeaveDetailsDayWise leaveDetailsDayWise) {
		
			LeaveDetailsDeptWiseDayWise leaveDetailsDeptWiseDayWise = new LeaveDetailsDeptWiseDayWise();
		    
		    HashMap<Date,HashMap<String,Set<EmployeeDTO>>> deptMap1 = new LinkedHashMap<>();
		    
		    leaveDetailsDeptWiseDayWise.setHashMap(deptMap1);
		    
		    if(CollectionUtils.isEmpty(leaveDetailsDayWise.getHashMap())){
		    	return leaveDetailsDeptWiseDayWise;
		    }
		    
		    
		    for(Map.Entry<Date, Set<EmployeeDTO>> me: leaveDetailsDayWise.getHashMap().entrySet()){
		    	Date key = me.getKey();
		    	Set<EmployeeDTO> set = me.getValue();
		    	HashMap<String,Set<EmployeeDTO>> inner = new LinkedHashMap<>();
		    	
		    	for(EmployeeDTO e: set){
		    		String dept = e.getEmpDepartment();
		 
		    		Set<EmployeeDTO> s1 = inner.get(dept);
		    		
		    		if(s1==null){
		    			s1 = new TreeSet<>(new Comparator<EmployeeDTO>() {

							@Override
							public int compare(EmployeeDTO o1, EmployeeDTO o2) {
								// TODO Auto-generated method stub
								return o1.getEmpCode().compareToIgnoreCase(o2.getEmpCode());
							}
						});
		    		}
		    		s1.add(e);
		    		inner.put(dept,s1);
		    		
		    	}
		    	if(!inner.isEmpty()){
		    		deptMap1.put(key,inner);
		    	}
		    }
		    
		return leaveDetailsDeptWiseDayWise;
	}

	
	private LeaveDetailsDeptWiseDayWiseConverted convert(LeaveDetailsDeptWiseDayWise l){
		LeaveDetailsDeptWiseDayWiseConverted c = new  LeaveDetailsDeptWiseDayWiseConverted();
		HashMap<String, HashMap<String, Set<EmployeeDTO>>> hm = new LinkedHashMap<>();
		c.setHashMap(hm);
		
		for( Map.Entry<Date,HashMap<String,Set<EmployeeDTO>>> me : l.getHashMap().entrySet()){
			
			Date date = me.getKey();
			String d1 = LeaveUtil.convertDateToStringInParticularFormat(date);
			hm.put(d1,me.getValue());
		}
		return c;
	}


	private LeaveDetailsDayWise getDetailsDayWise(Date fromDate,Date toDate,List<ApprovedLeaveDTO> leaveDetails){
		Calendar fromCal = Calendar.getInstance();
		fromCal.setTime(fromDate);
		Calendar toCal = Calendar.getInstance();
		toCal.setTime(toDate);
			if(fromCal.getTimeInMillis()>toCal.getTimeInMillis()){
				fromCal.setTime(toDate);
				toCal.setTime(fromDate);
			}
			
		LeaveDetailsDayWise leaveDetailsDayWise = new LeaveDetailsDayWise();
		HashMap<Date,Set<EmployeeDTO>> hashMap = new LinkedHashMap<>();
		leaveDetailsDayWise.setHashMap(hashMap);
		
		while(fromCal.getTimeInMillis()<=toCal.getTimeInMillis()){
			
			Date key = null;
			
			//System.out.println("processing for "+fromCal.getTime());
			if(true || (!LeaveUtil.isWeekend(fromCal) && !holidayService.isHoliday(fromCal.getTime()))){ // ignoring this condition right now
				for(ApprovedLeaveDTO ldto:leaveDetails){
					Date from = LeaveUtil.convertStringToDateInParticularFormat(ldto.getFromDate());
					Date to = LeaveUtil.convertStringToDateInParticularFormat(ldto.getToDate());
					
					if(from!=null && to!=null && LeaveUtil.isWithinRangeInclusive(fromCal.getTime(),from, to)){
						key = fromCal.getTime();
						Set<EmployeeDTO> emps = hashMap.get(key);
						if(emps==null){
							emps = new TreeSet<>(new Comparator<EmployeeDTO>() {

								@Override
								public int compare(EmployeeDTO o1, EmployeeDTO o2) {
									// TODO Auto-generated method stub
									return o1.getEmpCode().compareToIgnoreCase(o2.getEmpCode());
								}
								
							});
						}
						LeaveEntity lentity = leaveRepository.findOne(ldto.getLeaveId());
	                    if(lentity!=null){
	                    	EmployeeEntity entity = lentity.getEmployeeEntity();
	                    	if(entity!=null){
	                    		EmployeeDTO edto = new EmployeeDTO();
	    						edto.setEmpCode(entity.getEmployeeCode());
	    						edto.setEmpName(entity.getName());
	    						edto.setEmpDepartment(entity.getDepartmentEntity().getName());
	    						emps.add(edto);
	                    	}
	                    }
						hashMap.put(key,emps);
					}
					//System.out.println("key"+key);
					
				}
			}
			fromCal.add(Calendar.DAY_OF_YEAR,1);
		}		
		return leaveDetailsDayWise;
	}


	@Override
	public PageResponseDTO<PendingLeaveDTO> findAllPendingLeavesOfEmployee(LeaveRequestDTO requestDTO) {		
		PageResponseDTO<PendingLeaveDTO> pageResponseDTO = new PageResponseDTO<>();
		pageResponseDTO.setCode(1);
		pageResponseDTO.setMessage("NO EMPLOYEE WITH ID"+requestDTO.getEmpId());
		PageRequest pageRequest = new PageRequest(requestDTO.getPageNumber()-1, PAGE_SIZE, Sort.Direction.ASC,"appliedOn");
        Page<LeaveEntity> entityResponses = null;
        String id = requestDTO.getEmpId();
        Date fromDate = LeaveUtil.convertStringToDateInParticularFormat(requestDTO.getFromWhen());
        Date toDate = LeaveUtil.convertStringToDateInParticularFormat(requestDTO.getToWhen());
        if(!StringUtils.isEmpty(employeeRepository.findOne(id)) && !StringUtils.isEmpty(employeeRepository.findOne(id).getManager())){
			entityResponses = leaveRepository.findPendingOrRejectedLeavesByEmployeeEntityAndLeaveStatus(id,LeaveStatus.PENDING, fromDate, toDate,pageRequest);
		}
		pageResponseDTO.setMessage("EXCEPTION : EITHER EMPLOYEE OR HIS MANAGER DOES NOT EXISTS");
		Page<PendingLeaveDTO> pendingLeaves = null;
		if(entityResponses!=null){
			pageResponseDTO.setCode(0);
			pageResponseDTO.setMessage("SUCCESSFUL");
			System.out.println(entityResponses.getContent());
			pendingLeaves = entityResponses.map(this::convertToPending);
		}
		pageResponseDTO.setData(pendingLeaves);
		return pageResponseDTO;
	}


	@Override
	public PageResponseDTO<ApprovedLeaveDTO> findAllApprovedLeavesOfEmployee(LeaveRequestDTO requestDTO) {
		PageResponseDTO<ApprovedLeaveDTO> pageResponseDTO = new PageResponseDTO<>();
		pageResponseDTO.setCode(1);
		pageResponseDTO.setMessage("NO EMPLOYEE WITH ID"+requestDTO.getEmpId());
		PageRequest pageRequest = new PageRequest(requestDTO.getPageNumber()-1, PAGE_SIZE, Sort.Direction.ASC,"appliedOn");
        Page<LeaveEntity> entityResponses = null;
        String id = requestDTO.getEmpId();
        if(!StringUtils.isEmpty(employeeRepository.findOne(id)) && !StringUtils.isEmpty(employeeRepository.findOne(id).getManager())){
        	Date fromDate = LeaveUtil.convertStringToDateInParticularFormat(requestDTO.getFromWhen());
	        Date toDate = LeaveUtil.convertStringToDateInParticularFormat(requestDTO.getToWhen());
        	entityResponses = leaveRepository.findApprovedLeavesByEmployeeEntity(id, fromDate, toDate,pageRequest);
		}
		pageResponseDTO.setMessage("EXCEPTION");
		Page<ApprovedLeaveDTO> approvedLeaves = null;
		if(entityResponses!=null){
			pageResponseDTO.setCode(0);
			pageResponseDTO.setMessage("SUCCESSFUL");
			approvedLeaves = entityResponses.map(this::convertToApproved);
		}
		pageResponseDTO.setData(approvedLeaves);
		return pageResponseDTO;
	}
	
	
	


	@Override
	public PageResponseDTO<RejectedLeaveDTO> findAllRejectedLeavesOfEmployee(LeaveRequestDTO requestDTO) {
		PageResponseDTO<RejectedLeaveDTO> pageResponseDTO = new PageResponseDTO<>();
		pageResponseDTO.setCode(1);
		pageResponseDTO.setMessage("NO EMPLOYEE WITH ID"+requestDTO.getEmpId());
		PageRequest pageRequest = new PageRequest(requestDTO.getPageNumber()-1, PAGE_SIZE, Sort.Direction.ASC,"appliedOn");
        Page<LeaveEntity> entityResponses = null;
        String id = requestDTO.getEmpId();
        if(!StringUtils.isEmpty(employeeRepository.findOne(id)) && !StringUtils.isEmpty(employeeRepository.findOne(id).getManager())){
        	Date fromDate = LeaveUtil.convertStringToDateInParticularFormat(requestDTO.getFromWhen());
	        Date toDate = LeaveUtil.convertStringToDateInParticularFormat(requestDTO.getToWhen());
        	entityResponses = leaveRepository.findPendingOrRejectedLeavesByEmployeeEntityAndLeaveStatus(id,LeaveStatus.REJECTED, fromDate, toDate,pageRequest);
		}
		pageResponseDTO.setMessage("EXCEPTION");
		Page<RejectedLeaveDTO> rejectedLeaves = null;
		if(entityResponses!=null){
			pageResponseDTO.setCode(0);
			pageResponseDTO.setMessage("SUCCESSFUL");
			rejectedLeaves = entityResponses.map(this::convertToRejected);
		}
		pageResponseDTO.setData(rejectedLeaves);
		return pageResponseDTO;
	}


	@Override
	public UpdateLeaveResponseDTO findAllLeavesByManagerForUpdation(String id) {
		UpdateLeaveResponseDTO updateLeavesResponseDTO = new UpdateLeaveResponseDTO();
		updateLeavesResponseDTO.setCode(1);
		updateLeavesResponseDTO.setMessage("NO MANAGER WITH ID"+id);
		List<UpdateLeaveDTO> entityResponses = new ArrayList<>();
		if(!StringUtils.isEmpty(employeeRepository.findOne(id)) && hasRoleManager(employeeRepository.findOne(id))){
			entityResponses = leaveRepository.findByCurrentlyManagerAndLeaveStatus(employeeRepository.findOne(id),LeaveStatus.PENDING).stream().map(this::convertToUpdateLeaveDTO).collect(Collectors.toList());
		}
		System.out.println(entityResponses);
		if(CollectionUtils.isEmpty(entityResponses)){
			updateLeavesResponseDTO.setMessage("NO PENDING LEAVES");
			return updateLeavesResponseDTO;
		}
		
		updateLeavesResponseDTO.setCode(0);
	    updateLeavesResponseDTO.setMessage("SUCCESS");
		updateLeavesResponseDTO.setLeaves(entityResponses);
	    return updateLeavesResponseDTO;
	}


	@Override
	public PageResponseDTO<com.sahil.models.manager.leave.ApprovedLeaveDTO> findLeavesApprovedByManager(
			LeaveRequestDTO requestDTO) {
		System.out.println(requestDTO);
		PageResponseDTO<com.sahil.models.manager.leave.ApprovedLeaveDTO> pageResponseDTO = new PageResponseDTO<>();
		pageResponseDTO.setCode(1);
		pageResponseDTO.setMessage("NO MANAGER WITH ID"+requestDTO.getEmpId());
		PageRequest pageRequest = new PageRequest(requestDTO.getPageNumber()-1, PAGE_SIZE, Sort.Direction.ASC,"approvedOn");
        Page<LeaveEntity> entityResponses = null;
        String id = requestDTO.getEmpId();
        System.out.println(requestDTO);
        if(!StringUtils.isEmpty(employeeRepository.findOne(id)) && hasRoleManager(employeeRepository.findOne(id))){
        	Date fromDate = LeaveUtil.convertStringToDateInParticularFormat(requestDTO.getFromWhen());
	        Date toDate = LeaveUtil.convertStringToDateInParticularFormat(requestDTO.getToWhen());
	        
        	entityResponses = leaveRepository.findByCurrentlyManagerAndLeaveStatus(id,LeaveStatus.APPROVED,fromDate,toDate,pageRequest);
		    System.out.println(entityResponses);
        }
		pageResponseDTO.setMessage("NO LEAVE AVAILABLE");
		Page<com.sahil.models.manager.leave.ApprovedLeaveDTO> approvedLeaves = null;
		if(entityResponses!=null){
			pageResponseDTO.setCode(0);
			pageResponseDTO.setMessage("SUCCESSFUL");
			approvedLeaves = entityResponses.map(this::convertToApprovedForManager);
		}
		pageResponseDTO.setData(approvedLeaves);
		return pageResponseDTO;
	}


	private com.sahil.models.manager.leave.ApprovedLeaveDTO convertToApprovedForManager(LeaveEntity l){
		com.sahil.models.manager.leave.ApprovedLeaveDTO dto = new com.sahil.models.manager.leave.ApprovedLeaveDTO();
		if(Objects.nonNull(l)){
			dto.setLeaveId(l.getLeaveId());
			dto.setAppliedOn(LeaveUtil.convertDateToStringInParticularFormat(l.getAppliedOn()));
			dto.setApprovedOn(LeaveUtil.convertDateToStringInParticularFormat(l.getApprovedOn()));
			dto.setFromDate(LeaveUtil.convertDateToStringInParticularFormat(l.getFromDate()));
			dto.setToDate(LeaveUtil.convertDateToStringInParticularFormat(l.getToDate()));
			dto.setLeaveType(l.getLeaveType());
			EmployeeDTO e = new EmployeeDTO();
			e.setEmpCode(l.getEmployeeEntity().getEmployeeCode());
			e.setEmpName(l.getEmployeeEntity().getName());
			dto.setEmployee(e);
		}
		return dto;
	}
	
	
	@Override
	public PageResponseDTO<com.sahil.models.manager.leave.RejectedLeaveDTO> findLeavesRejectedByManager(
			LeaveRequestDTO requestDTO) {
		PageResponseDTO<com.sahil.models.manager.leave.RejectedLeaveDTO> pageResponseDTO = new PageResponseDTO<>();
		pageResponseDTO.setCode(1);
		pageResponseDTO.setMessage("NO MANAGER WITH ID"+requestDTO.getEmpId());
		PageRequest pageRequest = new PageRequest(requestDTO.getPageNumber()-1, PAGE_SIZE, Sort.Direction.ASC,"approvedOn");
        Page<LeaveEntity> entityResponses = null;
        String id = requestDTO.getEmpId();
        if(!StringUtils.isEmpty(employeeRepository.findOne(id)) && hasRoleManager(employeeRepository.findOne(id))){
        	Date fromDate = LeaveUtil.convertStringToDateInParticularFormat(requestDTO.getFromWhen());
	        Date toDate = LeaveUtil.convertStringToDateInParticularFormat(requestDTO.getToWhen());
        	entityResponses = leaveRepository.findByCurrentlyManagerAndLeaveStatus(id,LeaveStatus.REJECTED,fromDate,toDate,pageRequest);
		}
		pageResponseDTO.setMessage("NO LEAVE AVAILABLE");
		Page<com.sahil.models.manager.leave.RejectedLeaveDTO> approvedLeaves = null;
		if(entityResponses!=null){
			pageResponseDTO.setCode(0);
			pageResponseDTO.setMessage("SUCCESSFUL");
			approvedLeaves = entityResponses.map(this::convertToRejectedForManager);
		}
		pageResponseDTO.setData(approvedLeaves);
		return pageResponseDTO;

	}
	
	
	
	private com.sahil.models.manager.leave.RejectedLeaveDTO convertToRejectedForManager(LeaveEntity l){
		com.sahil.models.manager.leave.RejectedLeaveDTO dto = new com.sahil.models.manager.leave.RejectedLeaveDTO();
		if(Objects.nonNull(l)){
			dto.setLeaveId(l.getLeaveId());
			dto.setAppliedOn(LeaveUtil.convertDateToStringInParticularFormat(l.getAppliedOn()));
			dto.setFromDate(LeaveUtil.convertDateToStringInParticularFormat(l.getFromDate()));
			dto.setToDate(LeaveUtil.convertDateToStringInParticularFormat(l.getToDate()));
			dto.setRejectedOn(LeaveUtil.convertDateToStringInParticularFormat(l.getApprovedOn()));
			dto.setLeaveType(l.getLeaveType());
			dto.setRejectionReason(l.getRejectionReason());
			EmployeeDTO e = new EmployeeDTO();
			e.setEmpCode(l.getEmployeeEntity().getEmployeeCode());
			e.setEmpName(l.getEmployeeEntity().getName());
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
	public ApprovedLeavesResponseDTO findLeavesForHRReportForCurrentMonth() {
		// TODO Auto-generated method stub
		  Date startDate = LeaveUtil.getFirstDateOfMonth();
		  Date endDate = LeaveUtil.getLastDateOfMonth();
		  ApprovedLeavesResponseDTO approvedLeavesResponseDTO = new ApprovedLeavesResponseDTO();
		  approvedLeavesResponseDTO.setCode(1);
		  approvedLeavesResponseDTO.setMessage("ERROR");
		  List<LeaveEntity> leaves = leaveRepository.findApprovedLeaveEntitiesForReport(startDate,endDate);
		  
		  if(!CollectionUtils.isEmpty(leaves)){
			  approvedLeavesResponseDTO.setCode(0);
			  approvedLeavesResponseDTO.setMessage("SUCCESS");
			  approvedLeavesResponseDTO.setApprovedLeaves(convert(leaves));
		  }
		  
          approvedLeavesResponseDTO.getApprovedLeaves().forEach(e->System.out.println(e));
		  return approvedLeavesResponseDTO;
	}
	
	private List<ApprovedLeaves> convert(List<LeaveEntity> leaves){
		List<ApprovedLeaves> list = new ArrayList<>();
		for(LeaveEntity leave : leaves){
			ApprovedLeaves approvedLeaves = new ApprovedLeaves();
			approvedLeaves.setLeaveId(leave.getLeaveId());
			EmployeeDTO emp = new EmployeeDTO();
			if(Objects.nonNull(leave.getEmployeeEntity())){
				emp.setEmpCode(leave.getEmployeeEntity().getEmployeeCode());
				emp.setEmpName(leave.getEmployeeEntity().getName());
				emp.setEmpDepartment(leave.getEmployeeEntity().getDepartmentEntity().getName());
			}
			approvedLeaves.setEmployee(emp);
			

			EmployeeDTO manager = new EmployeeDTO();
			if(Objects.nonNull(leave.getManagerEntity())){
				manager.setEmpCode(leave.getManagerEntity().getEmployeeCode());
				manager.setEmpName(leave.getManagerEntity().getName());
				manager.setEmpDepartment(leave.getManagerEntity().getDepartmentEntity().getName());
			}
			approvedLeaves.setManager(manager);			
			
			EmployeeDTO approver = new EmployeeDTO();
			if(Objects.nonNull(leave.getCurrentlyManager())){
				approver.setEmpCode(leave.getCurrentlyManager().getEmployeeCode());
				approver.setEmpName(leave.getCurrentlyManager().getName());
				approver.setEmpDepartment(leave.getCurrentlyManager().getDepartmentEntity().getName());
			}
			approvedLeaves.setApprovedBy(approver);

		    approvedLeaves.setAppliedOn(LeaveUtil.convertDateToStringInParticularFormat(leave.getAppliedOn()));	
			approvedLeaves.setApprovedOn(LeaveUtil.convertDateToStringInParticularFormat(leave.getApprovedOn()));
		    
			approvedLeaves.setFromDate(LeaveUtil.convertDateToStringInParticularFormat(leave.getFromDate()));
			approvedLeaves.setToDate(LeaveUtil.convertDateToStringInParticularFormat(leave.getToDate()));
			
			approvedLeaves.setLeaveType(leave.getLeaveType().name());
			approvedLeaves.setNoOfDays(workingDaysCalculatorService.getWorkingDaysBetweenTwoDatesIncludingBothDaysIncludingHolidays(leave.getFromDate(),leave.getToDate()));
		    list.add(approvedLeaves);
		}
		return list;
		
	}

	
	private ApprovedLeavesForReport convertToApprovedLeavesForManagerReport(LeaveEntity leave){
		ApprovedLeavesForReport approvedLeaves = new ApprovedLeavesForReport();
		if(Objects.nonNull(leave)){
			approvedLeaves.setLeaveId(leave.getLeaveId());
			EmployeeDTO emp = new EmployeeDTO();
			if(Objects.nonNull(leave.getEmployeeEntity())){
				emp.setEmpCode(leave.getEmployeeEntity().getEmployeeCode());
				emp.setEmpName(leave.getEmployeeEntity().getName());
				emp.setEmpDepartment(leave.getEmployeeEntity().getDepartmentEntity().getName());
			}
			approvedLeaves.setEmployee(emp);
			EmployeeDTO approver = new EmployeeDTO();
			if(Objects.nonNull(leave.getCurrentlyManager())){
				approver.setEmpCode(leave.getCurrentlyManager().getEmployeeCode());
				approver.setEmpName(leave.getCurrentlyManager().getName());
				approver.setEmpDepartment(leave.getCurrentlyManager().getDepartmentEntity().getName());
			}
			approvedLeaves.setApprovedBy(approver);

		    approvedLeaves.setAppliedOn(LeaveUtil.convertDateToStringInParticularFormat(leave.getAppliedOn()));	
			approvedLeaves.setApprovedOn(LeaveUtil.convertDateToStringInParticularFormat(leave.getApprovedOn()));
		    
			approvedLeaves.setFromDate(LeaveUtil.convertDateToStringInParticularFormat(leave.getFromDate()));
			approvedLeaves.setToDate(LeaveUtil.convertDateToStringInParticularFormat(leave.getToDate()));
			
			approvedLeaves.setLeaveType(leave.getLeaveType().name());
			approvedLeaves.setNoOfDays(workingDaysCalculatorService.getWorkingDaysBetweenTwoDatesIncludingBothDaysIncludingHolidays(leave.getFromDate(),leave.getToDate()));
		}
		return approvedLeaves;
	}
	
	
	@Override
	public ApprovedLeavesReportWrapper findLeavesForManagersForCurrentWeek() {
		// TODO Auto-generated method stub
		
		ApprovedLeavesReportWrapper approvedLeavesReportWrapper = new ApprovedLeavesReportWrapper();
		approvedLeavesReportWrapper.setCode(1);
		approvedLeavesReportWrapper.setMessage("UNSUCCESSFUL");
		
		Map<Manager,List<ApprovedLeavesForReport>> hashMap = new LinkedHashMap<>();
		approvedLeavesReportWrapper.setLeaves(hashMap);
		
		Date fromDate = LeaveUtil.getFirstDateOfWeek();
		Date toDate = LeaveUtil.getLastDateOfWeek();
		
		List<LeaveEntity> leaves = leaveRepository.findApprovedLeaveEntitiesForReport(fromDate, toDate);
		
		if(!CollectionUtils.isEmpty(leaves)){
			hashMap = leaves.stream().collect(Collectors.groupingBy(l->convertToManager(l.getManagerEntity()),Collectors.mapping(e->convertToApprovedLeavesForManagerReport(e), Collectors.toList())));
			for(Map.Entry<Manager,List<ApprovedLeavesForReport>> entry:hashMap.entrySet()){
				System.out.println(entry.getKey());
				System.out.println(entry.getValue());
			}
		}
		
		
		
		if(!hashMap.values().isEmpty()){
			approvedLeavesReportWrapper.setCode(0);
			approvedLeavesReportWrapper.setMessage("SUCCESSFUL");
			approvedLeavesReportWrapper.setLeaves(hashMap);
		}
		
		approvedLeavesReportWrapper.getLeaves().forEach((a,b)->{
			System.out.println("manager"+a) ;
			System.out.println("leaves");
			b.forEach(e->System.out.println(e));
		});
		return approvedLeavesReportWrapper;
	}
	
	private Manager convertToManager(EmployeeEntity e){
		Manager manager = new Manager();
		if(Objects.nonNull(e)){
			manager.setEmpCode(e.getEmployeeCode());
			manager.setEmpName(e.getName());
			manager.setEmpDepartment(e.getDepartmentEntity().getName());
			manager.setEmail(contactRepository.findByEmployeeEntity(e).getEmailId());
		}
		return manager;
	}
	
	@Override
	public Response deleteLeave(int id){
		Response response = new Response();
		response.setCode(1);
		if(id<=0){
			response.setCode(1);
			response.setMessage("Invalid Leave Id");
			return response;
		}
		
		LeaveEntity leaveEntity = leaveRepository.findOne(id);
		if(Objects.isNull(leaveEntity)){
			response.setCode(1);
			response.setMessage("No leave found with leaveId"+id);
			return response;
		}
		
		if(!uploadFileService.isHolidayFileUploadedForCurrentYear()){
			response.setCode(1);
			response.setMessage("No Holiday File uploaded for current year"+Calendar.getInstance().get(Calendar.YEAR));
			return response;
		}
		
		if(LeaveStatus.APPROVED.equals(leaveEntity.getLeaveStatus())){
			Response response2 = leaveCountService.incrementLeaveCountForCancellation(id);
		    if(response2.getCode()==1){
		    	return response2;
		    }
		}
		
		
		leaveRepository.delete(id);
		response.setCode(0);
		response.setMessage("Deleted Leave with leaveId"+id);
		return response;
	}
	
	
}
