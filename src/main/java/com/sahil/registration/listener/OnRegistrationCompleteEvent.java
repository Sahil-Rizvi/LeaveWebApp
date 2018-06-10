package com.sahil.registration.listener;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.sahil.entities.EmployeeEntity;

@SuppressWarnings("serial")
public class OnRegistrationCompleteEvent extends ApplicationEvent {

	    private final String appUrl;
	    private final Locale locale;
	    private final EmployeeEntity user;

	    public OnRegistrationCompleteEvent(final EmployeeEntity user, final Locale locale, final String appUrl) {
	        super(user);
	        this.user = user;
	        this.locale = locale;
	        this.appUrl = appUrl;
	    }


	    public String getAppUrl() {
	        return appUrl;
	    }

	    public Locale getLocale() {
	        return locale;
	    }

	    public EmployeeEntity getUser() {
	        return user;
	    }

	}