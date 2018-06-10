package com.sahil.services;

import com.sahil.models.CountResponse;
import com.sahil.models.LeaveCountForYearDTO;
import com.sahil.models.LeaveCounts;
import com.sahil.models.Response;
import com.sahil.models.input.LeaveCountForYear;

public interface LeaveCountForYearService {
   
	public Response addLeaveCountForCurrentYear(LeaveCountForYear lc);
	public LeaveCountForYearDTO getLeaveCountForYear(int year);
	public Response updateLeaveCountForYear(LeaveCountForYearDTO leaveCountForYearDTO);
	public Response deleteLeaveCountForYear(int year);
	public LeaveCounts getAllLeaveCounts();
    public CountResponse getBDayCountForYear(int year);
    public CountResponse getCasualCountForYear(int year);
    public CountResponse getPrivilegedCountForYear(int year);
    public CountResponse getSickCountForYear(int year);
    public CountResponse getAllCountsForYear(int year);
	public boolean isLeaveCountAvailableForCurrentYear();
}
