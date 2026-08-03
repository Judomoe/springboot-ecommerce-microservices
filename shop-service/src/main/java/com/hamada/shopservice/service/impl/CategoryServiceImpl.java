package com.hamada.shopservice.service.impl;

import com.hamada.shopservice.entity.Category;
import com.hamada.shopservice.repository.CategoryRepository;
import com.hamada.shopservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(()->new RuntimeException("Category not found"));
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long id,Category category) {
        Category oldcategory=categoryRepository.findById(id).orElseThrow(()->new RuntimeException("Category not found"));
        oldcategory.setDescription(category.getDescription());
        oldcategory.setImageUrl(category.getImageUrl());
        oldcategory.setName(category.getName());
        return categoryRepository.save(oldcategory);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> findByName(String name) {
        return categoryRepository.findAllByNameContainingIgnoreCase(name);
    }
}
