package com.kosemeci.ecommerce.service;

import com.kosemeci.ecommerce.entity.User;

public interface UserService {
    
    User findUserByJwt(String token) throws Exception;
    User findUserByEmail(String email) throws Exception;
    
}
