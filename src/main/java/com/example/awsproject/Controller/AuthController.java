package com.example.awsproject.Controller;

import com.example.awsproject.Mapper.UserMapper;
import com.example.awsproject.Model.User;
import com.example.awsproject.dto.LoginDTO;
import com.example.awsproject.dto.LoginResponse;
import com.example.awsproject.dto.UserDTO;
import com.example.awsproject.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        log.error("vvv");
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>("Failed to register user: " + bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        try {
            User user = userMapper.toEntity(userDTO);
            authService.register(user);
            return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to register user: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDTO loginDTO){
        return new ResponseEntity<>(authService.login(loginDTO), HttpStatus.CREATED);
    }
}
