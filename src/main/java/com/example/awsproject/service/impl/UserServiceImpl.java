package com.example.awsproject.service.impl;

import com.example.awsproject.Model.User;
import com.example.awsproject.Repository.UserRepository;
import com.example.awsproject.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public String getStreamUrl(String username) {
        User user = userRepository.findByUsername(username);
        return user.getStreamUrl();
    }


}
