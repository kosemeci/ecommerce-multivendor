package com.kosemeci.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosemeci.ecommerce.entity.Seller;

public interface  SellerRepository extends JpaRepository<Seller, Long>{
    
    Seller findByEmail(String email);
}
