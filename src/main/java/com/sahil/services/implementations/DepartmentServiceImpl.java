package com.sahil.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.sahil.entities.DepartmentEntity;
import com.sahil.entities.EmployeeEntity;
import com.sahil.models.Department;
import com.sahil.models.DeptList;
import com.sahil.models.Response;
import com.sahil.repositories.DepartmentRepository;
import com.sahil.repositories.EmployeeRepository;
import com.sahil.services.DepartmentService;
import com.sahil.utils.LeaveUtil;

@Service
public class DepartmentServiceImpl implements DepartmentService{

	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public Response addDepartments(List<String> departments) {
		// TODO Auto-generated method stub
		Response response = new Response();
		if(CollectionUtils.isEmpty(departments)){
			response.setCode(1);
			response.setMessage("Please enter some department");
            return response;
		}
		
		List<String> list = new ArrayList<>();
		
		for(String department:departments){
			DepartmentEntity departmentEntity = new DepartmentEntity();
			if(!StringUtils.isEmpty(department)){
				departmentEntity.setName(LeaveUtil.toTitleCase(department));
				departmentRepository.save(departmentEntity);
				list.add(LeaveUtil.toTitleCase(department));
			}
		}
		
		if(!list.isEmpty()){
			response.setCode(0);
			response.setMessage(" ADDED DEPARTMENTS "+list);
		}
	    return response;
	}

	@Override
	public Response editDepartment(int id, String oldDepartment, String newDepartment) {
		// TODO Auto-generated method stub
		Response response = new Response();
		System.out.println(oldDepartment);
		System.out.println(newDepartment);
		String oldDept = LeaveUtil.toTitleCase(oldDepartment);
		String newDept = LeaveUtil.toTitleCase(newDepartment);
		System.out.println(id);
		System.out.println(oldDept);
		System.out.println(newDept);
		if(id<=0 || StringUtils.isEmpty(oldDept) || StringUtils.isEmpty(newDept)){
			response.setCode(1);
			response.setMessage("INVALID DATA");
			return response;
		}
		
		DepartmentEntity departmentEntity = departmentRepository.findOne(id);
		response.setCode(1);
		response.setMessage("NO DEPARTMENT EXISTS WITH ID "+id);
		
		if(departmentEntity!=null){
			if(departmentEntity.getName().equals(oldDept)){
				departmentEntity.setName(newDept);
				departmentRepository.save(departmentEntity);
				response.setCode(0);
				response.setMessage("DEPARTMENT ID: "+id+" EDITED FROM "+oldDept+" TO "+newDept);
				return response;
			}
			response.setMessage("DEPARTMENT ID: "+id+" DOES NOT HAVE NAME AS "+oldDept);
		}
		
		return response;
		
	}

	@Override
	public Response deleteDepartment(int id, String department) {
		// TODO Auto-generated method stub
		Response response = new Response();
		String dept = LeaveUtil.toTitleCase(department);
		if(id<=0 || StringUtils.isEmpty(dept)){
			response.setCode(1);
			response.setMessage("INVALID DATA");
			return response;
		}
		
		DepartmentEntity departmentEntity = departmentRepository.findOne(id);
		response.setCode(1);
		response.setMessage("NO DEPARTMENT EXISTS WITH ID "+id);
		
		if(departmentEntity!=null){
			if(departmentEntity.getName().equals(dept)){
				List<EmployeeEntity> employeeEntities = employeeRepository.findByDepartmentEntity(departmentEntity);
				for(EmployeeEntity employeeEntity:employeeEntities){
					employeeEntity.setDepartmentEntity(null);
					employeeRepository.save(employeeEntity);
				}
				departmentRepository.delete(departmentEntity);
				response.setCode(0);
				response.setMessage("DEPARTMENT ID: "+id+" WITH NAME "+dept+" DELETED SUCCESSFULLY ");
				return response;
			}
			response.setMessage("DEPARTMENT ID: "+id+" DOES NOT HAVE NAME AS "+dept);
		}
		
		return response;

	}

	@Override
	public DeptList getAllDepartments() {
		// TODO Auto-generated method stub
		DeptList deptList = new DeptList();
		deptList.setCode(1);
		deptList.setMessage("NO DEPARTMENT EXISTS");
		List<Department> list = new ArrayList<>();
		deptList.setDepartments(list);
      
		List<DepartmentEntity> departmentEntities = departmentRepository.findAll();
		
		if(!CollectionUtils.isEmpty(departmentEntities)){
			list = departmentEntities.stream().map(this::convert).collect(Collectors.toList());
			deptList.setCode(0);
			deptList.setMessage("SUCCESSFUL");
			deptList.setDepartments(list);
		}
		return deptList;
	}
	
	@Override
	public boolean anyDepartmentAdded(){
		return departmentRepository.count()>0 ?true:false;
	}
	
	
	private Department convert(DepartmentEntity departmentEntity){
		Department department = new Department();
		if(Objects.nonNull(departmentEntity)){
			department.setId(departmentEntity.getDepartmentId());
			department.setName(departmentEntity.getName());
		}
		return department;
	}

	@Override
	public List<String> getAllDepartmentNames() {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<>();
		List<DepartmentEntity> departmentEntities = departmentRepository.findAll();
		if(!CollectionUtils.isEmpty(departmentEntities)){
			list = departmentEntities.stream().map(e->e.getName()).collect(Collectors.toList());
		}
		return list;
	}
	
	


}
