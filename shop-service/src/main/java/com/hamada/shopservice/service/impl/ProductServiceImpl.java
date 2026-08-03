package com.hamada.shopservice.service.impl;

import com.hamada.shopservice.entity.Product;
import com.hamada.shopservice.entity.ProductGender;
import com.hamada.shopservice.repository.ProductRepository;
import com.hamada.shopservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found"));
    }

    @Override
    public Product updateProduct(Long id, Product newproduct) {
        Product product= productRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found"));
        product.setRating(newproduct.getRating());
        product.setPrice(newproduct.getPrice());
        product.setName(newproduct.getName());
        product.setCategory(newproduct.getCategory());
        product.setDescription(newproduct.getDescription());
        product.setDiscount(newproduct.getDiscount());
        product.setGender(newproduct.getGender());
//        product.setCreatedAt(LocalDateTime.now());
        product.setImageUrl(newproduct.getImageUrl());
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getProductByCategory(Long categoryId) {
        return productRepository.getProductsByCategoryId(categoryId);
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepository.getProductsByNameContains(name);
    }

    @Override
    public List<Product> getProductByGender(String gender) {
        return productRepository.getProductsByGender(ProductGender.valueOf(gender));
    }

    @Override
    public List<Product> getDiscountedProducts() {
//        List<Product> products=getAllProducts();
//        List<Product> filteredProducts = new ArrayList<>();
//        int count=0;
//        for(Product product:products){
//            if(product.getDiscount()>0.0){
//                filteredProducts.add(product);
//            }
//        }
//        return filteredProducts;

        return productRepository.getProductsByDiscountGreaterThan(0.0);
    }

    @Override
    public List<Product> sortByNewestFirst() {
        return productRepository.findAll(Sort.by("createdAt").descending());
    }

}
