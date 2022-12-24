package com.example.book_shop.controller;

import com.example.book_shop.model.Product;
import com.example.book_shop.model.User;
import com.example.book_shop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/delete-user/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes attributes) {
        userService.deleteById(id);
        attributes.addAttribute("message", "User has been deleted!");
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/user/{id}")
    public String updateUserForm(@PathVariable("id") Integer id, Model model, HttpSession session, Principal principal) {
        if(principal == null){
            return "redirect:/login";
        }
        String email = principal.getName();
        User userLoggedIn = userService.findByEmail(email);
        session.setAttribute("user", userLoggedIn);
        User user = userService.getById(id);
        model.addAttribute("user", user);
        return "admin-user-update";
    }

    @PostMapping("/do-user-update/{id}")
    public String updateUser(@PathVariable("id") Integer id,
                                @ModelAttribute("product") User user,
                                RedirectAttributes attributes) {
        try{
            userService.update(user, id);
            attributes.addAttribute("message", "Update successfully!");

        }catch (Exception e){
            e.printStackTrace();
            attributes.addAttribute("message", "Failed to update!");
        }
        return "redirect:/admin/users";
    }
}
