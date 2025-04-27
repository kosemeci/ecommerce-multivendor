package com.kosemeci.ecommerce.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kosemeci.ecommerce.domain.AccountStatus;
import com.kosemeci.ecommerce.entity.Seller;
import com.kosemeci.ecommerce.service.SellerService;

@Service
public class SellerServiceImpl implements SellerService{

    @Override
    public Seller getSellerProfile(String token) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSellerProfile'");
    }

    @Override
    public Seller createSeller(Seller seller) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createSeller'");
    }

    @Override
    public Seller getSellerById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSellerById'");
    }

    @Override
    public Seller getSellerByEmail(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSellerByEmail'");
    }

    @Override
    public List<Seller> getAllSellers(AccountStatus status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllSellers'");
    }

    @Override
    public Seller updateSeller(Long id, Seller seller) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateSeller'");
    }

    @Override
    public void deleteSeller(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteSeller'");
    }

    @Override
    public Seller verifyEmail(String email, String otp) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'verifyEmail'");
    }

    @Override
    public Seller updateSellerAccountStatus(Long sellerId, AccountStatus status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateSellerAccountStatus'");
    }

    
}
