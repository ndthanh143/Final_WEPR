package com.example.book_shop.service;

import com.example.book_shop.model.Order;
import com.example.book_shop.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OrderService {
    List<Order> findAll();

    Order save(Order order, User user);

    List<Order> ListUserOrders(Integer userID);

    Order getById(Integer id);

    void deleteByID(Integer id);

    Order update(Integer id, String paymentStatus, String orderStatus);
}
