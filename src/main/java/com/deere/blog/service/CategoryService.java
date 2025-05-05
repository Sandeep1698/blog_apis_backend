package com.deere.blog.service;

import com.deere.blog.payload.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto create(CategoryDto dto);
    CategoryDto update(CategoryDto dto,Integer id);
    List<CategoryDto> getAllCategories();
    CategoryDto getCategoryById(Integer Id);
    void delete(Integer Id);

}
