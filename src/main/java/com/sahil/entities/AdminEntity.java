package com.sahil.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity(name="admin_details")
public class AdminEntity {

	@Id
	@Column(name="admin_id",nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int adminId;

	@Column(name="name",nullable=false)
	private String name;
	
	@Temporal(TemporalType.DATE)
	@Column(name="date_of_creation",nullable=false)
	private Date dateOfCreation;
	
	@Column(name="emailId",nullable=false,unique=true)
	private String emailId;
	
	@Column(name="password",nullable=false)
	private String password;
	
	@Column(nullable=false,columnDefinition="TINYINT")
	@Type(type="org.hibernate.type.NumericBooleanType")
	private boolean enabled;

	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( 
        name = "admin_role", 
        joinColumns = @JoinColumn(
          name = "admin_id", referencedColumnName = "admin_id"), 
        inverseJoinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "role_id")) 
	private Set<RoleEntity> adminRoles = new HashSet<>();  

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<RoleEntity> getAdminRoles() {
		return adminRoles;
	}

	public void setAdminRoles(Set<RoleEntity> adminRoles) {
		this.adminRoles = adminRoles;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public Date getDateOfCreation() {
		return dateOfCreation;
	}

	public void setDateOfCreation(Date dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


}
