package com.sahil.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sahil.entities.EmployeeEntity;
import com.sahil.entities.LeaveEntity;
import com.sahil.enums.LeaveStatus;

@Repository
public interface LeaveRepository extends JpaRepository<LeaveEntity,Integer>{
   
	// for employees
	
	@Query("from leave_details l where l.employeeEntity.employeeCode = :employeeCode and l.leaveStatus = :status and l.appliedOn >= :fromDate and l.appliedOn <= :toDate ")
	public Page<LeaveEntity> findPendingOrRejectedLeavesByEmployeeEntityAndLeaveStatus(@Param("employeeCode")String employeeCode,@Param("status")LeaveStatus status,@Param("fromDate")Date fromDate,@Param("toDate")Date toDate,Pageable pageable);
	
	@Query("from leave_details l where l.employeeEntity.employeeCode = :employeeCode and l.leaveStatus = 'APPROVED' and l.approvedOn >= :fromDate and l.approvedOn <= :toDate ")
	public Page<LeaveEntity> findApprovedLeavesByEmployeeEntity(@Param("employeeCode")String employeeCode,@Param("fromDate")Date fromDate,@Param("toDate")Date toDate,Pageable pageable);

	@Query("from leave_details l where l.managerEntity.employeeCode = :managerCode and l.employeeEntity.employeeCode != :employeeCode and l.leaveStatus = 'APPROVED' and l.approvedOn >= :fromDate and l.approvedOn <= :toDate ")
	public Page<LeaveEntity> findApprovedLeavesOfTeamMates(@Param("employeeCode")String employeeCode,@Param("managerCode")String managerCode,@Param("fromDate")Date fromDate,@Param("toDate")Date toDate,Pageable pageable);
	
	// for managers
	
	@Query("from leave_details l where l.managerEntity.employeeCode = :managerCode and l.leaveStatus = :status and l.approvedOn >= :fromDate and l.approvedOn <= :toDate")
	public Page<LeaveEntity> findByManagerEntityAndLeaveStatus(@Param("managerCode")String managerCode,@Param("status")LeaveStatus status,@Param("fromDate")Date fromDate,@Param("toDate")Date toDate,Pageable pageable);
	
	@Query("from leave_details l where l.currentlyManager.employeeCode = :managerCode and l.leaveStatus = :status and l.approvedOn >= :fromDate and l.approvedOn <= :toDate")
	public Page<LeaveEntity> findByCurrentlyManagerAndLeaveStatus(@Param("managerCode")String managerCode,@Param("status")LeaveStatus status,@Param("fromDate")Date fromDate,@Param("toDate")Date toDate,Pageable pageable);
	
	
	public List<LeaveEntity> findByManagerEntityAndLeaveStatus(EmployeeEntity manager,LeaveStatus status);
	
	public List<LeaveEntity> findByCurrentlyManagerAndLeaveStatus(EmployeeEntity manager,LeaveStatus status);
	
	public List<LeaveEntity> findByManagerEntity(EmployeeEntity employeeEntity);
	
	public List<LeaveEntity> findByCurrentlyManager(EmployeeEntity employeeEntity);
	
	
	@Modifying(clearAutomatically=true)
	@Transactional
	@Query("update leave_details l set l.currentlyManager.employeeCode = :mId, l.leaveStatus = :status, l.approvedOn = :date , l.rejectionReason = :reason where l.leaveId = :lId")
	int updateLeaveDetails(@Param("mId") String mId , @Param("status") LeaveStatus status ,@Param("date") Date date, @Param("lId") int lId,@Param("reason") String reason);
	
	// for calendar
	
	@Query("from leave_details l where l.leaveStatus = 'APPROVED' and l.fromDate >= :fromDate and l.fromDate <= :toDate or l.toDate >= :fromDate and l.toDate <= :toDate")
	List<LeaveEntity> findApprovedLeaveEntitiesBetween(@Param("fromDate")Date fromDate,@Param("toDate")Date toDate);

	// group by l.employeeEntity.departmentEntity.name order by l.employeeEntity.employeeCode , l.approvedOn
	
    // for hr report
	@Query("from leave_details l where l.leaveStatus = 'APPROVED' and l.fromDate >= :fromDate and l.fromDate <= :toDate or l.toDate >= :fromDate and l.toDate <= :toDate order by l.employeeEntity.departmentEntity.name,l.employeeEntity.employeeCode,l.fromDate")
	List<LeaveEntity> findApprovedLeaveEntitiesForReport(@Param("fromDate")Date fromDate,@Param("toDate")Date toDate);

	// for manager report
	@Query("from leave_details l where l.managerEntity.employeeCode = :managerCode and l.leaveStatus = 'APPROVED' and l.fromDate >= :fromDate and l.fromDate <= :toDate or l.toDate >= :fromDate and l.toDate <= :toDate order by l.employeeEntity.employeeCode,l.fromDate")
	List<LeaveEntity> findApprovedLeaveEntitiesBetweenDatesForManagerReport(@Param("managerCode")String managerCode,@Param("fromDate")Date fromDate,@Param("toDate")Date toDate);

	@Query("from leave_details l where l.leaveStatus = 'PENDING' and l.appliedOn >= :startDate and l.appliedOn <= :endDate ")
    List<LeaveEntity> findLeavesForForwarding(@Param("startDate")Date start, @Param("endDate")Date end);

}



