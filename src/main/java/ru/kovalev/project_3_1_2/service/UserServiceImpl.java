package ru.kovalev.project_3_1_2.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kovalev.project_3_1_2.dao.RoleDao;
import ru.kovalev.project_3_1_2.dao.UserDao;
import ru.kovalev.project_3_1_2.model.Role;
import ru.kovalev.project_3_1_2.model.User;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder, RoleDao roleDao) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.roleDao = roleDao;
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public void saveUser(User user) {
        Set<Role> resolvedRoles = user.getRoles().stream()
                .map(role -> roleDao.findByName(role.getRoleName()))
                .collect(Collectors.toSet());

        user.setRoles(resolvedRoles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.saveUser(user);
    }


    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    @Override
    public void updateUser(User user) {
        User existingUser = userDao.showUser(user.getId());

        if (!user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(existingUser.getPassword());
        }

        Set<Role> resolvedRoles = user.getRoles().stream()
                .map(role -> roleDao.findByName(role.getRoleName()))
                .collect(Collectors.toSet());
        user.setRoles(resolvedRoles);

        userDao.updateUser(user);
    }


    public User showUser(Long id) {
        return userDao.showUser(id);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}