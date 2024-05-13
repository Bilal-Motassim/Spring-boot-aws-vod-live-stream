package com.example.awsproject.service.impl;

import com.amazonaws.services.ivs.model.GetStreamResult;
import com.example.awsproject.Model.User;
import com.example.awsproject.Repository.UserRepository;
import com.example.awsproject.service.StreamService;
import com.example.awsproject.utils.AWSCloudUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StreamServiceImpl implements StreamService {

    @Value("${aws.access.key.ivs}")
    private String AWS_ACCESS_KEY_IVS;

    @Value("${aws.secret.key.ivs}")
    private String AWS_SECRET_KEY_IVS;

    @Autowired
    private UserRepository userRepository;
    @Override
    public boolean checkLive(String username) {
        User user = userRepository.findByUsername(username);
        GetStreamResult result = AWSCloudUtil.getStream(AWS_ACCESS_KEY_IVS, AWS_SECRET_KEY_IVS, user.getStreamArn());
        log.error("User ARN: "+user.getStreamArn());
        log.error(result.toString());
        return false;
    }
}
