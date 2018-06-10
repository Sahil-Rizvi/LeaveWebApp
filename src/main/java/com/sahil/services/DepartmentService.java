package com.sahil.services;

import java.util.List;

import com.sahil.models.DeptList;
import com.sahil.models.Response;

public interface DepartmentService {

	public Response addDepartments(List<String> departments);
	public Response editDepartment(int id,String oldDepartment,String newDepartment);
	public Response deleteDepartment(int id,String department);
	public DeptList getAllDepartments();
	public List<String> getAllDepartmentNames();
	public boolean anyDepartmentAdded();
	
}
