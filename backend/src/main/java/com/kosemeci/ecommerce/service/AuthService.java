package com.kosemeci.ecommerce.service;

import com.kosemeci.ecommerce.request.LoginRequest;
import com.kosemeci.ecommerce.response.AuthResponse;
import com.kosemeci.ecommerce.response.SignupRequest;

public interface  AuthService {
    
    void sendLoginOtp(String email);
    String createUser(SignupRequest request);
    AuthResponse login(LoginRequest request);
}
