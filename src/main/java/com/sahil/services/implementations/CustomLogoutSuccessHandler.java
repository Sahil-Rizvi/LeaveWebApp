package com.sahil.services.implementations;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component("logoutSuccessHandler")
public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

@Override
public void onLogoutSuccess(HttpServletRequest request,
        HttpServletResponse response, Authentication authentication)
        throws IOException, ServletException {

	
	//response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
	//response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
	//response.setHeader("Expires", "0"); // Proxies.
	System.out.println("here");
    setAlwaysUseDefaultTargetUrl(true);
    setDefaultTargetUrl("/");
    super.onLogoutSuccess(request, response, authentication);
}
}