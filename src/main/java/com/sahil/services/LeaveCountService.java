package com.sahil.services;

import com.sahil.entities.EmployeeEntity;
import com.sahil.entities.LeaveCountEntity;
import com.sahil.models.LeaveCountResponse;
import com.sahil.models.Response;
import com.sahil.models.input.Employee;

public interface LeaveCountService {
	public LeaveCountEntity getInitialLeaveCount(EmployeeEntity employeeEntity, Employee employee);

	public LeaveCountResponse getLeaveCountForEmployee(String id);
	
	public Response decrementLeaveCountForApproved(int leaveId);

	public Response incrementCompOffCountForApproved(int compOffId);

	public Response incrementLeaveCountForCancellation(int leaveId);

	public Response decrementCompOffCountForCancellation(int compOffId);
}
