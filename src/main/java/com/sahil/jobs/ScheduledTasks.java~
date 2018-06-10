package com.sahil.jobs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.sahil.entities.AdminEntity;
import com.sahil.repositories.AdminRepository;
import com.sahil.services.DepartmentService;
import com.sahil.services.HRService;
import com.sahil.services.ReportGenerator;

@Component
public class ScheduledTasks {

    private static final Logger log = Logger.getLogger(ScheduledTasks.class);

	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private ReportGenerator reportGenerator;

	@Autowired
	private HRService hrService;
	
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    
    
    //@Scheduled(cron="* */2 * * * *")
    public void reportCurrentTime() {
        System.out.println("The time is "+dateFormat.format(new Date()));
        System.out.println("\n"+departmentService.getAllDepartmentNames()+"\n");
        
        mailSender.send(constructEmailMessage());
        
    }
    
    
    /*
    Seconds

    Y

    0-59

    , - * /

    Minutes

    Y

    0-59

    , - * /

    Hours

    Y

    0-23

    , - * /

    Day of month

    Y

    1-31

    , - * ? / L W C

    Month

    Y

    0-11 or JAN-DEC

    , - * /

    Day of week

    Y

    1-7 or SUN-SAT

    , - * ? / L C #

    Year
    
    */
    //daily 11:00 pm 
    @Scheduled(cron="0 0 23 * * ?")
    public void forwardLeaves(){
        hrService.forwardLeaves();	
    }
    
    //monthly 12:15 am 
    @Scheduled(cron="0 15 0 1 * ?") 
    public void reportForHr(){
    	List<AdminEntity> admins = new ArrayList<>();
    	admins = adminRepository.findAll();
    	if(!CollectionUtils.isEmpty(admins)){
    	     reportGenerator.generateReportForHR(admins);
    	}
    	
    }
    
    
    //weekly 12:15 am every Monday
    @Scheduled(cron="0 15 0 ? * MON")  
    public void reportForManager(){
    	reportGenerator.generateReportForManager();
    }
    
    
    
    private final SimpleMailMessage constructEmailMessage() {
        final String recipientAddress = "receiver@email.com";
        final String subject = "Registration Confirmation";
        final String message = "Successful Registration on LeaveWebApp";
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n"+new Date().getTime());
        email.setFrom("username@email.com");
        System.out.println("mail composed"+email.toString());
        return email;
    }

}
