package com.kosemeci.ecommerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kosemeci.ecommerce.entity.Cart;
import com.kosemeci.ecommerce.entity.CartItem;
import com.kosemeci.ecommerce.entity.Product;
import com.kosemeci.ecommerce.entity.User;
import com.kosemeci.ecommerce.request.AddItemRequest;
import com.kosemeci.ecommerce.response.ApiResponse;
import com.kosemeci.ecommerce.service.CartItemService;
import com.kosemeci.ecommerce.service.CartService;
import com.kosemeci.ecommerce.service.ProductService;
import com.kosemeci.ecommerce.service.UserService;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    
    private final CartService cartService;
    private final CartItemService cartItemService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping()
    public ResponseEntity<Cart> findUserCartHandler(
            @RequestHeader("Authorization") String jwt) throws Exception{
        
        User user = userService.findUserByJwt(jwt);
        Cart cart = cartService.findUserCart(user);

        return ResponseEntity.ok(cart);
    }

    @PutMapping("/add")
    public ResponseEntity<CartItem> addItemToCart(
            @RequestHeader("Authorization") String jwt,
            @RequestBody AddItemRequest request) throws Exception {
                
        User user = userService.findUserByJwt(jwt);
        Product product = productService.findProductById(request.getProductId());

        CartItem item = cartService.addCartItem(user, 
            product, 
            request.getSize(), 
            request.getQuantity());
        
        ApiResponse response = new ApiResponse();
        response.setMessage("Item added to cart successfully");

        return new ResponseEntity<>(item,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/item/{cartItemId}")
    @SuppressWarnings("Convert2Diamond")
    public ResponseEntity<ApiResponse> deleteCartItem(
            @PathVariable Long cartItemId,
            @RequestHeader("Authorization") String jwt) throws Exception{

        User user = userService.findUserByJwt(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);

        ApiResponse response = new ApiResponse();
        response.setMessage("Item remove from cart");

        return new ResponseEntity<ApiResponse>(response,HttpStatus.ACCEPTED);
    }
    
    @PutMapping("/item/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItem(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long cartItemId,
            @RequestBody CartItem cartItem) throws Exception {
                
        User user = userService.findUserByJwt(jwt);
        CartItem updatedCartItem = null;

        if(cartItem.getQuantity()>0){
            updatedCartItem = cartItemService.updateCartItem(
                user.getId(), cartItemId, cartItem);
        }

        return new ResponseEntity<>(updatedCartItem,HttpStatus.ACCEPTED);
    }

}
