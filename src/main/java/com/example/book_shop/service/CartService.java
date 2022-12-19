package com.example.book_shop.service;

import com.example.book_shop.model.Cart;
import com.example.book_shop.model.Product;
import com.example.book_shop.model.User;

public interface CartService {
    Cart addItemToCart(Product product, int quantity, User user);

    Cart updateItemInCart(Product product, int quantity, User user);

    Cart deleteItemFromCart(Product product, User user);
}
