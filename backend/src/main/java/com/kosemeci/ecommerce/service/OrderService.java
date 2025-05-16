package com.kosemeci.ecommerce.service;

import java.util.List;
import java.util.Set;

import com.kosemeci.ecommerce.domain.OrderStatus;
import com.kosemeci.ecommerce.entity.Address;
import com.kosemeci.ecommerce.entity.Cart;
import com.kosemeci.ecommerce.entity.Order;
import com.kosemeci.ecommerce.entity.OrderItem;
import com.kosemeci.ecommerce.entity.User;


public interface OrderService {
    
    Set<Order> createOrder(User user, Address shippingAddress, Cart cart);
    Order findOrderById(Long id) throws Exception;
    List<Order> usersOrderHistory(Long userId);
    List<Order> sellersOrder(Long sellerId);
    Order updateOrderStatus(Long orderId, OrderStatus orderStatus) throws Exception;
    Order cancelOrder(Long orderId, User user) throws Exception;
    OrderItem findById(Long id) throws Exception;

}
