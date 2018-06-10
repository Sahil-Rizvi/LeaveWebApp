package com.sahil.repositories;

import org.springframework.stereotype.Repository;
import com.sahil.entities.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity,Integer>{

	public AdminEntity findByEmailId(String email);
	public AdminEntity findByEmailIdAndPassword(String email,String password);
	
}
