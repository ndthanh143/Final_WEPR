package com.example.book_shop.service.impl;

import com.example.book_shop.model.*;
import com.example.book_shop.repository.OrderDetailRepository;
import com.example.book_shop.repository.OrderRepository;
import com.example.book_shop.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public Order save(Order order, User user) {
        Cart cart = user.getCart();

        Set<CartItem> cartItems = cart.getCartItem();
        Set<OrderDetail> orderDetails = new HashSet<>();
        cartItems.forEach(cartItem -> {
            OrderDetail item = new OrderDetail();
            item.setProduct(cartItem.getProduct());
            item.setTotalPrice(cartItem.getTotalPrice());
            item.setQuantity(cartItem.getQuantity());
            item.setOrder(order);
            orderDetails.add(item);
            orderDetailRepository.save(item);
        });

        order.setOrderDetails(orderDetails);
        order.setTotalPrice(cart.getTotalPrices());
        order.setUser(user);
        System.out.println(order.getPayment());
//        order.getOrderDetails().forEach(item -> {
//            item.setOrder(order);
//        });
        return orderRepository.save(order);
    }

    @Override
    public List<Order> ListUserOrders(Integer userID) {

        return null;
    }
}
