package com.deere.blog.controller;

import com.deere.blog.payload.ApiResponse;
import com.deere.blog.payload.CategoryDto;
import com.deere.blog.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/deere/categories")
public class CategoryController {
    @Autowired
    private CategoryService service;

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseEntity<CategoryDto> createNewCategory(@Valid @RequestBody CategoryDto dto){
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/update/{categoryId}",method = RequestMethod.PUT)
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto dto,@PathVariable("categoryId") Integer userId){
        return new ResponseEntity<>(service.update(dto,userId), HttpStatus.OK);
    }

    @RequestMapping(value = "/getCategories",method = RequestMethod.GET)
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        return new ResponseEntity<>(service.getAllCategories(), HttpStatus.OK);
    }

    @RequestMapping(value = "/getByCategoryId",method = RequestMethod.GET)
    public ResponseEntity<CategoryDto> getByCategoryId(@RequestParam("categoryId") Integer id) throws Exception {
        return new ResponseEntity<>(service.getCategoryById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public ResponseEntity<ApiResponse> deleteCategory(@RequestParam("categoryId") Integer id){
        service.delete(id);
        return new ResponseEntity<>(new ApiResponse("Deletion Successful !!",true,"200"), HttpStatus.OK);
    }
}
