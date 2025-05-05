package com.deere.blog.service.impl;

import com.deere.blog.entity.Category;
import com.deere.blog.exception.ResourceNotFoundException;
import com.deere.blog.payload.CategoryDto;
import com.deere.blog.repository.CategoryRepository;
import com.deere.blog.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto create(CategoryDto dto) {
        return entityToDto(repository.save(dtoToEntity(dto)));
    }

    @Override
    public CategoryDto update(CategoryDto dto, Integer id) {
        Category category = repository.findByCategoryId(id).orElseThrow(()-> new ResourceNotFoundException("Category","id",id));
        category.setCategoryTitle(dto.getCategoryTitle());
        category.setCategoryDesc(dto.getCategoryDesc());
        return entityToDto(repository.save(category));
    }

    private Category dtoToEntity(CategoryDto dto){
        return this.modelMapper.map(dto,Category.class);
    }

    private CategoryDto entityToDto(Category user){
        return  this.modelMapper.map(user,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return repository.findAll().stream().map(this::entityToDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Integer id) {
        return entityToDto(repository.findByCategoryId(id).orElseThrow(()-> new ResourceNotFoundException("Category","id",id)));
    }

    @Override
    public void delete(Integer id) {
        repository.delete(repository.findByCategoryId(id).orElseThrow(()-> new ResourceNotFoundException("Category","id",id)));

    }
}
