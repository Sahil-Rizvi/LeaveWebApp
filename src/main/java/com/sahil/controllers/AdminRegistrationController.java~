package com.sahil.controllers;

import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.sahil.entities.AdminEntity;
import com.sahil.entities.VerificationToken;
import com.sahil.entities.VerificationTokenAdmin;
import com.sahil.models.Response;
import com.sahil.models.input.Admin;
import com.sahil.models.input.PasswordDto;
import com.sahil.services.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminRegistrationController {

	private static final Logger logger = Logger.getLogger(AdminRegistrationController.class);
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
    private MailSender mailSender;
	


	@RequestMapping(value="/register", method = RequestMethod.GET)
	public String adminHome(ModelMap modelMap){
		logger.info("In adminHome");
		if(adminService.countAdmins()>0){
			modelMap.addAttribute("block","There can be atmost one admin who already exists.");
		}
		else{
			modelMap.addAttribute("admin", new Admin());
		}
		return "admregister";
	}
	
	
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public String addEmployee(@Valid @ModelAttribute("admin")Admin admin,BindingResult bindingResult,HttpServletRequest request,ModelMap modelMap){
		System.out.println(admin);
		logger.info("In addAdmin with Admin"+admin);
		if(bindingResult.hasErrors()){
			System.out.println(bindingResult.getAllErrors());
			logger.error("Error in bindingResult "+bindingResult.getAllErrors());
			logger.info("returning view : admregister");
			return "admregister";
		}
		Response response = adminService.addAdmin(admin);
		logger.info("response of addAdmin from service"+response);
		if(response.getCode()==0){
			
			try {
				
				String res = adminService.confirmRegistration(admin, getAppUrl(request));
		        if(res!=null){
		        	System.out.println("res "+res);
		        	modelMap.addAttribute("emailerror","Registered but sending verification link to your email Id got failed. Your email will get verified later. ");
		        	return "redirect:register"; 
		        }
		        else{
		        	System.out.println("Successful registration");
					modelMap.addAttribute("successmessage",response.getMessage());
			    	return "redirect:register";
		        }
				
		    } catch (Exception e) {
		    	System.out.println("Exception occured"+e);
		    	modelMap.addAttribute("emailerror","Registered but sending verification link to your email Id will take some time. Please cooperate.");
		        return "redirect:register";// email Error
		    }
			
	    }
	    else{
	    	System.out.println(response.getMessage());
			System.out.println(response.getCode());
			modelMap.addAttribute("message",response.getMessage());
	    	return "admregister";
	    }
	}
	
	
	
	 private String getAppUrl(HttpServletRequest request) {
	        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	 }
	

	@RequestMapping(value = "/registrationConfirm", method = RequestMethod.GET)
	public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token) {
	  
	    Locale locale = request.getLocale();
	     
	    System.out.println("in confirmation \n token =>" +token);
	    VerificationTokenAdmin verificationToken = adminService.getVerificationToken(token);
	    if (verificationToken == null) {
	        String message = "Invalid Token";//messages.getMessage("auth.message.invalidToken", null, locale);
	        model.addAttribute("ivt","ivt");
	        System.out.println(message);
	        return "redirect:/customAdmin/login?lang=" + request.getLocale().getLanguage();
	    }
	     
	    AdminEntity user = verificationToken.getUser();
	    Calendar cal = Calendar.getInstance();
	    if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
	        //String messageValue = messages.getMessage("auth.message.expired", null, locale);
	        String messageValue = "Token has expired";
	        System.out.println(messageValue);
	        model.addAttribute("te", "te");
	        return "redirect:/customAdmin/login?lang=" + request.getLocale().getLanguage();
	    } 
	     
	    user.setEnabled(true);
	    adminService.saveRegisteredUser(user);
	    model.addAttribute("v","v");
	    return "redirect:/customAdmin/login?lang=" + request.getLocale().getLanguage(); 
	}
	
	
	
	
	// Reset password
	    @RequestMapping(value = "/adm/resetPassword", method = RequestMethod.POST)
	    @ResponseBody
	    public String resetPassword(final HttpServletRequest request, @RequestParam("email") final String userEmail) {
	        final AdminEntity user = adminService.findUserByEmail(userEmail);
	        if (user != null) {
	            	final String token = UUID.randomUUID().toString();
		            adminService.createPasswordResetTokenForUser(user, token);
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

	    @RequestMapping(value = "/adm/changePassword", method = RequestMethod.GET)
	    public String showChangePasswordPage(final Locale locale, final Model model, @RequestParam("id") final String id, @RequestParam("token") final String token) {
	        final String result = adminService.validatePasswordResetToken(id, token);
	        if (result != null) {
	            model.addAttribute("message", result);
	            return "redirect:/customAdmin/login?lang=" + locale.getLanguage();
	        }
	        
	        return "admin/updatepassword";
	    }
	    
	    @RequestMapping(value = "/adm/forgetPassword", method = RequestMethod.GET)
	    public String showForgetPasswordPage() {
	        return "admin/forgetPassword";
	    }
	    
	    


	    @RequestMapping(value = "/adm/savePassword", method = RequestMethod.POST)
	    @ResponseBody
	    public String savePassword(final Locale locale, @Valid PasswordDto passwordDto) {
	        
	    	AdminEntity user =(AdminEntity)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        
	    	Response response = adminService.changeUserPassword(user, passwordDto);
	        if(response.getCode()==1){
	        	return response.getMessage();
	        }
	    	System.out.println("password reset");
	        return "Password reset successfully";
	    }

	    // change user password
	    @RequestMapping(value = "/adm/updatePassword", method = RequestMethod.POST)
	    @ResponseBody
	    public String changeUserPassword(final Locale locale, @Valid PasswordDto passwordDto) {
	    	AdminEntity adminEntity = (AdminEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    	
	        if (adminService.checkIfValidOldPassword(adminEntity, passwordDto.getOldPassword())) {
	        	Response response = adminService.changeUserPassword(adminEntity, passwordDto);
	        	if(response.getCode()==1){
	        		return response.getMessage();
	        	}
		        return "Password updated successfully";
	        }
	        return null;
	    }
	    



	    // ============== NON-API ============

	    private SimpleMailMessage constructResendVerificationTokenEmail(final String contextPath, final Locale locale, final VerificationToken newToken, final AdminEntity user) {
	        final String confirmationUrl = contextPath + "/admin/adm/registrationConfirm.html?token=" + newToken.getToken();
	        final String message = "We will send an email with a new registration token to your email account";
	        return constructEmail("Resend Registration Token", message + " \r\n" + confirmationUrl, user);
	    }

	    private SimpleMailMessage constructResetTokenEmail(final String contextPath, final Locale locale, final String token, final AdminEntity user) {
	        final String url = contextPath + "/admin/adm/changePassword?id=" + user.getAdminId() + "&token=" + token;
	        final String message = "Reset Password";
	        return constructEmail("Reset Password", message + " \r\n" + url, user);
	    }

	    private SimpleMailMessage constructEmail(String subject, String body, AdminEntity user) {
	        final SimpleMailMessage email = new SimpleMailMessage();
	        email.setSubject(subject);
	        email.setText(body);
	        String toEmailAddress = user.getEmailId();
	        if(!StringUtils.isEmpty(toEmailAddress)){
		        email.setTo(toEmailAddress);	
	        }
	        email.setFrom("username@email.com");
	        System.out.println("mail composed"+email.toString());
	       return email;
	    }

}
