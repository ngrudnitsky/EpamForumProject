package by.ngrudnitsky.service;

import by.ngrudnitsky.dto.PostDto;
import by.ngrudnitsky.entity.Post;
import by.ngrudnitsky.exception.PostNotFoundException;
import by.ngrudnitsky.exception.PostServiceException;

import java.util.List;

@SuppressWarnings("unused")
public interface PostService {

    PostDto create(PostDto postDto);

    Post updatePost(Post post) throws PostServiceException;

    List<PostDto> findAll();

    PostDto findById(Long id) throws PostNotFoundException;

    Post deleteById(Long id) throws PostNotFoundException;

}
