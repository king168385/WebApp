package com.oversea.shipping.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oversea.shipping.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
}
