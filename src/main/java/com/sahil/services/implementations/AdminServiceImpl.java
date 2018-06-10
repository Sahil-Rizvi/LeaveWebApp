package com.sahil.services.implementations;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sahil.entities.AdminEntity;
import com.sahil.entities.AdminPasswordResetToken;
import com.sahil.entities.RoleEntity;
import com.sahil.entities.VerificationTokenAdmin;
import com.sahil.models.Response;
import com.sahil.models.input.Admin;
import com.sahil.models.input.PasswordDto;
import com.sahil.repositories.AdminPasswordResetTokenRepository;
import com.sahil.repositories.AdminRepository;
import com.sahil.repositories.AdminVerificationTokenRepository;
import com.sahil.repositories.RoleRepository;
import com.sahil.services.AdminService;

@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private AdminVerificationTokenRepository adminVerificationTokenRepository;
	
	@Autowired
	private AdminPasswordResetTokenRepository adminPasswordResetTokenRepository;
	
    @Autowired
    private MailSender mailSender;
		
	@Override
	public Response addAdmin(Admin admin) {

		Response response = validate(admin);
		if(adminRepository.count()>0){
			response.setCode(1);
			response.setMessage("There can be atmost one admin who already exists.");
			return response;
		}
		if(response.getCode()==1){
			return response;
		}
		
		AdminEntity adminEntity = new AdminEntity();
		adminEntity.setDateOfCreation(new Date());
		adminEntity.setEmailId(admin.getEmail());
		adminEntity.setEnabled(false);
		adminEntity.setName(admin.getName());
		adminEntity.setPassword(passwordEncoder.encode(admin.getPassword()));
		
       
        RoleEntity roleEntity = roleRepository.findByRoleName("ROLE_HR");
        if(roleEntity==null){
        	roleEntity = new RoleEntity();
        	roleEntity.setRoleName("ROLE_HR");
        	roleRepository.save(roleEntity);
        }
        adminEntity.getAdminRoles().add(roleEntity);
        adminRepository.save(adminEntity);
        System.out.println("admin "+adminEntity+" added");
        response.setCode(0);
        response.setMessage("Successfully Registered. You will receive a verification link on your email Id.");
        return response;
	}
	
	
	@Override
	public int getAdminId(com.sahil.models.Admin admin){
		AdminEntity adminEntity = adminRepository.findByEmailId(admin.getEmail());
		return adminEntity!=null?adminEntity.getAdminId():-1;
	}
	
	@Override
	public com.sahil.models.Admin getAdminById(int id){
		if(id>0){
			AdminEntity adminEntity = adminRepository.findOne(id);
			if(Objects.nonNull(adminEntity)){
				com.sahil.models.Admin admin = new com.sahil.models.Admin();
				admin.setName(adminEntity.getName());
				admin.setEmail(adminEntity.getEmailId());
				return admin;
			}
		}
		return null;
	}
	
	
	@Override
	public String confirmRegistration(final Admin user,String appUrl) throws Exception {
        String res = null;
		final String token = UUID.randomUUID().toString();
        createVerificationTokenForUser(user, token);
        
        final SimpleMailMessage email = constructEmailMessage(appUrl,user, token);

        try{
        	mailSender.send(email);
        	System.out.println("mail sent");
        	
        }
        catch(MailException e){
        	System.out.println(e.getMessage());
        	res = "mailException";
        }
        return res;
                
    }

	
	
    private void createVerificationTokenForUser(final Admin user, final String token) throws Exception {
        AdminEntity adminEntity = adminRepository.findByEmailId(user.getEmail());
    	if(Objects.nonNull(adminEntity)){
    		final VerificationTokenAdmin myToken = new VerificationTokenAdmin(token, adminEntity);
    		adminVerificationTokenRepository.save(myToken);
    	}
    	else{
    		throw new Exception("admin not found");
     	}
    }


	
	 private final SimpleMailMessage constructEmailMessage(final String appUrl,final Admin user, final String token) {
	        final String recipientAddress = user.getEmail();
	        final String subject = "Registration Confirmation";
	        final String confirmationUrl = appUrl + "/admin/registrationConfirm?token=" + token;
	        final String message = "Successful Registration on LeaveWebApp";
	        final SimpleMailMessage email = new SimpleMailMessage();
	        email.setTo(recipientAddress);
	        email.setSubject(subject);
	        email.setText(message + " \r\n" + confirmationUrl);
	        email.setFrom("rizvisahil786@gmail.com");
	        System.out.println("mail composed"+email.toString());
	        return email;
	    }

	
	public Response validate(Admin admin){		
		// password verification pending
		Response response = new Response();
		response.setCode(0);
		response.setMessage("OK");
		if(StringUtils.isEmpty(admin) ||
		   StringUtils.isEmpty(admin.getName()) ||
		   StringUtils.isEmpty(admin.getEmail()) ||
		   StringUtils.isEmpty(admin.getPassword()) ||
		   StringUtils.isEmpty(admin.getMatchingPassword())) {
		   response.setCode(1);
		   response.setMessage("SOME INPUT IS MISSING");
		   return response;
		}
		
		
		if(adminRepository.findByEmailId(admin.getEmail())!=null){
			response.setCode(1);
			System.out.println("here    ------");
			response.setMessage("ADMIN WITH EMAILID"+admin.getEmail()+ " ALREADY EXISTS ");
			return response;
		}
		return response;
	}
	
	@Override
	public Response validate(String email,String password){
           Response response = new Response();
           response.setCode(1);
           response.setMessage("SUCCESSFUL");
           if(StringUtils.isEmpty(email) && StringUtils.isEmpty(password)){
        	  response.setMessage("Please enter email and password");
        	  return response;
           }
           if(StringUtils.isEmpty(email)){
        	  response.setMessage("Please enter email");
         	  return response;
           }
           
           if(StringUtils.isEmpty(password)){
        	   response.setMessage("Please enter password");
        	   return response;
           }
           
           if(!validateEmail(email)){
        	   response.setMessage("Please enter valid email");
        	   return response;
           }
           
           if(!validateCredentials(email, password)){
        	   response.setMessage("Invalid Credentials");
        	   return response;
           }
           
           
           response.setCode(0);
           response.setMessage("SUCCESSFUL");
           return response;
           
	}

	
	
	private boolean validateEmail(String email){
		final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	private boolean validateCredentials(String email,String password){
		if(StringUtils.isEmpty(email) || StringUtils.isEmpty(password)){
			return false;
		}
		System.out.println(email);
		System.out.println(password);
		
		AdminEntity adminEntity = adminRepository.findByEmailId(email);
		System.out.println(adminEntity);
		if(Objects.nonNull(adminEntity) &&  passwordEncoder.matches(password,adminEntity.getPassword())){
			return true;
	    }
	    return false;
	}


	@Override
	public com.sahil.models.Admin getAdmin(String email) {
		com.sahil.models.Admin admin = null;
		if(StringUtils.isEmpty(email)){
			return admin;
		}
		
		if(adminRepository.findByEmailId(email)!=null){
		   return convert(adminRepository.findByEmailId(email));	
		}
		return null;
	}

    private com.sahil.models.Admin convert(AdminEntity adminEntity){
    	com.sahil.models.Admin admin = new com.sahil.models.Admin();
    	  if(Objects.nonNull(adminEntity)){
    		  admin.setEmail(adminEntity.getEmailId());
    		  admin.setName(adminEntity.getName());
    	  }
    	return admin;
    }
    
    @Override
    public VerificationTokenAdmin getVerificationToken(String VerificationToken) {
        return adminVerificationTokenRepository.findByToken(VerificationToken);
    }
    
    @Override
    public void saveRegisteredUser(AdminEntity user) {
        adminRepository.save(user);
    }

    
    @Override
	public AdminEntity findUserByEmail(String email) {
    	AdminEntity adminEntity = null;
    	adminEntity = adminRepository.findByEmailId(email);
		if(Objects.nonNull(adminEntity)){
			return adminEntity;
		}
		return null;
	}
    
    @Override
    public void createPasswordResetTokenForUser(final AdminEntity user, final String token) {
    	final AdminPasswordResetToken myToken = new AdminPasswordResetToken(token, user);
    	adminPasswordResetTokenRepository.save(myToken);
    }
    
    
    
    
    @Override
    public Response changeUserPassword(final AdminEntity user, final PasswordDto passwordDto){
        Response response = new Response();
        response.setCode(1);
        
        if(StringUtils.isEmpty(passwordDto) || StringUtils.isEmpty(passwordDto.getOldPassword()) || StringUtils.isEmpty(passwordDto.getNewPassword())){
        	response.setMessage("NOT UPDATED");
        	return response;
        }
        
        if(!passwordDto.getOldPassword().equals(passwordDto.getNewPassword())){
        	response.setMessage("Passwords didn't match");
        	return response;
        }
        
        user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        adminRepository.save(user);
        response.setCode(0);
        response.setMessage("UPDATED");
    	return response;
    }
	
	
	 
	 @Override
	    public boolean checkIfValidOldPassword(final AdminEntity user, final String oldPassword) {
	        return passwordEncoder.matches(oldPassword, user.getPassword());
	    }

    
    @Override
    public String validatePasswordResetToken(String id, String token) {
        final AdminPasswordResetToken passToken = adminPasswordResetTokenRepository.findByToken(token);
        int intId = Integer.parseInt(id);
        System.out.println(intId);
        if ((passToken == null) || (passToken.getUser().getAdminId()!=intId)) {
            System.out.println("invalidToken");
        	return "Invalid Token";
        }

        
        System.out.println(passToken.getExpiryDate().getTime());
        
        final Calendar cal = Calendar.getInstance();
        System.out.println(cal.getTime().getTime());
        
        System.out.println("diff"+(passToken.getExpiryDate().getTime() - cal.getTime().getTime()));
        if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return "Token has expired";
        }

        final AdminEntity user = passToken.getUser();
        final Authentication auth = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return null;
    }


	@Override
	public long countAdmins() {
		// TODO Auto-generated method stub
		return adminRepository.count();
	}
	
    
    
    
	
}
