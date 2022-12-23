package com.example.book_shop.controller;

import com.example.book_shop.model.Cart;
import com.example.book_shop.model.Order;
import com.example.book_shop.model.User;
import com.example.book_shop.service.CartService;
import com.example.book_shop.service.OrderService;
import com.example.book_shop.service.ProductService;
import com.example.book_shop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
public class OrderController {
    private UserService userService;
    private CartService cartService;
    private ProductService productService;

    private OrderService orderService;

    public OrderController(UserService userService, CartService cartService, ProductService productService, OrderService orderService) {
        this.userService = userService;
        this.cartService = cartService;
        this.productService = productService;
        this.orderService = orderService;
    }

    @GetMapping("/checkout")
    public String checkout(Model model, Principal principal, HttpSession session) {
        if(principal == null) {
            return "redirect:/login";
        }
        else {
            String email = principal.getName();
            User user = userService.findByEmail(email);
            session.setAttribute("user", user);

            Order order = new Order();
            model.addAttribute("order", order);

            Cart cart = user.getCart();
            session.setAttribute("totalItems", cart.getTotalItems());
            model.addAttribute("cart", cart);

            return "checkout";
        }
    }

    @PostMapping("/do-checkout")
    public String processingCheckut(Order order, Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        String email = principal.getName();
        User user = userService.findByEmail(email);

        orderService.save(order, user);
        return "redirect:/myorder";
    }

    @GetMapping("/myorder")
    public String myOrder(Model model, Principal principal , HttpSession session) {
        if(principal == null) {
            return "redirect:/login";
        }
        String email = principal.getName();
        User user = userService.findByEmail(email);
        session.setAttribute("user", user);

        Cart cart = user.getCart();
        session.setAttribute("totalItems", cart.getTotalItems());
        model.addAttribute("cart", cart);

        List<Order> orderList = user.getOrders();

        model.addAttribute("orders", orderList);



        return "myorder";
    }

}
