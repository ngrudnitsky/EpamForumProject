package by.ngrudnitsky.data.impl;

import by.ngrudnitsky.data.PostRepository;
import by.ngrudnitsky.entity.Post;
import by.ngrudnitsky.entity.Status;
import by.ngrudnitsky.exception.PostRepositoryException;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static by.ngrudnitsky.util.ConnectionPool.getConnection;
import static by.ngrudnitsky.util.ConnectionPool.releaseConnection;

public class PostRepositoryImpl implements PostRepository {
    private final Logger log = LoggerFactory.getLogger(PostRepositoryImpl.class);

    @Override
    public Post findById(Integer id) throws PostRepositoryException {
        Connection connection = getConnection();
        String findByIdQuery = "SELECT * FROM posts WHERE id = ?";
        String errorMessage = String.format("IN PostRepositoryImpl.findById failed to find post by id %d", id);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(findByIdQuery);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Post post = new Post();
            if (resultSet.next()) {
                post.setId(id);
                post.setTitle(resultSet.getString("title"));
                post.setPreview(resultSet.getString("preview"));
                post.setContent(resultSet.getString("content"));
                post.setCreated(resultSet.getDate("createdAt"));
                post.setUpdated(resultSet.getDate("updatedAt"));
                post.setStatus(EnumUtils.getEnum(Status.class, resultSet.getString("status")));
                post.setUserId(resultSet.getInt("users_id"));
                return post;
            }
        } catch (SQLException e) {
            log.error(errorMessage);
            throw new PostRepositoryException(errorMessage, e);
        } finally {
            releaseConnection(connection);
        }
        log.error(errorMessage);
        throw new PostRepositoryException(errorMessage);
    }

    @Override
    public Post update(Post post) throws PostRepositoryException {
        Connection connection = getConnection();
        try {
            String updatePostQuery = "UPDATE posts SET title = ?, preview = ?, content = ?, status = ?, " +
                    "updatedAt= ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updatePostQuery);
            preparedStatement.setString(1, post.getTitle());
            preparedStatement.setString(2, post.getPreview());
            preparedStatement.setString(3, post.getContent());
            preparedStatement.setString(4, post.getStatus().name());
            preparedStatement.setDate(5, new Date(post.getUpdated().getTime()));
            preparedStatement.setInt(6, post.getId());
            preparedStatement.executeUpdate();
            return post;
        } catch (SQLException e) {
            String errorMessage = String.format(
                    "IN PostRepositoryImpl.update failed to update post with id %d", post.getId());
            log.error(errorMessage);
            throw new PostRepositoryException(errorMessage, e);
        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public Post deleteById(Integer id) throws PostRepositoryException {
        Connection connection = getConnection();
        try {
            String updateUserQuery = "UPDATE posts SET status = ?, updatedAt = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateUserQuery);
            preparedStatement.setString(1, Status.DELETED.name());
            preparedStatement.setDate(2, new Date(System.currentTimeMillis()));
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            return findById(id);
        } catch (SQLException e) {
            String errorMessage = String.format(
                    "IN PostRepositoryImpl.deleteById failed to delete post with id %s", id);
            log.error(errorMessage);
            throw new PostRepositoryException(errorMessage, e);
        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public Post create(Post post) throws PostRepositoryException {
        Connection connection = getConnection();
        try {
            String createPostQuery = "INSERT INTO posts(title, preview, content, status," +
                    "createdAt, updatedAt, users_id)VALUES(?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(createPostQuery);
            preparedStatement.setString(1, post.getTitle());
            preparedStatement.setString(2, post.getPreview());
            preparedStatement.setString(3, post.getContent());
            preparedStatement.setString(4, post.getStatus().name());
            preparedStatement.setDate(5, new Date(post.getCreated().getTime()));
            preparedStatement.setDate(6, new Date(post.getCreated().getTime()));
            preparedStatement.setInt(7, post.getUserId());
            preparedStatement.executeUpdate();
            post.setId(getLastId());
            return post;
        } catch (SQLException e) {
            String errorMessage = String.format(
                    "IN PostRepositoryImpl.create failed to create new post %s", post.getTitle());
            log.error(errorMessage);
            throw new PostRepositoryException(errorMessage, e);
        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public List<Post> findAll() throws PostRepositoryException {
        Connection connection = getConnection();
        try {
            String findAllPostsQuery = "SELECT id FROM posts";
            List<Post> posts = new ArrayList<>();
            ResultSet resultSet = connection.createStatement().executeQuery(findAllPostsQuery);
            while (resultSet.next()) {
                posts.add(findById(resultSet.getInt(1)));
            }
            return posts;
        } catch (SQLException e) {
            String errorMessage = "IN PostRepositoryImpl.findAll failed to find all posts";
            log.error(errorMessage);
            throw new PostRepositoryException(errorMessage, e);
        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public List<Post> findFromTo(Integer from, Integer to) throws PostRepositoryException {
        Connection connection = getConnection();
        try {
            String findAllPostsQuery = "SELECT id FROM posts WHERE status = 'ACTIVE' LIMIT ?, ?";
            PreparedStatement preparedStatement = connection.prepareStatement(findAllPostsQuery);
            preparedStatement.setInt(1, from);
            preparedStatement.setInt(2, to - from);
            List<Post> posts = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                posts.add(findById(resultSet.getInt(1)));
            }
            return posts;
        } catch (SQLException e) {
            String errorMessage = "IN PostRepositoryImpl.findAll failed to find all posts";
            log.error(errorMessage);
            throw new PostRepositoryException(errorMessage, e);
        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public Integer getLastId() throws PostRepositoryException {
        Connection connection = getConnection();
        try {
            String getLastId = "SELECT LAST_INSERT_ID()";
            ResultSet resultSet = connection.createStatement().executeQuery(getLastId);
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            String errorMessage = "IN PostRepositoryImpl.getLastId failed to get last id";
            log.error(errorMessage);
            throw new PostRepositoryException(errorMessage, e);
        } finally {
            releaseConnection(connection);
        }
        return -1;
    }
}
