package com.lean.Spring.hb.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lean.Spring.hb.entity.Customer;



@Repository
public class CustomerDAOJpaImpl implements CustomerDAO {

	private EntityManager entityManager;
	
	@Autowired
	public CustomerDAOJpaImpl(EntityManager theEntityManager)
	{
		entityManager = theEntityManager;
	}
			
	@Override
	public List<Customer> getCustomers() {
		
				
		// create a query  ... sort by last name
		TypedQuery<Customer> theQuery = 
				entityManager.createQuery("from Customer order by lastName",
											Customer.class);
		
		// execute query and get result list
		List<Customer> customers = theQuery.getResultList();
				
		// return the results		
		return customers;
	}

	@Override
	public void saveCustomer(Customer theCustomer) {

		// save/upate the customer ... finally LOL
		entityManager.merge(theCustomer);
		
	}

	@Override
	public Customer getCustomer(int theId) {

		// now retrieve/read from database using the primary key
		Customer theCustomer = entityManager.find(Customer.class, theId);
		
		return theCustomer;
	}

	@Override
	public void deleteCustomer(int theId) {

		// delete object with primary key
		Query theQuery = 
				entityManager.createQuery("delete from Customer where id=:customerId");
		theQuery.setParameter("customerId", theId);
		
		theQuery.executeUpdate();		
	}

}











