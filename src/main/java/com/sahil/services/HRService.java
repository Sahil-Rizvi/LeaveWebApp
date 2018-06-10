package com.sahil.services;

import com.sahil.models.PageResponseDTO;
import com.sahil.models.Response;
import com.sahil.models.UpdateResponse;
import com.sahil.models.admin.UpdateAdminDTO;
import com.sahil.models.hr.EmployeeDTO;
import com.sahil.models.hr.EmployeeForListing;
import com.sahil.models.hr.ListOfEmployeeForRights;
import com.sahil.models.hr.UpdateEmployeeRightsList;
import com.sahil.models.hr.emp.UpdateEmployeeDTO;
import com.sahil.models.input.ResetPasswordDTO;

public interface HRService {

	public ListOfEmployeeForRights findEmployeesToUpdateManagerStatus();
	public UpdateResponse updateEmployeeManagerStatus(UpdateEmployeeRightsList employeeList);
	public EmployeeDTO findEmployeeByEmployeeCode(String empCode);
	public PageResponseDTO<EmployeeForListing> findEmployeesByDepartment(int id,String department,int pageNumber);
	public UpdateEmployeeDTO getEmployeeForUpdation(String empId);
	public Response updateEmployee(UpdateEmployeeDTO employeeDTO);
	public Response deleteEmployee(String id);
	public Response updateAdmin(UpdateAdminDTO adminDTO);
	public UpdateAdminDTO getAdminForUpdation(String email);
	public Response deleteAdmin(String emailId, String password);
	public Response updatePassword(ResetPasswordDTO passwordDTO, String adminEmail);
	public void forwardLeaves();
	public Response deleteEmployee1(String id);		
}
