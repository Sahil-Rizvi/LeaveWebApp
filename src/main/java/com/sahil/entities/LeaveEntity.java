package com.sahil.entities;

import java.util.Date;

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

import com.sahil.enums.LeaveStatus;
import com.sahil.enums.LeaveType;


@Entity(name="leave_details")
public class LeaveEntity {
   
	@Id
	@Column(name="leave_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int leaveId;
	
	@ManyToOne
	@JoinColumn(name="employee_code",nullable=false)
    private EmployeeEntity employeeEntity;
    
	@ManyToOne
	@JoinColumn(name="manager_code")
    private EmployeeEntity managerEntity;
	
	@ManyToOne
	@JoinColumn(name="currently_with")
    private EmployeeEntity currentlyManager;
	
    @Temporal(TemporalType.DATE)
    @Column(name="from_date",nullable=false)
    private Date fromDate;
    
    @Temporal(TemporalType.DATE)
    @Column(name="to_date")
    private Date toDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="applied_on",nullable=false)
    private Date appliedOn;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="approved_on")
    private Date approvedOn;
    
    @Enumerated(EnumType.STRING)
    @Column(name="leave_type",nullable=false)
    private LeaveType leaveType;

    @Enumerated(EnumType.STRING)
    @Column(name="leave_status",nullable=false)
    private LeaveStatus leaveStatus;
    
    @Column(name="rejection_reason")
    private String rejectionReason;

	public int getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(int leaveId) {
		this.leaveId = leaveId;
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

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Date getAppliedOn() {
		return appliedOn;
	}

	public void setAppliedOn(Date appliedOn) {
		this.appliedOn = appliedOn;
	}

	public Date getApprovedOn() {
		return approvedOn;
	}

	public void setApprovedOn(Date approvedOn) {
		this.approvedOn = approvedOn;
	}

	public LeaveType getLeaveType() {
		return leaveType;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public void setLeaveType(LeaveType leaveType) {
		this.leaveType = leaveType;
	}

	public LeaveStatus getLeaveStatus() {
		return leaveStatus;
	}

	public void setLeaveStatus(LeaveStatus leaveStatus) {
		this.leaveStatus = leaveStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appliedOn == null) ? 0 : appliedOn.hashCode());
		result = prime * result + ((approvedOn == null) ? 0 : approvedOn.hashCode());
		result = prime * result + ((fromDate == null) ? 0 : fromDate.hashCode());
		result = prime * result + leaveId;
		result = prime * result + ((leaveStatus == null) ? 0 : leaveStatus.hashCode());
		result = prime * result + ((leaveType == null) ? 0 : leaveType.hashCode());
		result = prime * result + ((rejectionReason == null) ? 0 : rejectionReason.hashCode());
		result = prime * result + ((toDate == null) ? 0 : toDate.hashCode());
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
		LeaveEntity other = (LeaveEntity) obj;
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
		if (fromDate == null) {
			if (other.fromDate != null)
				return false;
		} else if (!fromDate.equals(other.fromDate))
			return false;
		if (leaveId != other.leaveId)
			return false;
		if (leaveStatus != other.leaveStatus)
			return false;
		if (leaveType != other.leaveType)
			return false;
		if (rejectionReason == null) {
			if (other.rejectionReason != null)
				return false;
		} else if (!rejectionReason.equals(other.rejectionReason))
			return false;
		if (toDate == null) {
			if (other.toDate != null)
				return false;
		} else if (!toDate.equals(other.toDate))
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
		
		return "LeaveEntity [leaveId=" + leaveId + ", employeeEntity=" + employeeEntity.getEmployeeCode() + ", managerEntity="
				+ manager + ", currentlyManager=" + currentlyWith + ", fromDate=" + fromDate + ", toDate="
				+ toDate + ", appliedOn=" + appliedOn + ", approvedOn=" + approvedOn + ", leaveType=" + leaveType
				+ ", leaveStatus=" + leaveStatus + ", rejectionReason=" + rejectionReason + "]";
	}

	
	

}
