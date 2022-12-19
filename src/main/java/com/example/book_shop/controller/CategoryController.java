package com.example.book_shop.controller;


import com.example.book_shop.model.Category;
import com.example.book_shop.model.Product;
import com.example.book_shop.service.CategoryService;
import com.example.book_shop.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/categories")
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        super();
        this.categoryService = categoryService;
    }

    // create
    @PostMapping()
    public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
        return new ResponseEntity<Category>(categoryService.save(category), HttpStatus.CREATED);
    }

    // get all
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") Integer id) {
        return new ResponseEntity<Category>(categoryService.getById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") Integer id, @RequestBody Category category) {
        return new ResponseEntity<Category>(categoryService.update(category, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Integer id) {
        categoryService.deleteById(id);

        return new ResponseEntity<String>("Category deleted successfully!", HttpStatus.OK);
    }
}
