package com.example.book_shop.controller;

import com.example.book_shop.model.Category;
import com.example.book_shop.model.Product;
import com.example.book_shop.service.CategoryService;
import com.example.book_shop.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ProductController {

    private ProductService productService;
    private CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        super();
        this.productService = productService;
        this.categoryService = categoryService;

    }

    @GetMapping("/admin/product/{id}")
    public String updateProductForm(@PathVariable("id") Integer id, Model model) {
        List<Category> categories = categoryService.findAll();
        Product product = productService.getById(id);
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "admin-product-update";
    }

    @GetMapping("/admin/product/new")
    public String addProductForm(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categories);
        return "admin-product-add";
    }

    @PostMapping("/do-product-add")
    public String saveProduct(@ModelAttribute("product") Product product,
                              @RequestParam("imageProduct")MultipartFile imageProduct,
                              RedirectAttributes attributes) {
        try {
            productService.save(product, imageProduct);
            attributes.addAttribute("message", "Add successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addAttribute("message", "Failed to add!");
        }
        return "redirect:/admin/products";
    }
    @PostMapping("/do-product-update/{id}")
    public String updateProduct(@PathVariable("id") Integer id,
                                @ModelAttribute("product") Product product,
                                @RequestParam("imageProduct") MultipartFile imageProduct,
                                RedirectAttributes attributes) {
        try{
            productService.update(product, imageProduct, id);
            attributes.addAttribute("message", "Update successfully!");

        }catch (Exception e){
            e.printStackTrace();
            attributes.addAttribute("message", "Failed to update!");
        }
        return "redirect:/admin/products";
    }

    @RequestMapping(value = "/delete-product/{id}")
    public String deleteProduct(@PathVariable("id") Integer id, RedirectAttributes attributes) {
        productService.deleteById(id);
        attributes.addAttribute("message", "Product has been deleted!");
        return "redirect:/admin/products";
    }

    // get request frontend
    @GetMapping("/product/{id}")
    public String findProductById(@PathVariable("id") Integer id, Model model){
        Product product = productService.getById(id);
        model.addAttribute("product", product);
        return "product-detail";
    }
}
