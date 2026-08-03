package com.hamada.shopservice.service;

import com.hamada.shopservice.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    Product createProduct(Product product);

    Product getProductById(Long id);

    Product updateProduct(Long id,Product newproduct);

    void deleteProduct(Long id);

    List<Product> getProductByCategory(Long categoryId);

    List<Product> getProductByName(String name);

    List<Product> getProductByGender(String gender);

    List<Product> getDiscountedProducts();

    List<Product> sortByNewestFirst();
}
