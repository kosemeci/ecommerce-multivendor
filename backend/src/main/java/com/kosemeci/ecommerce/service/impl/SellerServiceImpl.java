package com.kosemeci.ecommerce.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kosemeci.ecommerce.config.JwtProvider;
import com.kosemeci.ecommerce.domain.AccountStatus;
import com.kosemeci.ecommerce.domain.USER_ROLE;
import com.kosemeci.ecommerce.entity.Address;
import com.kosemeci.ecommerce.entity.BankDetails;
import com.kosemeci.ecommerce.entity.BusinessDetails;
import com.kosemeci.ecommerce.entity.Seller;
import com.kosemeci.ecommerce.exception.SellerException;
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
    public Seller getSellerById(Long id) throws SellerException {
        return sellerRepository.findById(id).orElseThrow(()->new SellerException("dont found with this id" + id));
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
    public Seller updateSeller(Long id, Seller updatedData) throws SellerException {

        Seller existingSeller = getSellerById(id);

        // Temel alanlar
        if (updatedData.getSellerName() != null)
            existingSeller.setSellerName(updatedData.getSellerName());

        if (updatedData.getMobile() != null)
            existingSeller.setMobile(updatedData.getMobile());

        if (updatedData.getPassword() != null)
            existingSeller.setPassword(updatedData.getPassword());

        if (updatedData.getGSTIN() != null)
            existingSeller.setGSTIN(updatedData.getGSTIN());

        // BusinessDetails güncelleme
        if (updatedData.getBusinessDetails() != null) {
            BusinessDetails bd = updatedData.getBusinessDetails();
            BusinessDetails existingBD = existingSeller.getBusinessDetails();

            if (bd.getBusinessName() != null)
                existingBD.setBusinessName(bd.getBusinessName());
            if (bd.getBusinessMail() != null)
                existingBD.setBusinessMail(bd.getBusinessMail());
            if (bd.getBusinessMobile() != null)
                existingBD.setBusinessMobile(bd.getBusinessMobile());
            if (bd.getBusinessAddress() != null)
                existingBD.setBusinessAddress(bd.getBusinessAddress());
            if (bd.getLogo() != null)
                existingBD.setLogo(bd.getLogo());
            if (bd.getBanner() != null)
                existingBD.setBanner(bd.getBanner());
        }

        // BankDetails güncelleme
        if (updatedData.getBankDetails() != null) {
            BankDetails bd = updatedData.getBankDetails();
            BankDetails existingBank = existingSeller.getBankDetails();

            if (bd.getAccountNumber() != null)
                existingBank.setAccountNumber(bd.getAccountNumber());
            if (bd.getAccountHolderName() != null)
                existingBank.setAccountHolderName(bd.getAccountHolderName());
            if (bd.getIfscCode() != null)
                existingBank.setIfscCode(bd.getIfscCode());
        }

        // PickUpAddress güncelleme
        if (updatedData.getPickUpAddress() != null) {
            Address newAddr = updatedData.getPickUpAddress();
            Address existingAddr = existingSeller.getPickUpAddress();

            if (newAddr.getName() != null)
                existingAddr.setName(newAddr.getName());
            if (newAddr.getLocality() != null)
                existingAddr.setLocality(newAddr.getLocality());
            if (newAddr.getAddress() != null)
                existingAddr.setAddress(newAddr.getAddress());
            if (newAddr.getCity() != null)
                existingAddr.setCity(newAddr.getCity());
            if (newAddr.getState() != null)
                existingAddr.setState(newAddr.getState());
            if (newAddr.getPinCode() != null)
                existingAddr.setPinCode(newAddr.getPinCode());
            if (newAddr.getMobile() != null)
                existingAddr.setMobile(newAddr.getMobile());
        }

        Seller updatedSeller = sellerRepository.save(existingSeller);
        return updatedSeller;
    }

    @Override
    public String deleteSeller(Long id) throws SellerException {

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
    public Seller updateSellerAccountStatus(Long sellerId, AccountStatus status) throws SellerException {

        Seller seller = getSellerById(sellerId);
        seller.setAccountStatus(status);
        
        return sellerRepository.save(seller);    
    }
    
}
