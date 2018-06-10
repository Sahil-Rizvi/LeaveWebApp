package com.sahil.services;

import com.sahil.entities.AdminEntity;
import com.sahil.entities.VerificationTokenAdmin;
import com.sahil.models.Response;
import com.sahil.models.input.Admin;
import com.sahil.models.input.PasswordDto;


public interface AdminService {

	public Response addAdmin(Admin admin);

	public Response validate(String email, String password);
	
	public com.sahil.models.Admin getAdmin(String email);

	public String confirmRegistration(Admin user, String appUrl) throws Exception;

	public VerificationTokenAdmin getVerificationToken(String VerificationToken);

	public void saveRegisteredUser(AdminEntity user);

	public AdminEntity findUserByEmail(String email);

	public void createPasswordResetTokenForUser(AdminEntity user, String token);

	public String validatePasswordResetToken(String id, String token);

	public Response changeUserPassword(AdminEntity user, PasswordDto passwordDto);

	public boolean checkIfValidOldPassword(AdminEntity user, String oldPassword);

	public int getAdminId(com.sahil.models.Admin admin);

	public com.sahil.models.Admin getAdminById(int id);
	
	public long countAdmins();
	
	
}
