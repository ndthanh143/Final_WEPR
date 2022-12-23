package com.example.book_shop.repository;

import com.example.book_shop.model.Order;
import com.example.book_shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
