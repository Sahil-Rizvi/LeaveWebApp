package com.sahil.controllers;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sahil.models.Admin;
import com.sahil.models.LeaveCountForYearDTO;
import com.sahil.models.LeaveCounts;
import com.sahil.models.input.LeaveCountForYear;
import com.sahil.services.AdminService;
import com.sahil.services.LeaveCountForYearService;

@Controller
@RequestMapping("/leavecount")
public class LeaveCountForYearController {

	private Logger logger = LoggerFactory.getLogger(LeaveCountForYearController.class);
	
	@Autowired
	private LeaveCountForYearService leaveCountForYearService;
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping("/add")
	public String getAddLeaveCountForm(HttpServletRequest request,ModelMap map){
		if(checkAdmin(request)){
		   if(leaveCountForYearService.isLeaveCountAvailableForCurrentYear()){
			   map.addAttribute("available","yes");
		   }
		   return "admin/addleavecount";	
		}
		return null;
	}
	
	@RequestMapping("/update")
	public String getUpdateLeaveCountForm(@RequestParam String year,HttpServletRequest request,ModelMap map){
		String view = "admin/editleavecount";
		if(checkAdmin(request)){
			if(StringUtils.isEmpty(year)){
				map.addAttribute("message","Invalid year");			    	
			}
			else{
				LeaveCountForYearDTO leaveCountForYearDTO = leaveCountForYearService.getLeaveCountForYear(Integer.parseInt(year));
			    if(leaveCountForYearDTO.getCode()==1){
			    	map.addAttribute("message",leaveCountForYearDTO.getMessage());			    
			    }
				else{
			    	map.addAttribute("lc",leaveCountForYearDTO);
				}	
			}
		}
		else{
			map.addAttribute("message","Please log in ");			    	
		}
		return view;
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public String addLeaveCount(@ModelAttribute LeaveCountForYear lc,HttpServletRequest request){
		if(checkAdmin(request)){
		   return leaveCountForYearService.addLeaveCountForCurrentYear(lc).getMessage();	
		}
		return "Please log in to perform this action";
	}
	
	@RequestMapping("/get")
	public String getLeaveCount(@RequestParam(value="year",required=false)Integer year,HttpServletRequest request,ModelMap map){
		String view = "admin/getleavecount";
		if(checkAdmin(request)){
			if(year==null){
				year = Calendar.getInstance().get(Calendar.YEAR);
			}
			LeaveCountForYearDTO leaveCountForYearDTO = leaveCountForYearService.getLeaveCountForYear(year);
		    if(leaveCountForYearDTO.getCode()==1){
		    	map.addAttribute("message",leaveCountForYearDTO.getMessage());
		    }
		    else{
		    	map.addAttribute("lc",leaveCountForYearDTO);
		    	
		    }
		    return view;
		}
		return "Please log in to perform this action";
	}
	
	@RequestMapping("/getAll")
	public String getAllLeaveCount(HttpServletRequest request,ModelMap map){
		String view = "admin/getallleavecount";
		if(checkAdmin(request)){
			LeaveCounts leaveCounts = leaveCountForYearService.getAllLeaveCounts();
		    System.out.println(leaveCounts);
			if(leaveCounts.getCode()==1){
		    	map.addAttribute("message",leaveCounts.getMessage());
		    }
		    else{
		    	map.addAttribute("lc",leaveCounts.getLeaveCounts());
		    	
		    }
		    return view;
		}
		return "Please log in to perform this action";
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public String updateLeaveCount(@ModelAttribute LeaveCountForYearDTO leaveCountForYearDTO,HttpServletRequest request){
		if(checkAdmin(request)){
		   return leaveCountForYearService.updateLeaveCountForYear(leaveCountForYearDTO).getMessage();	
		}
		return "Please log in to perform this action";
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public String deleteLeaveCount(@RequestParam int year,HttpServletRequest request){
		if(checkAdmin(request)){
		   return leaveCountForYearService.deleteLeaveCountForYear(year).getMessage();
		}
		return "Please log in to perform this action";
	}
	
	
	private boolean checkAdmin(HttpServletRequest request){
		logger.info("in checkAdmin");
        if(request.getSession(false)!=null && request.getSession(false).getAttribute("admin")!=null){
        	Admin admin = (Admin)request.getSession(false).getAttribute("admin");
        	if(admin!=null){
        		
        		com.sahil.models.Admin admin2 = adminService.getAdmin(admin.getEmail());
        		if(admin2!=null){
        			logger.info("Admin found with emailId"+admin.getEmail()+" so returning true ");
        		}
        		else{
        			logger.info("Admin not found with emailId"+admin.getEmail()+"so returning false ");
        		}
        		return admin2!=null? true:false;
        	}
        }
		return false;
	}
	
}
