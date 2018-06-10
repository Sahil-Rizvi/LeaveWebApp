package com.sahil.validators;


import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.sahil.models.input.Employee;

@Component
public class EmployeeValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Employee.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors err) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(err,"employeeId","employee.id.empty");
		ValidationUtils.rejectIfEmpty(err,"employeeName","employee.name.empty");
		ValidationUtils.rejectIfEmpty(err,"designation","employee.designation.empty");
		ValidationUtils.rejectIfEmpty(err,"department","employee.department.empty");
		ValidationUtils.rejectIfEmpty(err,"email","employee.email.empty");
		ValidationUtils.rejectIfEmpty(err,"phoneNo","employee.phoneNo.empty");
		ValidationUtils.rejectIfEmpty(err,"dateOfBirth","employee.dateOfBirth.empty");
		ValidationUtils.rejectIfEmpty(err,"password","employee.password.empty");
		
		Employee employee = (Employee)obj;
		
		System.out.println("inside validation \n"+employee);
		
		Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
	            Pattern.CASE_INSENSITIVE);
	      if (!(pattern.matcher(employee.getEmail()).matches())) {
	         err.rejectValue("email", "employee.email.invalid");
	      }

		
		
	}

}
