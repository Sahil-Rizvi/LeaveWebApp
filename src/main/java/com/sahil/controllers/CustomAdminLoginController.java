package com.sahil.controllers;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sahil.models.Admin;
import com.sahil.models.DeptList;
import com.sahil.models.DesigList;
import com.sahil.models.Response;
import com.sahil.services.AdminService;
import com.sahil.services.DepartmentService;
import com.sahil.services.DesignationService;
import com.sahil.services.LeaveCountForYearService;
import com.sahil.services.UploadFileService;

@Controller
@RequestMapping(value="/customAdmin")
public class CustomAdminLoginController {

	private static final Logger logger = Logger.getLogger(CustomAdminLoginController.class);
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private DesignationService designationService;
	
	@Autowired
	private UploadFileService uploadFileService;
	
	@Autowired
	private LeaveCountForYearService leaveCountForYearService;
	
    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String loginPage(@RequestParam(value="v",required=false) String v,Model model) {
    	   logger.info("in loginPage");
           return "login/admlogin";
    }
    
    
    
    @RequestMapping(value="/badUser") 
    public void badUser(@RequestParam(value="lang")String lang){
       System.out.println("bad USer lang"+lang);    	
    }
    


	@RequestMapping(value="/process_admlogin",method=RequestMethod.POST)
	public String processAdmLogin(@RequestParam(value="username",required=false) String email,@RequestParam(value="password",required=false) String password,HttpServletRequest request,Model model){
		logger.info("in processAdmLogin with email: "+email+", password: "+password);
		Response response = adminService.validate(email, password);
		logger.info("Response from validate :"+response);
		System.out.println(response.getMessage());
		if(response.getCode()==1){
			model.addAttribute("message",response.getMessage());
			logger.info("redirecting to login");
			return "redirect:login";
		}
		
		com.sahil.models.Admin admin = adminService.getAdmin(email);
		request.getSession(true).setAttribute("admin",admin);
		
		logger.info("setting sessionAttribute admin with value"+admin);
		System.out.println(request.getSession(false).getAttribute("admin"));
		logger.info("redirecting to adminhome");
		return "redirect:adminhome";
	}
	
	
	@RequestMapping(value="/adminhome")
	public String getAdminHome(HttpServletRequest request,HttpServletResponse response,ModelMap map){
		logger.info("in getAdminHome");
		if(request.getSession(false)==null){
			System.out.println("No session ");
			return "redirect:login";
		}
		Admin admin = (Admin)request.getSession(false).getAttribute("admin");
    	if(Objects.isNull(admin)){
    		System.out.println("admin not in session");
    		return "redirect:login";
    	}
    	
		if(!uploadFileService.isHolidayFileUploadedForCurrentYear()){
			map.addAttribute("holidayMessage","Upload holidays file");
		}
		if(!departmentService.anyDepartmentAdded()){
			map.addAttribute("departmentMessage","Add departments");
		}
		if(!designationService.anyDesignationAdded()){
			map.addAttribute("designationMessage","Add designations");
		}
		if(!leaveCountForYearService.isLeaveCountAvailableForCurrentYear()){
			map.addAttribute("leaveCountMessage","Add leave count");
		}
		
		System.out.println("here in hrhome");
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    	response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    	response.setHeader("Expires", "0"); // Proxies.
    	
    	System.out.println(admin);
		return "hr/hrhome";
	}
	
	@RequestMapping(value="/dept/add")
	public String getFormToAddDepartment(HttpServletRequest request){
		logger.info("in getFormToAddDepartment");
		return "hr/dept/addform";
	}

	@RequestMapping(value="/dept/add",method=RequestMethod.POST)
	@ResponseBody
	public String addDepartments(@RequestParam(value="dept[]")List<String> departments,HttpServletRequest request){
	    logger.info("in addDepartments with list"+departments);
		if(checkAdmin(request)){
	    	return departmentService.addDepartments(departments).getMessage();	
		}
		logger.error("Not permitted");
	    return "Permitted only after login.";
	}
	
