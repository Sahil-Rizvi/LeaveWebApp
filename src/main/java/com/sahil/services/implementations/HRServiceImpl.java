package com.sahil.services.implementations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.sahil.entities.AdminEntity;
import com.sahil.entities.AdminPasswordResetToken;
import com.sahil.entities.CompOffEntity;
import com.sahil.entities.ContactEntity;
import com.sahil.entities.DepartmentEntity;
import com.sahil.entities.DesignationEntity;
import com.sahil.entities.EmployeeEntity;
import com.sahil.entities.EmployeePasswordResetToken;
import com.sahil.entities.LeaveCountEntity;
import com.sahil.entities.LeaveEntity;
import com.sahil.entities.RoleEntity;
import com.sahil.entities.VerificationToken;
import com.sahil.entities.VerificationTokenAdmin;
import com.sahil.enums.CompOffStatus;
import com.sahil.enums.LeaveStatus;
import com.sahil.models.PageResponseDTO;
import com.sahil.models.Response;
import com.sahil.models.UpdateResponse;
import com.sahil.models.admin.UpdateAdminDTO;
import com.sahil.models.hr.ContactDTO;
import com.sahil.models.hr.EmployeeDTO;
import com.sahil.models.hr.EmployeeForListing;
import com.sahil.models.hr.EmployeeForRights;
import com.sahil.models.hr.LeaveCountDTO;
import com.sahil.models.hr.ListOfEmployeeForRights;
import com.sahil.models.hr.UpdateEmployeeRights;
import com.sahil.models.hr.UpdateEmployeeRightsList;
import com.sahil.models.hr.emp.UpdateEmployeeDTO;
import com.sahil.models.hr.emp.compoffs.ApprovedCompOffs;
import com.sahil.models.hr.emp.compoffs.PendingCompOffs;
import com.sahil.models.hr.emp.compoffs.RejectedCompOffs;
import com.sahil.models.hr.emp.leaves.ApprovedLeaves;
import com.sahil.models.hr.emp.leaves.PendingLeaves;
import com.sahil.models.hr.emp.leaves.RejectedLeaves;
import com.sahil.models.input.ResetPasswordDTO;
import com.sahil.repositories.AdminPasswordResetTokenRepository;
import com.sahil.repositories.AdminRepository;
import com.sahil.repositories.AdminVerificationTokenRepository;
import com.sahil.repositories.CompOffRepository;
import com.sahil.repositories.ContactRepository;
import com.sahil.repositories.DepartmentRepository;
import com.sahil.repositories.DesignationRepository;
import com.sahil.repositories.EmployeePasswordResetTokenRepository;
import com.sahil.repositories.EmployeeRepository;
import com.sahil.repositories.LeaveCountRepository;
import com.sahil.repositories.LeaveRepository;
import com.sahil.repositories.RoleRepository;
import com.sahil.repositories.VerificationTokenRepository;
import com.sahil.services.HRService;
import com.sahil.services.WorkingDaysCalculatorService;
import com.sahil.utils.LeaveUtil;

@Service
public class HRServiceImpl implements HRService{

	private static final int PAGE_SIZE = 1;
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private DesignationRepository designationRepository;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private LeaveCountRepository leaveCountRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private AdminVerificationTokenRepository adminVerificationTokenRepository;
	
	@Autowired
	private AdminPasswordResetTokenRepository adminPasswordResetTokenRepository;
	
	@Autowired
	private VerificationTokenRepository verificationTokenRepository;
	
	@Autowired
	private EmployeePasswordResetTokenRepository employeePasswordResetTokenRepository;
	
	
	@Autowired
	private WorkingDaysCalculatorService workingDaysCalculatorService;
	
	@Autowired
	private LeaveRepository leaveRepository;
	
	@Autowired
	private CompOffRepository compOffRepository;
	
	@Override
	public ListOfEmployeeForRights findEmployeesToUpdateManagerStatus() {
		// TODO Auto-generated method stub
		ListOfEmployeeForRights employeeForRights = new ListOfEmployeeForRights();
		employeeForRights.setCode(1);
		employeeForRights.setMessage("NO EMPLOYEE TO UPDATE MANAGER STATUS");
		List<EmployeeForRights> list = new ArrayList<>();
		employeeForRights.setEmployees(list);
		List<EmployeeEntity> entities = null;
		entities =  employeeRepository.findAll();
		
		for(EmployeeEntity employeeEntity:entities){
			Set<String> roles = employeeEntity.getUserRoles().stream().map(e->e.getRoleName()).collect(Collectors.toSet());
			if(!roles.isEmpty() && !roles.contains("ROLE_MANAGER")){
				list.add(convertToEmployeeForRights(employeeEntity));
				System.out.println(employeeEntity.getEmployeeCode());
			}
		}
		
		if(!CollectionUtils.isEmpty(list)){
		    employeeForRights.setCode(0);
		    employeeForRights.setMessage("SUCCESS");
		}
		return employeeForRights;
	}

	@Override
	public UpdateResponse updateEmployeeManagerStatus(UpdateEmployeeRightsList employeeList) {
		// TODO Auto-generated method stub
		UpdateResponse response = new UpdateResponse();
		List<String> list = new ArrayList<>();
	   	response.setCode(1);
	   	response.setMessage("NO DATA TO UPDATE");		   	
	   	response.setList(list);
	   	
		for(UpdateEmployeeRights updateEmployeeRights:employeeList.getEmployees()){
			String empId = updateEmployeeRights.getEmpCode();
			EmployeeEntity employeeEntity = employeeRepository.findOne(empId);
			if(employeeEntity!=null && !hasRoleManager(employeeEntity) && updateEmployeeRights.isManagerStatus()){
				RoleEntity roleEntity = roleRepository.findByRoleName("ROLE_MANAGER");
				if(roleEntity==null){
					roleEntity = new RoleEntity();
					roleEntity.setRoleName("ROLE_MANAGER");
					roleRepository.save(roleEntity);
				}
				employeeEntity.getUserRoles().add(roleEntity);
				employeeRepository.save(employeeEntity);
				list.add(empId);
			}
		}
		
		if(!list.isEmpty()){
			response.setCode(0);
		   	response.setMessage("UPDATED");
		}
		System.out.println(list);
		return response;
	}

