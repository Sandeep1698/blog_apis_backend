package com.deere.blog.service.impl;

import com.deere.blog.entity.Comment;
import com.deere.blog.entity.Post;
import com.deere.blog.entity.User;
import com.deere.blog.exception.ResourceNotFoundException;
import com.deere.blog.payload.CommentDto;
import com.deere.blog.repository.CommentRepository;
import com.deere.blog.repository.PostRepository;
import com.deere.blog.repository.UserRepository;
import com.deere.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public CommentDto create(CommentDto dto, Integer postId, Integer userId) {
        Post post=postRepository.findByPostId(postId).orElseThrow(()->new ResourceNotFoundException("Post ","id",postId));
        User user =userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
        return mapper.map(commentRepository.save(new Comment(dto.getContent(),post,user)),CommentDto.class);
    }

    @Override
    public void delete(Integer commentId) {
        commentRepository.delete(commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("User","id",commentId)));
    }
}
