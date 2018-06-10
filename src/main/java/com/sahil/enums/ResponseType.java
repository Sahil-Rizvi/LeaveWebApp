package com.sahil.enums;

public enum ResponseType {
	PENDING("PENDING"),APPROVED("APPROVED"),REJECTED("REJECTED");
	
	private String description;

	private ResponseType(String description) {
	this.description = description;
	}

	public String getValue() {
	return name();
	}

	public String getDescription() {
	return description;
	}

	public void setDescription(String description) {
	this.description = description;
	}
	
}