	@Override
	public EmployeeDTO findEmployeeByEmployeeCode(String empCode) {
		// TODO Auto-generated method stub
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setCode(1);
		if(StringUtils.isEmpty(empCode)){
			employeeDTO.setMessage("PLEASE ENTER THE EMPLOYEE ID");
			return employeeDTO;
		}
		
		EmployeeEntity employeeEntity = employeeRepository.findOne(empCode);
		if(StringUtils.isEmpty(employeeEntity)){
			employeeDTO.setMessage("NO EMPLOYEE FOUND WITH ID: "+empCode);
			return employeeDTO;
		}
		
	    setParamsEmployeeDTO(employeeDTO,employeeEntity);
		
		return employeeDTO;
	}

	private void setParamsEmployeeDTO(EmployeeDTO employeeDTO, EmployeeEntity employeeEntity) {
		// TODO Auto-generated method stub
		if(Objects.nonNull(employeeEntity)){
			employeeDTO.setCode(0);
			employeeDTO.setMessage("SUCCESS");
			System.out.println(employeeEntity.getEmployeeCode());
			employeeDTO.setEmployeeCode(employeeEntity.getEmployeeCode());
			employeeDTO.setEmployeeName(employeeEntity.getName());
			employeeDTO.setDateofBirth(LeaveUtil.convertDateToStringInParticularFormat(employeeEntity.getDateofBirth()));
			employeeDTO.setDateofJoining(LeaveUtil.convertDateToStringInParticularFormat(employeeEntity.getDateofJoining()));
			if(employeeEntity.getDepartmentEntity()!=null){
				employeeDTO.setDepartment(employeeEntity.getDepartmentEntity().getName());
			}
			if(employeeEntity.getDesignationEntity()!=null){
				employeeDTO.setDesignation(employeeEntity.getDesignationEntity().getName());
			}
			if(Objects.nonNull(employeeEntity.getManager())){
				employeeDTO.setManagerCode(employeeEntity.getManager().getEmployeeCode());
				employeeDTO.setManagerName(employeeEntity.getManager().getName());
			}
			//ContactEntity contactEntity = contactRepository.findByEmployeeEntity(employeeEntity);
			ContactEntity contactEntity = employeeEntity.getContactEntity();
			ContactDTO contact = new ContactDTO();
			if(!StringUtils.isEmpty(contactEntity)){
				contact.setEmailId(contactEntity.getEmailId());
				contact.setPhoneNo(contactEntity.getPhoneNo());
			}
			employeeDTO.setContact(contact);
			
			//LeaveCountEntity leaveCountEntity = leaveCountRepository.findByEmployeeEntity(employeeEntity);
			LeaveCountEntity leaveCountEntity = employeeEntity.getLeaveCountEntity();
			LeaveCountDTO leaveCountDTO = new LeaveCountDTO();
			if(!StringUtils.isEmpty(leaveCountEntity)){
				leaveCountDTO.setBdayCount(leaveCountEntity.getBdayCount());
				leaveCountDTO.setCasualCount(leaveCountEntity.getCasualCount());
				leaveCountDTO.setCompOffCount(leaveCountEntity.getCompOffCount());
				leaveCountDTO.setPrivilegedCount(leaveCountEntity.getPrivilegedCount());
				leaveCountDTO.setSickCount(leaveCountEntity.getSickCount());
			}
			employeeDTO.setLeaveCount(leaveCountDTO);
			
			List<ApprovedLeaves> approvedLeaves = new ArrayList<>();
			List<RejectedLeaves> rejectedLeaves = new ArrayList<>();
			List<PendingLeaves> pendingLeaves = new ArrayList<>();
			setLeavesOfAllStatus(employeeEntity,approvedLeaves,rejectedLeaves,pendingLeaves);
			employeeDTO.setApprovedLeaves(approvedLeaves);
			employeeDTO.setRejectedLeaves(rejectedLeaves);
			employeeDTO.setPendingLeaves(pendingLeaves);
			
			List<ApprovedCompOffs> approvedCompOffs = new ArrayList<>();
			List<RejectedCompOffs> rejectedCompOffs = new ArrayList<>();
			List<PendingCompOffs> pendingCompOffs = new ArrayList<>();
			setCompOffsOfAllStatus(employeeEntity,approvedCompOffs,rejectedCompOffs,pendingCompOffs);
			employeeDTO.setApprovedCompOffs(approvedCompOffs);
			employeeDTO.setRejectedCompOffs(rejectedCompOffs);
			employeeDTO.setPendingCompOffs(pendingCompOffs);
			
			employeeDTO.setManagerStatus(hasRoleManager(employeeEntity));
			
		}
	}

	private void setLeavesOfAllStatus(EmployeeEntity employeeEntity, List<ApprovedLeaves> approvedLeaves,
			List<RejectedLeaves> rejectedLeaves, List<PendingLeaves> pendingLeaves) {
		// TODO Auto-generated method stub
		if(!CollectionUtils.isEmpty(employeeEntity.getLeaves())){
			for(LeaveEntity leaveEntity : employeeEntity.getLeaves()){
				
				if(LeaveStatus.APPROVED.equals(leaveEntity.getLeaveStatus())){
					
					ApprovedLeaves approved = convertToApprovedLeave(leaveEntity);
					approvedLeaves.add(approved);
				}
				else
			    if(LeaveStatus.REJECTED.equals(leaveEntity.getLeaveStatus())){
			    	RejectedLeaves rejected = convertToRejectedLeave(leaveEntity);
			    	rejectedLeaves.add(rejected);	    
			    }
			    else
			    if(LeaveStatus.PENDING.equals(leaveEntity.getLeaveStatus())){
			      PendingLeaves pending = convertToPendingLeave(leaveEntity);
			      pendingLeaves.add(pending);
			    }
			}
		}
		
	}
	
	
	
