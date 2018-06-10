package com.sahil.models.hr;

import java.util.ArrayList;
import java.util.List;

import com.sahil.models.Response;
import com.sahil.models.hr.emp.compoffs.ApprovedCompOffs;
import com.sahil.models.hr.emp.compoffs.PendingCompOffs;
import com.sahil.models.hr.emp.compoffs.RejectedCompOffs;
import com.sahil.models.hr.emp.leaves.ApprovedLeaves;
import com.sahil.models.hr.emp.leaves.PendingLeaves;
import com.sahil.models.hr.emp.leaves.RejectedLeaves;

public class EmployeeDTO extends Response{

	private String employeeCode;

	private String employeeName;
	
	private String designation;
	
	private String dateofJoining;
	
	private String dateofBirth;	
	
	private String managerCode;
	
	private String managerName;
	
	private String department;

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	private boolean managerStatus;
	
	private List<ApprovedLeaves> approvedLeaves = new ArrayList<>();

	private List<RejectedLeaves> rejectedLeaves = new ArrayList<>();
	
	private List<PendingLeaves> pendingLeaves = new ArrayList<>();
	
	private List<ApprovedCompOffs> approvedCompOffs = new ArrayList<>();
	
	private List<RejectedCompOffs> rejectedCompOffs = new ArrayList<>();
	
	private List<PendingCompOffs> pendingCompOffs = new ArrayList<>();
	
	private ContactDTO contact;
	
	private LeaveCountDTO leaveCount;

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDateofJoining() {
		return dateofJoining;
	}

	public void setDateofJoining(String dateofJoining) {
		this.dateofJoining = dateofJoining;
	}

	public String getDateofBirth() {
		return dateofBirth;
	}

	public void setDateofBirth(String dateofBirth) {
		this.dateofBirth = dateofBirth;
	}

	public String getManagerCode() {
		return managerCode;
	}

	public void setManagerCode(String managerCode) {
		this.managerCode = managerCode;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public boolean isManagerStatus() {
		return managerStatus;
	}

	public void setManagerStatus(boolean managerStatus) {
		this.managerStatus = managerStatus;
	}

	public List<ApprovedLeaves> getApprovedLeaves() {
		return approvedLeaves;
	}

	public void setApprovedLeaves(List<ApprovedLeaves> approvedLeaves) {
		this.approvedLeaves = approvedLeaves;
	}

	public List<RejectedLeaves> getRejectedLeaves() {
		return rejectedLeaves;
	}

	public void setRejectedLeaves(List<RejectedLeaves> rejectedLeaves) {
		this.rejectedLeaves = rejectedLeaves;
	}

	public List<PendingLeaves> getPendingLeaves() {
		return pendingLeaves;
	}

	public void setPendingLeaves(List<PendingLeaves> pendingLeaves) {
		this.pendingLeaves = pendingLeaves;
	}

	public List<ApprovedCompOffs> getApprovedCompOffs() {
		return approvedCompOffs;
	}

	public void setApprovedCompOffs(List<ApprovedCompOffs> approvedCompOffs) {
		this.approvedCompOffs = approvedCompOffs;
	}

	public List<RejectedCompOffs> getRejectedCompOffs() {
		return rejectedCompOffs;
	}

	public void setRejectedCompOffs(List<RejectedCompOffs> rejectedCompOffs) {
		this.rejectedCompOffs = rejectedCompOffs;
	}

	public List<PendingCompOffs> getPendingCompOffs() {
		return pendingCompOffs;
	}

	public void setPendingCompOffs(List<PendingCompOffs> pendingCompOffs) {
		this.pendingCompOffs = pendingCompOffs;
	}

	public ContactDTO getContact() {
		return contact;
	}

	public void setContact(ContactDTO contact) {
		this.contact = contact;
	}

	public LeaveCountDTO getLeaveCount() {
		return leaveCount;
	}

	public void setLeaveCount(LeaveCountDTO leaveCount) {
		this.leaveCount = leaveCount;
	}

	@Override
	public String toString() {
		return "EmployeeDTO [employeeCode=" + employeeCode + ", employeeName=" + employeeName + ", designation="
				+ designation + ", dateofJoining=" + dateofJoining + ", dateofBirth=" + dateofBirth + ", managerCode="
				+ managerCode + ", managerName=" + managerName + ", department=" + department + ", managerStatus="
				+ managerStatus + ", approvedLeaves=" + approvedLeaves + ", rejectedLeaves=" + rejectedLeaves
				+ ", pendingLeaves=" + pendingLeaves + ", approvedCompOffs=" + approvedCompOffs + ", rejectedCompOffs="
				+ rejectedCompOffs + ", pendingCOmpOffs=" + pendingCompOffs + ", contact=" + contact + ", leaveCount="
				+ leaveCount + "]";
	}
	
}
