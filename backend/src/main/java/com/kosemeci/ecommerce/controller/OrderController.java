package com.kosemeci.ecommerce.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kosemeci.ecommerce.domain.PaymentMethod;
import com.kosemeci.ecommerce.entity.Address;
import com.kosemeci.ecommerce.entity.Order;
import com.kosemeci.ecommerce.entity.OrderItem;
import com.kosemeci.ecommerce.entity.User;
import com.kosemeci.ecommerce.response.PaymentLinkResponse;
import com.kosemeci.ecommerce.service.OrderService;
import com.kosemeci.ecommerce.service.UserService;

import lombok.RequiredArgsConstructor;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    
    private final OrderService orderService;
    private final UserService userService;
    // private final CartService cartService;
    // private final SellerService sellerService;

    @PostMapping()
    public ResponseEntity<PaymentLinkResponse> createOrder(
            @RequestBody Address shippingAddress,
            @RequestParam PaymentMethod paymentMethod,
            @RequestHeader("Authorization") String jwt) throws Exception {
        
        // User user = userService.findUserByJwt(jwt);
        // Cart cart = cartService.findUserCart(user);
        // Set<Order> orders = orderService.createOrder(user, shippingAddress, cart);
        
        // PaymentOrder paymentOrder = paymentService.createOrder(user,orders);
        PaymentLinkResponse response = new PaymentLinkResponse();

        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/user")
    public ResponseEntity<List<Order>> usersOrderHistoryHandler(
            @RequestHeader("Authorization") String token) throws Exception {
        
        User user = userService.findUserByJwt(token);
        List<Order> orders = orderService.usersOrderHistory(user.getId());

        return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(
            @RequestHeader("Authorization") String token,
            @PathVariable Long orderId) throws Exception {
        
        // User user = userService.findUserByJwt(token);
        Order orders = orderService.findOrderById(orderId);

        return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
    }

    @GetMapping("/item/{orderItemId}")
    public ResponseEntity<OrderItem> getOrderItemById(
            @RequestHeader("Authorization") String token,
            @PathVariable Long orderItemId) throws Exception {
        
        // User user = userService.findUserByJwt(token);
        OrderItem orderItem = orderService.findOrderItemById(orderItemId);

        return new ResponseEntity<>(orderItem,HttpStatus.ACCEPTED);
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrder(
            @RequestHeader("Authorization") String token,
            @PathVariable Long orderId) throws Exception {
        
        User user = userService.findUserByJwt(token);
        Order order = orderService.cancelOrder(orderId, user);

        // Seller seller = sellerService.getSellerById(order.getSellerId());
        // SellerReport report = sellerReportService.getSellerReport(seller);  

        return new ResponseEntity<>(order,HttpStatus.OK);
    }
    

}
