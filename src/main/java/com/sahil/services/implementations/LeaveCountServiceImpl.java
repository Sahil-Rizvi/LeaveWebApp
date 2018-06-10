package com.sahil.services.implementations;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sahil.entities.CompOffEntity;
import com.sahil.entities.EmployeeEntity;
import com.sahil.entities.LeaveCountEntity;
import com.sahil.entities.LeaveEntity;
import com.sahil.enums.CompOffStatus;
import com.sahil.enums.LeaveStatus;
import com.sahil.enums.LeaveType;
import com.sahil.models.CountResponse;
import com.sahil.models.LeaveCountResponse;
import com.sahil.models.Response;
import com.sahil.models.input.Employee;
import com.sahil.repositories.CompOffRepository;
import com.sahil.repositories.EmployeeRepository;
import com.sahil.repositories.LeaveCountRepository;
import com.sahil.repositories.LeaveRepository;
import com.sahil.services.LeaveCountForYearService;
import com.sahil.services.LeaveCountService;
import com.sahil.services.WorkingDaysCalculatorService;
import com.sahil.utils.LeaveUtil;

@Service
public class LeaveCountServiceImpl implements LeaveCountService{

	private Logger Logger = LoggerFactory.getLogger(LeaveCountServiceImpl.class); 

	@Autowired
	private LeaveCountRepository leaveCountRepository;
	
	@Autowired
	private CompOffRepository compOffRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private LeaveRepository leaveRepository;
	
	@Autowired
	private LeaveCountForYearService leaveCountForYearService;
	
	@Autowired
	private WorkingDaysCalculatorService workingDaysCalculatorService;
	
	private int totalLeaves = 28;
	
	private int totalBday = 1;
	
	private int totalCasual = 7;
	
	private int totalPrivileged = 13;
	
	private int totalSick = 7;
	
	private int totalCompOff = 0;
	
	@Override
	public LeaveCountEntity getInitialLeaveCount(EmployeeEntity employeeEntity, Employee employee) {
		
		LeaveCountEntity leaveCountEntity = new LeaveCountEntity();
		//EmployeeEntity employeeEntity = employeeRepository.findOne(id);
		Date dateOfJoining = LeaveUtil.getTodaysDate();
		Date bdayDate = employee.getDateOfBirth();
		System.out.println("dateOfJoining:"+dateOfJoining);
		System.out.println("bDayDate:"+bdayDate);
		
		
		double total1 = calculateTotal(dateOfJoining);
		
		double sickCount = calculateSick(dateOfJoining);
		double casualCount = calculateCasual(dateOfJoining);
		double privilegedCount = calculatePrivileged(dateOfJoining);
		double bdayCount = calculateBday(dateOfJoining,bdayDate);
		int compOffCount = calculateCompOff(dateOfJoining);
		
		leaveCountEntity.setEmployeeEntity(employeeEntity);
		leaveCountEntity.setSickCount(sickCount);
		leaveCountEntity.setCasualCount(casualCount);
		leaveCountEntity.setPrivilegedCount(privilegedCount);
		leaveCountEntity.setBdayCount(bdayCount);
		leaveCountEntity.setCompOffCount(compOffCount);
		
		System.out.println("sick Count"+sickCount);
		System.out.println("casualCount"+casualCount);
		System.out.println("privileged Count"+privilegedCount);
		System.out.println("bday Count"+bdayCount);
		System.out.println("compOff Count"+compOffCount);
		
		
		double total2 = sickCount + casualCount + privilegedCount + bdayCount + compOffCount;
		
		double diff = total1 - total2;
		
		System.out.println("total calculated are "+total2+" with a difference of "+diff);
		
		return leaveCountEntity;
	}
	
	
	@Override
	public LeaveCountResponse getLeaveCountForEmployee(String id){
		EmployeeEntity employeeEntity = employeeRepository.findOne(id);
		LeaveCountResponse leaveResponse = null;
	    if(!StringUtils.isEmpty(employeeEntity) && !StringUtils.isEmpty(employeeEntity.getLeaveCountEntity())){
	       LeaveCountEntity leaveCountEntity =  employeeEntity.getLeaveCountEntity();
	       leaveResponse = new LeaveCountResponse();
	       leaveResponse.setBdayCount(leaveCountEntity.getBdayCount());
	       leaveResponse.setCasualCount(leaveCountEntity.getCasualCount());
	       leaveResponse.setCompOffCount(leaveCountEntity.getCompOffCount());
	       leaveResponse.setPrivilegedCount(leaveCountEntity.getPrivilegedCount());
	       leaveResponse.setSickCount(leaveCountEntity.getSickCount());
	    }
	    return leaveResponse;
	}
	
	
	@SuppressWarnings("unused")
	private void remove(LeaveCountEntity leaveCountEntity){
		leaveCountRepository.delete(leaveCountEntity);
	}

	
	private double calculateTotal(Date dateOfJoining){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateOfJoining);
		int monthsLeft = 12;
		int m = calendar.get(Calendar.MONTH)+1;
		System.out.println(m);
		int d = calendar.get(Calendar.DAY_OF_MONTH);
		System.out.println(d);
		if(d>=15){
			monthsLeft = monthsLeft - m;
		}
		else{
			monthsLeft = monthsLeft - m + 1 ;
		}
		
