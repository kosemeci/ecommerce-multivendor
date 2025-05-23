package com.kosemeci.ecommerce.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kosemeci.ecommerce.domain.OrderStatus;
import com.kosemeci.ecommerce.domain.PaymentStatus;
import com.kosemeci.ecommerce.entity.Address;
import com.kosemeci.ecommerce.entity.Cart;
import com.kosemeci.ecommerce.entity.CartItem;
import com.kosemeci.ecommerce.entity.Order;
import com.kosemeci.ecommerce.entity.OrderItem;
import com.kosemeci.ecommerce.entity.User;
import com.kosemeci.ecommerce.repository.AddressRepository;
import com.kosemeci.ecommerce.repository.OrderItemRepository;
import com.kosemeci.ecommerce.repository.OrderRepository;
import com.kosemeci.ecommerce.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    
    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final OrderItemRepository orderItemRepository;
    
    
    @Override
    public Set<Order> createOrder(User user, Address shippingAddress, Cart cart) {
        
        if(!user.getAddresses().contains(shippingAddress)){
            user.getAddresses().add(shippingAddress);
        }
        Address address = addressRepository.save(shippingAddress);
        
        Map<Long,List<CartItem>> itemsBySeller = cart.getCartItems().stream()
                .collect(Collectors.groupingBy(item->item.getProduct()
                .getSeller().getId()));

        Set<Order> orders = new HashSet<>();
        for (Map.Entry<Long, List<CartItem>> entry : itemsBySeller.entrySet()) {

            Long sellerId = entry.getKey();
            List<CartItem> items = entry.getValue();

            int totalOrderPrice = items.stream().mapToInt(
                CartItem::getSellingPrice
            ).sum();
            int totalItem = items.stream().mapToInt(
                CartItem::getQuantity
            ).sum();

            Order createdOrder = new Order();
            createdOrder.setUser(user);
            createdOrder.setSellerId(sellerId);
            createdOrder.setTotalMrpPrice(totalOrderPrice);
            createdOrder.setTotalItem(totalItem);
            createdOrder.setShippingAddress(address);
            createdOrder.setTotalSellingPrice(totalOrderPrice);
            createdOrder.setOrderStatus(OrderStatus.PENDING);
            createdOrder.getPaymentDetails().setStatus(PaymentStatus.PENDING);

            Order savedOrder = orderRepository.save(createdOrder);
            orders.add(savedOrder);

            List<OrderItem> orderItems = new ArrayList<>();

            for(CartItem item : items){

                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(savedOrder);
                orderItem.setMrpPrice(item.getMrpPrice());
                orderItem.setQuantity(item.getQuantity());
                orderItem.setSize(item.getSize());
                orderItem.setProduct(item.getProduct());
                orderItem.setUserId(item.getUserId());
                orderItem.setSellingPrice(item.getSellingPrice());

                savedOrder.getOrderItems().add(orderItem);

                OrderItem savedOrderItem = orderItemRepository.save(orderItem);
                orderItems.add(savedOrderItem);
            }
        }        
        return orders;
    }

    @Override
    public Order findOrderById(Long id) throws Exception {
        return orderRepository.findById(id).orElseThrow(()->
                new Exception("Dont found order with id"+id.toString()));
    }

    @Override
    public List<Order> usersOrderHistory(Long userId) {        
        return orderRepository.findByUserId(userId);
    }

    @Override
    public List<Order> sellersOrder(Long sellerId) {
        return orderRepository.findBySellerId(sellerId);
    }

    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus orderStatus) throws Exception {
        
        Order order = findOrderById(orderId);
        order.setOrderStatus(orderStatus);
        return orderRepository.save(order);
    }

    @Override
    public Order cancelOrder(Long orderId, User user) throws Exception {
        
        Order order = findOrderById(orderId);
        if(!user.getId().equals(order.getUser().getId())){
            throw new Exception("you dont have acces to this order.");
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    @Override
    public OrderItem findOrderItemById(Long id) throws Exception{
        return orderItemRepository.findById(id).orElseThrow(()->
                new Exception("Dont found .."));
    }
    
}
