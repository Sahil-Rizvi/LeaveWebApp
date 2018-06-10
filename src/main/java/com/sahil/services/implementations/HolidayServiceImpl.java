package com.sahil.services.implementations;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sahil.constants.ConstantValues;
import com.sahil.entities.UploadedFileEntity;
import com.sahil.services.HolidayService;
import com.sahil.services.UploadFileService;
import com.sahil.utils.CSVUtil;
import com.sahil.utils.LeaveUtil;

@Service
public class HolidayServiceImpl implements HolidayService{

	@Autowired
	private UploadFileService uploadFileService;
	
	@Override
	public boolean isHoliday(Date d){
		 return checkHolidays(d);
	}
	
	private boolean checkHolidays(Date d){
		File file = new File(ConstantValues.HOLIDAY_DIRECTORY);
		
		if(file.exists()){
		    System.out.println("file exists");
		}
		if(file.isDirectory()){
			System.out.println(file + "is directory");
		}
		
		UploadedFileEntity uploadedFileEntity = uploadFileService.getHolidayFileForCurrentYear();
		if(uploadedFileEntity!=null){
			String filePath = ConstantValues.HOLIDAY_DIRECTORY+"/"+uploadedFileEntity.getFileName();
			
			System.out.println(filePath);
		
			String year = LeaveUtil.getYearFromFileName(filePath);
			String extension = LeaveUtil.getExtensionFromFileName(filePath);
			
			if(!StringUtils.isEmpty(year)){
				int Iyear = Integer.parseInt(year);
				Calendar fileCalendar = Calendar.getInstance();
				fileCalendar.setTime(uploadedFileEntity.getCreatedOn());
				
				Calendar todayCalendar = Calendar.getInstance();
				
				if(Iyear==fileCalendar.get(Calendar.YEAR) && fileCalendar.get(Calendar.YEAR)==todayCalendar.get(Calendar.YEAR)  && todayCalendar.get(Calendar.YEAR)==Iyear){
					if("csv".equals(extension)){
							
						try {
							HashMap<Date,String> hashMap = CSVUtil.CSVReader(filePath);
							for(Map.Entry<Date,String> me : hashMap.entrySet()){
								System.out.println("Day : "+me.getKey());
								System.out.println("Festival : "+me.getValue());
								if(d.getTime() == me.getKey().getTime()){
									System.out.println(me.getKey()+" is "+ me.getValue());
									System.out.println("Hence, a holiday .");
									
									return true;
								}
								
							}
						} catch (IOException | ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							System.out.println("exception"+e1.getMessage());
							return false;
						}
						
						
					}
					else{
						System.out.println("different extension:"+extension);
						return false;
					}
				}
				else{
					System.out.println("Years are different");
					return false;
				}
			}
			
			return false;

		}
		else{
			System.out.println("Holiday File not uploaded");
			return false;
		}
		
	}
	
}
