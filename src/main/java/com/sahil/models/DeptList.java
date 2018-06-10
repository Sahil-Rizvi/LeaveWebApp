package com.sahil.models;

import java.util.ArrayList;
import java.util.List;

public class DeptList extends Response{

	private List<Department> departments = new ArrayList<>();

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	@Override
	public String toString() {
		return "DeptList [departments=" + departments + "]";
	}
	
	
	
}
