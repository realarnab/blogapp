package com.blogapp.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    private long id;
    private String name;
    private String email;
    private String message;
}
