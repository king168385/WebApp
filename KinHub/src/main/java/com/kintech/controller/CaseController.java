package com.kintech.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kintech.dao.CaseItemRepository;
import com.kintech.dao.CaseRepository;
import com.kintech.dao.CaseTranRepository;
import com.kintech.entity.Case;
import com.kintech.entity.CaseItem;
import com.kintech.entity.CaseTran;


@Controller
@RequestMapping("/case")
public class CaseController {

	@Autowired
	private CaseRepository caseDao;
	
	@Autowired
	private CaseItemRepository caseItemDao;
	
	@Autowired
	private CaseTranRepository caseTranDao;
	
	@GetMapping("/list")
	public String listCustomers(Model theModel) {
		
		// get customers from the service
		List<Case> caseList = caseDao.findAll();
				
		// add the customers to the model
		theModel.addAttribute("cases", caseList);
		
		return "list-cases";
	}
	
	
	@GetMapping("/createCase")
	public String showFormForAdd(Model theModel) {
		
		// create model attribute to bind form data
		CaseTran caseTran = new CaseTran();
		
		theModel.addAttribute("caseTran", caseTran);
		
		return "caseTran-form";
	}
	
	@PostMapping("/createCase")
	public String saveCustomer(@ModelAttribute("caseTran") CaseTran caseTran) {
		
		CaseItem caseItem = new CaseItem();
		caseItem.setStatus("Pending");
		caseItem.setOpenDt(new Date());
		caseItem.setAccountNumber(caseTran.getAccountNumber());
		caseItem.setPan(caseTran.getPan());
		caseItem.setTranId(caseTran.getTranId());
		caseItem.setTranAmount(caseTran.getTranAmount());
		caseItem.getTranList().add(caseTran);
		
		
		// save the customer using our service
		caseTranDao.saveAndFlush(caseTran);
		
		return "redirect:/case/list";
	}
}
