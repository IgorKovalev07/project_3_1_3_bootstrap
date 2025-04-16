package ru.kovalev.project_3_1_2.dao;

import ru.kovalev.project_3_1_2.model.Role;

import java.util.List;

public interface RoleDao {
    Role findByName(String name);

    void save(Role role);

    List<Role> getAllRoles();
}
