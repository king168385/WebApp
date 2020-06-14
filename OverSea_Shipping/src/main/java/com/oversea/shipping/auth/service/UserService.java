package com.oversea.shipping.auth.service;

import com.oversea.shipping.model.User;

public interface UserService {
    public void save(User user);
    
    public void update(User user);

    public User findByUsername(String username);
    
    public User getCurrentUser();
}
