package com.deere.blog.repository;

import com.deere.blog.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    Optional<Post> findByPostId(Integer id);
    Page<Post> findByUserId(Integer id, Pageable pageable);
    Page<Post> findByCategoryCategoryId(Integer catId, Pageable pageable);
    List<Post> findByTitleContainingOrContentContaining(String title,String content);
}
