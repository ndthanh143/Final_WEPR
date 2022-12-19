package com.example.book_shop.service;

import com.example.book_shop.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User save(User user);

    User update(User user, Integer id);

    User findByEmail(String email);

    User getById(Integer id);

    void deleteById(Integer id);

}
