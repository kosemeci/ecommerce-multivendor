package com.kosemeci.ecommerce.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kosemeci.ecommerce.domain.OrderStatus;
import com.kosemeci.ecommerce.entity.Order;
import com.kosemeci.ecommerce.entity.Seller;
import com.kosemeci.ecommerce.exception.SellerException;
import com.kosemeci.ecommerce.service.OrderService;
import com.kosemeci.ecommerce.service.SellerService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seller/orders")
public class SellerOrderController {
    
    private final OrderService orderService;
    private final SellerService sellerService;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrdersHandler(
        @RequestHeader("Authorization") String token) throws SellerException {

        Seller seller = sellerService.getSellerByToken(token);
        List<Order> orders = orderService.sellersOrder(seller.getId());

        return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
    }

    @PatchMapping("/{orderId}/status/{orderStatus}")
    public ResponseEntity<Order> updateOrderHandler(
        @RequestHeader("Authorization") String token,
        @PathVariable Long orderId,
        @PathVariable OrderStatus orderStatus) throws Exception {

        Order order= orderService.updateOrderStatus(orderId, orderStatus);

        return new ResponseEntity<>(order,HttpStatus.ACCEPTED);
    }
    
}
