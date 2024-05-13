package com.example.awsproject.Controller;

import com.example.awsproject.Mapper.UserMapper;
import com.example.awsproject.dto.UserDTO;
import com.example.awsproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOs = userMapper.toDTOList(userService.getAllUsers());
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    @GetMapping("/stream/{username}")
    public ResponseEntity<String> getStreamUrl(@PathVariable String username){
        String url = userService.getStreamUrl(username);
        return ResponseEntity.status(200).body(url);
    }
}
