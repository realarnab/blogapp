package com.blogapp.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {
    private long id;
    @NotEmpty //to ensure that it will not empty
    @Size(min = 3,message = "Title should be at least 3 character")
    private  String title;

    @NotEmpty
    @Size(min = 10,message = "Description should be at least 10 characters")
    private  String description;
    private  String content;
}
