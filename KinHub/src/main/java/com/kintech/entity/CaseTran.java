package com.kintech.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "caseItem")
	private CaseItem caseItem;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public Date getTranId() {
		return tranId;
	}

	public void setTranId(Date tranId) {
		this.tranId = tranId;
	}

	public BigDecimal getTranAmount() {
		return tranAmount;
	}

	public void setTranAmount(BigDecimal tranAmount) {
		this.tranAmount = tranAmount;
	}

	public Date getTranDt() {
		return tranDt;
	}

	public void setTranDt(Date tranDt) {
		this.tranDt = tranDt;
	}

	public String getArn() {
		return arn;
	}

	public void setArn(String arn) {
		this.arn = arn;
	}

	public CaseItem getCaseItem() {
		return caseItem;
	}

	public void setCaseItem(CaseItem caseItem) {
		this.caseItem = caseItem;
	}
	
}
