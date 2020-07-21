package by.ngrudnitsky.data.impl;

import by.ngrudnitsky.data.UserRepository;
import by.ngrudnitsky.entity.Status;
import by.ngrudnitsky.entity.User;
import by.ngrudnitsky.exception.UserRepositoryException;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static by.ngrudnitsky.util.ConnectionPool.getConnection;
import static by.ngrudnitsky.util.ConnectionPool.releaseConnection;

public class UserRepositoryImpl implements UserRepository {
    private final Logger log = LoggerFactory.getLogger(UserRepositoryImpl.class);
    
    @Override
    public User findByUsername(String userName) throws UserRepositoryException {
        Connection connection = getConnection();;
        String errorMessage = String.format(
                "IN UserRepositoryImpl.findByUsername failed to find user by userName %s", userName);
        try {
            String findByNameQuery = "SELECT * FROM users WHERE userName = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(findByNameQuery);
            preparedStatement.setString(1, userName);
            User user = new User();
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("lastName"));
                user.setUserName(userName);
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setCreated(resultSet.getDate("createdAt"));
                user.setUpdated(resultSet.getDate("updatedAt"));
                user.setStatus(EnumUtils.getEnum(Status.class, resultSet.getString("status")));
                //todo log.info for all repos
                return user;
            }
        } catch (SQLException e) {
            log.error(errorMessage);
            throw new UserRepositoryException(errorMessage, e);
        } finally {
            releaseConnection(connection);
        }
        log.error(errorMessage);
        throw new UserRepositoryException(errorMessage);
    }

    @Override
    public User create(User user) throws UserRepositoryException {
        Connection connection = getConnection();
        try {
            String saveUserQuery = "INSERT INTO users(firstName, LastName, userName," +
                    "email, password, status, createdAt, updatedAt)VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(saveUserQuery);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getStatus().name());
            preparedStatement.setDate(7, new Date(user.getCreated().getTime()));
            preparedStatement.setDate(8, new Date(user.getCreated().getTime()));
            preparedStatement.executeUpdate();
            user.setId(getLastId());
            return user;
        } catch (SQLException e) {
            String errorMessage = String.format(
                    "IN UserRepositoryImpl.create failed to create new user %s", user.getUserName());
            log.error(errorMessage);
            throw new UserRepositoryException(errorMessage, e);
        } finally {
            releaseConnection(connection);
        }
    }

    //todo findFromTo
    @Override
    public List<User> findAll() throws UserRepositoryException {
        Connection connection = getConnection();
        try {
            String findAllUsersQuery = "SELECT userName FROM users";
            List<User> users = new ArrayList<>();
            ResultSet resultSet = connection.createStatement().executeQuery(findAllUsersQuery);
            while (resultSet.next()) {
                users.add(findByUsername(resultSet.getString(1)));
            }
            return users;
        } catch (SQLException e) {
            String errorMessage = "IN UserRepositoryImpl.findAll failed to find all users.";
            log.error(errorMessage);
            throw new UserRepositoryException(errorMessage, e);
        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public User update(User user) throws UserRepositoryException {
        Connection connection = getConnection();
        try {
            String updateUserQuery = "UPDATE users SET firstName = ?, LastName = ?, userName = ?," +
                    "email = ?, password = ?, status = ?, updatedAt = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateUserQuery);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getStatus().name());
            preparedStatement.setDate(7, new Date(user.getCreated().getTime()));
            preparedStatement.setInt(8, user.getId());
            preparedStatement.executeUpdate();
            return user;
        } catch (SQLException e) {
            String errorMessage = String.format(
                    "IN UserRepositoryImpl.update failed to update user %s", user.getUserName());
            log.error(errorMessage);
            throw new UserRepositoryException(errorMessage, e);
        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public User deleteById(Integer id) throws UserRepositoryException {
        Connection connection = getConnection();
        try {
            String updateUserQuery = "UPDATE users SET status = ?, updatedAt = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateUserQuery);
            preparedStatement.setString(1, Status.DELETED.name());
            preparedStatement.setDate(2, new Date(System.currentTimeMillis()));
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            return findById(id);
        } catch (SQLException e) {
            String errorMessage = String.format(
                    "IN UserRepositoryImpl.deleteById failed to delete user with id %s", id);
            log.error(errorMessage);
            throw new UserRepositoryException(errorMessage, e);
        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public User findById(Integer id) throws UserRepositoryException {
        Connection connection = getConnection();
        String errorMessage = String.format("IN UserRepositoryImpl.findById failed to find user by id %s", id);
        try {
            String findByIdQuery = "SELECT * FROM users WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(findByIdQuery);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(id);
                user.setFirstName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("lastName"));
                user.setUserName(resultSet.getString("userName"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setCreated(resultSet.getDate("createdAt"));
                user.setUpdated(resultSet.getDate("updatedAt"));
                user.setStatus(EnumUtils.getEnum(Status.class, resultSet.getString("status")));
                return user;
            }
        } catch (SQLException e) {
            log.error(errorMessage);
            throw new UserRepositoryException(errorMessage, e);
        } finally {
            releaseConnection(connection);
        }
        log.error(errorMessage);
        throw new UserRepositoryException(errorMessage);
    }

    @Override
    public Integer getLastId() throws UserRepositoryException {
        Connection connection = getConnection();
        try {
            String getLastId = "SELECT LAST_INSERT_ID()";
            ResultSet resultSet = connection.createStatement().executeQuery(getLastId);
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            String errorMessage = "IN UserRepositoryImpl.getLastId failed to get last id";
            log.error(errorMessage);
            throw new UserRepositoryException(errorMessage, e);
        } finally {
            releaseConnection(connection);
        }
        return -1;
    }
}
