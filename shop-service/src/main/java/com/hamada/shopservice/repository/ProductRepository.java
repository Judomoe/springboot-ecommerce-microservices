package com.hamada.shopservice.repository;

import com.hamada.shopservice.entity.Product;
import com.hamada.shopservice.entity.ProductGender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> getProductsByCategoryId(Long categoryId);

    List<Product> getProductsByNameContains(String name);

    List<Product> getProductsByGender(ProductGender gender);

    List<Product> getProductsByDiscountGreaterThan(Double discountIsGreaterThan);
}
