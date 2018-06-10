package com.sahil.services;

import java.util.Date;

public interface WorkingDaysCalculatorService {

	public int getWorkingDaysBetweenTwoDatesIncludingBothDaysIncludingHolidays(Date d1,Date d2);
	public int getWorkingDaysBetweenTwoDatesIncludingBothDaysExcludingHolidays(Date d1,Date d2);

}
