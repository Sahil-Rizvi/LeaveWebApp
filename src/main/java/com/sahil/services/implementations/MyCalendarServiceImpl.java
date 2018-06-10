package com.sahil.services.implementations;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sahil.models.EmployeeDTO;
import com.sahil.models.Event;
import com.sahil.models.LeaveDetailsDeptWiseDayWise;
import com.sahil.services.LeaveService;
import com.sahil.services.MyCalendarService;
import com.sahil.utils.LeaveUtil;

@Service
public class MyCalendarServiceImpl implements MyCalendarService{

	@Autowired
	private LeaveService leaveService;

	@Override
	public List<Event> populateCalendarData(Date fromDate,Date toDate){
		
		List<Event> list = new ArrayList<>();
		LeaveDetailsDeptWiseDayWise data = leaveService.findLeaveDetailsBetween(fromDate, toDate);
		System.out.println(data);
		for(Map.Entry<Date,HashMap<String, Set<EmployeeDTO>>> me: data.getHashMap().entrySet()){
			Event event = new Event();
			event.setId(me.getKey().toString());
			System.out.println(me.getKey());
			event.setStart(LeaveUtil.addDays(me.getKey(),1));
			event.setEnd(LeaveUtil.addDays(me.getKey(),1));
			event.setAllDay(true);
			StringBuilder sb = new StringBuilder();
			for(Map.Entry<String,Set<EmployeeDTO>> m : me.getValue().entrySet()){
				sb.append(m.getKey()+" "+me.getValue().size() + "\n");
			}
			event.setTitle(sb.toString());
			list.add(event);
		}
		System.out.println(list);
		return list;
	}
	
}
