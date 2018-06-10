package com.sahil.services.implementations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sahil.entities.ContactEntity;
import com.sahil.entities.DepartmentEntity;
import com.sahil.entities.DesignationEntity;
import com.sahil.entities.EmployeeEntity;
import com.sahil.entities.EmployeePasswordResetToken;
import com.sahil.entities.LeaveCountEntity;
import com.sahil.entities.RoleEntity;
import com.sahil.entities.VerificationToken;
import com.sahil.models.Response;
import com.sahil.models.UpdateEmployeeDTO;
import com.sahil.models.input.Employee;
import com.sahil.models.input.PasswordDto;
import com.sahil.models.input.ResetPasswordDTO;
import com.sahil.repositories.ContactRepository;
import com.sahil.repositories.DepartmentRepository;
import com.sahil.repositories.DesignationRepository;
import com.sahil.repositories.EmployeePasswordResetTokenRepository;
import com.sahil.repositories.EmployeeRepository;
import com.sahil.repositories.RoleRepository;
import com.sahil.repositories.VerificationTokenRepository;
import com.sahil.services.EmployeeService;
import com.sahil.services.LeaveCountService;
import com.sahil.utils.LeaveUtil;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private DesignationRepository designationRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private LeaveCountService leaveCountService;

	@Autowired
	private VerificationTokenRepository tokenRepository;
	
	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private EmployeePasswordResetTokenRepository employeePasswordResetTokenRepository;
	
	public Response addEmployee(Employee employee) {
		
		Response response = validate(employee);
		if(response.getCode()==1){
			return response;
		}
		EmployeeEntity employeeEntity = new EmployeeEntity();
		employeeEntity.setEmployeeCode(employee.getEmployeeId());
		employeeEntity.setName(employee.getEmployeeName());
		
		DepartmentEntity departmentEntity = departmentRepository.findByName(LeaveUtil.toTitleCase(employee.getDepartment()));
		
		DesignationEntity designationEntity = designationRepository.findByName(LeaveUtil.toTitleCase(employee.getDesignation()));
		
		employeeEntity.setDepartmentEntity(departmentEntity);
		employeeEntity.setDesignationEntity(designationEntity);
		employeeEntity.setDateofBirth(employee.getDateOfBirth());
        employeeEntity.setDateofJoining(LeaveUtil.getTodaysDate());
        employeeEntity.setPassword(passwordEncoder.encode(employee.getPassword()));
        employeeEntity.setManager(employeeRepository.findOne(employee.getManagerId()));
        employeeEntity.setEnabled(false);
        ContactEntity contactEntity = new ContactEntity(employeeEntity,employee.getEmail(),employee.getPhoneNo());
        employeeEntity.setContactEntity(contactEntity);
        
        LeaveCountEntity leaveCountEntity = leaveCountService.getInitialLeaveCount(employeeEntity,employee);
        employeeEntity.setLeaveCountEntity(leaveCountEntity);
        
        RoleEntity roleEntity = roleRepository.findByRoleName("ROLE_EMPLOYEE");
        
        if(roleEntity==null){
        	roleEntity = new RoleEntity();
            roleEntity.setRoleName("ROLE_EMPLOYEE");
            roleRepository.save(roleEntity);
        }
        
        employeeEntity.getUserRoles().add(roleEntity);
        employeeRepository.save(employeeEntity);
        logger.info("Employee {} added ",employeeEntity);
        response.setCode(0);
        response.setMessage("Successfully Registered. You will receive a verification link on your email Id.");
        return response;
	}

	
	public Response validate(Employee employee){		
		// password verification pending
		Response response = new Response();
		response.setCode(0);
		response.setMessage("OKAY");
		if(StringUtils.isEmpty(employee) ||
		   StringUtils.isEmpty(employee.getEmployeeId()) ||
		   StringUtils.isEmpty(employee.getDepartment()) ||
		   StringUtils.isEmpty(employee.getEmployeeName()) ||
		   StringUtils.isEmpty(employee.getDesignation()) ||
		   StringUtils.isEmpty(employee.getEmail()) ||
		   StringUtils.isEmpty(employee.getPhoneNo()) ||
		   StringUtils.isEmpty(employee.getDateOfBirth())||
		   StringUtils.isEmpty(employee.getPassword())) {
		   response.setCode(1);
		   response.setMessage("SOME INPUT IS MISSING");
		   return response;
		}
		
		if(employeeRepository.findOne(employee.getEmployeeId())!=null){
			response.setCode(1);
			response.setMessage("EMPLOYEE WITH EMPID : "+employee.getEmployeeId()+ " ALREADY EXISTS ");
			return response;
		}
		
		if(Objects.nonNull(contactRepository.findByEmailId(employee.getEmail()))){
			response.setCode(1);
			response.setMessage("EMAIL ID ALREADY REGISTERED. <br>PLEASE TRY A NEW ONE.");
			return response;
		}
		
		if(Objects.nonNull(contactRepository.findByPhoneNo(employee.getPhoneNo()))){
			response.setCode(1);
			response.setMessage("PHONE NO. ALREADY REGISTERED. <br>PLEASE TRY A NEW ONE.");
			return response;
		}
		
		
		if(!validateAge(employee.getDateOfBirth())){
			response.setCode(1);
			response.setMessage("AGE LESS THAN 18");
			return response;
		}
		
		
		if(departmentRepository.findByName(LeaveUtil.toTitleCase(employee.getDepartment()))==null){
			response.setCode(1);
			response.setMessage(employee.getDepartment()+" IS AN INVALID DEPARTMENT");
			return response;
		}
		
		if(designationRepository.findByName(LeaveUtil.toTitleCase(employee.getDesignation()))==null){
			response.setCode(1);
			response.setMessage(employee.getDesignation()+" IS AN INVALID DESIGNATION");
			return response;
		}
		
		
		if(!StringUtils.isEmpty(employee.getManagerId()) && StringUtils.isEmpty(employeeRepository.findOne(employee.getManagerId()))){
			response.setCode(1);
			response.setMessage("MANAGER DOES NOT EXISTS WITH MANAGER ID : "+employee.getManagerId());
			return response;
		}
		if(!StringUtils.isEmpty(employee.getManagerId()) && employeeRepository.findOne(employee.getManagerId())!=null){
			if(!hasRoleManager(employeeRepository.findOne(employee.getManagerId()))){
				response.setCode(1);
				response.setMessage(employee.getManagerId()+" IS NOT ASSIGNED MANAGER ROLE");
				return response;
			}
		}
		
		return response;
	}
	
	
	private boolean hasRoleManager(EmployeeEntity e){
		if(Objects.nonNull(e)){
			return e.getUserRoles().stream().map(r->r.getRoleName()).collect(Collectors.toSet()).contains("ROLE_MANAGER");
		}
		return false;
	}
	
	private boolean validateAge(Date dateOfBirth) {
		Date todaysDate = LeaveUtil.getTodaysDate();
		long difference = todaysDate.getTime() - dateOfBirth.getTime();
		long age = (difference/(24 * 60 * 60 * 1000))/365;
		return age>=18 ? true : false;
	}


	
	
    

	public Employee findEmployee(String id) {
		
		EmployeeEntity employeeEntity = employeeRepository.findOne(id);
		Employee employee = null;
		if(employeeEntity!=null){
			logger.info("Employee with id {} found ",id);
		    employee = new Employee();
			employee.setEmployeeId(employeeEntity.getEmployeeCode());
			employee.setEmployeeName(employeeEntity.getName());
			employee.setDateOfBirth(employeeEntity.getDateofBirth());
			if(employeeEntity.getDesignationEntity()!=null)
				employee.setDesignation(employeeEntity.getDesignationEntity().getName());
			if(employeeEntity.getDepartmentEntity()!=null)
				employee.setDepartment(employeeEntity.getDepartmentEntity().getName());
			if(employeeEntity.getManager()!=null){
				employee.setManagerId(employeeEntity.getManager().getEmployeeCode());	
			}	
			ContactEntity contactEntity = employeeEntity.getContactEntity();
			if(contactEntity!=null){
				employee.setEmail(contactEntity.getEmailId());
				employee.setPhoneNo(contactEntity.getPhoneNo());
			}
		}
		else{
			logger.error("Employee with id {} not found ",id);
		}
		return employee;
	}
	
	
	
	@Override
	public UpdateEmployeeDTO findEmployeeForUpdation(String id) {
		System.out.println(id);
		EmployeeEntity employeeEntity = employeeRepository.findOne(id);
		UpdateEmployeeDTO employee = null;
		if(employeeEntity!=null){
			logger.info("Employee with id {} found ",id);
		    employee = new UpdateEmployeeDTO();
			employee.setEmployeeName(employeeEntity.getName());
			ContactEntity contactEntity = employeeEntity.getContactEntity();
			if(contactEntity!=null){
				employee.setPhoneNo(contactEntity.getPhoneNo());
			}
		}
		else{
			logger.error("Employee with id {} not found ",id);
		}
		System.out.println(employee);
		return employee;
	}
	


	public List<Employee> findAllEmployees() {
		List<Employee> employees = new ArrayList<>();
		logger.info("Listing all employees");
		for(EmployeeEntity employeeEntity:employeeRepository.findAll()){
			Employee employee = new Employee();
			employee.setEmployeeId(employeeEntity.getEmployeeCode());
			employee.setEmployeeName(employeeEntity.getName());
			employee.setDateOfBirth(employeeEntity.getDateofBirth());
			
			
			if(employeeEntity.getDesignationEntity()!=null)
				employee.setDesignation(employeeEntity.getDesignationEntity().getName());
			if(employeeEntity.getDepartmentEntity()!=null)
				employee.setDepartment(employeeEntity.getDepartmentEntity().getName());
			if(employeeEntity.getManager()!=null){
				employee.setManagerId(employeeEntity.getManager().getEmployeeCode());				
			}
			ContactEntity contactEntity = employeeEntity.getContactEntity();
			if(contactEntity!=null){
				employee.setEmail(contactEntity.getEmailId());
				employee.setPhoneNo(contactEntity.getPhoneNo());
			}
			employees.add(employee);
		}
		return employees;
	}
	
	public boolean checkEmployee(String id,String pass){	
		 if(!StringUtils.isEmpty(id) && !StringUtils.isEmpty(pass)){
			 EmployeeEntity employeeEntity = employeeRepository.findOne(id);
			 if(employeeEntity!=null && employeeEntity.getPassword().equals(pass)){
				 return true;
			 }
		 }
		 return false;
	}
	
	public String getManagerString(String id){
		if(!StringUtils.isEmpty(id) && employeeRepository.findOne(id)!=null){
			if(hasRoleManager(employeeRepository.findOne(id))){
				return "manager_status";
			}
		}
		return "";
	}
	
	
	@Override
	public Response updatePassword(ResetPasswordDTO passwordDTO,String empId){
		Response response = new Response();
		response.setCode(1);
		if(StringUtils.isEmpty(empId)){
			response.setMessage("Please logIn");
			return response;
		}
		EmployeeEntity employeeEntity = employeeRepository.findOne(empId);
		if(StringUtils.isEmpty(employeeEntity)){
			response.setMessage("Please logIn");
			return response;
		}
		
		if(StringUtils.isEmpty(passwordDTO.getOldPassword()) || 
		   StringUtils.isEmpty(passwordDTO.getNewPassword()) ||
		   StringUtils.isEmpty(passwordDTO.getConfirmPassword())){
			response.setMessage("Please enter valid inputs");
			return response;
		}
		
		
		if(!passwordEncoder.matches(passwordDTO.getOldPassword(),employeeEntity.getPassword())){
			response.setMessage("Invalid Old Password entered");
			return response;
		}
		
		if(!passwordDTO.getNewPassword().equals(passwordDTO.getConfirmPassword())){
			response.setMessage("New Password and Confirm Password didn't match");
			return response;
		}
		
		employeeEntity.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
		employeeRepository.save(employeeEntity);
		response.setCode(0);
		response.setMessage("Updated Password");
		return response;
	}
	
	
	@Override
	public Response updateEmployee(UpdateEmployeeDTO employee,String empId){
		
		EmployeeEntity employeeEntity = employeeRepository.findOne(empId);
		
		
		Response response = validateUpdateEmployee(employee);
		if(response.getCode()==1){
			return response;
		}
		
		response.setMessage("UPDATED");
		
		
		employeeEntity.setName(employee.getEmployeeName());
		
		
		
        ContactEntity contactEntity = employeeEntity.getContactEntity();
        
        contactEntity.setEmployeeEntity(employeeEntity);
        contactEntity.setPhoneNo(employee.getPhoneNo());
        
        employeeEntity.setContactEntity(contactEntity);
        
        
        employeeRepository.save(employeeEntity);
        logger.info("Employee {} updated ",employeeEntity);
        return response;
		
	}
	


	private Response validateUpdateEmployee(UpdateEmployeeDTO employee) {
		Response response = new Response();
		response.setCode(0);
		response.setMessage("OKAY");
		if(StringUtils.isEmpty(employee) ||
		   StringUtils.isEmpty(employee.getEmployeeName()) ||
		   StringUtils.isEmpty(employee.getPhoneNo())){
		   response.setCode(1);
		   response.setMessage("SOME INPUT IS MISSING");
		   return response;
		}
		
		
		return response;
	}
	
    @Override
    public void createVerificationTokenForUser(final EmployeeEntity user, final String token) {
        final VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }
    
    
    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }
    
    
    @Override
    public void saveRegisteredUser(EmployeeEntity user) {
        employeeRepository.save(user);
    }

    @Override
    public void createPasswordResetTokenForUser(final EmployeeEntity user, final String token) {
    	final EmployeePasswordResetToken myToken = new EmployeePasswordResetToken(token, user);
    	employeePasswordResetTokenRepository.save(myToken);
    }


	@Override
	public EmployeeEntity findUserByEmail(String email) {
		ContactEntity contactEntity = null;
		contactEntity = contactRepository.findByEmailId(email);
		if(Objects.nonNull(contactEntity)){
			return contactEntity.getEmployeeEntity();
		}
		return null;
	}
	
	
	 @Override
	    public Response changeUserPassword(final EmployeeEntity user, final PasswordDto passwordDto){
	        Response response = new Response();
	        response.setCode(1);
	        
	        if(StringUtils.isEmpty(passwordDto) || StringUtils.isEmpty(passwordDto.getOldPassword()) || StringUtils.isEmpty(passwordDto.getNewPassword())){
	        	response.setMessage("NOT UPDATED");
	        	return response;
	        }
	        
	        
	        
	        user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
	        employeeRepository.save(user);
	        response.setCode(0);
	        response.setMessage("UPDATED");
        	return response;
	    }

	 
	 @Override
	    public boolean checkIfValidOldPassword(final EmployeeEntity user, final String oldPassword) {
	        return passwordEncoder.matches(oldPassword, user.getPassword());
	    }

	 
    // API

    @Override
    public String validatePasswordResetToken(String id, String token) {
        final EmployeePasswordResetToken passToken = employeePasswordResetTokenRepository.findByToken(token);
        if ((passToken == null) || (!passToken.getUser().getEmployeeCode().equals(id))) {
            System.out.println("invalidToken");
        	return "Invalid Token";
        }

        
        System.out.println(passToken.getExpiryDate().getTime());
        
        final Calendar cal = Calendar.getInstance();
        System.out.println(cal.getTime().getTime());
        
        System.out.println("diff"+(passToken.getExpiryDate().getTime() - cal.getTime().getTime()));
        if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return "Token has expired";
        }

        final EmployeeEntity user = passToken.getUser();
        final Authentication auth = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return null;
    }
    
    
}
