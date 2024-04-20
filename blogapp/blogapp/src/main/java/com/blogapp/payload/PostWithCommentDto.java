package com.blogapp.payload;

import com.blogapp.entity.Post;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostWithCommentDto {
    private PostDto post;
    private List<CommentDto> commentDtos;
}