	private void setCompOffsOfAllStatus(EmployeeEntity employeeEntity, List<ApprovedCompOffs> approvedCompOffs,
			List<RejectedCompOffs> rejectedCompOffs, List<PendingCompOffs> pendingCompOffs) {
		// TODO Auto-generated method stub
		if(!CollectionUtils.isEmpty(employeeEntity.getCompOffs())){
			for(CompOffEntity compOffEntity : employeeEntity.getCompOffs()){
				if(CompOffStatus.APPROVED.equals(compOffEntity.getCompOffStatus())){
					System.out.println("approved");
					ApprovedCompOffs approved = convertToApprovedCompOff(compOffEntity);
					approvedCompOffs.add(approved);
				}
				else
			    if(CompOffStatus.REJECTED.equals(compOffEntity.getCompOffStatus())){
			    	RejectedCompOffs rejected = convertToRejectedCompOff(compOffEntity);
			    	rejectedCompOffs.add(rejected);	    
			    }
			    else
			    if(CompOffStatus.PENDING.equals(compOffEntity.getCompOffStatus())){
			      PendingCompOffs pending = convertToPendingCompOff(compOffEntity);
			      pendingCompOffs.add(pending);
			    }
			}
		}
	}


	private PendingCompOffs convertToPendingCompOff(CompOffEntity compOffEntity) {
		// TODO Auto-generated method stub
		PendingCompOffs pending = new PendingCompOffs();
		if(!StringUtils.isEmpty(compOffEntity)){
			pending.setCompOffId(compOffEntity.getCompOffId());
			pending.setAppliedOn(LeaveUtil.convertDateToStringInParticularFormat(compOffEntity.getAppliedOn()));
			
			pending.setForDate(LeaveUtil.convertDateToStringInParticularFormat(compOffEntity.getForDate()));
			
			
			com.sahil.models.EmployeeDTO eDto = new com.sahil.models.EmployeeDTO();
			EmployeeEntity e = compOffEntity.getCurrentlyManager();
			if(!StringUtils.isEmpty(e)){
				eDto.setEmpCode(e.getEmployeeCode());
				eDto.setEmpName(e.getName());
			}
			pending.setPendingWith(eDto);
			
		}
	    return pending;
	}

	private RejectedCompOffs convertToRejectedCompOff(CompOffEntity compOffEntity) {
		// TODO Auto-generated method stub
		RejectedCompOffs rejected = new RejectedCompOffs();
				if(!StringUtils.isEmpty(compOffEntity)){
					rejected.setCompOffId(compOffEntity.getCompOffId());
					rejected.setAppliedOn(LeaveUtil.convertDateToStringInParticularFormat(compOffEntity.getAppliedOn()));
					
					rejected.setForDate(LeaveUtil.convertDateToStringInParticularFormat(compOffEntity.getForDate()));
					
					rejected.setRejectedOn(LeaveUtil.convertDateToStringInParticularFormat(compOffEntity.getApprovedOn()));
					
					com.sahil.models.EmployeeDTO eDto = new com.sahil.models.EmployeeDTO();
					EmployeeEntity e = compOffEntity.getCurrentlyManager();
					if(!StringUtils.isEmpty(e)){
						eDto.setEmpCode(e.getEmployeeCode());
						eDto.setEmpName(e.getName());
					}
					rejected.setRejectedBy(eDto);
					rejected.setRejectionReason(compOffEntity.getRejectionReason());
					
				}
			    return rejected;
	}

	private ApprovedCompOffs convertToApprovedCompOff(CompOffEntity compOffEntity) {
		// TODO Auto-generated method stub
		ApprovedCompOffs approved = new ApprovedCompOffs();
		if(!StringUtils.isEmpty(compOffEntity)){
			approved.setCompOffId(compOffEntity.getCompOffId());
			approved.setAppliedOn(LeaveUtil.convertDateToStringInParticularFormat(compOffEntity.getAppliedOn()));
			approved.setApprovedOn(LeaveUtil.convertDateToStringInParticularFormat(compOffEntity.getApprovedOn()));
			
			com.sahil.models.EmployeeDTO eDto = new com.sahil.models.EmployeeDTO();
			EmployeeEntity e = compOffEntity.getCurrentlyManager();
			if(!StringUtils.isEmpty(e)){
				eDto.setEmpCode(e.getEmployeeCode());
				eDto.setEmpName(e.getName());
			}
			approved.setApprovedBy(eDto);
			
			approved.setForDate(LeaveUtil.convertDateToStringInParticularFormat(compOffEntity.getForDate()));
	
		}
	    return approved;
	}

