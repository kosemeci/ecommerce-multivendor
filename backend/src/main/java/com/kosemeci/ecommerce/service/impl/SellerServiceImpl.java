package com.kosemeci.ecommerce.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kosemeci.ecommerce.config.JwtProvider;
import com.kosemeci.ecommerce.domain.AccountStatus;
import com.kosemeci.ecommerce.domain.USER_ROLE;
import com.kosemeci.ecommerce.entity.Address;
import com.kosemeci.ecommerce.entity.Seller;
import com.kosemeci.ecommerce.repository.AddressRepository;
import com.kosemeci.ecommerce.repository.SellerRepository;
import com.kosemeci.ecommerce.service.SellerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService{

    private final SellerRepository sellerRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;

    @Override
    public Seller getSellerByToken(String token) {

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

        Address savedAddress = addressRepository.save(seller.getPickUpAddress());

        Seller newSeller = new Seller();
        newSeller.setEmail(seller.getEmail());
        newSeller.setPassword(passwordEncoder.encode(seller.getPassword()));
        newSeller.setPickUpAddress(savedAddress);
        newSeller.setRole(USER_ROLE.ROLE_SELLER);
        newSeller.setGSTIN(seller.getGSTIN());
        newSeller.setMobile(seller.getMobile());
        newSeller.setAccountStatus(seller.getAccountStatus());
        newSeller.setBankDetails(seller.getBankDetails());
        newSeller.setBusinessDetails(seller.getBusinessDetails());
        
        return sellerRepository.save(newSeller);
    }

    @Override
    public Seller getSellerById(Long id) throws Exception {
        return sellerRepository.findById(id).orElseThrow(()->new Exception("dont found with this id" + id));
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

        return sellerRepository.findByAccountStatus(status);
    }

    @Override
    public Seller updateSeller(Long id, Seller seller) throws Exception {

        Seller existingSeller = getSellerById(id);

        //null değilse diye atamalar yapılacak1 6.00.00
        return existingSeller;
    }

    @Override
    public String deleteSeller(Long id) throws Exception {

        Seller seller = getSellerById(id);
        sellerRepository.delete(seller);
        return "User delete successfully.";
    }

    @Override
    public Seller verifyEmail(String email, String otp) {

        Seller seller = getSellerByEmail(email);
        seller.setEmailVerified(true);
        
        return sellerRepository.save(seller);
    }

    @Override
    public Seller updateSellerAccountStatus(Long sellerId, AccountStatus status) throws Exception {

        Seller seller = getSellerById(sellerId);
        seller.setAccountStatus(status);
        
        return sellerRepository.save(seller);    }

    
}
