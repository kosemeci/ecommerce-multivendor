package com.kosemeci.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kosemeci.ecommerce.entity.VerificationCode;
import com.kosemeci.ecommerce.repository.VerificationCodeRepository;
import com.kosemeci.ecommerce.request.LoginRequest;
import com.kosemeci.ecommerce.response.AuthResponse;
import com.kosemeci.ecommerce.service.AuthService;
import com.kosemeci.ecommerce.service.SellerService;

import lombok.RequiredArgsConstructor;



@RestController
@RequiredArgsConstructor
@RequestMapping("/seller")
public class SellerController {

    private SellerService sellerService;
    private VerificationCodeRepository verificationCodeRepository;
    private AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        
        String email = request.getEmail();
        String otp = request.getOtp();

        VerificationCode verificationCode = verificationCodeRepository.findByEmail(email);

        if(verificationCode==null || !verificationCode.getOtp().equals(otp)){
            try {
                throw new Exception("wrong code bro");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        request.setEmail("seller_"+email);
        AuthResponse response = authService.login(request);

        return ResponseEntity.ok(response);
    }
        
}
