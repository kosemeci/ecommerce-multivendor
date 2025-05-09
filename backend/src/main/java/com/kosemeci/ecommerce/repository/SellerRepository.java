package com.kosemeci.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosemeci.ecommerce.entity.Seller;
import java.util.List;
import com.kosemeci.ecommerce.domain.AccountStatus;


public interface  SellerRepository extends JpaRepository<Seller, Long>{
    
    Seller findByEmail(String email);
    List<Seller> findByAccountStatus(AccountStatus accountStatus);
}
