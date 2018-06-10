package com.sahil.registration.listener;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.sahil.entities.EmployeeEntity;
import com.sahil.repositories.ContactRepository;
import com.sahil.services.EmployeeService;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    
	@Autowired
    private EmployeeService service;

    @Autowired
    private MessageSource messages;

    @Autowired
	private MailSender mailSender;
	

    @Autowired
    private ContactRepository contactRepository;
    
    @Autowired
    private Environment env;

    // API

    private static boolean done = true;
    
    @Override
    public void onApplicationEvent(final OnRegistrationCompleteEvent event) {
        if(done){
        	this.confirmRegistration(event);
        }
        done = !done;
    }

    private void confirmRegistration(final OnRegistrationCompleteEvent event) {
        final EmployeeEntity user = event.getUser();
        final String token = UUID.randomUUID().toString();
        service.createVerificationTokenForUser(user, token);
        
        final SimpleMailMessage email = constructEmailMessage(event, user, token);

        mailSender.send(email);
        System.out.println("mail sent");        
    }

    //

    private final SimpleMailMessage constructEmailMessage(final OnRegistrationCompleteEvent event, final EmployeeEntity user, final String token) {
        final String recipientAddress = contactRepository.findByEmployeeEntity(event.getUser()).getEmailId();
        final String subject = "Registration Confirmation";
        final String confirmationUrl = event.getAppUrl() + "/r/registrationConfirm?token=" + token;
        final String message = "Successful Registration on LeaveWebApp";
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" + confirmationUrl);
        email.setFrom("username@email.com");
        System.out.println("mail composed"+email.toString());
        return email;
    }

}