	private PendingLeaves convertToPendingLeave(LeaveEntity leaveEntity) {
		// TODO Auto-generated method stub
		PendingLeaves pending = new PendingLeaves();
		if(!StringUtils.isEmpty(leaveEntity)){
			pending.setLeaveId(leaveEntity.getLeaveId());
			pending.setAppliedOn(LeaveUtil.convertDateToStringInParticularFormat(leaveEntity.getAppliedOn()));
			
			com.sahil.models.EmployeeDTO eDto = new com.sahil.models.EmployeeDTO();
			EmployeeEntity e = leaveEntity.getCurrentlyManager();
			if(!StringUtils.isEmpty(e)){
				eDto.setEmpCode(e.getEmployeeCode());
				eDto.setEmpName(e.getName());
			}
			pending.setPendingWith(eDto);
			
			pending.setFromDate(LeaveUtil.convertDateToStringInParticularFormat(leaveEntity.getFromDate()));
			pending.setToDate(LeaveUtil.convertDateToStringInParticularFormat(leaveEntity.getToDate()));
			pending.setLeaveType(leaveEntity.getLeaveType().toString());
	
			int noOfDays = workingDaysCalculatorService.getWorkingDaysBetweenTwoDatesIncludingBothDaysIncludingHolidays(leaveEntity.getFromDate(),leaveEntity.getToDate());
			pending.setNoOfDays(noOfDays);	
			
		}
		return pending;
	}

	private ApprovedLeaves convertToApprovedLeave(LeaveEntity leaveEntity ){
		ApprovedLeaves approved = new ApprovedLeaves();
		if(!StringUtils.isEmpty(leaveEntity)){
			approved.setLeaveId(leaveEntity.getLeaveId());
			approved.setAppliedOn(LeaveUtil.convertDateToStringInParticularFormat(leaveEntity.getAppliedOn()));
			approved.setApprovedOn(LeaveUtil.convertDateToStringInParticularFormat(leaveEntity.getApprovedOn()));
			
			com.sahil.models.EmployeeDTO eDto = new com.sahil.models.EmployeeDTO();
			EmployeeEntity e = leaveEntity.getCurrentlyManager();
			if(!StringUtils.isEmpty(e)){
				eDto.setEmpCode(e.getEmployeeCode());
				eDto.setEmpName(e.getName());
			}
			approved.setApprovedBy(eDto);
			
			approved.setFromDate(LeaveUtil.convertDateToStringInParticularFormat(leaveEntity.getFromDate()));
			approved.setToDate(LeaveUtil.convertDateToStringInParticularFormat(leaveEntity.getToDate()));
			approved.setLeaveType(leaveEntity.getLeaveType().toString());
	
			int noOfDays = workingDaysCalculatorService.getWorkingDaysBetweenTwoDatesIncludingBothDaysIncludingHolidays(leaveEntity.getFromDate(),leaveEntity.getToDate());
		    approved.setNoOfDays(noOfDays);	
		}
	    return approved;
	}
	
	private RejectedLeaves convertToRejectedLeave(LeaveEntity leaveEntity){
		RejectedLeaves rejected = new RejectedLeaves();
		if(!StringUtils.isEmpty(leaveEntity)){
			rejected.setLeaveId(leaveEntity.getLeaveId());
	    	rejected.setAppliedOn(LeaveUtil.convertDateToStringInParticularFormat(leaveEntity.getAppliedOn()));
	        rejected.setRejectedOn(LeaveUtil.convertDateToStringInParticularFormat(leaveEntity.getApprovedOn()));
	        rejected.setFromDate(LeaveUtil.convertDateToStringInParticularFormat(leaveEntity.getFromDate()));
	        rejected.setToDate(LeaveUtil.convertDateToStringInParticularFormat(leaveEntity.getToDate()));
	        
	    	com.sahil.models.EmployeeDTO eDto = new com.sahil.models.EmployeeDTO();
			EmployeeEntity e = leaveEntity.getCurrentlyManager();
			if(!StringUtils.isEmpty(e)){
				eDto.setEmpCode(e.getEmployeeCode());
				eDto.setEmpName(e.getName());
			}
			rejected.setRejectedBy(eDto);
			rejected.setLeaveType(leaveEntity.getLeaveType().toString());
			
			int noOfDays = workingDaysCalculatorService.getWorkingDaysBetweenTwoDatesIncludingBothDaysIncludingHolidays(leaveEntity.getFromDate(),leaveEntity.getToDate());
			rejected.setNoOfDays(noOfDays);
		
		}
		return rejected;
	}
	
	@Override
	public PageResponseDTO<EmployeeForListing> findEmployeesByDepartment(int id,String department,int pageNumber) {
		// TODO Auto-generated method stub
		
		PageResponseDTO<EmployeeForListing> emResponseDTO = new PageResponseDTO<>();
		emResponseDTO.setCode(1);
		emResponseDTO.setMessage("INVALID DEPARTMENT "+department);
		String dept =LeaveUtil.toTitleCase(department);
		if(!dept.isEmpty()){ 
		  System.out.println(dept);
		  DepartmentEntity d = departmentRepository.findOne(id);
		  System.out.println(d);
		  if(d!=null && d.getName().equals(LeaveUtil.toTitleCase(department))){
		
			  PageRequest pageRequest = new PageRequest(pageNumber-1, PAGE_SIZE, Sort.Direction.ASC,"dateofJoining");
		        
			  Page<EmployeeEntity> employeeEntitiesPages =  employeeRepository.findByDepartmentEntity(d, pageRequest);
			  System.out.println("empent"+employeeEntitiesPages.getContent());
			  Page<EmployeeForListing> emps = null;
			  emResponseDTO.setMessage("NO EMPLOYEE FOUND IN DEPARTMENT "+d.getName());
			  if(!CollectionUtils.isEmpty(employeeEntitiesPages.getContent())){
				  emps = employeeEntitiesPages.map(this::convertToEmployeeForListing);
				  emResponseDTO.setCode(0);
				  emResponseDTO.setMessage("SUCCESSFUL");
				  emResponseDTO.setData(emps);
			  }
			  
			  
		  }
		}
		
		return emResponseDTO;
	}
	
