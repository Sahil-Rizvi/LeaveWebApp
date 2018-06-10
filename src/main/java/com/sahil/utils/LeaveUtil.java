package com.sahil.utils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.util.StringUtils;



public class LeaveUtil {


	private static String[] months = {"JANUARY","FEBRUARY","MARCH","APRIL","MAY","JUNE",
			"JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER"};
	
	
	public static String getMonth(int month){
		if(month<0 || month>11){
			throw new IllegalArgumentException();
		}
		return months[month];
	}
	
	
	public static Date getTodaysDate(){
		return setTimeToZero(new Date());
	}
	
	public static long subtractDates(Date one,Date two){
		setTimeToZero(one);
        setTimeToZero(two);
        long diffDates = -1; // if two > one 
        if(one.getTime()>=two.getTime()){
        	long difference = one.getTime() - two.getTime();
        	diffDates = (difference/(24*60*60*1000));
        	System.out.println("diffDates "+diffDates);
        }
        return diffDates;
	}
	
	public static Date addDays(Date date,int day){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_YEAR,day);
		return c.getTime();
	}
	
	public static long subtractDatesIncludingBoth(Date one,Date two){
		setTimeToZero(one);
        setTimeToZero(two);
        long diffDates = -1; // if two > one 
        if(one.getTime()>=two.getTime()){
        	long difference = one.getTime() - two.getTime();
        	diffDates = (difference/(24*60*60*1000));
        	System.out.println("diffDates "+diffDates);
        }
        System.out.println(diffDates);
        return diffDates+1;
	}
	
	
	public static boolean isAfterStrictlyWithinYear(Date one,Date two){
        setTimeToZero(one);
        setTimeToZero(two);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(one);
        
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(two);
        
        int day1 = calendar1.get(Calendar.DAY_OF_MONTH);
        int month1 = calendar1.get(Calendar.MONTH);
        //int year1 = calendar1.get(Calendar.YEAR);
        
        int day2 = calendar2.get(Calendar.DAY_OF_MONTH);
        int month2 = calendar2.get(Calendar.MONTH);
        //int year2 = calendar2.get(Calendar.YEAR);
        
        if(month1>month2){
        		return true;
        }
        if(month1==month2){
        	if(day1>day2){
        			return true;
        	}
        }
        return false;
	}
	
	public static boolean isAfterWithinYear(Date one,Date two){
		setTimeToZero(one);
        setTimeToZero(two);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(one);
        
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(two);
        
        int day1 = calendar1.get(Calendar.DAY_OF_MONTH);
        int month1 = calendar1.get(Calendar.MONTH);
        //int year1 = calendar1.get(Calendar.YEAR);
        
        int day2 = calendar2.get(Calendar.DAY_OF_MONTH);
        int month2 = calendar2.get(Calendar.MONTH);
        //int year2 = calendar2.get(Calendar.YEAR);
        
        if(month1>month2){
        		return true;
        }
        if(month1==month2){
        	if(day1>=day2){
        			return true;
        	}
        }
        return false;
	}
	
	
	
	
	public static Date setTimeToZero(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND,0);
		return calendar.getTime();
	}
	
	/*
	public static Date getLastAnnualDateWithTimeZero(){
		Date date = setTimeToZero(new Date());
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE,Calendar.);
		return calendar.getTime();
	}
	*/
	
	public static Date convertStringToDateInParticularFormat(String date){
		try {
			return new SimpleDateFormat("dd/MM/yyyy").parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static String convertDateToStringInParticularFormat(Date date){
		return date!=null ? new SimpleDateFormat("dd/MM/yyyy").format(date) : "";
	}
	
	public static boolean isWeekend(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
			return true;
		}
		return false;
	}
	
	
	private static double round(double value, int places){
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	public static double roundingOffToTwoPlaces(double value){
		   try{
			   return round(value,2);   
		   }
		   catch(IllegalArgumentException e){
			   System.out.println(e);
		   }
		   return 0;
	}
	
	
	public static String toTitleCase(String string){
		if(string!=null && !string.isEmpty()){
			String dummy = string.trim();
			return Character.toUpperCase(dummy.charAt(0)) + dummy.substring(1).toLowerCase();
		}
		return "";
	}
			
	
	
	
	public static boolean isWeekend(Calendar c){
		return c.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || c.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY;
	}
	
	
	
	public static Date getFirstDateOfYear(){
		Calendar c = Calendar.getInstance();
		c.setTime(getTodaysDate());
		c.set(Calendar.DAY_OF_YEAR,1);
		return c.getTime();
	}
	
	public static Date getLastDateOfYear(){
		Calendar c = Calendar.getInstance();
		c.setTime(getTodaysDate());
		c.add(Calendar.YEAR,1);
		c.set(Calendar.DAY_OF_YEAR,0);
		return c.getTime();
	}
	
	public static Date getFirstDateOfMonth(){
		Calendar c = Calendar.getInstance();
		c.setTime(getTodaysDate());
		c.set(Calendar.DAY_OF_MONTH,1);
		return c.getTime();
	}
	
	public static Date getLastDateOfMonth(){
		Calendar c = Calendar.getInstance();
		c.setTime(getTodaysDate());
		c.add(Calendar.MONTH,1);
		c.set(Calendar.DAY_OF_MONTH,0);
		return c.getTime();
	}
	
	
	public static Date getFirstDateOfPreviousMonth(){
		Calendar c = Calendar.getInstance();
		c.setTime(getTodaysDate());
		c.add(Calendar.MONTH,-1);
		c.set(Calendar.DAY_OF_MONTH,1);
		return c.getTime();
	}
	
	public static Date getLastDateOfPreviousMonth(){
		Calendar c = Calendar.getInstance();
		c.setTime(getTodaysDate());
		c.add(Calendar.MONTH,1);
		c.add(Calendar.MONTH,-1);
		c.set(Calendar.DAY_OF_MONTH,0);
		return c.getTime();
	}
	
	public static Date getFirstDateOfWeek(){
		Calendar c = Calendar.getInstance();
		c.setTime(getTodaysDate());
		c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
		return c.getTime();
	}
	
	public static Date getLastDateOfWeek(){
		Calendar c = Calendar.getInstance();
		c.setTime(getTodaysDate());
		c.add(Calendar.WEEK_OF_MONTH,1);
		c.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
		return c.getTime();
	}
	
	public static Date getFirstDateOfPreviousWeek(){
		Calendar c = Calendar.getInstance();
		c.setTime(getTodaysDate());
		c.add(Calendar.DAY_OF_MONTH,-7);
		c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
		return c.getTime();
	}
	
	public static Date getLastDateOfPreviousWeek(){
		Calendar c = Calendar.getInstance();
		c.setTime(getTodaysDate());
		c.add(Calendar.WEEK_OF_MONTH,1);
		c.add(Calendar.DAY_OF_MONTH,-7);
		c.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
		return c.getTime();
	}
	
    public static String getLastPartFromFileName(String fileNameWithYear){
		if(!StringUtils.isEmpty(fileNameWithYear)){
			int pipe = fileNameWithYear.lastIndexOf('|');
			if(pipe!=-1){
				return fileNameWithYear.substring(pipe+1);
			}
		}
		return null;
	}
	
	public static String getYearFromFileName(String fileNameWithYear){
		if(!StringUtils.isEmpty(fileNameWithYear)){
			int pipe = fileNameWithYear.lastIndexOf('|');
			int dot = fileNameWithYear.lastIndexOf('.');
			if(pipe!=-1 && dot!=-1){
				return fileNameWithYear.substring(pipe+1,dot);
			}
			if(pipe!=-1){
				return fileNameWithYear.substring(pipe+1);
			}
		}
		return null;
	}
	
	public static String getExtensionFromFileName(String fileNameWithYear){
		if(!StringUtils.isEmpty(fileNameWithYear)){
			int dot = fileNameWithYear.lastIndexOf('.');
			if(dot!=-1){
				return fileNameWithYear.substring(dot+1);
			}
		}
		return null;
	}
	
	
	/*
	public static Date getFirstDateOfMonth(){
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(LeaveUtil.getTodaysDate());
	    int firstDate = calendar.getActualMinimum(Calendar.DATE);
	    calendar.set(Calendar.DATE,firstDate);
	    return calendar.getTime();
	}
	
	public static Date getLastDateOfMonth(){
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(LeaveUtil.getTodaysDate());
	    int lastDate = calendar.getActualMaximum(Calendar.DATE);
	    calendar.set(Calendar.DATE,lastDate);
	    return calendar.getTime();
	}
	*/
	
	public static boolean isSameDay(Date one,Date two){ 
		if(one==null || two==null){
			return false;
		}
		setTimeToZero(one);
		setTimeToZero(two);
		Calendar c1 = Calendar.getInstance();
		c1.setTime(one);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(two);
		return c1.getTimeInMillis()==c2.getTimeInMillis();
			
	}
	
	public static boolean isWithinRangeInclusive(Date testDate,Date d1,Date d2){
		return (!testDate.before(d1) && !testDate.after(d2));
	}
	
	
	public static void main(String[] args) throws ParseException{
		
		/*
		String sDate1="2018-3-1";  
	    Date d1=new SimpleDateFormat("yyyy-MM-dd").parse(sDate1);  
	    System.out.println(d1);
	    
	    
	    String sDate2="2018-3-5";  
	    Date d2=new SimpleDateFormat("yyyy-MM-dd").parse(sDate2);  	
	    System.out.println(d2);

		Date d1 = getTodaysDate();
		System.out.println(d1);
		System.out.println(LeaveUtil.addDays(d1, 30));
	    System.out.println(LeaveUtil.addDays(d1,-30));
	    
	    String sDate2="2018-4-17";  
	    Date d2=new SimpleDateFormat("yyyy-MM-dd").parse(sDate2);  	
	    System.out.println(d2);
	    
	    System.out.println(getFirstDateOfMonth());
	    System.out.println(getLastDateOfMonth());
	    System.out.println(getFirstDateOfWeek());
	    System.out.println(getLastDateOfWeek());
	    System.out.println(getFirstDateOfYear());
	    System.out.println(getLastDateOfYear());
	    System.out.println(getFirstDateOfPreviousWeek());
	    System.out.println(getLastDateOfPreviousWeek());
	    System.out.println(!d2.after(d1));
	    System.out.println(getFirstDateOfPreviousMonth());
	    System.out.println(getLastDateOfPreviousMonth());
	    
	    Calendar c = Calendar.getInstance();
	    c.add(Calendar.DAY_OF_YEAR,1);
	    
	    long a = c.getTime().getTime() - Calendar.getInstance().getTime().getTime();
	    System.out.println(a);
	    
	    String fileNameWithYear = "holidayname.csv";
	    
		String lastPart = getLastPartFromFileName(fileNameWithYear);
		String year = getYearFromFileName(fileNameWithYear);
		String ext = getExtensionFromFileName(fileNameWithYear);
		
	    System.out.println(lastPart);
		System.out.println(year);
		System.out.println(ext);
		
		File file = new File("");
		System.out.println(file.exists());
		System.out.println(file.isDirectory());
		System.out.println(file.isFile());
		System.out.println(file.getName());
		
		System.out.println(file.getAbsolutePath());
		System.out.println(file.getPath());
		
		try {
			System.out.println(file.getCanonicalPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(file.getParent());
		*/
		
		
		Date today = LeaveUtil.getTodaysDate();
		Date twoDaysEarlier = LeaveUtil.addDays(today,-2);
		Date fourDaysEarlier = LeaveUtil.addDays(today,-3);
		System.out.println(today);
		System.out.println(twoDaysEarlier);
		System.out.println(fourDaysEarlier);
		
		Date d1 = LeaveUtil.addDays(today,-1);
		Date d2 = LeaveUtil.addDays(today,1);
		Date d3 = LeaveUtil.addDays(today,-2);
		Date d4 = LeaveUtil.addDays(today,-3);
		Date d5 = LeaveUtil.addDays(today,-4);
		Date d6 = LeaveUtil.addDays(today,-5);
		
		Date[] dates = {d1,d2,d3,d4,d5,d6};
		
		
		for(Date d:dates){
			System.out.println(d);
			if(!d.before(fourDaysEarlier) && !d.after(twoDaysEarlier)){
				System.out.println("ddd ->"+d);
			}
		}
		
		
		
		
		
		System.out.println(LeaveUtil.convertDateToStringInParticularFormat(today));
		System.out.println(LeaveUtil.convertDateToStringInParticularFormat(twoDaysEarlier));
		System.out.println(LeaveUtil.convertDateToStringInParticularFormat(fourDaysEarlier));
		
		
	}
	
	
	
	
	
	
}
