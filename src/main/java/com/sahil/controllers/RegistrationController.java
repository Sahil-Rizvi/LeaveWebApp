package com.sahil.controllers;

import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.sahil.entities.EmployeeEntity;
import com.sahil.entities.VerificationToken;
import com.sahil.models.Response;
import com.sahil.models.input.Employee;
import com.sahil.models.input.PasswordDto;
import com.sahil.registration.listener.OnRegistrationCompleteEvent;
import com.sahil.repositories.EmployeeRepository;
import com.sahil.services.ContactService;
import com.sahil.services.DepartmentService;
import com.sahil.services.DesignationService;
import com.sahil.services.EmployeeService;
import com.sahil.services.LeaveCountForYearService;


@Controller
@RequestMapping("/r")
public class RegistrationController {

	private Logger logger = LoggerFactory.getLogger(RegistrationController.class);
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private ContactService contactService;
	
	@Autowired
    private DepartmentService departmentService;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
    
    @Autowired
    private DesignationService designationService;
	
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private LeaveCountForYearService leaveCountForYearService;
    
    @Autowired
    private MailSender mailSender;
	
    
	@RequestMapping(value="/register", method = RequestMethod.GET)
	public String empHome(ModelMap modelMap){
		modelMap.addAttribute("departmentTypes",departmentService.getAllDepartmentNames());
		modelMap.addAttribute("designationTypes",designationService.getAllDesignationNames());
		if(!leaveCountForYearService.isLeaveCountAvailableForCurrentYear()){
			modelMap.addAttribute("lcmessage","Leave Count not added for Current Year");
		}
		modelMap.addAttribute("employee", new Employee());
		return "register";
	}
	
	
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public String addEmployee(@Valid @ModelAttribute("employee")Employee employee,BindingResult bindingResult,HttpServletRequest request,ModelMap modelMap){
		System.out.println(employee);
		
		if(bindingResult.hasErrors()){
			System.out.println(bindingResult.getAllErrors());
			modelMap.addAttribute("departmentTypes",departmentService.getAllDepartmentNames());
			modelMap.addAttribute("designationTypes",designationService.getAllDesignationNames());
			return "register";
		}
		Response response = employeeService.addEmployee(employee);
		if(response.getCode()==0){
			
			try {
		        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(employeeRepository.findOne(employee.getEmployeeId()), request.getLocale(), getAppUrl(request)));
		    } catch (Exception me) {
		    	System.out.println("mailError "+me);
		    	modelMap.addAttribute("successmessage","Registered but sending verification link to your email Id will take some time. Please cooperate.");
		        return "redirect:register";// email Error
		    }
			System.out.println("Successful registration");
			modelMap.addAttribute("successmessage","Verification link sent on your email id.");
		    return "redirect:register";// successful registration
	    }
	    else{
	    	modelMap.addAttribute("departmentTypes",departmentService.getAllDepartmentNames());
			modelMap.addAttribute("designationTypes",designationService.getAllDesignationNames());
			modelMap.addAttribute("message",response.getMessage());
			System.out.println(response.getMessage());
			System.out.println(response.getCode());	
	    	return "register";
	    }
	}
	
	
	@RequestMapping(value = "/registrationConfirm", method = RequestMethod.GET)
	public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token) {
	  
	    Locale locale = request.getLocale();
	     
	    System.out.println("in confirmation \n token =>" +token);
	    VerificationToken verificationToken = employeeService.getVerificationToken(token);
	    if (verificationToken == null) {
	        String message = "Invalid Token";//messages.getMessage("auth.message.invalidToken", null, locale);
	        model.addAttribute("ivt","ivt");
	        System.out.println(message);
	        System.out.println("ivt added");
	        return "redirect:/?lang=" + request.getLocale().getLanguage();
	    }
	     
	    EmployeeEntity user = verificationToken.getUser();
	    Calendar cal = Calendar.getInstance();
	    if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
	        //String messageValue = messages.getMessage("auth.message.expired", null, locale);
	        String messageValue = "Token has expired";
	        System.out.println(messageValue);
	        model.addAttribute("te","te");
	        return "redirect:/?lang=" + request.getLocale().getLanguage();
	    } 
	     
	    user.setEnabled(true);
	    employeeService.saveRegisteredUser(user);
	    model.addAttribute("v","v");
	    return "redirect:/?lang=" + request.getLocale().getLanguage(); 
	}
	
	
	
	
	// Reset password
	    @RequestMapping(value = "/emp/resetPassword", method = RequestMethod.POST)
	    @ResponseBody
	    public String resetPassword(final HttpServletRequest request, @RequestParam("email") final String userEmail) {
	        final EmployeeEntity user = employeeService.findUserByEmail(userEmail);
	        if (user != null) {
	            	final String token = UUID.randomUUID().toString();
		            employeeService.createPasswordResetTokenForUser(user, token);
		               try{
	            		   mailSender.send(constructResetTokenEmail(getAppUrl(request), request.getLocale(), token, user));
	            		   return "You should receive a Password Reset Email shortly";
		               }
	            	   catch(MailException e){
	            		   logger.error("MailException message"+e.getMessage());
	            		   System.out.println("Please try after sometime.");
	            		   return "Please try after sometime.";
	            	   }
	        }
	        logger.info("EmailId:"+userEmail+" is not registered");
        	System.out.println("EmailId:"+userEmail+" is not registered");
        	return "EmailId:"+userEmail+" is not registered";
	    }

	    @RequestMapping(value = "/emp/changePassword", method = RequestMethod.GET)
	    public String showChangePasswordPage(final Locale locale, final Model model, @RequestParam("id") final String id, @RequestParam("token") final String token) {
	        final String result = employeeService.validatePasswordResetToken(id, token);
	        if (result != null) {
	            model.addAttribute("message", result);
	            return "redirect:/?lang=" + locale.getLanguage();
	        }
	        
	        return "login/updatepassword";
	    }
	    
	    @RequestMapping(value = "/emp/forgetPassword", method = RequestMethod.GET)
	    public String showForgetPasswordPage() {
	        return "login/forgetPassword";
	    }
	    

	    @RequestMapping(value = "/emp/savePassword", method = RequestMethod.POST)
	    @ResponseBody
	    public String savePassword(final Locale locale, @Valid PasswordDto passwordDto) {
	        
	    	EmployeeEntity user =(EmployeeEntity)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        
	    	Response response = employeeService.changeUserPassword(user, passwordDto);
	        if(response.getCode()==1){
	        	return response.getMessage();
	        }
	    	System.out.println("password reset");
	        return "Password reset successfully";
	    }

	    // change user password
	    @RequestMapping(value = "/emp/updatePassword", method = RequestMethod.POST)
	    @ResponseBody
	    public String changeUserPassword(final Locale locale, @Valid PasswordDto passwordDto) {
	    	EmployeeEntity employeeEntity = (EmployeeEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    	String email = contactService.findEmail(employeeEntity);
	    	
	        final EmployeeEntity user = employeeService.findUserByEmail(email);
	        if (employeeService.checkIfValidOldPassword(user, passwordDto.getOldPassword())) {
	        	Response response = employeeService.changeUserPassword(user, passwordDto);
	        	if(response.getCode()==1){
	        		return response.getMessage();
	        	}
		        return "Password updated successfully";
	        }
	        return null;
	    }



	    // ============== NON-API ============

	    private SimpleMailMessage constructResendVerificationTokenEmail(final String contextPath, final Locale locale, final VerificationToken newToken, final EmployeeEntity user) {
	        final String confirmationUrl = contextPath + "/registrationConfirm.html?token=" + newToken.getToken();
	        final String message = "We will send an email with a new registration token to your email account";
	        return constructEmail("Resend Registration Token", message + " \r\n" + confirmationUrl, user);
	    }

	    private SimpleMailMessage constructResetTokenEmail(final String contextPath, final Locale locale, final String token, final EmployeeEntity user) {
	        final String url = contextPath + "/r/emp/changePassword?id=" + user.getEmployeeCode() + "&token=" + token;
	        final String message = "Reset Password";
	        return constructEmail("Reset Password", message + " \r\n" + url, user);
	    }

	    private SimpleMailMessage constructEmail(String subject, String body, EmployeeEntity user) {
	        final SimpleMailMessage email = new SimpleMailMessage();
	        email.setSubject(subject);
	        email.setText(body);
	        String toEmailAddress = contactService.findEmail(user);
	        if(!StringUtils.isEmpty(toEmailAddress)){
		        email.setTo(toEmailAddress);	
	        }
	        email.setFrom("username@email.com");
	        System.out.println("mail composed"+email.toString());
	       return email;
	    }

		 private String getAppUrl(HttpServletRequest request) {
		        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		 }
		
		 


	 
	
}