	private EmployeeForListing convertToEmployeeForListing(EmployeeEntity employeeEntity){
		EmployeeForListing employeeForListing = new EmployeeForListing();
		if(Objects.nonNull(employeeEntity)){
			employeeForListing.setEmployeeCode(employeeEntity.getEmployeeCode());
			employeeForListing.setEmployeeName(employeeEntity.getName());
			if(!StringUtils.isEmpty(employeeEntity.getManager())){
				employeeForListing.setManagerCode(employeeEntity.getManager().getEmployeeCode());
				employeeForListing.setManagerName(employeeEntity.getManager().getName());
			}
			employeeForListing.setManagerStatus(hasRoleManager(employeeEntity));
			employeeForListing.setDateofBirth(LeaveUtil.convertDateToStringInParticularFormat(employeeEntity.getDateofBirth()));
			employeeForListing.setDateofJoining(LeaveUtil.convertDateToStringInParticularFormat(employeeEntity.getDateofJoining()));
			if(employeeEntity.getDepartmentEntity()!=null)
				employeeForListing.setDepartment(employeeEntity.getDepartmentEntity().getName());
			if(employeeEntity.getDesignationEntity()!=null)
				employeeForListing.setDesignation(employeeEntity.getDesignationEntity().getName());
		}
		return employeeForListing;
	}
	
	
	private EmployeeForRights convertToEmployeeForRights(EmployeeEntity e){
		EmployeeForRights employeeForRights  = new EmployeeForRights();
		if(Objects.nonNull(e)){
			employeeForRights.setEmpCode(e.getEmployeeCode());
			employeeForRights.setName(e.getName());
			if(e.getDesignationEntity()!=null)
				employeeForRights.setDesignation(e.getDesignationEntity().getName());
			if(e.getDepartmentEntity()!=null)
				employeeForRights.setDepartment(e.getDepartmentEntity().getName());
			employeeForRights.setManagerStatus(hasRoleManager(e));
			employeeForRights.setDateOfJoining(LeaveUtil.convertDateToStringInParticularFormat(e.getDateofJoining()));
		}
		return employeeForRights;
	}
	
	
	private boolean hasRoleManager(EmployeeEntity e){
		if(Objects.nonNull(e)){
			
			e.getUserRoles().forEach(em->{ System.out.println(em);});
			return e.getUserRoles().stream().map(r->r.getRoleName()).collect(Collectors.toSet()).contains("ROLE_MANAGER");
		}
		return false;
	}
	
	
	
	
	
	
	
	
	
	@Override
	public UpdateEmployeeDTO getEmployeeForUpdation(String empId){
		UpdateEmployeeDTO dto = new UpdateEmployeeDTO();
		
		if(StringUtils.isEmpty(empId)){
		 dto.setCode(1);
		 dto.setMessage("Invalid empCode"+empId);
		 return dto;
		}
		
		EmployeeEntity employeeEntity = employeeRepository.findOne(empId);
		if(StringUtils.isEmpty(employeeEntity)){
			 dto.setCode(1);
			 dto.setMessage("Invalid EmpCode"+empId);
			 return dto;
		}
		
		dto.setEmployeeCode(employeeEntity.getEmployeeCode());
		dto.setName(employeeEntity.getName());
		if(Objects.nonNull(employeeEntity.getManager())){
			dto.setManagerCode(employeeEntity.getManager().getEmployeeCode());
		}
		String ans = hasRoleManager(employeeEntity)?"ASSIGN":"REMOVE";
		dto.setIsManager(ans);
		dto.setDateofBirth(LeaveUtil.convertDateToStringInParticularFormat(employeeEntity.getDateofBirth()));
		if(Objects.nonNull(employeeEntity.getDepartmentEntity())){
			dto.setDepartment(employeeEntity.getDepartmentEntity().getName());
		}
		if(Objects.nonNull(employeeEntity.getDesignationEntity())){
			dto.setDesignation(employeeEntity.getDesignationEntity().getName());
		}
		if(Objects.nonNull(employeeEntity.getContactEntity())){
		   dto.setEmailId(employeeEntity.getContactEntity().getEmailId());
		   dto.setPhoneNo(employeeEntity.getContactEntity().getPhoneNo());
		}
		dto.setCode(0);
		dto.setMessage("Successful");
		return dto;
	}
	
