package com.sahil.models.admin;

import com.sahil.models.Response;

public class UpdateAdminDTO extends Response{

	private int adminId;
	
	private String name;
	
	private String emailId;

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@Override
	public String toString() {
		return "UpdateAdminDTO [adminId=" + adminId + ", name=" + name + ", emailId=" + emailId + "]";
	}

	
	
}
