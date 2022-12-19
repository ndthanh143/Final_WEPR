package com.example.book_shop.service;

import com.example.book_shop.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product save(Product product, MultipartFile imageProduct);
    Product getById(Integer id);
    Product update(Product product,MultipartFile imageProduct, Integer id) throws IOException;
    void deleteById(Integer id);
}
