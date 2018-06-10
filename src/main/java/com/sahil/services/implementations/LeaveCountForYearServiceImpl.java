package com.sahil.services.implementations;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.sahil.entities.LeaveCountForYearEntity;
import com.sahil.models.CountResponse;
import com.sahil.models.LeaveCountForYearDTO;
import com.sahil.models.LeaveCounts;
import com.sahil.models.Response;
import com.sahil.models.input.LeaveCountForYear;
import com.sahil.repositories.LeaveCountForYearRepository;
import com.sahil.services.LeaveCountForYearService;
import com.sahil.utils.LeaveUtil;

@Service
public class LeaveCountForYearServiceImpl implements LeaveCountForYearService{

	@Autowired
	private LeaveCountForYearRepository leaveCountForYearRepository;
	
	@Override
	public Response addLeaveCountForCurrentYear(LeaveCountForYear lc) {
		// TODO Auto-generated method stub
		Response response = new Response();
		if(!isValidData(lc)){
		   response.setCode(1);
		   response.setMessage("Invalid data");
		   return response;
		}
		
		
		
		int year = Calendar.getInstance().get(Calendar.YEAR);
		LeaveCountForYearEntity leaveCountForYearEntity = leaveCountForYearRepository.findOne(year);
		if(Objects.nonNull(leaveCountForYearEntity)){
			response.setCode(1);
			response.setMessage("Leave Count already exists for current year");
		    return response;
		}
		
		leaveCountForYearEntity = new LeaveCountForYearEntity();
		leaveCountForYearEntity.setLeaveCountForYearId(year);
		leaveCountForYearEntity.setBdayCount(LeaveUtil.roundingOffToTwoPlaces(Double.parseDouble(lc.getBdayCount())));
		leaveCountForYearEntity.setCasualCount(LeaveUtil.roundingOffToTwoPlaces(Double.parseDouble(lc.getCasualCount())));
		leaveCountForYearEntity.setPrivilegedCount(LeaveUtil.roundingOffToTwoPlaces(Double.parseDouble(lc.getPrivilegedCount())));
		leaveCountForYearEntity.setSickCount(LeaveUtil.roundingOffToTwoPlaces(Double.parseDouble(lc.getSickCount())));
		leaveCountForYearRepository.save(leaveCountForYearEntity);
		response.setCode(0);
		response.setMessage("Updated");
		return response;
	}

	
	@Override
	public boolean isLeaveCountAvailableForCurrentYear() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		LeaveCountForYearEntity leaveCountForYearEntity = leaveCountForYearRepository.findOne(year);
		if(Objects.nonNull(leaveCountForYearEntity)){
			return true;
		}
		return false;
	}

	private boolean isValidData(LeaveCountForYear lc){
		if(Objects.isNull(lc) || StringUtils.isEmpty(lc.getBdayCount()) || StringUtils.isEmpty(lc.getCasualCount()) || StringUtils.isEmpty(lc.getPrivilegedCount()) || StringUtils.isEmpty(lc.getSickCount())){
			return false;
		}
		
		try{
			double b = Double.parseDouble(lc.getBdayCount());
			double c = Double.parseDouble(lc.getCasualCount());
			double p = Double.parseDouble(lc.getPrivilegedCount());
			double s = Double.parseDouble(lc.getSickCount());
		}
		catch(NumberFormatException e){
		  System.out.println(e);
		  return false;
		}
		return true;
	}
	
	
	
	@Override
	public LeaveCountForYearDTO getLeaveCountForYear(int year) {
		LeaveCountForYearDTO dto = new LeaveCountForYearDTO();
		if(year<=1970){
			dto.setCode(1);
			dto.setMessage("Invalid year");
			return dto;
		}
		LeaveCountForYearEntity entity = leaveCountForYearRepository.findOne(year);
		
		if(Objects.isNull(entity)){
			dto.setCode(1);
			dto.setMessage("Not found for year"+year);
			return dto;
		}
		
		dto.setCode(0);
		dto.setMessage("Successful");
		dto.setYear(String.valueOf(entity.getLeaveCountForYearId()));
		dto.setBdayCount(String.valueOf(entity.getBdayCount()));
		dto.setCasualCount(String.valueOf(entity.getCasualCount()));
		dto.setPrivilegedCount(String.valueOf(entity.getPrivilegedCount()));
		dto.setSickCount(String.valueOf(entity.getSickCount()));
		
		return dto;
	}

	@Override
	public Response updateLeaveCountForYear(LeaveCountForYearDTO leaveCountForYearDTO) {
		// TODO Auto-generated method stub
		Response response = new Response();
		int year = Integer.parseInt(leaveCountForYearDTO.getYear());
		if(year<=1970){
			response.setCode(1);
			response.setMessage("Invalid year"+year);
			return response;
		}
		LeaveCountForYearEntity entity = leaveCountForYearRepository.findOne(year);
		if(Objects.isNull(entity)){
			response.setCode(1);
			response.setMessage("No leave count exists for year "+year);
			return response;
		}
		entity.setBdayCount(LeaveUtil.roundingOffToTwoPlaces(Double.parseDouble(leaveCountForYearDTO.getBdayCount())));
		entity.setCasualCount(LeaveUtil.roundingOffToTwoPlaces(Double.parseDouble(leaveCountForYearDTO.getCasualCount())));
		entity.setPrivilegedCount(LeaveUtil.roundingOffToTwoPlaces(Double.parseDouble(leaveCountForYearDTO.getPrivilegedCount())));
		entity.setSickCount(LeaveUtil.roundingOffToTwoPlaces(Double.parseDouble(leaveCountForYearDTO.getSickCount())));
		leaveCountForYearRepository.save(entity);
		response.setCode(0);
		response.setMessage("Updated");
		return response;
	
	}

	@Override
	public Response deleteLeaveCountForYear(int year) {
		Response response = new Response();
		if(year<=1970){
			response.setCode(1);
			response.setMessage("Invalid year"+year);
			return response;
		}
		LeaveCountForYearEntity entity = leaveCountForYearRepository.findOne(year);
		if(Objects.isNull(entity)){
			response.setCode(1);
			response.setMessage("No leave count exists for year "+year);
			return response;
		}
		leaveCountForYearRepository.delete(entity);
		response.setCode(0);
		response.setMessage("Deleted");
		return response;		
	}

	@Override
	public CountResponse getBDayCountForYear(int year) {
		CountResponse countResponse = new CountResponse();
		if(year<=1970){
			countResponse.setCode(1);
			countResponse.setMessage("Invalid year");
			return countResponse;
		}
		LeaveCountForYearEntity entity = leaveCountForYearRepository.findOne(year);
		
		if(Objects.isNull(entity)){
			countResponse.setCode(1);
			countResponse.setMessage("Not found for year"+year);
			return countResponse;
		}
		
		countResponse.setCode(0);
		countResponse.setMessage("Successful");
		countResponse.setCount(entity.getBdayCount());
		return countResponse;
	}

	@Override
	public CountResponse getCasualCountForYear(int year) {
		CountResponse countResponse = new CountResponse();
		if(year<=1970){
			countResponse.setCode(1);
			countResponse.setMessage("Invalid year");
			return countResponse;
		}
		LeaveCountForYearEntity entity = leaveCountForYearRepository.findOne(year);
		
		if(Objects.isNull(entity)){
			countResponse.setCode(1);
			countResponse.setMessage("Not found for year"+year);
			return countResponse;
		}
		
		countResponse.setCode(0);
		countResponse.setMessage("Successful");
		countResponse.setCount(entity.getCasualCount());
		return countResponse;
	}

	@Override
	public CountResponse getPrivilegedCountForYear(int year) {
		CountResponse countResponse = new CountResponse();
		if(year<=1970){
			countResponse.setCode(1);
			countResponse.setMessage("Invalid year");
			return countResponse;
		}
		LeaveCountForYearEntity entity = leaveCountForYearRepository.findOne(year);
		
		if(Objects.isNull(entity)){
			countResponse.setCode(1);
			countResponse.setMessage("Not found for year"+year);
			return countResponse;
		}
		countResponse.setCode(0);
		countResponse.setMessage("Successful");
		countResponse.setCount(entity.getPrivilegedCount());
		return countResponse;
	}

	@Override
	public CountResponse getSickCountForYear(int year) {
		CountResponse countResponse = new CountResponse();
		if(year<=1970){
			countResponse.setCode(1);
			countResponse.setMessage("Invalid year");
			return countResponse;
		}
		LeaveCountForYearEntity entity = leaveCountForYearRepository.findOne(year);
		
		if(Objects.isNull(entity)){
			countResponse.setCode(1);
			countResponse.setMessage("Not found for year"+year);
			return countResponse;
		}
		countResponse.setCode(0);
		countResponse.setMessage("Successful");
		countResponse.setCount(entity.getSickCount());
		return countResponse;
	}

	@Override
	public CountResponse getAllCountsForYear(int year) {
		System.out.println(year);
		CountResponse countResponse = new CountResponse();
		if(year<=1970){
			countResponse.setCode(1);
			countResponse.setMessage("Invalid year");
			return countResponse;
		}
		LeaveCountForYearEntity entity = leaveCountForYearRepository.findOne(year);
		
		if(Objects.isNull(entity)){
			countResponse.setCode(1);
			countResponse.setMessage("Not found for year"+year);
			return countResponse;
		}
		countResponse.setCode(0);
		countResponse.setMessage("Successful");
		countResponse.setCount(sumCounts(entity));
		return countResponse;
	}

	private double sumCounts(LeaveCountForYearEntity entity){
		if(Objects.nonNull(entity)){
			return entity.getBdayCount() + entity.getCasualCount() + entity.getPrivilegedCount() + entity.getSickCount();
		}
		return 0.0;
	}

	@Override
	public LeaveCounts getAllLeaveCounts() {
		// TODO Auto-generated method stub
		LeaveCounts leaveCounts = new LeaveCounts();
		leaveCounts.setCode(1);
		leaveCounts.setMessage("Leave Counts Not Found");
		List<LeaveCountForYearEntity> leaveCountForYearEntities = leaveCountForYearRepository.findAll();
		System.out.println(leaveCountForYearEntities);
		if(!CollectionUtils.isEmpty(leaveCountForYearEntities)){
			leaveCounts.setCode(0);
			leaveCounts.setMessage("Successful");
			leaveCounts.setLeaveCounts(leaveCountForYearEntities.stream().map(this::convert).collect(Collectors.toList()));
		}
		return leaveCounts;
	}
	
	private LeaveCountForYearDTO convert(LeaveCountForYearEntity le){
		LeaveCountForYearDTO dto = new LeaveCountForYearDTO();
		if(Objects.nonNull(le)){
			dto.setCode(0);
			dto.setMessage("success");
			dto.setYear(String.valueOf(le.getLeaveCountForYearId()));
			dto.setBdayCount(String.valueOf(le.getBdayCount()));
			dto.setCasualCount(String.valueOf(le.getCasualCount()));
			dto.setPrivilegedCount(String.valueOf(le.getPrivilegedCount()));
			dto.setSickCount(String.valueOf(le.getSickCount()));
		}
		return dto;
	}
}