		CountResponse countResponse = leaveCountForYearService.getAllCountsForYear(calendar.get(Calendar.YEAR));
		System.out.println("total");
		System.out.println(monthsLeft);
		if(countResponse.getCode()==0){
			System.out.println((countResponse.getCount()/12.0)*monthsLeft);
			return LeaveUtil.roundingOffToTwoPlaces((countResponse.getCount()/12.0)*monthsLeft);
		}
		
		System.out.println(countResponse);
		return 0;
	}
	
	private int calculateCompOff(Date dateOfJoining) {
		return 0;
	}

	private double calculateBday(Date dateOfJoining,Date bday) {
		// TODO Auto-generated method stub
	    if(LeaveUtil.isAfterStrictlyWithinYear(bday,dateOfJoining)){
	    	Calendar calendar = Calendar.getInstance();
			CountResponse countResponse = leaveCountForYearService.getBDayCountForYear(calendar.get(Calendar.YEAR));
			System.out.println("bday Count");
			if(countResponse.getCode()==0){
				System.out.println(countResponse.getCount());
				return LeaveUtil.roundingOffToTwoPlaces(countResponse.getCount());
			}
			
			System.out.println(countResponse);
			return 0;
	    }
		return 0;
	}

	private double calculatePrivileged(Date dateOfJoining) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateOfJoining);
		int monthsLeft = 12;
		int m = calendar.get(Calendar.MONTH)+1;
		int d = calendar.get(Calendar.DAY_OF_MONTH);
		if(d>=15){
			monthsLeft = monthsLeft - m;
		}
		else{
			monthsLeft = monthsLeft - m + 1 ;
		}
		
		CountResponse countResponse = leaveCountForYearService.getPrivilegedCountForYear(calendar.get(Calendar.YEAR));
		System.out.println("Privileged");
		System.out.println(monthsLeft);
		if(countResponse.getCode()==0){
			System.out.println((countResponse.getCount()/12.0)*monthsLeft);
			return LeaveUtil.roundingOffToTwoPlaces((countResponse.getCount()/12.0)*monthsLeft);
		}
		
		System.out.println(countResponse);
		return 0;
		
	}

	private double calculateCasual(Date dateOfJoining) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateOfJoining);
		int monthsLeft = 12;
		int m = calendar.get(Calendar.MONTH)+1;
		int d = calendar.get(Calendar.DAY_OF_MONTH);
		if(d>=15){
			monthsLeft = monthsLeft - m;
		}
		else{
			monthsLeft = monthsLeft - m + 1 ;
		}
		
		CountResponse countResponse = leaveCountForYearService.getCasualCountForYear(calendar.get(Calendar.YEAR));
		System.out.println("Casual");
		System.out.println(monthsLeft);
		if(countResponse.getCode()==0){
			System.out.println((countResponse.getCount()/12.0)*monthsLeft);
			return LeaveUtil.roundingOffToTwoPlaces((countResponse.getCount()/12.0)*monthsLeft);
		}
		
		System.out.println(countResponse);
		return 0;
		
	}

	private double calculateSick(Date dateOfJoining) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateOfJoining);
		int monthsLeft = 12;
		int m = calendar.get(Calendar.MONTH)+1;
		int d = calendar.get(Calendar.DAY_OF_MONTH);
		if(d>=15){
			monthsLeft = monthsLeft - m;
		}
		else{
			monthsLeft = monthsLeft - m + 1 ;
		}
		
		
		CountResponse countResponse = leaveCountForYearService.getSickCountForYear(calendar.get(Calendar.YEAR));
		System.out.println("Sick");
		System.out.println(monthsLeft);
		if(countResponse.getCode()==0){
			System.out.println((countResponse.getCount()/12.0)*monthsLeft);
			return LeaveUtil.roundingOffToTwoPlaces((countResponse.getCount()/12.0)*monthsLeft);
		}
		
		System.out.println(countResponse);
		return 0;
	
	}


	@Override
	public Response decrementLeaveCountForApproved(int leaveId) {
		// TODO Auto-generated method stub
		
		Response response = new Response();
		response.setCode(1);
		response.setMessage("NOT DECREMENTED");
		
		LeaveEntity entity = leaveRepository.findOne(leaveId);
		if(!StringUtils.isEmpty(entity)){
			LeaveType leaveType = entity.getLeaveType();
			
		    int diff = (int)workingDaysCalculatorService.getWorkingDaysBetweenTwoDatesIncludingBothDaysIncludingHolidays(entity.getFromDate(),entity.getToDate());
		    System.out.println(diff);
		    
			EmployeeEntity employeeEntity = entity.getEmployeeEntity();
			System.out.println(employeeEntity.getLeaveCountEntity().getSickCount());
			
			switch(leaveType){
			
			case SICK : employeeEntity.getLeaveCountEntity().setSickCount(LeaveUtil.roundingOffToTwoPlaces(employeeEntity.getLeaveCountEntity().getSickCount()-diff));
			            break;
			
			case BDAY : employeeEntity.getLeaveCountEntity().setBdayCount((int)LeaveUtil.roundingOffToTwoPlaces(employeeEntity.getLeaveCountEntity().getBdayCount()-diff));
			            break;
			            
			case CASUAL : employeeEntity.getLeaveCountEntity().setCasualCount(LeaveUtil.roundingOffToTwoPlaces(employeeEntity.getLeaveCountEntity().getCasualCount()-diff));
			              break;
			              
			case PRIVILEGED : employeeEntity.getLeaveCountEntity().setPrivilegedCount(LeaveUtil.roundingOffToTwoPlaces(employeeEntity.getLeaveCountEntity().getPrivilegedCount()-diff));
			                  break;
			
			case COMPOFF : employeeEntity.getLeaveCountEntity().setCompOffCount((int)LeaveUtil.roundingOffToTwoPlaces(employeeEntity.getLeaveCountEntity().getCompOffCount()-diff));
			
			default : break;
			
			}
			
			System.out.println(employeeEntity.getLeaveCountEntity().getSickCount());
			employeeRepository.save(employeeEntity);
			
			response.setCode(0);
			response.setMessage(leaveType+" COUNT DECREMENTED BY "+diff);
		}
		
		
		return response;
	}
	
	
	
	@Override
	public Response incrementLeaveCountForCancellation(int leaveId) {
		// TODO Auto-generated method stub
		
		Response response = new Response();
		response.setCode(1);
		response.setMessage("NOT INCREMENTED");
		
		LeaveEntity entity = leaveRepository.findOne(leaveId);
		LeaveStatus leaveStatus = entity.getLeaveStatus();
		if(!StringUtils.isEmpty(entity) && LeaveStatus.APPROVED.equals(leaveStatus) && entity.getFromDate().after(LeaveUtil.getTodaysDate())){
			LeaveType leaveType = entity.getLeaveType();
			
			
		    int diff = (int)workingDaysCalculatorService.getWorkingDaysBetweenTwoDatesIncludingBothDaysIncludingHolidays(entity.getFromDate(),entity.getToDate());
		    System.out.println(diff);
		    
			EmployeeEntity employeeEntity = entity.getEmployeeEntity();
			
			
			
			switch(leaveType){
			
			case SICK : employeeEntity.getLeaveCountEntity().setSickCount(LeaveUtil.roundingOffToTwoPlaces(employeeEntity.getLeaveCountEntity().getSickCount()+diff));
			            break;
			
			case BDAY : employeeEntity.getLeaveCountEntity().setBdayCount((int)LeaveUtil.roundingOffToTwoPlaces(employeeEntity.getLeaveCountEntity().getBdayCount()+diff));
			            break;
			            
			case CASUAL : employeeEntity.getLeaveCountEntity().setCasualCount(LeaveUtil.roundingOffToTwoPlaces(employeeEntity.getLeaveCountEntity().getCasualCount()+diff));
			              break;
			              
			case PRIVILEGED : employeeEntity.getLeaveCountEntity().setPrivilegedCount(LeaveUtil.roundingOffToTwoPlaces(employeeEntity.getLeaveCountEntity().getPrivilegedCount()+diff));
			                  break;
			
			case COMPOFF : employeeEntity.getLeaveCountEntity().setCompOffCount((int)LeaveUtil.roundingOffToTwoPlaces(employeeEntity.getLeaveCountEntity().getCompOffCount()+diff));
			
			default : break;
			
			}
			
			employeeRepository.save(employeeEntity);
			
			response.setCode(0);
			response.setMessage(leaveType+" COUNT INCREMENTED BY "+diff);
		}
		
		
		return response;
	}
	
	
	
	
	@Override
	public Response incrementCompOffCountForApproved(int compOffId) {
		// TODO Auto-generated method stub
		
		Response response = new Response();
		response.setCode(1);
		response.setMessage("NOT INCREMENTED");
		
		CompOffEntity compOffEntity = compOffRepository.findOne(compOffId);
		if(!StringUtils.isEmpty(compOffEntity)){
			
		    
			EmployeeEntity employeeEntity = compOffEntity.getEmployeeEntity();
			System.out.println(employeeEntity.getLeaveCountEntity().getCompOffCount());
			
			employeeEntity.getLeaveCountEntity().setCompOffCount(employeeEntity.getLeaveCountEntity().getCompOffCount()+1);
			employeeRepository.save(employeeEntity);
			
			response.setCode(0);
			response.setMessage("COMP-OFF COUNT INCREMENTED BY 1");
		}
		
		return response;
	}


	@Override
	public Response decrementCompOffCountForCancellation(int compOffId) {
		// TODO Auto-generated method stub
		
		Response response = new Response();
		response.setCode(1);
		response.setMessage("NOT DECREMENTED");
		
		CompOffEntity compOffEntity = compOffRepository.findOne(compOffId);
		
		CompOffStatus compOffStatus = compOffEntity.getCompOffStatus();
		if(!StringUtils.isEmpty(compOffEntity) && CompOffStatus.APPROVED.equals(compOffStatus)){
			
		    
			EmployeeEntity employeeEntity = compOffEntity.getEmployeeEntity();
			System.out.println(employeeEntity.getLeaveCountEntity().getCompOffCount());
			
			employeeEntity.getLeaveCountEntity().setCompOffCount(employeeEntity.getLeaveCountEntity().getCompOffCount()-1);
			employeeRepository.save(employeeEntity);
			
			response.setCode(0);
			response.setMessage("COMP-OFF COUNT DECREMENTED BY 1");
		}
		
		return response;
	}

	
	
}
