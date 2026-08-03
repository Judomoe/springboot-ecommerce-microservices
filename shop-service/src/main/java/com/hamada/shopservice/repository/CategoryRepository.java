package com.hamada.shopservice.repository;

import com.hamada.shopservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findAllByNameContainingIgnoreCase(String name);
}
