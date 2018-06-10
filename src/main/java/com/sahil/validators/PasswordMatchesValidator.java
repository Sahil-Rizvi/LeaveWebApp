package com.sahil.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.sahil.annotations.PasswordMatches;
import com.sahil.models.input.Admin;
import com.sahil.models.input.Employee;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches,Object>{

	@Override
	public void initialize(PasswordMatches constraintAnnotation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(Object obj, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		
		System.out.println(obj.getClass().getName());
		
		if("com.sahil.models.input.Admin".equals(obj.getClass().getName())){
			Admin admin = (Admin)obj;
			return admin.getPassword().equals(admin.getMatchingPassword());
		}
		else
		if("com.sahil.models.input.Employee".equals(obj.getClass().getName())){
			Employee employee = (Employee)obj;
			return employee.getPassword().equals(employee.getMatchingPassword());
		}
		return false;
		
	}

	
}
