package com.deere.blog.service.impl;

import com.deere.blog.entity.Post;
import com.deere.blog.exception.ResourceNotFoundException;
import com.deere.blog.payload.PostDto;
import com.deere.blog.payload.PostResponse;
import com.deere.blog.repository.CategoryRepository;
import com.deere.blog.repository.PostRepository;
import com.deere.blog.repository.UserRepository;
import com.deere.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    public static final String IMAGE = "default.png";
    public static final String ASC = "asc";
    @Autowired
    private PostRepository postRepo;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private CategoryRepository catRepo;
    @Value("${project.image}")
    private String path;

    @Override
    public PostDto create(PostDto dto,Integer userId,Integer catId) {
        Post post = mapper.map(dto,Post.class);
        post.setImageName(dto.getImageName().isEmpty() ? IMAGE : dto.getImageName());
        post.setAddedDate(new Date());
        post.setUser(userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User ","User id",userId)));
        post.setCategory(catRepo.findByCategoryId(catId).orElseThrow(()->new ResourceNotFoundException("Category ","Category id",catId)));
        return mapper.map(postRepo.save(post),PostDto.class);
    }

    @Override
    public PostDto update(PostDto dto, Integer postId) {
        Post post = postRepo.getReferenceById(postId);
        post.setContent(dto.getContent());
        post.setTitle(dto.getTitle());
        post.setImageName(dto.getImageName().isEmpty() ? IMAGE : dto.getImageName());
        return mapper.map(postRepo.save(post),PostDto.class);
    }
    @Override
    public PostDto getById(Integer id) {
        return mapper.map(postRepo.findByPostId(id).orElseThrow(()->new ResourceNotFoundException("Post ","id",id)),PostDto.class);
    }

    @Override
    public void delete(Integer id) {
        Post post = postRepo.findByPostId(id).orElseThrow(()->new ResourceNotFoundException("Post ","id",id));
        postRepo.delete(post);
    }

    @Override
    public PostResponse getPostsByCategory(Integer categoryId,Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(ASC)? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable page = PageRequest.of(pageNumber,pageSize,sort);
        Page<Post> postPage = postRepo.findByCategoryCategoryId(categoryId,page);

        return new PostResponse(postPage.getContent().stream().map(this::entityToDto).collect(Collectors.toList()),postPage.getNumber(),postPage.getSize(),
                postPage.getTotalElements(),postPage.getTotalPages(),postPage.isLast());
    }

    private PostDto entityToDto(Post post){
        return  this.mapper.map(post,PostDto.class);
    }

    @Override
    public PostResponse getPostsByUser(Integer userId,Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(ASC)? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable page = PageRequest.of(pageNumber,pageSize,sort);
        Page<Post> postPage = postRepo.findByUserId(userId,page);

        PostResponse res = new PostResponse();
        res.setContent(postPage.getContent().stream().map(this::entityToDto).collect(Collectors.toList()));
        res.setPageNumber(postPage.getNumber());
        res.setPageSize(postPage.getSize());
        res.setTotalPages(postPage.getTotalPages());
        res.setTotalElements(postPage.getTotalElements());
        res.setLastPage(postPage.isLast());

        return res;
    }


    @Override
    public PostResponse getPosts(Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(ASC)? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable page = PageRequest.of(pageNumber,pageSize,sort);
        Page<Post> pagePosts = postRepo.findAll(page);
        List<Post> posts = pagePosts.getContent();

        PostResponse res = new PostResponse();
        res.setContent(posts.stream().map(this::entityToDto).collect(Collectors.toList()));
        res.setPageNumber(pagePosts.getNumber());
        res.setPageSize(pagePosts.getSize());
        res.setTotalPages(pagePosts.getTotalPages());
        res.setTotalElements(pagePosts.getTotalElements());
        res.setLastPage(pagePosts.isLast());

        return res;
    }

    @Override
    public List<PostDto> searchPost(String keyword) {
        return postRepo.findByTitleContainingOrContentContaining(keyword,keyword).stream().map(this::entityToDto).toList();
    }
}
