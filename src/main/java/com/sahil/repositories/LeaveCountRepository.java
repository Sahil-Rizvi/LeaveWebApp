package com.sahil.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sahil.entities.EmployeeEntity;
import com.sahil.entities.LeaveCountEntity;

@Repository
public interface LeaveCountRepository extends JpaRepository<LeaveCountEntity,Integer>{

	
	public LeaveCountEntity findByEmployeeEntity(EmployeeEntity employeeEntity);
	/*
	@Query("SELECT t.sickCount from leave_count t where t.employeeEntity.employeeCode = :id")
	int findSickCountByEmployeeId(@Param("id")int id);
	
	@Query("SELECT t.bdayCount from leave_count t where t.employeeEntity.employeeCode = :id")
	int findBdayCountByEmployeeId(@Param("id")int id);
	
	@Query("SELECT t.casualCount from leave_count t where t.employeeEntity.employeeCode = :id")
	int findCasualCountByEmployeeId(@Param("id")int id);
	
	@Query("SELECT t.compOffCount from leave_count t where t.employeeEntity.employeeCode = :id")
	int findCompOffCountByEmployeeId(@Param("id")int id);
	
	@Query("SELECT t.privilegedCount from leave_count t where t.employeeEntity.employeeCode = :id")
	int findPrivilegedCountByEmployeeId(@Param("id")int id);
	*/
	
}
