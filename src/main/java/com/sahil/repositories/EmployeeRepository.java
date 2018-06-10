package com.sahil.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sahil.entities.DepartmentEntity;
import com.sahil.entities.DesignationEntity;
import com.sahil.entities.EmployeeEntity;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity,String>{     

	public List<EmployeeEntity> findByManager(EmployeeEntity manager);
	
	public EmployeeEntity findByEmployeeCodeAndPassword(EmployeeEntity employeeEntity,String password);
	
	//@Query("from employee_details e where e.departmentEntity.name = :managerCode and l.leaveStatus = :status and l.approvedOn >= :fromDate and l.approvedOn <= :toDate")
	
	
	public Page<EmployeeEntity> findByDepartmentEntity(DepartmentEntity department,Pageable pageable);
	
	public List<EmployeeEntity> findByDepartmentEntity(DepartmentEntity department);
	
	public List<EmployeeEntity> findByDesignationEntity(DesignationEntity designationEntity);
}
