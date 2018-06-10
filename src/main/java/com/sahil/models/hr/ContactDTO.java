package com.sahil.models.hr;

public class ContactDTO {

	private String emailId;
	
	private String phoneNo;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	@Override
	public String toString() {
		return "ContactDTO [emailId=" + emailId + ", phoneNo=" + phoneNo + "]";
	}
	
}
