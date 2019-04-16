package com.lean.Spring.hb.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lean.Spring.hb.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

}
