package com.sahil.services;

import java.util.Date;
import java.util.List;

import com.sahil.models.Event;

public interface MyCalendarService {

	public List<Event> populateCalendarData(Date fromDate, Date toDate);

}
