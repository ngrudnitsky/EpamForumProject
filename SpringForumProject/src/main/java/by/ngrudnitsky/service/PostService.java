package by.ngrudnitsky.service;

import by.ngrudnitsky.dto.PostDto;
import by.ngrudnitsky.entity.Post;
import by.ngrudnitsky.exeption.PostNotFoundException;
import by.ngrudnitsky.exeption.PostServiceException;

import java.util.List;

public interface PostService {

    PostDto create(PostDto postDto);

    Post updatePost(Post post) throws PostServiceException;

    List<PostDto> findAll();

    PostDto findById(Long id) throws PostNotFoundException;

    Post deleteById(Long id) throws PostNotFoundException;

}
