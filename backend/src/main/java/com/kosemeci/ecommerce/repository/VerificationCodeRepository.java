package com.kosemeci.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosemeci.ecommerce.entity.VerificationCode;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long>{
    
    VerificationCode findByEmail(String email);
    VerificationCode findByOtp(String otp);
}
