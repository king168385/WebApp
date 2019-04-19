package com.kintech.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CaseTran")
public class CaseTran {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="accountNumber")
	private String accountNumber;
	
	@Column(name="pan")
	private String pan;
	
	@Column(name="tranId")
	private Date tranId;
	
	@Column(name="tranAmount")
	private BigDecimal tranAmount;
	
	@Column(name="tranDt")
	private Date tranDt;

	@Column(name="arn")
	private String arn;
	
}
