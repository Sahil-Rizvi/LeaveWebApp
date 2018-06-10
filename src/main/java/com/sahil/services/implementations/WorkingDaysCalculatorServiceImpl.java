package com.sahil.services.implementations;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sahil.services.HolidayService;
import com.sahil.services.WorkingDaysCalculatorService;
import com.sahil.utils.LeaveUtil;

@Service
public class WorkingDaysCalculatorServiceImpl implements WorkingDaysCalculatorService{
	
	
	@Autowired
	private HolidayService holidayService;

	@Override
	public int getWorkingDaysBetweenTwoDatesIncludingBothDaysIncludingHolidays(Date d1,Date d2){

		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);

		if(c1.getTimeInMillis()>c2.getTimeInMillis()){
			c1.setTime(d2);
			c2.setTime(d1);
		}

		int workingDays = 0;
		
		while(c1.getTimeInMillis()<=c2.getTimeInMillis()){
			if(!LeaveUtil.isWeekend(c1) && !holidayService.isHoliday(c1.getTime())){
				workingDays++;
			}
			c1.add(Calendar.DAY_OF_MONTH,1);
		}

		return workingDays;
	}


	@Override
	public int getWorkingDaysBetweenTwoDatesIncludingBothDaysExcludingHolidays(Date d1,Date d2){

		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);

		if(c1.getTimeInMillis()>c2.getTimeInMillis()){
			c1.setTime(d2);
			c2.setTime(d1);
		}

		int workingDays = 0;
		
		while(c1.getTimeInMillis()<=c2.getTimeInMillis()){
			if(!LeaveUtil.isWeekend(c1)){
				workingDays++;
			}
			c1.add(Calendar.DAY_OF_MONTH,1);
		}

		return workingDays;
	}





}
