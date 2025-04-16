package ru.kovalev.project_3_1_2.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kovalev.project_3_1_2.model.Role;
import ru.kovalev.project_3_1_2.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAllUsers();

    void saveUser(User user);

    void saveUserWithRoles(User user, List<String> roleNames);

    void deleteUser(Long id);

    void updateUser(User user);

    void updateUserWithRoles(User user, List<String> roleNames);

    User showUser(Long id);

    User findByEmail(String email);

    List<Role> getAllRoles();
}
