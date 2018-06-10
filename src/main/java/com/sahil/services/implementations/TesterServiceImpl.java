package com.sahil.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.sahil.entities.AdminEntity;
import com.sahil.entities.EmployeeEntity;
import com.sahil.entities.LeaveEntity;
import com.sahil.enums.LeaveStatus;
import com.sahil.repositories.AdminRepository;
import com.sahil.repositories.LeaveRepository;
import com.sahil.services.ReportGenerator;
import com.sahil.services.TesterService;

@Service
public class TesterServiceImpl implements TesterService{

	@Autowired
	private LeaveRepository leaveRepository;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private ReportGenerator reportGenerator;
	
	@Override
	public void forwardLeaves(){
		System.out.println("here");
		List<LeaveEntity> leaves = leaveRepository.findAll();
		System.out.println(leaves);
		if(!CollectionUtils.isEmpty(leaves)){
			for(LeaveEntity leaveEntity : leaves){
				if(leaveEntity.getLeaveStatus().equals(LeaveStatus.PENDING) && leaveEntity.getCurrentlyManager()!=null){					
					System.out.println(leaveEntity);
					EmployeeEntity upperManager = leaveEntity.getCurrentlyManager().getManager();
					if(Objects.nonNull(upperManager)){
						System.out.println("updating");
						leaveEntity.setCurrentlyManager(upperManager);
						leaveRepository.save(leaveEntity);
						// send mail 
					}
				}
			}
			
		}
		
	}
	
	@Override
	public void reportForHr(){
    	List<AdminEntity> admins = new ArrayList<>();
    	admins = adminRepository.findAll();
    	if(!CollectionUtils.isEmpty(admins)){
    	     reportGenerator.generateReportForHR(admins);
    	}
    	
    }
    
    @Override  
    public void reportForManager(){
    	reportGenerator.generateReportForManager();
    }
    
	
	
	
}
