package com.sahil.models;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

public class LeaveDetailsDayWise {

	private HashMap<Date,Set<EmployeeDTO>> hashMap = new LinkedHashMap<>();

	public HashMap<Date, Set<EmployeeDTO>> getHashMap() {
		return hashMap;
	}

	public void setHashMap(HashMap<Date, Set<EmployeeDTO>> hashMap) {
		this.hashMap = hashMap;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hashMap == null) ? 0 : hashMap.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LeaveDetailsDayWise other = (LeaveDetailsDayWise) obj;
		if (hashMap == null) {
			if (other.hashMap != null)
				return false;
		} else if (!hashMap.equals(other.hashMap))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LeaveDetailsDayWise [hashMap=" + hashMap + "]";
	}
	
	
	
}
