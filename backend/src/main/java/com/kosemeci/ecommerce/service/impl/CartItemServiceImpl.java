package com.kosemeci.ecommerce.service.impl;

import org.springframework.stereotype.Service;

import com.kosemeci.ecommerce.entity.CartItem;
import com.kosemeci.ecommerce.entity.User;
import com.kosemeci.ecommerce.repository.CartItemRepository;
import com.kosemeci.ecommerce.service.CartItemService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService{

    private final CartItemRepository cartItemRepository;

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws Exception {
        
        CartItem dbCartItem = findCartItemById(id);
        User cartItemUser = dbCartItem.getCart().getUser();

        if(cartItemUser.getId().equals(userId)){
            
            dbCartItem.setQuantity(cartItem.getQuantity());
            dbCartItem.setMrpPrice(dbCartItem.getQuantity()*dbCartItem.getProduct().getMrpPrice());
            dbCartItem.setSellingPrice(dbCartItem.getQuantity()*dbCartItem.getProduct().getSellingPrice());

            return cartItemRepository.save(dbCartItem);
        }

        throw new Exception("You cant update this cartItem");
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws Exception {
        
        CartItem cartItem = findCartItemById(cartItemId);
        User cartItemUser = cartItem.getCart().getUser();

        if(cartItemUser.getId().equals(userId)){
            cartItemRepository.delete(cartItem);
        }
        else throw new Exception("You cant delete this item");
    }

    @Override
    public CartItem findCartItemById(Long id) throws Exception {
        
        return cartItemRepository.findById(id).orElseThrow(()->new Exception("Cart item not found this id - "+ id));
    }
    
}
