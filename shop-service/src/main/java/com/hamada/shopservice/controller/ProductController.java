package com.hamada.shopservice.controller;

import com.hamada.shopservice.entity.Product;
import com.hamada.shopservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product){
        return productService.createProduct(product);
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id,@RequestBody Product product){
        return productService.updateProduct(id,product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
    }

    @GetMapping("/category/{categoryId}")
    public List<Product> getProductByCategory(@PathVariable Long categoryId){
        return productService.getProductByCategory(categoryId);
    }

    @GetMapping("/search")
    public List<Product> getProductByName(@RequestParam String name){
        return productService.getProductByName(name);
    }

    @GetMapping("/gender/{gender}")
    public List<Product> getProductByGender(@PathVariable String gender){
        return productService.getProductByGender(gender.toUpperCase());
    }

    @GetMapping("/discounted")
    public List<Product> getDiscountedProducts(){
        return productService.getDiscountedProducts();
    }

    @GetMapping("/newest")
    public List<Product> sortByNewestFirst(){
        return productService.sortByNewestFirst();
    }
}
