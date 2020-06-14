package com.oversea.shipping.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oversea.shipping.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}