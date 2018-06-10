package com.sahil.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/ad")
public class AdminLogInController {
	
	private static final Logger logger = Logger.getLogger(AdminLogInController.class);

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public ModelAndView loginPage() {
    	logger.info("In loginPage ");
        ModelAndView model = new ModelAndView();
        model.setViewName("login/admlogin");
        return model;
    }
    
    
    @RequestMapping(value="/badUser") 
    public void badUser(@RequestParam(value="lang")String lang){
       System.out.println("bad USer lang"+lang);    	
    }
    

    
    
    @RequestMapping(value = "/adminpage", method = RequestMethod.GET)
    public ModelAndView adminPage() {
    	logger.info("In adminPage ");
        ModelAndView model = new ModelAndView();
        model.addObject("title", "Spring Security Admin Hello World");
        model.addObject("admin", getUser());
        model.setViewName("login/admin");
        return model;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("In logoutPage");
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return new ModelAndView("login/admin");
    }

    private String getUser() {
    	logger.info("In getUser");
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        logger.info("Returning user as "+userName);
        return userName;
    }
	


}
