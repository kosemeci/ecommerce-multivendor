package com.kosemeci.ecommerce.service;

import java.util.List;

import com.kosemeci.ecommerce.domain.AccountStatus;
import com.kosemeci.ecommerce.entity.Seller;
import com.kosemeci.ecommerce.exception.SellerException;

public interface  SellerService {
    
    Seller getSellerByToken(String token) throws SellerException;
    Seller createSeller(Seller seller);
    Seller getSellerById(Long id) throws SellerException;
    Seller getSellerByEmail(String email) throws SellerException;
    List<Seller> getAllSellers(AccountStatus status);
    Seller updateSeller(Long id,Seller seller) throws SellerException;
    String deleteSeller(Long id) throws SellerException;
    Seller verifyEmail(String email,String otp) throws SellerException;
    Seller updateSellerAccountStatus(Long sellerId,AccountStatus status) throws SellerException;
}
