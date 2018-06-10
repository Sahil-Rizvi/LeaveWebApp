package com.sahil.services;

import java.util.Date;

import com.sahil.models.LeaveDetailsDeptWiseDayWise;
import com.sahil.models.PageResponseDTO;
import com.sahil.models.Response;
import com.sahil.models.UpdateResponse;
import com.sahil.models.emp.leave.ApprovedLeaveDTO;
import com.sahil.models.emp.leave.PendingLeaveDTO;
import com.sahil.models.emp.leave.RejectedLeaveDTO;
import com.sahil.models.emp.leave.TeamMateLeaveDTO;
import com.sahil.models.hr.report.ApprovedLeavesResponseDTO;
import com.sahil.models.input.Leave;
import com.sahil.models.manager.leave.UpdateLeaveRequestListDTO;
import com.sahil.models.manager.leave.UpdateLeaveResponseDTO;
import com.sahil.models.manager.report.ApprovedLeavesReportWrapper;
import com.sahil.models.request.LeaveRequestDTO;


public interface LeaveService {
	
	public Response addLeave(Leave leave,String empId); 
		
	public PageResponseDTO<PendingLeaveDTO> findAllPendingLeavesOfEmployee(LeaveRequestDTO requestDTO);

	public PageResponseDTO<ApprovedLeaveDTO> findAllApprovedLeavesOfEmployee(LeaveRequestDTO requestDTO);
	
	public PageResponseDTO<RejectedLeaveDTO> findAllRejectedLeavesOfEmployee(LeaveRequestDTO requestDTO);
	
	public PageResponseDTO<TeamMateLeaveDTO> findLeavesOfTeamMates(LeaveRequestDTO requestDTO);

	public PageResponseDTO<com.sahil.models.manager.leave.ApprovedLeaveDTO> findLeavesApprovedByManager(LeaveRequestDTO requestDTO);
	
	public PageResponseDTO<com.sahil.models.manager.leave.RejectedLeaveDTO> findLeavesRejectedByManager(LeaveRequestDTO requestDTO);
	
	public LeaveDetailsDeptWiseDayWise findLeaveDetailsBetween(Date fromDate, Date toDate);

	public UpdateLeaveResponseDTO findAllLeavesByManagerForUpdation(String id);

	public UpdateResponse updateLeaveDetails(String string, UpdateLeaveRequestListDTO updateLeavesDTOs);
	
	public ApprovedLeavesResponseDTO findLeavesForHRReportForCurrentMonth();
	
	public ApprovedLeavesReportWrapper findLeavesForManagersForCurrentWeek();

	public Response deleteLeave(int id);
}
