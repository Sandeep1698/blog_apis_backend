package com.deere.blog.controller;

import com.deere.blog.payload.ApiResponse;
import com.deere.blog.payload.PostDto;
import com.deere.blog.payload.PostResponse;
import com.deere.blog.service.FileService;
import com.deere.blog.service.PostService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.deere.blog.config.BlogLiterals.*;

@RestController
@RequestMapping("api/deere/posts")
public class PostController {

    @Autowired
    private PostService service;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;

    @RequestMapping(value = "/user/{userId}/category/{categoryId}/create",method = RequestMethod.POST)
    public ResponseEntity<PostDto> create(@Valid @RequestBody PostDto dto, @PathVariable Integer userId, @PathVariable Integer categoryId){
        return new ResponseEntity<PostDto>(service.create(dto,userId,categoryId), HttpStatus.CREATED);
    }
    @RequestMapping(value = "/update/{postId}",method = RequestMethod.PUT)
    public ResponseEntity<PostDto> update(@Valid @RequestBody PostDto dto,@PathVariable Integer postId){
        return new ResponseEntity<>(service.update(dto,postId),HttpStatus.OK);
    }
    @RequestMapping(value = "/delete/{postId}",method = RequestMethod.PUT)
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer postId){
        service.delete(postId);
        return new ResponseEntity<>(new ApiResponse("Deletion Successful !!",true,"200"),HttpStatus.OK);
    }
    @RequestMapping(value = "/getPostById/{postId}",method = RequestMethod.GET)
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
        return new ResponseEntity<>(service.getById(postId),HttpStatus.OK);
    }
    @RequestMapping(value = "/getPostByCategory",method = RequestMethod.GET)
    public ResponseEntity<PostResponse> getPostsByCategory(@RequestParam("categoryId")  Integer id,
                                                           @RequestParam(value = "pageNumber",defaultValue = PAGE_NUMBER,required = false) Integer pageNumber,
                                                           @RequestParam(value = "pageSize",defaultValue = PAGE_SIZE,required = false) Integer pageSize,
                                                           @RequestParam(value = "sortBy",defaultValue = SORT_BY,required = false) String sortBy,
                                                           @RequestParam(value = "sortDir",defaultValue = SORT_DIR,required = false) String sortDir){
        return new ResponseEntity<>(service.getPostsByCategory(id,pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
    }
    @RequestMapping(value = "/getPostByUser",method = RequestMethod.GET)
    public ResponseEntity<PostResponse> getPostsByUser(@RequestParam("userId")  Integer id,
                                                        @RequestParam(value = "pageNumber",defaultValue = PAGE_NUMBER,required = false) Integer pageNumber,
                                                        @RequestParam(value = "pageSize",defaultValue = PAGE_SIZE,required = false) Integer pageSize,
                                                        @RequestParam(value = "sortBy",defaultValue = SORT_BY,required = false) String sortBy,
                                                        @RequestParam(value = "sortDir",defaultValue = SORT_DIR,required = false) String sortDir){
        return new ResponseEntity<>(service.getPostsByUser(id,pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
    }
    @RequestMapping(value = "/getAllPosts",method = RequestMethod.GET)
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber",defaultValue = PAGE_NUMBER,required = false) Integer pageNumber,
                                                    @RequestParam(value = "pageSize",defaultValue = PAGE_SIZE,required = false) Integer pageSize,
                                                    @RequestParam(value = "sortBy",defaultValue = SORT_BY,required = false) String sortBy,
                                                    @RequestParam(value = "sortDir",defaultValue = SORT_DIR,required = false) String sortDir){
        return new ResponseEntity<>(service.getPosts(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
    }
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public ResponseEntity<List<PostDto>> search(@RequestParam("keywords") String keywords){
        return new ResponseEntity<>(service.searchPost(keywords),HttpStatus.OK);
    }

    @RequestMapping(value = "/image/upload/{postId}",method = RequestMethod.POST)
    public ResponseEntity<PostDto> uploadImage(@PathVariable("postId") Integer postId,
                                                     @RequestParam("image") MultipartFile file) throws IOException {
        PostDto post = service.getById(postId);

        String fileName = fileService.uploadImage(path,file);
        post.setImageName(fileName);
        return new ResponseEntity<>(service.update(post,postId),HttpStatus.OK);
    }

    @RequestMapping(value = "/image/download/{imageName}",method = RequestMethod.GET ,produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String name, HttpServletResponse response) throws IOException {
        InputStream resource = fileService.getResource(path,name);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }

    @RequestMapping(value = "/image/downloadById/{postId}",method = RequestMethod.GET ,produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImageByPostId(@PathVariable("postId") Integer id, HttpServletResponse response) throws IOException {
        InputStream resource = fileService.getResource(path,service.getById(id).getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
