package com.sahil.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name="leave_count")
public class LeaveCountEntity {
	
	@Id
	@Column(name="leave_count_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int leaveCountId;
	
	@OneToOne
	@JoinColumn(name="employee_code",nullable=false,unique=true)
	private EmployeeEntity employeeEntity;
	
	@Column(name="sick_count",nullable=false)
	private double sickCount;
	
	@Column(name="casual_count",nullable=false)
	private double casualCount;
	
	@Column(name="privileged_count",nullable=false)
	private double privilegedCount;
	
	@Column(name="bday_count",nullable=false)
	private double bdayCount;
	
	@Column(name="comp_off_count",nullable=false)
	private double compOffCount;
	
	public int getLeaveCountId() {
		return leaveCountId;
	}

	public void setLeaveCountId(int leaveCountId) {
		this.leaveCountId = leaveCountId;
	}

	public EmployeeEntity getEmployeeEntity() {
		return employeeEntity;
	}

	public void setEmployeeEntity(EmployeeEntity employeeEntity) {
		this.employeeEntity = employeeEntity;
	}

	public double getSickCount() {
		return sickCount;
	}

	public void setSickCount(double sickCount) {
		this.sickCount = sickCount;
	}

	public double getCasualCount() {
		return casualCount;
	}

	public void setCasualCount(double casualCount) {
		this.casualCount = casualCount;
	}

	public double getPrivilegedCount() {
		return privilegedCount;
	}

	public void setPrivilegedCount(double privilegedCount) {
		this.privilegedCount = privilegedCount;
	}

	public double getBdayCount() {
		return bdayCount;
	}

	public void setBdayCount(double bdayCount) {
		this.bdayCount = bdayCount;
	}

	public double getCompOffCount() {
		return compOffCount;
	}

	public void setCompOffCount(double compOffCount) {
		this.compOffCount = compOffCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(bdayCount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(casualCount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(compOffCount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + leaveCountId;
		temp = Double.doubleToLongBits(privilegedCount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(sickCount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		LeaveCountEntity other = (LeaveCountEntity) obj;
		if (Double.doubleToLongBits(bdayCount) != Double.doubleToLongBits(other.bdayCount))
			return false;
		if (Double.doubleToLongBits(casualCount) != Double.doubleToLongBits(other.casualCount))
			return false;
		if (Double.doubleToLongBits(compOffCount) != Double.doubleToLongBits(other.compOffCount))
			return false;
		if (leaveCountId != other.leaveCountId)
			return false;
		if (Double.doubleToLongBits(privilegedCount) != Double.doubleToLongBits(other.privilegedCount))
			return false;
		if (Double.doubleToLongBits(sickCount) != Double.doubleToLongBits(other.sickCount))
			return false;
		return true;
	}
	
	
}
