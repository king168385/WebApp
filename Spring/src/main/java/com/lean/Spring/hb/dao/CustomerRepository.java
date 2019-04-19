package com.lean.Spring.hb.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.lean.Spring.hb.entity.Customer;

@RepositoryRestResource(path="customers")
public interface CustomerRepository extends JpaRepository<Customer, Integer>{

}
