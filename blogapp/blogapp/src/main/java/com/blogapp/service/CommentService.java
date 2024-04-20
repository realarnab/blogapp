package com.blogapp.service;

import com.blogapp.payload.CommentDto;
import com.blogapp.payload.PostWithCommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto,long postId);
    PostWithCommentDto getCommentsByPostId(long postId);

}
