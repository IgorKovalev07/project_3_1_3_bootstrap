package ru.kovalev.project_3_1_2.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kovalev.project_3_1_2.dao.RoleDao;
import ru.kovalev.project_3_1_2.dao.UserDao;
import ru.kovalev.project_3_1_2.model.Role;
import ru.kovalev.project_3_1_2.model.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder, RoleDao roleDao) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.roleDao = roleDao;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.saveUser(user);
    }

    @Override
    public void saveUserWithRoles(User user, List<String> roleNames) {
        user.setRoles(resolveRoles(roleNames));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.saveUser(user);
    }

    @Override
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

        userDao.updateUser(user);
    }

    @Override
    public void updateUserWithRoles(User user, List<String> roleNames) {
        user.setRoles(resolveRoles(roleNames));

        User existingUser = userDao.showUser(user.getId());

        if (!user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(existingUser.getPassword());
        }

        userDao.updateUser(user);
    }

    @Override
    public User showUser(Long id) {
        return userDao.showUser(id);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь с email " + email + " не найден");
        }
        user.getRoles().size(); // подгружаем роли
        return user;
    }

    private Set<Role> resolveRoles(List<String> roleNames) {
        return roleNames.stream()
                .map(roleDao::findByName)
                .collect(Collectors.toSet());
    }
}
