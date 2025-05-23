package com.deere.blog.repository;

import com.deere.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {
    Optional<Comment> findById(Integer id);
}

