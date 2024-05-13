package com.example.awsproject.service;

import com.example.awsproject.Model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    String getStreamUrl(String username);
}
