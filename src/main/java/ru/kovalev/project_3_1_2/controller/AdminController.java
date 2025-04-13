package ru.kovalev.project_3_1_2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kovalev.project_3_1_2.dao.RoleDao;
import ru.kovalev.project_3_1_2.model.Role;
import ru.kovalev.project_3_1_2.model.User;
import ru.kovalev.project_3_1_2.service.UserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleDao roleDao;

    @Autowired
    public AdminController(UserService userService, RoleDao roleDao) {
        this.userService = userService;
        this.roleDao = roleDao;
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }


    @GetMapping("/new")
    public String newUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleDao.getAllRoles());
        return "user-form";
    }


    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user,
                           @RequestParam("rolesNames") List<String> rolesNames) {

        Set<Role> resolvedRoles = rolesNames.stream()
                .map(roleDao::findByName)
                .collect(Collectors.toSet());

        user.setRoles(resolvedRoles);
        userService.saveUser(user);
        return "redirect:/admin";
    }


    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.showUser(id);
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleDao.getAllRoles());
        return "user-form";
    }


    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam("roleNames") List<String> roleNames,
                             @RequestParam("id") Long id) {

        user.setId(id);

        Set<Role> resolvedRoles = roleNames.stream()
                .map(roleDao::findByName)
                .collect(Collectors.toSet());

        user.setRoles(resolvedRoles);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}