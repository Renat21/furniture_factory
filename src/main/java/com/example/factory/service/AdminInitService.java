package com.example.factory.service;


import com.example.factory.Enum.Role;
import com.example.factory.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class AdminInitService implements CommandLineRunner {

    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(String... args) throws Exception {

        User admin = new User("admin", bCryptPasswordEncoder.encode("admin"));
        admin.setRoles(Collections.singleton(Role.ROLE_ADMIN));
        if (userService.findUserByUsername("admin") == null)
            userService.saveUser(admin);

    }
}
