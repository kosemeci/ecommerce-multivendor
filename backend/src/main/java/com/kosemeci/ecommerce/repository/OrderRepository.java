package com.kosemeci.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosemeci.ecommerce.entity.Order;

public interface  OrderRepository extends JpaRepository<Order, Long>{
    
    List<Order> findByUserId (Long userId);
    List<Order> findBySellerId (Long sellerId);
}
