package by.ngrudnitsky.service.impl;

import by.ngrudnitsky.data.PostRepository;
import by.ngrudnitsky.dto.PostDto;
import by.ngrudnitsky.entity.Post;
import by.ngrudnitsky.entity.Status;
import by.ngrudnitsky.exeption.PostNotFoundException;
import by.ngrudnitsky.exeption.PostServiceException;
import by.ngrudnitsky.mapper.PostMapper;
import by.ngrudnitsky.service.AuthService;
import by.ngrudnitsky.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final AuthService authService;
    private final PostMapper postMapper;

    @Override
    public PostDto create(PostDto postDto) {
        Post post = postMapper.map(postDto, authService.getCurrentUser());
        Post createdPost = postRepository.save(post);
        log.info("IN create - post: {} successfully registered", createdPost);
        return postMapper.mapToPostDto(post);
    }

    @Override
    public Post updatePost(Post post) {
        post.setUpdated(Instant.now());
        postRepository.update(post.getTitle(), post.getPreview(), post.getContent(),
                post.getStatus(), new Date(post.getUpdated().getEpochSecond()), post.getPostId());
        log.info("IN PostServiceImpl.updatePost - post: #{} successfully registered", post.getPostId());
        return post;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostDto> findAll() {
        List<Post> posts = postRepository.findAll();
        log.info("IN getAll - {} posts found", posts.size());
        return posts.stream()
                .map(postMapper::mapToPostDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PostDto findById(Long id) throws  PostNotFoundException {
        Post post = postRepository.findById(id).orElse(null);
        if (post == null) {
            String errorMessage = String.format(
                    "IN findById - no post found by id: %S", id);
            log.error(errorMessage);
            throw new PostNotFoundException(errorMessage);
        }
        log.info("IN findById - post: {} found by id: {}", post, id);
        return postMapper.mapToPostDto(post);
    }

    @Override
    public Post deleteById(Long id) throws  PostNotFoundException {
        Post post = postRepository.findById(id).orElse(null);
        if (post == null) {
            String errorMessage = String.format(
                    "IN delete - no post found by id: %s", id);
            log.error(errorMessage);
            throw new PostNotFoundException(errorMessage);
        }
        post.setStatus(Status.DELETED);
        post.setUpdated(Instant.now());
        Post deletedPost = postRepository.save(post);
        log.info("IN delete - user with id: {} successfully deleted", id);
        return deletedPost;
    }
}
