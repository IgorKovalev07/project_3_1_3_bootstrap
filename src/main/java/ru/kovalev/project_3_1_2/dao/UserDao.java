package ru.kovalev.project_3_1_2.dao;


import ru.kovalev.project_3_1_2.model.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers();

    void saveUser(User user);

    void deleteUser(Long id);

    void updateUser(User user);

    User showUser(Long id);

    User findByEmail(String email);
}
