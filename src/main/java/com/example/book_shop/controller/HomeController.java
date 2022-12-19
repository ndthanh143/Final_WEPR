package com.example.book_shop.controller;

import com.example.book_shop.config.CustomUserDetails;
import com.example.book_shop.model.Cart;
import com.example.book_shop.model.Category;
import com.example.book_shop.model.Product;
import com.example.book_shop.model.User;
import com.example.book_shop.repository.UserRepository;
import com.example.book_shop.service.CategoryService;
import com.example.book_shop.service.ProductService;
import javax.servlet.http.HttpSession;

import com.example.book_shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {
    ProductService productService;
    @Autowired
    private UserService userService;
    CategoryService categoryService;

    public HomeController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String index(Model model, Principal principal, HttpSession session) {
        if(principal != null) {
            String email = principal.getName();
            User user = userService.findByEmail(email);
            session.setAttribute("user", user);
            Cart cart = user.getCart();
            if(cart != null) {
                session.setAttribute("totalItems", cart.getTotalItems());
            } else {
                session.setAttribute("totalItems", null);
            }
        }
        List<Category> categories = categoryService.findAll();
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        return "home";
    }
}
