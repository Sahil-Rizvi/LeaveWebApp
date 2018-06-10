package com.sahil.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sahil.models.Admin;
import com.sahil.models.PageResponseDTO;
import com.sahil.models.Response;
import com.sahil.models.admin.UpdateAdminDTO;
import com.sahil.models.hr.EmployeeDTO;
import com.sahil.models.hr.EmployeeForListing;
import com.sahil.models.hr.ListOfEmployeeForRights;
import com.sahil.models.hr.UpdateEmployeeRights;
import com.sahil.models.hr.UpdateEmployeeRightsList;
import com.sahil.models.hr.emp.UpdateEmployeeDTO;
import com.sahil.models.input.ResetPasswordDTO;
import com.sahil.services.AdminService;
import com.sahil.services.DepartmentService;
import com.sahil.services.DesignationService;
import com.sahil.services.HRService;
import com.sahil.services.LeaveService;
import com.sahil.services.UploadFormService;

@Controller
@RequestMapping("/hr")
public class HRController {

	private Logger logger = LoggerFactory.getLogger(HRController.class);
	
	@Autowired
	private HRService hRService;
	
	@Autowired
	private LeaveService leaveService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private DesignationService designationService;
	
	@Autowired
	private UploadFormService uploadFileService;
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping(value="/home")
	public String getHRHome(ModelMap map){
		if(!uploadFileService.isHolidayFileUploaded()){
			map.addAttribute("holidayMessage","Upload holidays file");
		}
		if(!departmentService.anyDepartmentAdded()){
			map.addAttribute("departmentMessage","Add departments");
		}
		if(!designationService.anyDesignationAdded()){
			map.addAttribute("desigationMessage","Add designations");
		}
		return "hrhome";
	}
	
	
	@RequestMapping(value="/uploadForm")
	public String getUploadForm(ModelMap modelMap){
		String uploadForm="fileuploadform";
		String name = uploadFileService.getUploadedFileNameOfHolidayType();
		if(name.equals("")){
			modelMap.addAttribute("status","Please upload the holidays file");
		}
		else{
			modelMap.addAttribute("status","Holidays file is already uploaded. <br> Download it to update if needed.");
		}
		return uploadForm;
	}
	
	
	@RequestMapping(value="/empToUpdateManagerStatus")
	public String getEmployeesToUpdateManagerStatus(HttpServletRequest httpServletRequest,ModelMap map){
		String jsp = "hr/updateManagerStatus";
		System.out.println("here");
		ListOfEmployeeForRights listOfEmployeeForRights = hRService.findEmployeesToUpdateManagerStatus();
		if(listOfEmployeeForRights.getCode()==0){
			map.addAttribute("employees",listOfEmployeeForRights);
		}
		System.out.println("Code"+listOfEmployeeForRights.getCode());
		System.out.println("Message"+listOfEmployeeForRights.getMessage());
		System.out.println("List"+listOfEmployeeForRights.getEmployees());
		return jsp;
	}
	
	@RequestMapping(value="/updateManagerStatus")
	@ResponseBody
	public String updateEmployeeRights(@ModelAttribute UpdateEmployeeRightsList employees,HttpServletRequest request){
		for(UpdateEmployeeRights e : employees.getEmployees()){
			System.out.println("in"+e.toString());
		}
		if(checkAdmin(request)){
			return hRService.updateEmployeeManagerStatus(employees).getMessage();
		}
		return "Permitted only after login";
	}
	
	@RequestMapping(value="/findByEmpCode")
	public String getEmployeeByEmpCode(@RequestParam(value="id",required=false)String id,ModelMap map){
		String jsp="hr/empdetails";
		EmployeeDTO employeeDTO = hRService.findEmployeeByEmployeeCode(id);
	    if(employeeDTO.getCode()==1){
	    	map.addAttribute("message",employeeDTO.getMessage());
	    }
	    else{
	    	map.addAttribute("employee",employeeDTO);
	    }
	    System.out.println("code"+employeeDTO.getCode());
	    System.out.println("message"+employeeDTO.getMessage());
	    System.out.println("employee"+employeeDTO.toString());
	    return jsp;
	}
	
	@RequestMapping(value="/form/{option}")
	public String getForm(@PathVariable("option") String option,ModelMap modelMap){
		String jsp = "";
		if(option.contains("empByCode")){
			jsp = "hr/getEmpByCodeForm";
		}
		else
		if(option.contains("empByDept")){
			jsp = "hr/getEmpByDeptForm";
			if(departmentService.getAllDepartments().getCode()==1){
				modelMap.addAttribute("message",departmentService.getAllDepartments().getMessage());
			}
			else{
				modelMap.addAttribute("dept",departmentService.getAllDepartments().getDepartments());
			}
			
		}
		return jsp;
	}
	
