package com.kosemeci.ecommerce.service;

import com.kosemeci.ecommerce.entity.User;

public interface UserService {
    
    public User findUserByJwt(String token);
    
}
