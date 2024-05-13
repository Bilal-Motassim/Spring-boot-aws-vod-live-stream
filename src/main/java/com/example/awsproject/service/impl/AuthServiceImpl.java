package com.example.awsproject.service.impl;

import com.amazonaws.services.ivs.model.CreateChannelResult;
import com.example.awsproject.Model.User;
import com.example.awsproject.Repository.UserRepository;
import com.example.awsproject.dto.LoginDTO;
import com.example.awsproject.dto.LoginResponse;
import com.example.awsproject.service.AuthService;
import com.example.awsproject.utils.AWSCloudUtil;
import com.example.awsproject.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Value("${aws.access.key.ivs}")
    private String AWS_ACCESS_KEY_IVS;

    @Value("${aws.secret.key.ivs}")
    private String AWS_SECRET_KEY_IVS;

    @Override
    public void register(User user) throws Exception {

        log.info("========================== REGISTERING USER ===========================");

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new Exception("Email already exists");
        }

        CreateChannelResult result = AWSCloudUtil.createIVSChannel(AWS_ACCESS_KEY_IVS, AWS_SECRET_KEY_IVS, user.getUsername());
        //CreateRoomResult r2 = AWSCloudUtil.createRoom(AWS_ACCESS_KEY_IVS, AWS_ACCESS_KEY_IVS, user.getUsername());
        if(result != null || result != null){
            log.error("Channel Created Name:"+result.getChannel().getName() + " StreamKey: "+result.getStreamKey().getValue());
            user.setStreamKey(result.getStreamKey().getValue());
            user.setStreamUrl(result.getChannel().getPlaybackUrl());
            user.setStreamArn(result.getChannel().getArn());
            //user.setChatArn(r2.getArn());

            //CreateChatTokenResult res = AWSCloudUtil.createChatToken(AWS_ACCESS_KEY_IVS, AWS_SECRET_KEY_IVS, r2.getArn());

            //user.setChatToken(res.getToken());

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(user.getPassword());

            user.setPassword(encodedPassword);

            userRepository.save(user);
        }
    }

    @Override
    public LoginResponse login(LoginDTO loginDTO) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getEmail(),
                loginDTO.getPassword()
        ));

        if(authentication.isAuthenticated()){
            LoginResponse response = new LoginResponse();
            response.setAuthToken(jwtUtils.GenerateToken(loginDTO.getEmail()));
            return response;
        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }
}
