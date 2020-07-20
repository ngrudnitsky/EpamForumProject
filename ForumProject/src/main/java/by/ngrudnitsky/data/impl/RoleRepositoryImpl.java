package by.ngrudnitsky.data.impl;

import by.ngrudnitsky.data.RoleRepository;
import by.ngrudnitsky.entity.Role;
import by.ngrudnitsky.entity.Status;
import by.ngrudnitsky.exception.RoleRepositoryException;
import by.ngrudnitsky.util.ConnectionPool;
import by.ngrudnitsky.util.CustomConnectionPool;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleRepositoryImpl implements RoleRepository {
    private final Logger log = LoggerFactory.getLogger(RoleRepositoryImpl.class);
    private final ConnectionPool connectionPool = CustomConnectionPool
            .getConnectionPool("jdbc:mysql://localhost:3306/mydb?serverTimezone=UTC", "root", "root");

    @Override
    public Role findByName(String roleName) throws RoleRepositoryException {
        Connection connection = connectionPool.getConnection();
        String findByNameQuery = "SELECT * FROM roles WHERE name = ?";
        String errorMessage = "IN RoleRepositoryImpl.findByName failed to find role by name %s";
        Role role = new Role();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(findByNameQuery);
            preparedStatement.setString(1, roleName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                role.setId(resultSet.getInt("id"));
                role.setName(roleName);
                role.setStatus(EnumUtils.getEnum(Status.class, resultSet.getString("status")));
                role.setCreated(resultSet.getDate("createdAt"));
                role.setUpdated(resultSet.getDate("updatedAt"));
                return role;
            }
        } catch (SQLException e) {
            log.error(errorMessage);
            throw new RoleRepositoryException(errorMessage, e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        log.error(errorMessage);
        throw new RoleRepositoryException(errorMessage);
    }

    //todo List<Integer>
    @Override
    public List<String> findAllUserRoles(Integer userId) throws RoleRepositoryException {
        Connection connection = connectionPool.getConnection();
        String errorMessage = String.format(
                "IN RoleRepositoryImpl.findAllUserRoles failed to find all roles of %s user.", userId);
        try {
            String findAllUserRolesQuery = "SELECT roles_id FROM users_has_roles WHERE users_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(findAllUserRolesQuery);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<String> roles = new ArrayList<>();
            while (resultSet.next()) {
                roles.add(resultSet.getString(1));
            }
            if (roles.isEmpty()) {
                log.error(errorMessage);
                throw new RoleRepositoryException(errorMessage);
            }
            return roles;
        } catch (SQLException e) {
            log.error(errorMessage);
            throw new RoleRepositoryException(errorMessage, e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void setRoleUser(Integer userId) throws RoleRepositoryException {
        Connection connection = connectionPool.getConnection();
        try {
            String saveUserRoleQuery = "INSERT INTO users_has_roles(users_id, roles_id)VALUES(?,1)";
            PreparedStatement preparedStatement = connection.prepareStatement(saveUserRoleQuery);
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            String errorMessage = String.format(
                    "IN RoleRepositoryImpl.setRoleUser failed to set USER role for user with id %s", userId);
            log.error(errorMessage);
            throw new RoleRepositoryException(errorMessage, e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }
}
