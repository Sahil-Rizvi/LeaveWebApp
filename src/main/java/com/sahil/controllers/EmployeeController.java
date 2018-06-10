package com.sahil.controllers;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sahil.models.Response;
import com.sahil.models.UpdateEmployeeDTO;
import com.sahil.models.input.Employee;
import com.sahil.models.input.ResetPasswordDTO;
import com.sahil.services.EmployeeService;



@Controller
@RequestMapping("/emp")
public class EmployeeController {

	private static final Logger logger = Logger.getLogger(EmployeeController.class);
	
	@Autowired
	private EmployeeService employeeService;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
		logger.info("in initBinder");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	/*
	
	@RequestMapping(value="/home", method = RequestMethod.GET)
	public String empHome(ModelMap modelMap){
		modelMap.addAttribute("departmentTypes",Department.getValues());
		modelMap.addAttribute("employee", new Employee());
		return "empHome";
	}
	
	
	
	@RequestMapping(value="/addEmployee",method=RequestMethod.POST)
	public String addEmployee(@Valid @ModelAttribute("employee")Employee employee,BindingResult bindingResult,ModelMap modelMap){
		System.out.println(employee);
		
		if(bindingResult.hasErrors()){
			System.out.println(bindingResult.getAllErrors());
			modelMap.addAttribute("departmentTypes",Department.getValues());
			return "empHome";
		}
		Response response = employeeService.addEmployee(employee);
		if(response.getCode()==0){
		  return "redirect:logInEmployee";
	    }
	    else{
	    	modelMap.addAttribute("departmentTypes",Department.getValues());
	    	System.out.println(response.getMessage());
			System.out.println(response.getCode());	
	    	return "empHome";
	    }
	}
	
	@RequestMapping(value="/deleteEmployee")
	@ResponseBody
	public String deleteEmployee(@RequestParam String empId){
		Response response = employeeService.deleteEmployee(empId);
		return response.getMessage();
	}
	*/
	
	// needs a lot of modifications
	@RequestMapping(value="/updateEmployee")
	public String getUpdateEmployeeForm(HttpServletRequest request,ModelMap modelMap){
		String form="updateempform";
		System.out.println("getting update form");
	    logger.info("in getUpdateEmployeeForm");
	    if(request.getUserPrincipal().getName()!=null){
	    	logger.info("in session");
			modelMap.addAttribute("emp1",employeeService.findEmployeeForUpdation(request.getUserPrincipal().getName()));
	    }
	    return form;
	}

	
	
	
	
	@RequestMapping(value="/updateEmployee",method=RequestMethod.POST)
	@ResponseBody
	public String updateEmployee(@ModelAttribute("emp1")UpdateEmployeeDTO employee,BindingResult bindingResult,HttpServletRequest request, ModelMap modelMap){
		//updateEmployeeValidator.validate(employee,bindingResult);
		logger.info("in updateEmployee with employee : "+employee);
		System.out.println("here"+employee);
		if(bindingResult.hasErrors()){
			logger.error("BindingResult errors "+bindingResult.getAllErrors());
			System.out.println(bindingResult.getAllErrors());
			return "Not Updated";
		}
		System.out.println(employee);
		if(request.getUserPrincipal().getName()!=null){
			String empId = request.getUserPrincipal().getName();
			logger.info("In session with empId"+empId);
			System.out.println(employee);
			Response response = employeeService.updateEmployee(employee,empId);
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

	
	@RequestMapping(value="/updatePassword")
	public String getUpdatePasswordForm(){
		return "emp/updatePassword";
	}
	
	@RequestMapping(value="/updatePassword",method=RequestMethod.POST)
	@ResponseBody
	public String updatePassword(@ModelAttribute("emp1")ResetPasswordDTO password,BindingResult bindingResult,HttpServletRequest request, ModelMap modelMap){
		//updateEmployeeValidator.validate(employee,bindingResult);
		System.out.println("here"+password);
		if(bindingResult.hasErrors()){
			logger.error("BindingResult errors "+bindingResult.getAllErrors());
			System.out.println(bindingResult.getAllErrors());
			return "Not Updated";
		}
		if(request.getUserPrincipal().getName()!=null){
			String empId = request.getUserPrincipal().getName();
			logger.info("In session with empId"+empId);
			
			Response response = employeeService.updatePassword(password,empId);
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

	
	@RequestMapping(value="/findEmployee")
	@ResponseBody
	public Employee findEmployee(@RequestParam String id){
		logger.info("in findEmployee with"+id);
		return employeeService.findEmployee(id);
	}
	
	@RequestMapping(value="/findAllEmployees")
	@ResponseBody
	public List<Employee> findAllEmployees(){
		logger.info("in findAllEmployees");
		return employeeService.findAllEmployees();
	}
	/*
	@RequestMapping(value="/logInEmployee")
	public String logInEmployee(){
		return "logInEmployee";
	}
	
    @RequestMapping(value="/submitLogInEmpDetails",method=RequestMethod.POST)
	public String submitDetails(@RequestParam String empId,@RequestParam String empPass,HttpServletRequest request){
    	if(employeeService.checkEmployee(empId,empPass)){
    		request.getSession(true).setAttribute("empId",empId);
    		return "redirect:loggedIn";
    	}		
        return "redirect:logInEmployee";
    }
*/
    @RequestMapping(value={"/empHome"})
    public String getLoggedIn(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap,Principal principal){
    	String view = "emphome";
        logger.info("in getLoggedIn with empCode"+principal.getName());
    	
    	System.out.println("i"+request.getRequestURI());
    	
    	
    	String empId = principal.getName();
    	System.out.println("Name"+principal.getName());
    	
    	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    	response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    	response.setHeader("Expires", "0"); // Proxies.
    	
    	
    	if(!StringUtils.isEmpty(empId)){
    		modelMap.addAttribute("emp",employeeService.findEmployee(empId));
    		modelMap.addAttribute("managerLink",employeeService.getManagerString(empId));
        	logger.info("return view:"+view);
        	return view;
    	}
    	
    	logger.info("redirecting to login");
    	return "redirect:/login";
    }
    
    @RequestMapping(value="/managerHome")
    public String getManagerHome(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap,Principal principal){
    	String managerHome="managerhome";
    	logger.info("in getManagerHome  with empCode"+principal.getName());	
    	String empId = principal.getName();
    	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    	response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    	response.setHeader("Expires", "0"); // Proxies.
    	modelMap.addAttribute("mg",employeeService.findEmployee(empId));
    	logger.info("returing view:"+managerHome);
    	return managerHome;
    }
    
    /*
    @RequestMapping(value="/logout")
    public String logout(HttpServletRequest request){
    	if(request.getSession(false)!=null && request.getSession(false).getAttribute("empId")!=null){
    		HttpSession httpSession = request.getSession(false);
    		httpSession.invalidate();
    	}
    	return "redirect:logInEmployee";
    }
    */
    
    
    
}
