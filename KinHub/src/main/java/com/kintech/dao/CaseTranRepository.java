package com.kintech.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.kintech.entity.CaseTran;

@RepositoryRestResource(path="CaseTrans")
public interface CaseTranRepository extends JpaRepository<CaseTran, Integer>{

}
