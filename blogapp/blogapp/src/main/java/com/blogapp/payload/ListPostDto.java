package com.blogapp.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListPostDto {
    private List<PostDto> postDto;  //this will return back all the post as collection
    private int totalPages;
    private int getTotalElements;
    private boolean lastPage;
    private boolean firstPage;
    private int pageNumber;
}
