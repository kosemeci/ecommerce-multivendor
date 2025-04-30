package com.kosemeci.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kosemeci.ecommerce.domain.USER_ROLE;
import com.kosemeci.ecommerce.request.LoginOtpRequest;
import com.kosemeci.ecommerce.request.LoginRequest;
import com.kosemeci.ecommerce.response.ApiResponse;
import com.kosemeci.ecommerce.response.AuthResponse;
import com.kosemeci.ecommerce.response.SignupRequest;
import com.kosemeci.ecommerce.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUser(@RequestBody SignupRequest request) throws Exception{

        String jwt = authService.createUser(request);
        AuthResponse response = new AuthResponse();
        response.setJwt(jwt);
        response.setMessage("Register successfully");
        response.setRole(USER_ROLE.ROLE_CUSTOMER);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/send/otp")
    public ResponseEntity<ApiResponse> sendOtpHandle(@RequestBody LoginOtpRequest request) throws Exception{

        authService.sendLoginOtp(request.getEmail(),request.getRole());
        ApiResponse response = new ApiResponse();
        response.setMessage("otp send successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest request) throws Exception{

        AuthResponse authResponse = authService.login(request);
        return ResponseEntity.ok(authResponse);
    }

}
