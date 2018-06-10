package com.sahil.models;

public class Admin {

	private String name;
	
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String password) {
		this.name = password;
	}

	@Override
	public String toString() {
		return "Admin [email=" + email + ", name=" + name + "]";
	}
	
}
