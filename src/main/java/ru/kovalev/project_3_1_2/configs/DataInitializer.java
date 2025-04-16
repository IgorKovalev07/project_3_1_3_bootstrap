package ru.kovalev.project_3_1_2.configs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kovalev.project_3_1_2.dao.RoleDao;
import ru.kovalev.project_3_1_2.model.Role;
import ru.kovalev.project_3_1_2.model.User;
import ru.kovalev.project_3_1_2.service.UserService;

import java.util.List;


@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final RoleDao roleDao;

    public DataInitializer(UserService userService, RoleDao roleDao) {
        this.userService = userService;
        this.roleDao = roleDao;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleDao.findByName("ROLE_ADMIN") == null) {
            Role adminRole = new Role("ROLE_ADMIN");
            roleDao.save(adminRole);
        }
        if (roleDao.findByName("ROLE_USER") == null) {
            Role userRole = new Role("ROLE_USER");
            roleDao.save(userRole);
        }

        User admin = new User();
        admin.setFirstName("Admin");
        admin.setLastName("User");
        admin.setEmail("admin@example.com");
        admin.setPassword("admin");
        userService.saveUserWithRoles(admin, List.of("ROLE_ADMIN"));

        User user = new User();
        user.setFirstName("User");
        user.setLastName("Simple");
        user.setEmail("user@example.com");
        user.setPassword("user");
        userService.saveUserWithRoles(user, List.of("ROLE_USER"));
    }
}