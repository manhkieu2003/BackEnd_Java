package com.example.backend_java.service;

import com.example.backend_java.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceDetail {
    @Autowired
    private UserRepository userRepository;
    public UserDetailsService UserServiceDetail() {
        return userRepository::findByUsername;
    }
}
