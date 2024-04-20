package com.blogapp.service.Impl;

import com.blogapp.entity.Comment;
import com.blogapp.entity.Post;
import com.blogapp.exception.ResourceNotFound;
import com.blogapp.payload.CommentDto;
import com.blogapp.payload.PostDto;
import com.blogapp.payload.PostWithCommentDto;
import com.blogapp.repository.CommentRepository;
import com.blogapp.repository.PostRepository;
import com.blogapp.service.CommentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private ModelMapper modelMapper;
    private PostRepository postRepository;
    @Override
    public CommentDto createComment(CommentDto commentDto,long postId) {
        Optional<Post> byId = postRepository.findById(postId);
        Post post = byId.get(); //convert it to entity object
        Comment comment = mapToEntity(commentDto);
        comment.setPost(post); //
        Comment saved = commentRepository.save(comment);
        CommentDto dto = mapToDto(saved);
        return dto;
    }

    @Override
    public PostWithCommentDto getCommentsByPostId(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFound("Post not found with id: " + postId));

        PostDto postDto=new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());

        List<Comment> comments = commentRepository.findByPostId(postId);
        List<CommentDto> dto = comments.stream().map(e -> mapToDto(e)).collect(Collectors.toList());

        PostWithCommentDto postWithCommentDto=new PostWithCommentDto();
        postWithCommentDto.setCommentDtos(dto);
        postWithCommentDto.setPost(postDto);
        return postWithCommentDto;
    }

    public Comment mapToEntity(CommentDto commentDto){
        Comment comment = modelMapper.map(commentDto, Comment.class);
        return comment;
    }

    public CommentDto mapToDto(Comment comment){
        CommentDto dto = modelMapper.map(comment, CommentDto.class);
        return dto;
    }

}
