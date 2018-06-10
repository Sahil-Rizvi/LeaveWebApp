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

import com.sahil.entities.CompOffEntity;
import com.sahil.entities.EmployeeEntity;
import com.sahil.enums.CompOffStatus;

@Repository
public interface CompOffRepository extends JpaRepository<CompOffEntity,Integer>{
	
	public CompOffEntity findByCompOffId(Integer id);
	
	
	public List<CompOffEntity> findByManagerEntity(EmployeeEntity managerEntity);
	
	// for employees
	
		@Query("from comp_off_details c where c.employeeEntity.employeeCode = :employeeCode and c.compOffStatus = :status and c.appliedOn >= :fromDate and c.appliedOn <= :toDate ")
		public Page<CompOffEntity> findPendingOrRejectedCompOffsByEmployeeEntityAndCompOffStatus(@Param("employeeCode")String employeeCode,@Param("status")CompOffStatus status,@Param("fromDate")Date fromDate,@Param("toDate")Date toDate,Pageable pageable);
		
		@Query("from comp_off_details c where c.employeeEntity.employeeCode = :employeeCode and c.compOffStatus = 'APPROVED' and c.approvedOn >= :fromDate and c.approvedOn <= :toDate ")
		public Page<CompOffEntity> findApprovedCompOffsByEmployeeEntity(@Param("employeeCode")String employeeCode,@Param("fromDate")Date fromDate,@Param("toDate")Date toDate,Pageable pageable);

		@Query("from comp_off_details c where c.managerEntity.employeeCode = :managerCode and c.employeeEntity.employeeCode != :employeeCode and c.compOffStatus = 'APPROVED' and c.approvedOn >= :fromDate and c.approvedOn <= :toDate ")
		public Page<CompOffEntity> findApprovedCompOffsOfTeamMates(@Param("employeeCode")String employeeCode,@Param("managerCode")String managerCode,@Param("fromDate")Date fromDate,@Param("toDate")Date toDate,Pageable pageable);
		
		// for managers
		@Query("from comp_off_details c where c.managerEntity.employeeCode = :managerCode and c.compOffStatus = :status and c.approvedOn >= :fromDate and c.approvedOn <= :toDate ")
		public Page<CompOffEntity> findByManagerEntityAndCompOffStatus(@Param("managerCode")String managerCode,@Param("status")CompOffStatus status,@Param("fromDate")Date fromDate,@Param("toDate")Date toDate,Pageable pageable);
		
		@Query("from comp_off_details c where c.currentlyManager.employeeCode = :managerCode and c.compOffStatus = :status and c.approvedOn >= :fromDate and c.approvedOn <= :toDate ")
		public Page<CompOffEntity> findByCurrentlyManagerAndCompOffStatus(@Param("managerCode")String managerCode,@Param("status")CompOffStatus status,@Param("fromDate")Date fromDate,@Param("toDate")Date toDate,Pageable pageable);
		
		
		public List<CompOffEntity> findByManagerEntityAndCompOffStatus(EmployeeEntity manager,CompOffStatus status);
		
		public List<CompOffEntity> findByCurrentlyManagerAndCompOffStatus(EmployeeEntity manager,CompOffStatus status);
		
		public List<CompOffEntity> findByCurrentlyManager(EmployeeEntity employeeEntity);
		
		
		@Modifying(clearAutomatically=true)
		@Transactional
		@Query("update comp_off_details c set c.currentlyManager.employeeCode = :mId, c.compOffStatus = :status, c.approvedOn = :date ,c.rejectionReason = :reason where c.compOffId = :cId")
		int updateCompOffDetails(@Param("mId") String mId , @Param("status") CompOffStatus status ,@Param("date") Date date, @Param("cId") int lId,@Param("reason") String reason);


			
}
