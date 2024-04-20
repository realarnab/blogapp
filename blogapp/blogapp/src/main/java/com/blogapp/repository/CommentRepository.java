package com.blogapp.repository;

import com.blogapp.entity.Comment;
import com.blogapp.payload.CommentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    public List<Comment> findByPostId(long postId);
}
