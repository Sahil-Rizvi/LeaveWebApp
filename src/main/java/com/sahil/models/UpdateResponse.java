package com.sahil.models;

import java.util.ArrayList;
import java.util.List;

public class UpdateResponse extends Response {
	
	private String message;
	
	private List<String> list = new ArrayList<>();

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}
		
}
