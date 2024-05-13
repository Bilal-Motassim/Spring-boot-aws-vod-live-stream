package com.example.portfolio.service;

import com.example.portfolio.Model.User;
import com.example.portfolio.dto.LoginDTO;
import com.example.portfolio.dto.LoginResponse;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    String getStreamUrl(String username);
}
