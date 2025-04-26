package com.kosemeci.ecommerce.service;

import com.kosemeci.ecommerce.response.SignupRequest;

public interface  AuthService {
    
    String createUser(SignupRequest request);
}