	@RequestMapping(value="/dept/view")
	public String viewDepartments(ModelMap modelMap){
		logger.info("in viewDepartments");
		DeptList deptList = departmentService.getAllDepartments();
		logger.info("deptList"+deptList);
		if(deptList.getCode()==0){
			modelMap.addAttribute("dept",deptList.getDepartments());
		}
		else{
			modelMap.addAttribute("message",deptList.getMessage());
		}
		return "hr/dept/viewAllDept";
	}
	
	@RequestMapping(value="/dept/edit",method=RequestMethod.POST)
	@ResponseBody
	public String editDepartment(@RequestParam(value="id",required=false)int id,@RequestParam(value="oldDepartment",required=false) String oldDepartment,@RequestParam(value="newDepartment",required=false)String newDepartment,HttpServletRequest request){		
		logger.info("in edirDepartment with id: "+id+"oldDepartment: "+oldDepartment+"newDepartment: "+newDepartment);
		if(checkAdmin(request)){
			return departmentService.editDepartment(id, oldDepartment, newDepartment).getMessage();
		}
		logger.info("Not permitted");
		return "Permitted only after login.";
	}
	
	
	@RequestMapping(value="/dept/delete",method=RequestMethod.POST)
	@ResponseBody
	public String deleteDepartment(@RequestParam(value="id",required=false)int id,@RequestParam(value="department",required=false) String department,HttpServletRequest request){
		logger.info("in deleteDepartment with id: "+id+"department: "+department);
		if(checkAdmin(request)){
			return departmentService.deleteDepartment(id,department).getMessage();
		}
		logger.info("Not permitted");
		return "Permitted only after login.";
	}
	

	@RequestMapping(value="/desig/add")
	public String getFormToAddDesignation(HttpServletRequest request){
	    logger.info("in getFormToAddDesignation");
		return "hr/desig/addform";
	}

	@RequestMapping(value="/desig/add",method=RequestMethod.POST)
	@ResponseBody
	public String addDesignations(@RequestParam(value="desig[]")List<String> designations,HttpServletRequest request){
	    logger.info("in addDesignations with designations: "+designations);
		if(checkAdmin(request)){
	    	return designationService.addDesignations(designations).getMessage();
	    }
		logger.info("Not permitted");
	    return "Permitted only after login.";
	}

	@RequestMapping(value="/desig/view")
	public String viewDesignations(ModelMap modelMap){
		logger.info("in viewDesignations");
		DesigList desigList =  designationService.getAllDesignations();
		logger.info("desigList: "+desigList);
		if(desigList.getCode()==0){
			modelMap.addAttribute("desig",desigList.getDesignations());
		}
		else{
			modelMap.addAttribute("message",desigList.getMessage());
		}
		return "hr/desig/viewAllDesig";
	}
	
	@RequestMapping(value="/desig/edit",method=RequestMethod.POST)
	@ResponseBody
	public String editDesignation(@RequestParam(value="id",required=false)int id,@RequestParam(value="oldDesignation",required=false) String oldDesignation,@RequestParam(value="newDesignation",required=false)String newDesignation,HttpServletRequest request){
		logger.info("in editDesignation with id: "+id+"oldDesignation: "+oldDesignation+" newDesignation: "+newDesignation);
		if(checkAdmin(request)){
			return designationService.editDesignation(id, oldDesignation, newDesignation).getMessage();
		}
		logger.info("Not permitted");
		return "Permitted only after login.";
	}
	
	@RequestMapping(value="/desig/delete",method=RequestMethod.POST)
	@ResponseBody
	public String deleteDesignation(@RequestParam(value="id",required=false)int id,@RequestParam(value="designation",required=false) String designation,HttpServletRequest request){
		logger.info("in deleteDesignation with id :"+id+", designation:"+designation);
		if(checkAdmin(request)){
			return designationService.deleteDesignation(id,designation).getMessage();
		}
		logger.info("Not permitted");
		return "Permitted only after login.";
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

	
    @RequestMapping(value="/logout")
    public String logout(HttpServletRequest request){
    	logger.info("in logout");
    	if(checkAdmin(request)){
    		HttpSession httpSession = request.getSession(false);
    		logger.info("invalidating session");
    		httpSession.invalidate();
    	}
    	logger.info("redirecting to login");
    	return "redirect:login";
    }
    
}
