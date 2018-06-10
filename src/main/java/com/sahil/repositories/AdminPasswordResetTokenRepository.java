package com.sahil.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sahil.entities.AdminEntity;
import com.sahil.entities.AdminPasswordResetToken;

@Repository
public interface AdminPasswordResetTokenRepository extends JpaRepository<AdminPasswordResetToken, Long>{

	public AdminPasswordResetToken findByToken(String token);
	public AdminPasswordResetToken findByUser(AdminEntity adminEntity);
	
}