	@Override
	public Response updateEmployee(UpdateEmployeeDTO employeeDTO){
		Response response = new Response();
		if(Objects.isNull(employeeDTO) || StringUtils.isEmpty(employeeDTO.getEmployeeCode())){
			response.setCode(1);
			response.setMessage("No such employee");
			return response;
		}
		
		EmployeeEntity employeeEntity = employeeRepository.findOne(employeeDTO.getEmployeeCode());
		if(Objects.isNull(employeeEntity)){
			response.setCode(1);
			response.setMessage("No such employee found");
			return response;
		}
		
		
		Response response2 = isValid(employeeDTO);
		
		
		if(response2.getCode()==0){
			employeeEntity.setEmployeeCode(employeeDTO.getEmployeeCode());
			employeeEntity.setName(employeeDTO.getName());
			employeeEntity.setDateofBirth(LeaveUtil.convertStringToDateInParticularFormat(employeeDTO.getDateofBirth()));
			DepartmentEntity departmentEntity = departmentRepository.findByName(LeaveUtil.toTitleCase(employeeDTO.getDepartment()));
			employeeEntity.setDepartmentEntity(departmentEntity);
			DesignationEntity designationEntity = designationRepository.findByName(LeaveUtil.toTitleCase(employeeDTO.getDesignation()));
			employeeEntity.setDesignationEntity(designationEntity);
			
			ContactEntity contactEntity = employeeEntity.getContactEntity();
			if(Objects.isNull(contactEntity)){
				contactEntity = new ContactEntity();
			}
			contactEntity.setEmployeeEntity(employeeEntity);
			contactEntity.setEmailId(employeeDTO.getEmailId());
			contactEntity.setPhoneNo(employeeDTO.getPhoneNo());
			
			employeeEntity.setContactEntity(contactEntity);
			if(!StringUtils.isEmpty(employeeDTO.getManagerCode())){
				employeeEntity.setManager(employeeRepository.findOne(employeeDTO.getManagerCode()));
			}
			
			
			RoleEntity roleEntity = roleRepository.findByRoleName("ROLE_MANAGER");
			if(roleEntity==null){
				roleEntity = new RoleEntity();
				roleEntity.setRoleName("ROLE_MANAGER");
				roleRepository.save(roleEntity);
			}
			
			employeeEntity.getUserRoles().forEach(e->{System.out.println(e.getRoleId());
			 System.out.println(e.getRoleName());
			 System.out.println(e.getClass()); 
			});
			
			employeeEntity.getUserRoles().add(roleEntity);
			System.out.println("*******");
			if("ASSIGN".equals(employeeDTO.getIsManager())){
				if(!hasRoleManager(employeeEntity)){
					employeeEntity.getUserRoles().add(roleEntity);
				}
			}
			else
			if("REMOVE".equals(employeeDTO.getIsManager())){
				System.out.println("here");
				if(hasRoleManager(employeeEntity)){
					System.out.println("here");
					employeeEntity.getUserRoles().remove(roleEntity);
				}
			}
			
			
			employeeRepository.save(employeeEntity);
			
			employeeEntity.getUserRoles().forEach(e->{System.out.println(e.getRoleId());
			 System.out.println(e.getRoleName());
			 System.out.println(e.getClass()); 
			});
			
			response.setCode(0);
			response.setMessage("Updated");
		}
		else{
			response.setCode(1);
			response.setMessage(response2.getMessage());
		}
		return response;
	}
	
	
	private Response isValidAdmin(UpdateAdminDTO adminDTO){
		Response response = new Response();
		response.setCode(1);
		if(Objects.isNull(adminDTO) || adminDTO.getAdminId()<=0 || StringUtils.isEmpty(adminDTO.getName()) || StringUtils.isEmpty(adminDTO.getEmailId())){
			response.setMessage("Invalid details");
			return response;
		}
		if(!validateEmail(adminDTO.getEmailId())){
			System.out.println("invalid emailId"+adminDTO.getEmailId());
			response.setMessage("invalid emailId"+adminDTO.getEmailId());
			return response;
		}
		response.setCode(0);
		response.setMessage("Valid details");
		return response;
	}
	
	private Response isValid(UpdateEmployeeDTO employeeDTO){
		System.out.println(employeeDTO);
		Response response = new Response();
		response.setCode(1);
		if(Objects.isNull(employeeDTO) || StringUtils.isEmpty(employeeDTO.getEmployeeCode()) || StringUtils.isEmpty(employeeDTO.getName())  || StringUtils.isEmpty(employeeDTO.getDateofBirth()) || StringUtils.isEmpty(employeeDTO.getDepartment()) || StringUtils.isEmpty(employeeDTO.getDesignation())|| StringUtils.isEmpty(employeeDTO.getEmailId()) || StringUtils.isEmpty(employeeDTO.getPhoneNo()) || employeeDTO.getManagerCode()==null){
			response.setMessage("Invalid details");
			return response;
		}
		
		
		DepartmentEntity departmentEntity = departmentRepository.findByName(LeaveUtil.toTitleCase(employeeDTO.getDepartment()));
		if(Objects.isNull(departmentEntity)){
			System.out.println("No such department");
			response.setMessage("No such department");
			return response;
		}
		
		DesignationEntity designationEntity = designationRepository.findByName(LeaveUtil.toTitleCase(employeeDTO.getDesignation()));
		if(Objects.isNull(designationEntity)){
			System.out.println("No such designation");
			response.setMessage("No such designation");
			return response;
		}
		
		EmployeeEntity employeeEntity = employeeRepository.findOne(employeeDTO.getEmployeeCode());
		if(Objects.isNull(employeeEntity)){
			System.out.println("No such employee");
			response.setMessage("No such employee");
			return response;
		}
		
		if(!StringUtils.isEmpty(employeeDTO.getManagerCode())){
			EmployeeEntity managerEntity = employeeRepository.findOne(employeeDTO.getManagerCode());
			
			if(Objects.isNull(managerEntity)){
			System.out.println("No such manager with empCode"+employeeDTO.getManagerCode());
			response.setMessage("No such manager with empCode"+employeeDTO.getManagerCode());
			return response;
			}
		
		
		if(!hasRoleManager(managerEntity)){
			System.out.println("Employee with empCode"+employeeDTO.getManagerCode()+"is not a manager");
			response.setMessage("Employee with empCode"+employeeDTO.getManagerCode()+"is not a manager");
			return response;
		}

		/*
		if(!managerEntity.getDepartmentEntity().getName().equals(LeaveUtil.toTitleCase(employeeDTO.getDepartment()))){
			System.out.println("Employee with empCode"+employeeDTO.getEmployeeCode()+" and \n manager with empCode "+employeeDTO.getManagerCode()+" don't belong to same department");
			response.setMessage("Employee with empCode"+employeeDTO.getEmployeeCode()+" and \n manager with empCode "+employeeDTO.getManagerCode()+" don't belong to same department");
			return response;
		}*/
		
		}
		if(!validateEmail(employeeDTO.getEmailId())){
			System.out.println("Invalid EmailId"+employeeDTO.getEmailId());
			response.setMessage("Invalid EmailId"+employeeDTO.getEmailId());
			return response;
		}
		response.setCode(0);
		response.setMessage("Valid");
		return response;
	}
	

