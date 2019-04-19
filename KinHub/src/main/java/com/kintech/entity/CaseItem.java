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
@Table(name="CaseItem")
public class CaseItem {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="status")
	private String status;
	
	@Column(name="openDt")
	private Date openDt;
	
	@Column(name="closeDt")
	private Date closeDt;
	
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
	
	@Column(name="claimAmount")
	private BigDecimal claimAmount;
}
