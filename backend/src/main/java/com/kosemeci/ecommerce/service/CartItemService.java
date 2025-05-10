package com.kosemeci.ecommerce.service;

import com.kosemeci.ecommerce.entity.CartItem;

public interface CartItemService {
    
    CartItem updateCartItem(Long userId,Long id,CartItem cartItem) throws Exception;
    void removeCartItem(Long userId,Long cartItemId) throws Exception;
    CartItem findCartItemById(Long id) throws Exception;
}
