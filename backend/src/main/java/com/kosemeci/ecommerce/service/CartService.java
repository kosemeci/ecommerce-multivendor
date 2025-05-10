package com.kosemeci.ecommerce.service;

import com.kosemeci.ecommerce.entity.Cart;
import com.kosemeci.ecommerce.entity.CartItem;
import com.kosemeci.ecommerce.entity.Product;
import com.kosemeci.ecommerce.entity.User;

public interface CartService {
    
    public CartItem addCartItem(
        User user,
        Product product,
        String size,
        int quantity
    );
    public Cart findUserCart(User user);
}
