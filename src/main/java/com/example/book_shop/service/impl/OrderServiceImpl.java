package com.example.book_shop.service.impl;

import com.example.book_shop.exception.ResourceNotFoundException;
import com.example.book_shop.model.*;
import com.example.book_shop.repository.CartRepository;
import com.example.book_shop.repository.OrderDetailRepository;
import com.example.book_shop.repository.OrderRepository;
import com.example.book_shop.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailRepository;

    private CartRepository cartRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order save(Order order, User user) {
        Cart cart = user.getCart();

        Set<CartItem> cartItems = cart.getCartItem();
        List<OrderDetail> orderDetails = new ArrayList<>();
        cartItems.forEach(cartItem -> {
            OrderDetail item = new OrderDetail();
            item.setProduct(cartItem.getProduct());
            item.setTotalPrice(cartItem.getTotalPrice());
            item.setQuantity(cartItem.getQuantity());
            item.setOrder(order);
            orderDetailRepository.save(item);
            orderDetails.add(item);
        });
        Date day = new Date();
        order.setOrderDetails(orderDetails);
        order.setOrderDate(day);
        order.setTotalPrice(cart.getTotalPrices());
        order.setUser(user);

        cart.setCartItem(new HashSet<>());
        cart.setTotalItems(0);
        cart.setTotalPrices(0);
        cartRepository.save(cart);

        return orderRepository.save(order);
    }

    @Override
    public List<Order> ListUserOrders(Integer userID) {

        return orderRepository.findAll();
    }

    @Override
    public Order getById(Integer id) {
        return orderRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Order","ID",id)
        );
    }

    @Override
    public void deleteByID(Integer id) {
        Order existingOrder = orderRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Order","ID",id));

        orderRepository.deleteById(id);
    }

    @Override
    public Order update(Integer id, String paymentStatus, String orderStatus) {
        Order existingOrder = orderRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Order","ID",id));

        existingOrder.setPaymentStatus(paymentStatus);
        existingOrder.setOrderStatus(orderStatus);

        return orderRepository.save(existingOrder);
    }
}
