package com.sahil.models.hr;

import java.util.List;

import com.sahil.models.Response;

public class ListOfEmployeeForRights extends Response{

	private List<EmployeeForRights> employees;

	public List<EmployeeForRights> getEmployees() {
		return employees;
	}

	public void setEmployees(List<EmployeeForRights> employees) {
		this.employees = employees;
	}

	@Override
	public String toString() {
		return "ListOfEmployeeForRights [employees=" + employees + "]";
	}

		
}
