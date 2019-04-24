package com.kintech.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.kintech.entity.CaseItem;

@RepositoryRestResource(path="CaseItems")
public interface CaseItemRepository extends JpaRepository<CaseItem, Integer>{

}
