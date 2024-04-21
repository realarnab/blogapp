package com.blogapp.controller;

import com.blogapp.payload.ListPostDto;
import com.blogapp.payload.PostDto;
import com.blogapp.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //http://localhost:8080/api/posts
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult bindingResult){
//        if (postDto.getTitle().length()<3){
//            return new ResponseEntity<>("Title is too short!",HttpStatus.INTERNAL_SERVER_ERROR);
//        } else if (postDto.getDescription().length()<10) {
//            return new ResponseEntity<>("Description is too short!",HttpStatus.INTERNAL_SERVER_ERROR);
//        }
        if (bindingResult.hasErrors()){
            String messages = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.joining("/n"));
            return new  ResponseEntity<>(messages,HttpStatus.BAD_REQUEST);
        }
        PostDto dto = postService.createPost(postDto);
        return  new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    //http://localhost:8080/api/posts/2
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id){
            postService.deletePost(id);
            return new ResponseEntity<>("Post is deleted!", HttpStatus.OK);
    }

    //http://localhost:8080/api/posts -->to get all details
    //http://localhost:8080/api/posts?pageNo=0&pageSize=5 --> to get the data as a set of 5 in a page
    //http://localhost:8080/api/posts?pageNo=0&pageSize=5&sortBy=title --> to get the data as a set of 5 in a page in a sorting order
    //http://localhost:8080/api/posts?pageNo=0&pageSize=5&sortBy=title&sortDir=asc --> to get the data as a set of 5 in a page in a sorting order as ascending
    @GetMapping
    public ResponseEntity<ListPostDto> getAllBlogs(@RequestParam(name = "pageNo",defaultValue = "0",required = false) int pageNo,@RequestParam(name = "pageSize",defaultValue = "5",required = false) int pageSize,@RequestParam(name = "sortBy",defaultValue = "id",required = false) String sortBy,@RequestParam(name = "sortDir",defaultValue = "asc",required = false) String sortDir){
        ListPostDto allDetails = postService.getAllDetails(pageNo,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(allDetails,HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostByPostId(@PathVariable long postId){
        PostDto postById = postService.getPostById(postId);
        return new ResponseEntity<>(postById,HttpStatus.OK);
    }
}
