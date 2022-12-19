package com.example.book_shop.controller;

import com.example.book_shop.model.Product;
import com.example.book_shop.model.User;
import com.example.book_shop.service.CategoryService;
import com.example.book_shop.service.ProductService;
import com.example.book_shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
public class AdminController {
    ProductService productService;
    @Autowired
    private UserService userService;
    CategoryService categoryService;

    public AdminController(ProductService productService, UserService userService, CategoryService categoryService) {
        this.productService = productService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model, Principal principal, HttpSession session) {
        if(principal == null){
            return "redirect:/login";
        }
        String email = principal.getName();
        User user = userService.findByEmail(email);
        session.setAttribute("user", user);
        List<Product> products = productService.findAll();
        List<User> users = userService.findAll();
        model.addAttribute("products",products);
        model.addAttribute("users",users);
        return "admin-dashboard";
    }

    @GetMapping("/admin/products")
    public String products(Model model, Principal principal, HttpSession session) {
        if(principal == null){
            return "redirect:/login";
        }
        String email = principal.getName();
        User user = userService.findByEmail(email);
        session.setAttribute("user", user);
        List<Product> products = productService.findAll();
        List<User> users = userService.findAll();
        model.addAttribute("products",products);
        model.addAttribute("users",users);
        return "admin-product";
    }

    @GetMapping("/admin/users")
    public String users(Model model, Principal principal, HttpSession session) {
        if(principal == null){
            return "redirect:/login";
        }
        String email = principal.getName();
        User user = userService.findByEmail(email);
        session.setAttribute("user", user);
        List<Product> products = productService.findAll();
        List<User> users = userService.findAll();
        model.addAttribute("products",products);
        model.addAttribute("users",users);
        return "admin-user";
    }


}
