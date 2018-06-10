package com.sahil.services;

import java.util.List;

import com.sahil.models.DesigList;
import com.sahil.models.Response;

public interface DesignationService {
	
	public Response addDesignations(List<String> designations);
	public Response editDesignation(int id,String oldDesignation,String newDesignation);
	public Response deleteDesignation(int id,String designation);
	public DesigList getAllDesignations();
	public List<String> getAllDesignationNames();
	public boolean anyDesignationAdded();
	
}