	@RequestMapping(value="/findByEmpDepartment/{pageNumber}")
	public String getEmployeeByDepartment(@PathVariable int pageNumber,@RequestParam(value="id",required=false)Integer id,@RequestParam(value="dept",required=false)String dept,ModelMap model){
		String leaves = "hr/empByDeptList";
		System.out.println("id"+id);
		System.out.println("dept"+dept);
    	if(id==null || StringUtils.isEmpty(dept)){
    		model.addAttribute("message","Please enter valid department");
    	}
    	else{
    		PageResponseDTO<EmployeeForListing> pageResponseDTO = hRService.findEmployeesByDepartment(id.intValue(),dept, pageNumber);
        	System.out.println(pageResponseDTO);
    		if(pageResponseDTO.getCode()!=1){    		
        		System.out.println("in findByEmpDepartment/"+pageNumber);
        		if(!StringUtils.isEmpty(pageResponseDTO.getData()) && !CollectionUtils.isEmpty(pageResponseDTO.getData().getContent())  ){
        			setParam(model, pageResponseDTO);
        			System.out.println(pageResponseDTO.getData().getContent());
        		}
        		else{
        			model.addAttribute("message",pageResponseDTO.getMessage());	
        		}
        	}
        	else{
        		System.out.println(pageResponseDTO.getMessage());	
    		}
    	}
	return leaves;
	}
	
	/*
	@RequestMapping(value="/genReport")
	public void generateReport(){
		ApprovedLeavesResponseDTO approvedLeavesResponseDTO = leaveService.findLeavesForHRReportForCurrentMonth();
		System.out.println(approvedLeavesResponseDTO.getCode());
		System.out.println(approvedLeavesResponseDTO.getMessage());
		for(ApprovedLeaves leaves:approvedLeavesResponseDTO.getApprovedLeaves()){
			System.out.println(leaves.getEmployee().getEmpDepartment());
			System.out.println(leaves);
		}
		
		if(!approvedLeavesResponseDTO.getApprovedLeaves().isEmpty()){
			try {
				reportGenerator.generateAnnualReportHR(approvedLeavesResponseDTO.getApprovedLeaves());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("exception:"+e);
			}
		}
	}
	*/
	
	@RequestMapping(value="/genHRReport")
	public void generateHRReport(){
		leaveService.findLeavesForHRReportForCurrentMonth();
	}
	
	@RequestMapping(value="/genManagerReport")
	public void generateManagerReport(){
		leaveService.findLeavesForManagersForCurrentWeek();
	}
	
	
	
	@RequestMapping(value="/updateEmployee")
	public String getUpdateEmployeeForm(@RequestParam("id")String id,HttpServletRequest request,ModelMap modelMap){
		String form="hr/updateempform";
		System.out.println("getting update form for hr");
	    logger.info("in getUpdateEmployeeForm");
	    if(checkAdmin(request)){
	    	logger.info("in session");
	    	UpdateEmployeeDTO dto = hRService.getEmployeeForUpdation(id);
	    	System.out.println(dto);
			modelMap.addAttribute("emp",dto);
			modelMap.addAttribute("departmentTypes",departmentService.getAllDepartmentNames());
			modelMap.addAttribute("designationTypes",designationService.getAllDesignationNames());
	    }
	    return form;
	}
	
	

	@RequestMapping(value="/deleteEmp",method=RequestMethod.POST)
	@ResponseBody
	public String deleteEmployee(@RequestParam(value="id",required=false)String id,HttpServletRequest request){
		logger.info("in deleteEmployee with id :"+id);
		if(checkAdmin(request)){
			Response r = hRService.deleteEmployee(id);
			System.out.println(r);
			if(r.getCode()==0){
				Response r1 = hRService.deleteEmployee1(id);
				System.out.println(r1);
				if(r1.getCode()==0){
					return r1.getMessage();
				}
				else{
					r1.getMessage();
				}
			}
			return r.getMessage();
		}
		logger.info("Not permitted");
		return "Permitted only after login.";
	}
	
	
	@RequestMapping(value="/deleteAdmin")
	public String getDeleteAdminForm(HttpServletRequest request,ModelMap modelMap){
		String form="hr/deleteadmform";
		System.out.println("getting delete form for hr");
	    logger.info("in getDeleteAdminForm");
	    if(checkAdmin(request)){
	    	logger.info("in session");
		    return form;
	    }
	    return null;
	}
	
	
	
	@RequestMapping(value="/deleteAdmin",method=RequestMethod.POST)
	@ResponseBody
	public String deleteAdmin(@RequestParam(value="emailId",required=false)String emailId,@RequestParam(value="password",required=false)String password,HttpServletRequest request){
		logger.info("in deleteEmployee with id :"+emailId);
		if(checkAdmin(request)){
			Response response = hRService.deleteAdmin(emailId, password);
			if(response.getCode()==1){
				return response.getMessage();
			}
			HttpSession session = request.getSession(false);
			session.invalidate();
			return response.getMessage();
		}
		logger.info("Not permitted");
		return "Permitted only after login.";
	}

	
	
