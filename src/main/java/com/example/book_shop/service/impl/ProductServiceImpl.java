package com.example.book_shop.service.impl;

import com.example.book_shop.exception.ResourceNotFoundException;
import com.example.book_shop.model.Product;
import com.example.book_shop.repository.ProductRepository;
import com.example.book_shop.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        super();
        this.productRepository = productRepository;
    }


    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product save(Product product, MultipartFile imageProduct) {
        try {
            Product productSave = new Product();
            if(imageProduct == null){
                productSave.setImageUrl(null);
            }else{
                productSave.setImageUrl(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            }
            productSave.setName(product.getName());
            productSave.setAuthor(product.getAuthor());
            productSave.setPrice(product.getPrice());
            productSave.setStock(product.getStock());
            productSave.setDescription(product.getDescription());
            productSave.setCategory(product.getCategory());

            return productRepository.save(productSave);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Product getById(Integer id) {
        return productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product","ID",id)
        );
    }

    @Override
    public Product update(Product product, MultipartFile imageProduct, Integer id) throws IOException {
        Product existingProduct = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product", "Id", id)
        );
        if(imageProduct != null) {
            existingProduct.setImageUrl(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
        }
        existingProduct.setName(product.getName());
        existingProduct.setAuthor(product.getAuthor());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setStock(product.getStock());
        existingProduct.setCategory(product.getCategory());


        productRepository.save(existingProduct);
        return existingProduct;
    }

    @Override
    public void deleteById(Integer id) {
        Product existingProduct = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product", "Id", id)
        );
            productRepository.deleteById(id);
    }
}
