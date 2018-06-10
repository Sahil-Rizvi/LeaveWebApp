package com.sahil.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sahil.enums.CompOffStatus;

@Entity(name="comp_off_details")
public class CompOffEntity {

	@Id
	@Column(name="comp_off_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int compOffId;
		
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="employee_code",nullable=false)
	private EmployeeEntity employeeEntity;
	    
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="manager_code")
	private EmployeeEntity managerEntity;
		
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="currently_with")
	private EmployeeEntity currentlyManager;
		
	@Temporal(TemporalType.DATE)
	@Column(name="applied_on",nullable=false)
	private Date appliedOn;
	    
	@Temporal(TemporalType.DATE)
	@Column(name="for_date",nullable=false)
	private Date forDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name="approved_on")
	private Date approvedOn;
	
	@Enumerated(EnumType.STRING)
	@Column(name="comp_off_status",nullable=false)
	private CompOffStatus compOffStatus;
	
	@Column(name="rejection_reason")
    private String rejectionReason;

	public int getCompOffId() {
		return compOffId;
	}

	public void setCompOffId(int compOffId) {
		this.compOffId = compOffId;
	}

	public EmployeeEntity getEmployeeEntity() {
		return employeeEntity;
	}

	public void setEmployeeEntity(EmployeeEntity employeeEntity) {
		this.employeeEntity = employeeEntity;
	}

	public EmployeeEntity getManagerEntity() {
		return managerEntity;
	}

	public void setManagerEntity(EmployeeEntity managerEntity) {
		this.managerEntity = managerEntity;
	}

	public EmployeeEntity getCurrentlyManager() {
		return currentlyManager;
	}

	public void setCurrentlyManager(EmployeeEntity currentlyManager) {
		this.currentlyManager = currentlyManager;
	}

	public Date getAppliedOn() {
		return appliedOn;
	}

	public void setAppliedOn(Date appliedOn) {
		this.appliedOn = appliedOn;
	}

	public Date getForDate() {
		return forDate;
	}

	public void setForDate(Date forDate) {
		this.forDate = forDate;
	}

	public Date getApprovedOn() {
		return approvedOn;
	}

	public void setApprovedOn(Date approvedOn) {
		this.approvedOn = approvedOn;
	}


	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public CompOffStatus getCompOffStatus() {
		return compOffStatus;
	}

	public void setCompOffStatus(CompOffStatus compOffStatus) {
		this.compOffStatus = compOffStatus;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appliedOn == null) ? 0 : appliedOn.hashCode());
		result = prime * result + ((approvedOn == null) ? 0 : approvedOn.hashCode());
		result = prime * result + compOffId;
		result = prime * result + ((compOffStatus == null) ? 0 : compOffStatus.hashCode());
		result = prime * result + ((forDate == null) ? 0 : forDate.hashCode());
		result = prime * result + ((rejectionReason == null) ? 0 : rejectionReason.hashCode());
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
		CompOffEntity other = (CompOffEntity) obj;
		if (appliedOn == null) {
			if (other.appliedOn != null)
				return false;
		} else if (!appliedOn.equals(other.appliedOn))
			return false;
		if (approvedOn == null) {
			if (other.approvedOn != null)
				return false;
		} else if (!approvedOn.equals(other.approvedOn))
			return false;
		if (compOffId != other.compOffId)
			return false;
		if (compOffStatus != other.compOffStatus)
			return false;
				if (forDate == null) {
			if (other.forDate != null)
				return false;
		} else if (!forDate.equals(other.forDate))
			return false;
		if (rejectionReason == null) {
			if (other.rejectionReason != null)
				return false;
		} else if (!rejectionReason.equals(other.rejectionReason))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String manager = " ";
		if(managerEntity!=null){
			manager = managerEntity.getEmployeeCode();
		}
		String currentlyWith = "";
		if(currentlyManager!=null){
			currentlyWith = currentlyManager.getEmployeeCode();
		}
		
		return "CompOffEntity [compOffId=" + compOffId + ", employeeEntity=" + employeeEntity.getEmployeeCode() + ", managerEntity="
				+ manager + ", currentlyManager=" + currentlyWith + ", appliedOn=" + appliedOn + ", forDate="
				+ forDate + ", approvedOn=" + approvedOn + ", compOffStatus=" + compOffStatus + ", rejectionReason="
				+ rejectionReason + "]";
	}
	
	

}
