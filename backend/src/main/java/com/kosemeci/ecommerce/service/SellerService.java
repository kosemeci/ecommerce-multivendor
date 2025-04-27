package com.kosemeci.ecommerce.service;

import java.util.List;

import com.kosemeci.ecommerce.domain.AccountStatus;
import com.kosemeci.ecommerce.entity.Seller;

public interface  SellerService {
    
    Seller getSellerProfile(String token);
    Seller createSeller(Seller seller);
    Seller getSellerById(Long id);
    Seller getSellerByEmail(String email);
    List<Seller> getAllSellers(AccountStatus status);
    Seller updateSeller(Long id,Seller seller);
    void deleteSeller(Long id);
    Seller verifyEmail(String email,String otp);
    Seller updateSellerAccountStatus(Long sellerId,AccountStatus status);
}
