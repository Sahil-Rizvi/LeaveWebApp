package com.sahil.services;

import java.util.List;

import com.sahil.models.input.Employee;
import com.sahil.models.input.PasswordDto;
import com.sahil.models.input.ResetPasswordDTO;
import com.sahil.entities.EmployeeEntity;
import com.sahil.entities.VerificationToken;
import com.sahil.models.Response;
import com.sahil.models.UpdateEmployeeDTO;


public interface EmployeeService {

	public Response addEmployee(Employee employee);
	public Employee findEmployee(String id);
	public EmployeeEntity findUserByEmail(String email);
	public List<Employee> findAllEmployees();
	public boolean checkEmployee(String id,String pass);
	public String getManagerString(String id);
	public UpdateEmployeeDTO findEmployeeForUpdation(String id);
	public Response updateEmployee(UpdateEmployeeDTO employee, String empId);
	public void createVerificationTokenForUser(EmployeeEntity user, String token);
	public VerificationToken getVerificationToken(String VerificationToken);
	public void saveRegisteredUser(EmployeeEntity user);
	public void createPasswordResetTokenForUser(EmployeeEntity user, String token);
	public String validatePasswordResetToken(String id, String token);
	public Response changeUserPassword(final EmployeeEntity user, final PasswordDto passwordDto);
	public boolean checkIfValidOldPassword(EmployeeEntity user, String oldPassword);
	public Response updatePassword(ResetPasswordDTO passwordDTO, String empId);
	  
}

