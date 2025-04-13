package ru.kovalev.project_3_1_2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Project312Application {

    public static void main(String[] args) {
        SpringApplication.run(Project312Application.class, args);
    }
}


