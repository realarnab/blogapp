package com.blogapp.service;
import com.blogapp.payload.ListPostDto;
import com.blogapp.payload.PostDto;

import java.util.List;

public interface PostService {
    public PostDto createPost(PostDto postDto);

   // public boolean checkExistance(long id);

    void deletePost(long id);
    public ListPostDto getAllDetails(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto getPostById(long id);
}