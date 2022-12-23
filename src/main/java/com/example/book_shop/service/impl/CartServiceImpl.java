package com.example.book_shop.service.impl;

import com.example.book_shop.model.Cart;
import com.example.book_shop.model.CartItem;
import com.example.book_shop.model.Product;
import com.example.book_shop.model.User;
import com.example.book_shop.repository.CartItemRepository;
import com.example.book_shop.repository.CartRepository;
import com.example.book_shop.service.CartService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CartServiceImpl implements CartService {
    private CartItemRepository itemRepository;
    private CartRepository cartRepository;

    public CartServiceImpl(CartItemRepository itemRepository, CartRepository cartRepository) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart addItemToCart(Product product, int quantity, User user) {
        Cart cart = user.getCart();

        if (cart == null) {
            cart = new Cart();
        }
        Set<CartItem> cartItems = cart.getCartItem();
        CartItem cartItem = findCartItem(cartItems, product.getProductID());
        if (cartItems == null) {
            cartItems = new HashSet<>();
            if (cartItem == null) {
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setTotalPrice(quantity * product.getPrice());
                cartItem.setQuantity(quantity);
                cartItem.setCart(cart);
                cartItems.add(cartItem);
                itemRepository.save(cartItem);
            }
        } else {
            if (cartItem == null) {
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setTotalPrice(quantity * product.getPrice());
                cartItem.setQuantity(quantity);
                cartItem.setCart(cart);
                cartItems.add(cartItem);
                itemRepository.save(cartItem);
            } else {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartItem.setTotalPrice(cartItem.getTotalPrice() + ( quantity * product.getPrice()));
                itemRepository.save(cartItem);
            }
        }
        cart.setCartItem(cartItems);
        int totalItems = totalItems(cart.getCartItem());
        int totalPrice = totalPrice(cart.getCartItem());

        cart.setTotalPrices(totalPrice);
        cart.setTotalItems(totalItems);
        cart.setUser(user);


        return cartRepository.save(cart);
    }

    @Override
    public Cart updateItemInCart(Product product, int quantity, User user) {
        Cart cart = user.getCart();

        Set<CartItem> cartItems = cart.getCartItem();

        CartItem item = findCartItem(cartItems, product.getProductID());

        item.setQuantity(quantity);
        item.setTotalPrice(quantity * product.getPrice());

        itemRepository.save(item);

        int totalItems = totalItems(cartItems);
        int totalPrice = totalPrice(cartItems);

        cart.setTotalItems(totalItems);
        cart.setTotalPrices(totalPrice);

        return cartRepository.save(cart);
    }

    @Override
    public Cart deleteItemFromCart(Product product, User user) {
        Cart cart = user.getCart();

        Set<CartItem> cartItems = cart.getCartItem();

        CartItem item = findCartItem(cartItems, product.getProductID());

        cartItems.remove(item);
        itemRepository.delete(item);

        int totalPrice = totalPrice(cartItems);
        int totalItems = totalItems(cartItems);

        cart.setCartItem(cartItems);
        cart.setTotalItems(totalItems);
        cart.setTotalPrices(totalPrice);

        return cartRepository.save(cart);
    }


    private CartItem findCartItem(Set<CartItem> cartItems, Integer productId) {
        if (cartItems == null) {
            return null;
        }
        CartItem cartItem = null;

        for (CartItem item : cartItems) {
            if (item.getProduct().getProductID() == productId) {
                cartItem = item;
            }
        }
        return cartItem;
    }

    private int totalItems(Set<CartItem> cartItems){
        int totalItems = 0;
        for(CartItem item : cartItems){
            totalItems += item.getQuantity();
        }
        return totalItems;
    }

    private int totalPrice(Set<CartItem> cartItems){
        int totalPrice = 0;

        for(CartItem item : cartItems){
            totalPrice += item.getTotalPrice();
        }
        return totalPrice;
    }
}
