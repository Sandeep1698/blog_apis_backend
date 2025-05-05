package com.deere.blog.service;

import com.deere.blog.payload.CommentDto;

public interface CommentService {

    CommentDto create(CommentDto dto,Integer postId,Integer userId);
    void delete(Integer commentId);
}
