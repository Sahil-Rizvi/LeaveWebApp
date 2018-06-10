package com.sahil.models;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class HolidayResponse extends Response{

	private HashMap<String,String> map = new LinkedHashMap<>();

	public HashMap<String, String> getMap() {
		return map;
	}

	public void setMap(HashMap<String, String> map) {
		this.map = map;
	}

	@Override
	public String toString() {
		return "HolidayResponse [map=" + map + ", getCode()=" + getCode() + ", getMessage()=" + getMessage() + "]";
	}

}
