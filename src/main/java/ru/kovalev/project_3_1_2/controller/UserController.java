package ru.kovalev.project_3_1_2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kovalev.project_3_1_2.model.User;
import ru.kovalev.project_3_1_2.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showUserInfo(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("activeTab", "user");
        return "user";
    }
}

