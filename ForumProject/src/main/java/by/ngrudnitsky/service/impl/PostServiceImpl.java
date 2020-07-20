package by.ngrudnitsky.service.impl;

import by.ngrudnitsky.data.PostRepository;
import by.ngrudnitsky.data.impl.PostRepositoryImpl;
import by.ngrudnitsky.entity.Post;
import by.ngrudnitsky.entity.Status;
import by.ngrudnitsky.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

public class PostServiceImpl implements PostService {
    private final PostRepository postRepository = new PostRepositoryImpl();
    private final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);

    @Override
    public Post create(Post post) throws PostServiceException {
        checkIfValueIsNull(post, "IN PostServiceImpl.create  - post is null");
        try {
            post.setStatus(Status.ACTIVE);
            post.setCreated(new Date());
            post.setUpdated(new Date());
            post = postRepository.create(post);
            log.info("IN PostServiceImpl.create - post: #{} is successfully created", post.getId());
            return post;
        } catch (PostRepositoryException e) {
            String errorMessage = String.format(
                    "IN PostServiceImpl.create - failed to create post #%s", post.getId());
            log.error(errorMessage);
            throw new PostServiceException(errorMessage, e);
        }
    }

    @Override
    public Post updatePost(Post post) throws PostServiceException {
        checkIfValueIsNull(post, "IN PostServiceImpl.updatePost - post is null");
        try {
            post = postRepository.update(post);
            log.info("IN PostServiceImpl.updatePost - post: #{} successfully registered", post.getId());
            return post;
        } catch (PostRepositoryException e) {
            String errorMessage = String.format(
                    "IN PostServiceImpl.updatePost - failed to create post #%s", post.getId());
            log.error(errorMessage);
            throw new PostServiceException(errorMessage, e);
        }
    }

    @Override
    public List<Post> findAll() throws PostServiceException {
        try {
            List<Post> posts = postRepository.findAll();
            log.info("IN PostServiceImpl.findAll - {} posts found", posts.size());
            return posts;
        } catch (PostRepositoryException e) {
            String errorMessage = "IN PostServiceImpl.findAll - failed to get all posts";
            log.error(errorMessage);
            throw new PostServiceException(errorMessage, e);
        }
    }

    @Override
    public List<Post> findFromTo(Integer from, Integer to) throws PostServiceException {
        try {
            List<Post> posts = postRepository.findFromTo(from, to);
            log.info("IN PostServiceImpl.findFromTo - {} posts found", posts.size());
            return posts;
        } catch (PostRepositoryException e) {
            String errorMessage = String.format(
                    "IN PostServiceImpl.findFromTo - failed to get posts from %s to %s", from, to);
            log.error(errorMessage);
            throw new PostServiceException(errorMessage, e);
        }
    }

    @Override
    public Post findById(Integer id) throws PostServiceException {
        checkIfValueIsNull(id, "IN PostServiceImpl.findById - id is Null");
        try {
            Post post = postRepository.findById(id);
            log.info("IN PostServiceImpl.findById - post: found by id: #{}", id);
            return post;
        } catch (PostRepositoryException e) {
            String errorMessage = String.format(
                    "IN PostServiceImpl.findById Failed to find user by id %s", id);
            log.error(errorMessage);
            throw new PostServiceException(errorMessage, e);
        }
    }

    @Override
    public Post deleteById(Integer id) throws PostServiceException {
        checkIfValueIsNull(id, "IN PostServiceImpl.deleteById - id is Null");
        try {
            Post post = postRepository.deleteById(id);
            log.info("IN PostServiceImpl.deleteById - post with id: {} successfully deleted", id);
            return post;
        } catch (PostRepositoryException e) {
            String errorMessage = String.format(
                    "IN PostServiceImpl.deleteById - Failed to delete post by id %s", id);
            log.error(errorMessage);
            throw new PostServiceException(errorMessage, e);
        }
    }

    @Override
    public Integer getLastId() throws PostServiceException {
        String errorMessage = "IN PostServiceImpl.getLastId - Failed to get last id";
        try {
            Integer id = postRepository.getLastId();
            if (id == -1) {
                throw new PostServiceException(errorMessage);
            }
            log.info("IN PostServiceImpl.getLastId - id: {} was successfully got", id);
            return id;
        } catch (PostRepositoryException e) {
            log.error(errorMessage);
            throw new PostServiceException(errorMessage, e);
        }
    }

    private void checkIfValueIsNull(Object value, String errorMessage) throws PostServiceException {
        if (value == null) {
            log.error(errorMessage);
            throw new PostServiceException(errorMessage);
        }
    }
}
