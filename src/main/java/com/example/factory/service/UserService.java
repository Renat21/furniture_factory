package com.example.factory.service;


import com.example.factory.Enum.Role;
import com.example.factory.entity.User;
import com.example.factory.repository.OrderRepository;
import com.example.factory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    ProductService productService;

    public void saveUser(User user){
        userRepository.save(user);
    }

    public User saveUserAndReturn(User user){
        user.setOrders(new ArrayList<>());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(Role.ROLE_USER));
        saveUser(user);
        return userRepository.findUserByUsername(user.getUsername());
    }

    public String registerUser(User user, String confirmPassword, RedirectAttributes redirectAttributes){
        if (!confirmPassword.equals(user.getPassword())){
            redirectAttributes.addFlashAttribute("confirmPasswordError", true);
            return "redirect:/registration";
        }
        user.setOrders(new ArrayList<>());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(Role.ROLE_USER));
        saveUser(user);
        return "redirect:/login";
    }

    public User updateUser(User user, Long userId){
        User oldUser = userRepository.findUserById(userId);
        if (user.getPassword() != null){
            oldUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        oldUser.setName(user.getName());
        oldUser.setSurname(user.getSurname());
        oldUser.setTelephone(user.getTelephone());
        oldUser.setUsername(user.getUsername());
        userRepository.save(oldUser);
        return oldUser;
    }

    @Transactional
    public void deleteUser(Long userId){
        userRepository.deleteUserById(userId);
    }

    @Transactional
    public User findUserByUsername(String username){
        return userRepository.findUserByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

}
