package com.sahil.models;

public class CountResponse extends Response{
	
	private double count;

	public double getCount() {
		return count;
	}

	public void setCount(double count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "CountResponse [count=" + count + ", getCode()=" + getCode() + ", getMessage()=" + getMessage() + "]";
	}

}