	@RequestMapping(value="/updateEmployee",method=RequestMethod.POST)
	@ResponseBody
	public String updateEmployee(@ModelAttribute("employee") UpdateEmployeeDTO employee,BindingResult bindingResult,HttpServletRequest request, ModelMap modelMap){
		logger.info("in updateEmployee with employee : "+employee);
		if(bindingResult.hasErrors()){
			logger.error("BindingResult errors "+bindingResult.getAllErrors());
			System.out.println(bindingResult.getAllErrors());
			return "Not Updated";
		}
		if(checkAdmin(request)){
			Response response = hRService.updateEmployee(employee);
			logger.info("Response "+response);
			if(response.getCode()==0){
				  return response.getMessage();
			   }
			 else{
				    logger.info("Not updated "+response.getMessage());
			    	return "Not Updated "+response.getMessage();
			 }
		}
		logger.info("Not permitted");
		return "Permitted only after login.";
	}

	

	
	@RequestMapping(value="/updateAdmin")
	public String getUpdateAdminForm(HttpServletRequest request,ModelMap modelMap){
		String form="hr/updateadminform";
		System.out.println("getting update form for admin");
	    logger.info("in getUpdateAdminForm");
	    Admin admin = (Admin)request.getSession(false).getAttribute("admin");
    	System.out.println(admin);
		
	    if(checkAdmin(request)){
	    	logger.info("in session");
	    	modelMap.addAttribute("adm",hRService.getAdminForUpdation(admin.getEmail()));
	    }
	    return form;
	}

	
	@RequestMapping(value="/updateAdmin",method=RequestMethod.POST)
	@ResponseBody
	public String updateAdmin(@ModelAttribute UpdateAdminDTO admin,BindingResult bindingResult,HttpServletRequest request, ModelMap modelMap){
		//updateEmployeeValidator.validate(employee,bindingResult);
		logger.info("in updateAdmin with admin : "+admin);
		System.out.println("here"+admin);
		if(bindingResult.hasErrors()){
			logger.error("BindingResult errors "+bindingResult.getAllErrors());
			System.out.println(bindingResult.getAllErrors());
			return "Not Updated";
		}
		System.out.println(admin);
		if(checkAdmin(request)){
			
			HttpSession httpSession = request.getSession(false);
			Admin adminSession = (Admin)httpSession.getAttribute("admin");	
			int prevId = adminService.getAdminId(adminSession);
			
			Response response = hRService.updateAdmin(admin);
			logger.info("Response "+response);
			if(response.getCode()==0){
				Admin admin2 = adminService.getAdminById(prevId);
				httpSession.setAttribute("admin",admin2);
				return response.getMessage();
			   }
			 else{
				    logger.info("Not updated "+response.getMessage());
			    	return "Not Updated "+response.getMessage();
			 }
		}
		logger.info("Not permitted");
		return "Permitted only after login.";
	}


	@RequestMapping(value="/updatePassword")
	public String getUpdatePasswordForm(){
		return "hr/updatePassword";
	}
	
	@RequestMapping(value="/updatePassword",method=RequestMethod.POST)
	@ResponseBody
	public String updatePassword(@ModelAttribute("emp1")ResetPasswordDTO password,BindingResult bindingResult,HttpServletRequest request, ModelMap modelMap){
		System.out.println("here"+password);
		if(bindingResult.hasErrors()){
			logger.error("BindingResult errors "+bindingResult.getAllErrors());
			System.out.println(bindingResult.getAllErrors());
			return "Not Updated";
		}
		if(checkAdmin(request)){
			Admin admin = (Admin)request.getSession(false).getAttribute("admin");
			logger.info("In session with emailId"+admin.getEmail());
			
			Response response = hRService.updatePassword(password,admin.getEmail());
			logger.info("Response "+response);
			if(response.getCode()==0){
				  return response.getMessage();
			   }
			 else{
				    logger.info("Not updated "+response.getMessage());
			    	return "Not Updated "+response.getMessage();
			 }
		}
		logger.info("Not updated");
		return "Not Updated";
	}



	
	
	
	private <T> void setParam(ModelMap model,PageResponseDTO<T> pageResponseDTO){
    	int current = pageResponseDTO.getData().getNumber()+1;
		int begin = Math.max(1, current - 5);
		int end = Math.min(begin + 10, pageResponseDTO.getData().getTotalPages());
		model.addAttribute("pages", pageResponseDTO.getData());
		model.addAttribute("beginIndex",begin);
		model.addAttribute("endIndex",end);
		model.addAttribute("currentIndex",current);
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
