package com.oversea.shipping.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


@Entity
@Table(name="user")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@OneToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name="customer_id")
	private Customer customer;
	
	@ManyToMany
    private Collection<Role> roles; //Role.name: MEMBER, EMPLOYEE, ADMIN
	
	private String username;
	
	private String password;
	
	@Transient
    private String passwordConfirm;

	private boolean active;
	
	@Column(name="reset_password")
	private boolean resetPassword;
	
	
	@Column(name = "reset_expiry", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date resetExpiry;


	public int getId() { 
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean hasRole(String roleName) {
		for(Role role: roles) {
			if(role.getName().equals(roleName))
				return true;
		}
		return false;
	}

	public boolean isResetPassword() {
		return resetPassword;
	}

	public void setResetPassword(boolean resetPassword) {
		this.resetPassword = resetPassword;
	}

	public Date getResetExpiry() {
		return resetExpiry;
	}

	public void setResetExpiry(Date resetExpiry) {
		this.resetExpiry = resetExpiry;
	}

}
