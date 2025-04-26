package com.kosemeci.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosemeci.ecommerce.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{
    
}
