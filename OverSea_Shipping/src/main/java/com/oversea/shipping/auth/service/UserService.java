package com.oversea.shipping.auth.service;

import com.oversea.shipping.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
