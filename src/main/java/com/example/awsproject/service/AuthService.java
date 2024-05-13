package com.example.awsproject.service;

import com.example.awsproject.Model.User;
import com.example.awsproject.dto.LoginDTO;
import com.example.awsproject.dto.LoginResponse;

public interface AuthService {
    void register(User user) throws Exception;
    LoginResponse login(LoginDTO loginDTO);
}
