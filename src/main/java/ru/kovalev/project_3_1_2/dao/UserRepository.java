package ru.kovalev.project_3_1_2.dao;

import ru.kovalev.project_3_1_2.model.User;

import java.util.List;


public interface UserRepository {
    User findByEmail(String email);

    List<User> findAll();

    void save(User user);
}
