package com.kintech.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CaseItem")
public class CaseItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "status")
	private String status;

	@Column(name = "openDt")
	private Date openDt;

	@Column(name = "closeDt")
	private Date closeDt;

	@Column(name = "accountNumber")
	private String accountNumber;

	@Column(name = "pan")
	private String pan;

	@Column(name = "tranId")
	private Date tranId;

	@Column(name = "tranAmount")
	private BigDecimal tranAmount;

	@Column(name = "tranDt")
	private Date tranDt;

	@Column(name = "claimAmount")
	private BigDecimal claimAmount;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "caseId")
	private Case caseObj;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "caseItem", cascade = { CascadeType.ALL })
	private List<CaseTran> tranList;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getOpenDt() {
		return openDt;
	}

	public void setOpenDt(Date openDt) {
		this.openDt = openDt;
	}

	public Date getCloseDt() {
		return closeDt;
	}

	public void setCloseDt(Date closeDt) {
		this.closeDt = closeDt;
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

	public BigDecimal getClaimAmount() {
		return claimAmount;
	}

	public void setClaimAmount(BigDecimal claimAmount) {
		this.claimAmount = claimAmount;
	}

	public Case getCaseObj() {
		return caseObj;
	}

	public void setCaseObj(Case caseObj) {
		this.caseObj = caseObj;
	}

	public List<CaseTran> getTranList() {
		return tranList;
	}

	public void setTranList(List<CaseTran> tranList) {
		this.tranList = tranList;
	}
}