	private boolean validateEmail(String email){
		final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	@Override
	public Response deleteEmployee1(String id) {
		Response response = new Response();
		response.setCode(1);
		response.setMessage("Not deleted");
		
		if(StringUtils.isEmpty(id)){
			response.setCode(1);
			response.setMessage("Please enter a valid id");
			return response;
		}
		EmployeeEntity employeeEntity = employeeRepository.findOne(id);
		
		if(Objects.nonNull(employeeEntity)){
			employeeRepository.delete(employeeEntity);
			response.setCode(0);
			response.setMessage("Successfully deleted");
		}
		return response;
	}
	
	@Override
	public Response deleteEmployee(String id) {
		Response response = new Response();
		
		if(StringUtils.isEmpty(id)){
			response.setCode(1);
			response.setMessage("Please enter a valid id");
			return response;
		}
		
		
		EmployeeEntity employeeEntity = employeeRepository.findOne(id);
		if(Objects.nonNull(employeeEntity)){
			response.setCode(0);
			response.setMessage("OK");
			
			// making foreign key in subemployees as null
			List<EmployeeEntity> subEmployees = null;
			subEmployees = employeeRepository.findByManager(employeeEntity);
			
			EmployeeEntity managerEntity = employeeEntity.getManager();
			
			if(!CollectionUtils.isEmpty(subEmployees)){
				System.out.println("making managers null");
				for(EmployeeEntity e : subEmployees){
						e.setManager(managerEntity);
						employeeRepository.save(e);
						System.out.println("manager done");
				}
			}
			
			if(Objects.nonNull(employeeEntity.getManager())){
				employeeEntity.setManager(null);
				
			}
			
			
			// handling foreign keys in leaves
			
			for(LeaveEntity l:leaveRepository.findByManagerEntity(employeeEntity)){
				l.setManagerEntity(null);
				leaveRepository.save(l);
			}
			
			for(LeaveEntity l:leaveRepository.findByCurrentlyManager(employeeEntity)){
				l.setCurrentlyManager(null);
				leaveRepository.save(l);
			}
			
			
			// handling foreign keys in compoffs
			for(CompOffEntity c:compOffRepository.findByManagerEntity(employeeEntity)){
				c.setManagerEntity(null);
				compOffRepository.save(c);
			}
			
			for(CompOffEntity c:compOffRepository.findByCurrentlyManager(employeeEntity)){
				c.setCurrentlyManager(null);
				compOffRepository.save(c);
			}
			
			
			ContactEntity contactEntity = employeeEntity.getContactEntity();
			System.out.println("contact details");
			
			if(Objects.nonNull(contactEntity)){
				System.out.println(contactEntity.getId());
				System.out.println("c done");
				employeeEntity.setContactEntity(null);
				//ContactEntity contactEntity1 = contactRepository.findByEmployeeEntity(employeeEntity);
				System.out.println(contactEntity.getId());
			}
			
			
			
			LeaveCountEntity leaveCountEntity = leaveCountRepository.findByEmployeeEntity(employeeEntity);
			System.out.println("leave count details");
			if(Objects.nonNull(leaveCountEntity)){
				System.out.println(leaveCountEntity.getLeaveCountId());
				employeeEntity.setLeaveCountEntity(null);
				//leaveCountRepository.delete(leaveCountEntity);
				System.out.println("lc done");
				//LeaveCountEntity leaveCountEntity1 = leaveCountRepository.findByEmployeeEntity(employeeEntity);
				System.out.println(leaveCountEntity.getLeaveCountId());
			}
			
			
			Set<LeaveEntity> leaveEntities =  employeeEntity.getLeaves();
			System.out.println("leave entities");
			
			if(!CollectionUtils.isEmpty(leaveEntities)){
				    leaveEntities.forEach(le->{System.out.println(le);});
			    	employeeEntity.getLeaves().clear();
				    System.out.println("le done");
			    	leaveEntities.forEach(le->{System.out.println(le);});
			}
			
			Set<CompOffEntity> compOffEntities = employeeEntity.getCompOffs();
			System.out.println("comp off entities");
			
			if(!CollectionUtils.isEmpty(compOffEntities)){
				compOffEntities.forEach(le->{System.out.println(le);});
			  	employeeEntity.getCompOffs().clear();
				//employeeEntity.setCompOffs(null);
			  	System.out.println("ce done");
			  	compOffEntities.forEach(le->{System.out.println(le);});
			}
			
			VerificationToken verificationToken = verificationTokenRepository.findByUser(employeeEntity);
			System.out.println(verificationToken);
			if(Objects.nonNull(verificationToken)){
				System.out.println(verificationToken.getId());
				verificationTokenRepository.delete(verificationToken);
				System.out.println("vt done");
				//VerificationToken verificationToken1 = verificationTokenRepository.findByUser(employeeEntity);
				//System.out.println(verificationToken.getId());
			}
			
			EmployeePasswordResetToken employeePasswordResetToken = employeePasswordResetTokenRepository.findByUser(employeeEntity);
			System.out.println(employeePasswordResetToken);
			if(Objects.nonNull(employeePasswordResetToken)){
				System.out.println(employeePasswordResetToken.getId());
				employeePasswordResetTokenRepository.delete(employeePasswordResetToken);
				System.out.println("ept done");
				EmployeePasswordResetToken employeePasswordResetToken1 = employeePasswordResetTokenRepository.findByUser(employeeEntity);
				System.out.println(employeePasswordResetToken1.getId());
			}
			

			System.out.println(employeeEntity.getUserRoles());
			employeeEntity.getUserRoles().clear();
			System.out.println("roles after deletion"+employeeEntity.getUserRoles());
			System.out.println("ur done");
			employeeRepository.save(employeeEntity);
			//employeeRepository.delete(employeeEntity);
			//EmployeeEntity employeeEntity1 = employeeRepository.findOne(id);
			//System.out.println(employeeEntity1);
			System.out.println("e done");
		}
		else{
			response.setCode(1);
			response.setMessage("Employee not found");
		}
		return response;
	}
	
	
	@Override
	public Response deleteAdmin(String emailId,String password) {
		Response response = new Response();
		response.setCode(1);
		response.setMessage("NOT DELETED");
		if(StringUtils.isEmpty(emailId) || StringUtils.isEmpty(password)){
			response.setMessage("Please enter the inputs");
			return response;
		}
		
		AdminEntity adminEntity = adminRepository.findByEmailId(emailId);
		if(Objects.isNull(adminEntity)){
			response.setMessage("Invalid emailId");
			return response;
		}
		
		if(!passwordEncoder.matches(password,adminEntity.getPassword())){
			response.setMessage("Invalid password");
			return response;
		}
		
		VerificationTokenAdmin verificationTokenAdmin = adminVerificationTokenRepository.findByUser(adminEntity);
		if(Objects.nonNull(verificationTokenAdmin)){
			adminVerificationTokenRepository.delete(verificationTokenAdmin);
		}
		
		AdminPasswordResetToken adminPasswordResetToken = adminPasswordResetTokenRepository.findByUser(adminEntity);
		if(Objects.nonNull(adminPasswordResetToken)){
			adminPasswordResetTokenRepository.delete(adminPasswordResetToken);
		}
		
		
		adminEntity.getAdminRoles().clear();
		// deletion from admin_role to be done
		adminRepository.delete(adminEntity);
		response.setCode(0);
		response.setMessage("Deleted successfully");
		
		return response;
	}
	
	
	
	
	@Override
	public UpdateAdminDTO getAdminForUpdation(String email){
		UpdateAdminDTO dto = new UpdateAdminDTO();
		
		if(StringUtils.isEmpty(email)){
		 dto.setCode(1);
		 dto.setMessage("Invalid adminEmail"+email);
		 return dto;
		}
		
		AdminEntity adminEntity = adminRepository.findByEmailId(email);
		if(StringUtils.isEmpty(adminEntity)){
			 dto.setCode(1);
			 dto.setMessage("Invalid adminEmail"+email);
			 return dto;
		}
		
		dto.setAdminId(adminEntity.getAdminId());
		dto.setName(adminEntity.getName());
		dto.setEmailId(adminEntity.getEmailId());
		
		dto.setCode(0);
		dto.setMessage("Successful");
		return dto;
	}
	
	@Override
	public Response updateAdmin(UpdateAdminDTO adminDTO){
		Response response = new Response();
		if(Objects.isNull(adminDTO) || StringUtils.isEmpty(adminDTO.getAdminId())){
			response.setCode(1);
			response.setMessage("No such admin");
			return response;
		}
		
		AdminEntity adminEntity = adminRepository.findOne(adminDTO.getAdminId());
		//EmployeeEntity employeeEntity = employeeRepository.findOne(employeeDTO.getEmployeeCode());
		if(Objects.isNull(adminEntity)){
			response.setCode(1);
			response.setMessage("No such admin found");
			return response;
		}
		
		Response response2 = isValidAdmin(adminDTO);
		
		if(response2.getCode()==0){
			
			adminEntity.setAdminId(adminDTO.getAdminId());
			adminEntity.setName(adminDTO.getName());
			adminEntity.setEmailId(adminDTO.getEmailId());
			
			adminRepository.save(adminEntity);
			response.setCode(0);
			response.setMessage("Updated");
		}
		else{
			response.setCode(1);
			response.setMessage(response2.getMessage());
		}
		return response;
	}
	
		
	@Override
	public Response updatePassword(ResetPasswordDTO passwordDTO,String adminEmail){
		Response response = new Response();
		response.setCode(1);
		if(StringUtils.isEmpty(adminEmail)){
			response.setMessage("Please logIn");
			return response;
		}
		AdminEntity adminEntity = adminRepository.findByEmailId(adminEmail);
		if(StringUtils.isEmpty(adminEntity)){
			response.setMessage("Please logIn");
			return response;
		}
		
		if(StringUtils.isEmpty(passwordDTO.getOldPassword()) || 
		   StringUtils.isEmpty(passwordDTO.getNewPassword()) ||
		   StringUtils.isEmpty(passwordDTO.getConfirmPassword())){
			response.setMessage("Please enter valid inputs");
			return response;
		}
		
		
		if(!passwordEncoder.matches(passwordDTO.getOldPassword(),adminEntity.getPassword())){
			response.setMessage("Invalid Old Password entered");
			return response;
		}
		
		if(!passwordDTO.getNewPassword().equals(passwordDTO.getConfirmPassword())){
			response.setMessage("New Password and Confirm Password didn't match");
			return response;
		}
		
		adminEntity.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
		adminRepository.save(adminEntity);
		response.setCode(0);
		response.setMessage("Updated Password");
		return response;
	}
	
	@Override
	public void forwardLeaves(){
		Date today = LeaveUtil.getTodaysDate();
		Date twoDaysEarlier = LeaveUtil.addDays(today,-2); // endDate
		Date threeDaysEarlier = LeaveUtil.addDays(today,-3); // startDate
		System.out.println(today);
		System.out.println(twoDaysEarlier);
		System.out.println(threeDaysEarlier);
		
		List<LeaveEntity> leaves = leaveRepository.findLeavesForForwarding(threeDaysEarlier,twoDaysEarlier);
		if(!CollectionUtils.isEmpty(leaves)){
			for(LeaveEntity leaveEntity : leaves){
				
				if(leaveEntity.getCurrentlyManager()!=null){
					
					EmployeeEntity upperManager = leaveEntity.getCurrentlyManager().getManager();
					if(Objects.nonNull(upperManager)){
						leaveEntity.setCurrentlyManager(upperManager);
						leaveRepository.save(leaveEntity);
						// send mail 
					}
				}
			}
			
		}
		
	}
	
	
	
	

}
