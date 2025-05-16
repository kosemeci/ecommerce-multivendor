package com.kosemeci.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosemeci.ecommerce.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{
    
}
