package com.sahil.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Type;


@Entity(name="employee_details")
public class EmployeeEntity {

	@Id
	@Column(name="employee_code",nullable=false)
	private String employeeCode;

	@Column(nullable=false)
	private String name;
	
	@ManyToOne
	@JoinColumn(name="designation_id")
	private DesignationEntity designationEntity; 
	
	@ManyToOne
	@JoinColumn(name="department_id")
	private DepartmentEntity departmentEntity;
	
	@Temporal(TemporalType.DATE)
	@Column(name="date_of_joining",nullable=false)
	private Date dateofJoining;
	
	@Temporal(TemporalType.DATE)
	@Past
	@Column(name="date_of_birth",nullable=false)
	private Date dateofBirth;	
	
	@Column(nullable=false)
	private String password;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="manager_code")
	private EmployeeEntity manager;
	
	@Column(nullable=false,columnDefinition="TINYINT")
	@Type(type="org.hibernate.type.NumericBooleanType")
	private boolean enabled;
	
	@Cascade({CascadeType.ALL})
	@OneToMany(fetch=FetchType.EAGER,mappedBy="employeeEntity",orphanRemoval=true)
	private Set<LeaveEntity> leaves = new HashSet<>();

	@Cascade({CascadeType.ALL})
	@OneToMany(fetch=FetchType.EAGER,mappedBy="employeeEntity",orphanRemoval=true)
	private Set<CompOffEntity> compOffs = new HashSet<>();

	@Cascade({CascadeType.ALL})
	@OneToOne(fetch=FetchType.LAZY,mappedBy="employeeEntity",orphanRemoval=true)
    private ContactEntity contactEntity;
	
	@Cascade({CascadeType.ALL})
	@OneToOne(fetch=FetchType.LAZY,mappedBy="employeeEntity",orphanRemoval=true)
	private LeaveCountEntity leaveCountEntity;
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "employee_role",
            joinColumns = @JoinColumn(name = "employee_code"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
	private Set<RoleEntity> userRoles = new HashSet<>();

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DesignationEntity getDesignationEntity() {
		return designationEntity;
	}

	public void setDesignationEntity(DesignationEntity designationEntity) {
		this.designationEntity = designationEntity;
	}

	public DepartmentEntity getDepartmentEntity() {
		return departmentEntity;
	}

	public void setDepartmentEntity(DepartmentEntity departmentEntity) {
		this.departmentEntity = departmentEntity;
	}

	public Date getDateofJoining() {
		return dateofJoining;
	}

	public void setDateofJoining(Date dateofJoining) {
		this.dateofJoining = dateofJoining;
	}

	public Date getDateofBirth() {
		return dateofBirth;
	}

	public void setDateofBirth(Date dateofBirth) {
		this.dateofBirth = dateofBirth;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public EmployeeEntity getManager() {
		return manager;
	}

	public void setManager(EmployeeEntity manager) {
		this.manager = manager;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<LeaveEntity> getLeaves() {
		return leaves;
	}

	public void setLeaves(Set<LeaveEntity> leaves) {
		this.leaves = leaves;
	}

	public void addLeaves(LeaveEntity leaveEntity){
		leaves.add(leaveEntity);
	}
	
	public void removeLeaves(LeaveEntity leaveEntity){
		leaves.remove(leaveEntity);
	}
	
	public Set<CompOffEntity> getCompOffs() {
		return compOffs;
	}

	public void setCompOffs(Set<CompOffEntity> compOffs) {
		this.compOffs = compOffs;
	}
	
	public void addCompOffs(CompOffEntity compOffEntity){
		compOffs.add(compOffEntity);
	}
	
	public void removeCompOffs(CompOffEntity compOffEntity){
		compOffs.remove(compOffEntity);
	}

	public ContactEntity getContactEntity() {
		return contactEntity;
	}

	public void setContactEntity(ContactEntity contactEntity) {
		this.contactEntity = contactEntity;
	}

	public LeaveCountEntity getLeaveCountEntity() {
		return leaveCountEntity;
	}

	public void setLeaveCountEntity(LeaveCountEntity leaveCountEntity) {
		this.leaveCountEntity = leaveCountEntity;
	}

	public Set<RoleEntity> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<RoleEntity> userRoles) {
		this.userRoles = userRoles;
	}  
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((compOffs == null) ? 0 : compOffs.hashCode());
		result = prime * result + ((contactEntity == null) ? 0 : contactEntity.hashCode());
		result = prime * result + ((dateofBirth == null) ? 0 : dateofBirth.hashCode());
		result = prime * result + ((dateofJoining == null) ? 0 : dateofJoining.hashCode());
		result = prime * result + ((departmentEntity == null) ? 0 : departmentEntity.hashCode());
		result = prime * result + ((designationEntity == null) ? 0 : designationEntity.hashCode());
		result = prime * result + ((employeeCode == null) ? 0 : employeeCode.hashCode());
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result + ((leaveCountEntity == null) ? 0 : leaveCountEntity.hashCode());
		result = prime * result + ((manager == null) ? 0 : manager.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((userRoles == null) ? 0 : userRoles.hashCode());
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
		EmployeeEntity other = (EmployeeEntity) obj;
		if (compOffs == null) {
			if (other.compOffs != null)
				return false;
		} else if (!compOffs.equals(other.compOffs))
			return false;
		if (contactEntity == null) {
			if (other.contactEntity != null)
				return false;
		} else if (!contactEntity.equals(other.contactEntity))
			return false;
		if (dateofBirth == null) {
			if (other.dateofBirth != null)
				return false;
		} else if (!dateofBirth.equals(other.dateofBirth))
			return false;
		if (dateofJoining == null) {
			if (other.dateofJoining != null)
				return false;
		} else if (!dateofJoining.equals(other.dateofJoining))
			return false;
		if (departmentEntity == null) {
			if (other.departmentEntity != null)
				return false;
		} else if (!departmentEntity.equals(other.departmentEntity))
			return false;
		if (designationEntity == null) {
			if (other.designationEntity != null)
				return false;
		} else if (!designationEntity.equals(other.designationEntity))
			return false;
		if (employeeCode == null) {
			if (other.employeeCode != null)
				return false;
		} else if (!employeeCode.equals(other.employeeCode))
			return false;
		if (enabled != other.enabled)
			return false;
		if (leaveCountEntity == null) {
			if (other.leaveCountEntity != null)
				return false;
		} else if (!leaveCountEntity.equals(other.leaveCountEntity))
			return false;
		if (leaves == null) {
			if (other.leaves != null)
				return false;
		} else if (!leaves.equals(other.leaves))
			return false;
		if (manager == null) {
			if (other.manager != null)
				return false;
		} else if (!manager.equals(other.manager))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userRoles == null) {
			if (other.userRoles != null)
				return false;
		} else if (!userRoles.equals(other.userRoles))
			return false;
		return true;
	}

}
