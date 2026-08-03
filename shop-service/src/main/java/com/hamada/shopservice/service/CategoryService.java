package com.hamada.shopservice.service;

import com.hamada.shopservice.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getCategories();

    Category findCategoryById(Long id);

    Category createCategory(Category category);

    Category updateCategory(Long id,Category category);

    void deleteCategory(Long id);

    List<Category> findByName(String name);
}
