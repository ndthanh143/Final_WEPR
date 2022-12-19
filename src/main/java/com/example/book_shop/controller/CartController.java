package com.example.book_shop.controller;

import com.example.book_shop.model.Cart;
import com.example.book_shop.model.Product;
import com.example.book_shop.model.User;
import com.example.book_shop.service.CartService;
import com.example.book_shop.service.ProductService;
import com.example.book_shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class CartController {
    private UserService userService;
    private CartService cartService;
    private ProductService productService;

    public CartController(UserService userService, CartService cartService, ProductService productService) {
        super();
        this.userService = userService;
        this.cartService = cartService;
        this.productService = productService;
    }

//    @GetMapping("/cart")
//    public String cart(Model model, Principal principal, HttpSession session){
//        if(principal == null){
//            return "redirect:/login";
//        }
//        String username = principal.getName();
//        Customer customer = customerService.findByUsername(username);
//        ShoppingCart shoppingCart = customer.getShoppingCart();
//        if(shoppingCart == null){
//            model.addAttribute("check", "No item in your cart");
//        }
//        session.setAttribute("totalItems", shoppingCart.getTotalItems());
//        model.addAttribute("subTotal", shoppingCart.getTotalPrices());
//        model.addAttribute("shoppingCart", shoppingCart);
//        return "cart";
//    }
    @PostMapping("/add-to-cart")
    public String addItemToCart(
            @RequestParam("id") Integer productID,
            @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
            Principal principal,
            HttpServletRequest request
    ) {
        if(principal == null) {
            return "redirect:/login";
        }
        Product product = productService.getById((productID));
        String email = principal.getName();
        User user = userService.findByEmail(email);

        cartService.addItemToCart(product, quantity, user);
        return "redirect:" + request.getHeader("Referer");
    }

    @GetMapping("/cart")
    public String cart(Model model, Principal principal, HttpSession session) {
        if(principal == null) {
            return "redirect:/login?cart";
        } else {
            String email = principal.getName();
            User user = userService.findByEmail(email);
            session.setAttribute("user", user);
            Cart cart = user.getCart();
            session.setAttribute("totalItems", cart.getTotalItems());
            model.addAttribute("cart", cart);
            return "cart";
        }

    }
}
