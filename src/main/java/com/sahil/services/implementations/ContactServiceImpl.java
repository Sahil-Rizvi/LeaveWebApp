package com.sahil.services.implementations;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sahil.entities.ContactEntity;
import com.sahil.entities.EmployeeEntity;
import com.sahil.repositories.ContactRepository;
import com.sahil.services.ContactService;

@Service
public class ContactServiceImpl implements ContactService{

	@Autowired
	private ContactRepository contactRepository;

	@Override
	public String findEmail(EmployeeEntity employeeEntity) {
		// TODO Auto-generated method stub
		if(Objects.nonNull(employeeEntity)){
			ContactEntity contactEntity = contactRepository.findByEmployeeEntity(employeeEntity);
			if(Objects.nonNull(contactEntity)){
				return contactEntity.getEmailId();
			}
		}
		return null;
	}
	
	

}
