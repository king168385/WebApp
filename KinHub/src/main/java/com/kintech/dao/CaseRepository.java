package com.kintech.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.kintech.entity.Case;

@RepositoryRestResource(path="Cases")
public interface CaseRepository extends JpaRepository<Case, Integer>{

}
