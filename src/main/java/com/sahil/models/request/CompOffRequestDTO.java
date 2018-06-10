package com.sahil.models.request;

public class CompOffRequestDTO {

	private String empId;
	
	private int pageNumber;
	
	private String fromWhen;

	private String toWhen;
	
	public CompOffRequestDTO(String empId, int pageNumber, String fromWhen, String toWhen) {
		super();
		this.empId = empId;
		this.pageNumber = pageNumber;
		this.fromWhen = fromWhen;
		this.toWhen = toWhen;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getFromWhen() {
		return fromWhen;
	}

	public void setFromWhen(String fromWhen) {
		this.fromWhen = fromWhen;
	}

	public String getToWhen() {
		return toWhen;
	}

	public void setToWhen(String toWhen) {
		this.toWhen = toWhen;
	}

	@Override
	public String toString() {
		return "CompOffRequestDTO [empId=" + empId + ", pageNumber=" + pageNumber + ", fromWhen=" + fromWhen
				+ ", toWhen=" + toWhen + "]";
	}

	
}
