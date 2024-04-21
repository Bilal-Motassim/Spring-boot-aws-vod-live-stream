package com.example.portfolio.service;

import com.example.portfolio.Model.User;
import com.example.portfolio.dto.LoginDTO;
import com.example.portfolio.dto.LoginResponse;

public interface AuthService {
    void register(User user) throws Exception;
    LoginResponse login(LoginDTO loginDTO);
}
