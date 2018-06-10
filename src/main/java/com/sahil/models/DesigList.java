package com.sahil.models;

import java.util.ArrayList;
import java.util.List;

public class DesigList extends Response{

	private List<Designation> designations = new ArrayList<>();

	public List<Designation> getDesignations() {
		return designations;
	}

	public void setDesignations(List<Designation> designations) {
		this.designations = designations;
	}

	@Override
	public String toString() {
		return "DesigList [designations=" + designations + "]";
	}
	
	
	
}
