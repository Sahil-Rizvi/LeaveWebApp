package com.sahil.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sahil.entities.ContactEntity;
import com.sahil.entities.EmployeeEntity;

@Repository
public interface ContactRepository extends JpaRepository<ContactEntity,Integer>{
	
	public ContactEntity findByEmployeeEntity(EmployeeEntity employeeEntity);
	
	public ContactEntity findByEmailId(String emailId);
	
	public ContactEntity findByPhoneNo(String phoneNo);
	
}
