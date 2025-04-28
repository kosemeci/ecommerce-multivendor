package com.kosemeci.ecommerce.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kosemeci.ecommerce.config.JwtProvider;
import com.kosemeci.ecommerce.domain.AccountStatus;
import com.kosemeci.ecommerce.domain.USER_ROLE;
import com.kosemeci.ecommerce.entity.Seller;
import com.kosemeci.ecommerce.repository.SellerRepository;
import com.kosemeci.ecommerce.service.SellerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService{

    private final SellerRepository sellerRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Seller getSellerProfile(String token) {

        String email = jwtProvider.getEmailFromJwtToken(token);
        return getSellerByEmail(email);
    }

    @Override
    public Seller createSeller(Seller seller) {
        Seller sellerExist = sellerRepository.findByEmail(seller.getEmail());
        if(sellerExist!=null){
            try {
                throw new Exception("Seller already created with this email. Try another email or login.");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        Seller newSeller = new Seller();
        newSeller.setEmail(seller.getEmail());
        newSeller.setRole(USER_ROLE.ROLE_SELLER);

        return newSeller;

    }

    @Override
    public Seller getSellerById(Long id) {

        Seller seller = sellerRepository.findById(id).orElseThrow(()->new Exception("dont found with this id" + id));
    }

    @Override
    public Seller getSellerByEmail(String email) {

        Seller seller = sellerRepository.findByEmail(email);
        try {
            if(seller==null){
                throw new Exception("Not found with email - " + email);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return seller;
    }

    @Override
    public List<Seller> getAllSellers(AccountStatus status) {

        throw new UnsupportedOperationException("Unimplemented method 'getAllSellers'");
    }

    @Override
    public Seller updateSeller(Long id, Seller seller) {

        throw new UnsupportedOperationException("Unimplemented method 'updateSeller'");
    }

    @Override
    public void deleteSeller(Long id) {

        throw new UnsupportedOperationException("Unimplemented method 'deleteSeller'");
    }

    @Override
    public Seller verifyEmail(String email, String otp) {

        throw new UnsupportedOperationException("Unimplemented method 'verifyEmail'");
    }

    @Override
    public Seller updateSellerAccountStatus(Long sellerId, AccountStatus status) {

        throw new UnsupportedOperationException("Unimplemented method 'updateSellerAccountStatus'");
    }

    
}
