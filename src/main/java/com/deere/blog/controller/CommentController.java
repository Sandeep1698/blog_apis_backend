package com.deere.blog.controller;

import com.deere.blog.payload.ApiResponse;
import com.deere.blog.payload.CommentDto;
import com.deere.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/deere/comments")
public class CommentController {

    @Autowired
    private CommentService service;

    @RequestMapping(value = "/create/{postId}/user/{userId}",method = RequestMethod.POST)
    public ResponseEntity<CommentDto> create(@RequestBody CommentDto dto, @PathVariable("postId") Integer postId,@PathVariable("userId") Integer userId){
        return new ResponseEntity<>(service.create(dto,postId,userId), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/delete/{commentId}",method = RequestMethod.DELETE)
    public ResponseEntity<ApiResponse> delete(@PathVariable("commentId") Integer id){
        service.delete(id);
        return new ResponseEntity<>(new ApiResponse("Deletion Successful !!",true,"200"), HttpStatus.OK);
    }
}
