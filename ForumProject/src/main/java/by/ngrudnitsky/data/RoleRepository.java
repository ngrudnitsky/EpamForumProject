package by.ngrudnitsky.data;

import by.ngrudnitsky.entity.Role;
import by.ngrudnitsky.exception.RoleRepositoryException;

import java.util.List;

public interface RoleRepository {
    Role findByName(String name) throws RoleRepositoryException;

    List<String> findAllUserRoles(Integer userId) throws RoleRepositoryException;

    void setRoleUser(Integer userId) throws RoleRepositoryException;
}
