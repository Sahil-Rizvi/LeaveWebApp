package com.sahil.models.input;

import java.util.Date;

public class CompOff {

	private Date forDate;
	    
	public Date getForDate() {
		return forDate;
	}

	public void setForDate(Date forDate) {
		this.forDate = forDate;
	}

	@Override
	public String toString() {
		return "CompOff [forDate=" + forDate + "]";
	}
	
}
