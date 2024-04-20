package com.blogapp.service.Impl;

import com.blogapp.entity.Post;
import com.blogapp.exception.ResourceNotFound;
import com.blogapp.payload.ListPostDto;
import com.blogapp.payload.PostDto;
import com.blogapp.repository.PostRepository;
import com.blogapp.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

//Constructor based Dependency Injection
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.modelMapper=modelMapper;
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
//        Post post=new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        //Reducing the above lines of code by using this method 'mapToEntity'
        Post post = mapToEntity(postDto);

        Post savedPost = postRepository.save(post);

//        PostDto dto=new PostDto();

//        dto.setId(savedPost.getId());
//        dto.setTitle(savedPost.getTitle());
//        dto.setDescription(savedPost.getDescription());
//        dto.setContent(savedPost.getContent());
//        return  dto;
        //Reducing the above lines of code by using this method 'mapToDto', we reuse the above lines of code by using the method
        PostDto dto = mapToDto(savedPost);
        return dto;
    }

//    @Override
//    public boolean checkExistance(long id) {
//        boolean val = postRepository.existsById(id);
//        return val;
//    }

    @Override
    public void deletePost(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Post not found with id: " + id));
        postRepository.deleteById(id);
    }

    @Override
    public ListPostDto getAllDetails(int pageNo, int pageSize, String sortBy, String sortDir) {
        // List<Post> all = postRepository.findAll();
        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending(); //used ternary operator to check that whether the condition is ASC or DESC
        //Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by(sortBy)); //Sort.by() --> will convert the string to sort object
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);
        Page<Post> page = postRepository.findAll(pageable);
        List<Post> post = page.getContent(); //it converts it to List
        List<PostDto> dtos = post.stream().map(e -> this.mapToDto(e)).collect(Collectors.toList());//converting entity to dto using Stream API

        ListPostDto listPostDto=new ListPostDto();
        listPostDto.setPostDto(dtos); //it will store the contents to listPostDto
        listPostDto.setTotalPages(page.getTotalPages()); //return the number of total pages
        listPostDto.setPageNumber(page.getNumber());
        listPostDto.setGetTotalElements(page.getNumberOfElements());
        listPostDto.setLastPage(page.isLast());
        listPostDto.setFirstPage(page.isFirst());

        return listPostDto;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Post not found with id: " + id));
        PostDto postDto = mapToDto(post);
        return postDto;
    }


    public Post mapToEntity(PostDto postDto){
//        Post post=new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
//        return post;
        //by the use of ModelMapper class we can map object easily, we don't need to write the above lines of code
        Post post = modelMapper.map(postDto, Post.class);
        return post;
    }

    public PostDto mapToDto(Post post){
//        PostDto dto=new PostDto();
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());
//        return  dto;
        PostDto dto = modelMapper.map(post, PostDto.class);
        return dto;
    }
}
