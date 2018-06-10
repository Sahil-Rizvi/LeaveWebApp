package com.sahil.services;

import java.util.List;

import com.sahil.entities.AdminEntity;

public interface ReportGenerator{
	
	public void generateReportForHR(List<AdminEntity> admins);
	
	public void generateReportForManager();
}
