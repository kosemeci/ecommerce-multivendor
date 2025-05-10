package com.kosemeci.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosemeci.ecommerce.entity.Cart;
import com.kosemeci.ecommerce.entity.CartItem;
import com.kosemeci.ecommerce.entity.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{
    
    CartItem findByCartAndProductAndSize(Cart cart,Product product,String size);
}
