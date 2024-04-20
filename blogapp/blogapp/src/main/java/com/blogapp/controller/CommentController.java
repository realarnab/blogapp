package com.blogapp.controller;

import com.blogapp.payload.CommentDto;
import com.blogapp.payload.PostWithCommentDto;
import com.blogapp.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {

    private CommentService commentService;

    //http://localhost:8080/api/comments/1
    @PostMapping("/{postId}")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable long postId){
        CommentDto dto = commentService.createComment(commentDto, postId);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


    @GetMapping("/{postId}")
    public ResponseEntity<PostWithCommentDto> getAllCommentsByPostId(@PathVariable long postId){
        PostWithCommentDto commentsByPostId = commentService.getCommentsByPostId(postId);
        return new ResponseEntity<>(commentsByPostId,HttpStatus.OK);
    }
}
