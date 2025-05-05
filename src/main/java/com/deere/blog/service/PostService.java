package com.deere.blog.service;

import com.deere.blog.entity.Post;
import com.deere.blog.payload.PostDto;
import com.deere.blog.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto create(PostDto dto,Integer userId,Integer catId);
    PostDto update(PostDto dto,Integer postId);
    PostDto getById(Integer id);
    void delete(Integer id);
    PostResponse getPostsByCategory(Integer categoryId,Integer pageNumber, Integer pageSize,String sortBy,String sortDir);
    PostResponse getPostsByUser(Integer userId,Integer pageNumber, Integer pageSize,String sortBy,String sortDir);
    PostResponse getPosts(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);
    List<PostDto> searchPost(String keyword);
}
