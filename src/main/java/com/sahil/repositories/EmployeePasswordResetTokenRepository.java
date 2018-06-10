package com.sahil.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sahil.entities.EmployeeEntity;
import com.sahil.entities.EmployeePasswordResetToken;

@Repository
public interface EmployeePasswordResetTokenRepository extends JpaRepository<EmployeePasswordResetToken,Long>{
	public EmployeePasswordResetToken findByToken(String token);
	public EmployeePasswordResetToken findByUser(EmployeeEntity adminEntity);
	
}
