package com.sahil.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sahil.enums.FileType;
import com.sahil.models.HolidayResponse;
import com.sahil.services.UploadFileService;

@Controller
@RequestMapping(value="/tab")
public class UpperTabController {
	
	@Autowired
	private UploadFileService uploadFileService;
	
	@RequestMapping(value="/holiday")
	public String getHolidayDataForCurrentYear(ModelMap map){	
		HolidayResponse holidayResponse = uploadFileService.getHolidayDataForCurrentYear();
	    System.out.println(holidayResponse);
		if(holidayResponse.getCode()==0){
	    	map.addAttribute("holidays",holidayResponse.getMap());
	    	return "tab/holidays";
	    }
	    return null;
	}
	
	@RequestMapping(value="/files")
	public String getFileList(ModelMap map){	
		System.out.println("here in files in tab controller");
		String view = "tab/listfiles";
		map.addAttribute("leavepolicyfiles",uploadFileService.getFiles(FileType.LEAVE_POLICY));
		map.addAttribute("noticefiles",uploadFileService.getFiles(FileType.NOTICE));
		map.addAttribute("otherfiles",uploadFileService.getFiles(FileType.OTHERS));
	    return view;
	}
	
	@RequestMapping(value="/about")
	public String getAboutFile(ModelMap map){	
		System.out.println("here in about in tab controller");
		String view = "tab/aboutus";
		return view;
	}
	
	@RequestMapping(value="/contact")
	public String getContactFile(ModelMap map){	
		System.out.println("here in contact in tab controller");
		String view = "tab/contactus";
		return view;
	}
	
}
