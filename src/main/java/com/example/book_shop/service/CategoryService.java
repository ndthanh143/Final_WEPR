package com.example.book_shop.service;

import com.example.book_shop.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category save(Category category);
    Category getById(Integer categoryID);
    Category update(Category category, Integer id);
    void deleteById(Integer categoryID);
}
