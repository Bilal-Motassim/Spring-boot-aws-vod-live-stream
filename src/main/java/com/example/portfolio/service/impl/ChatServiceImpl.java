package com.example.portfolio.service.impl;

import com.amazonaws.services.ivschat.model.CreateRoomResult;
import com.example.portfolio.service.ChatService;
import com.example.portfolio.utils.AWSCloudUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ChatServiceImpl implements ChatService {
    @Value("${aws.access.key.ivs}")
    private String AWS_ACCESS_KEY_IVS;

    @Value("${aws.secret.key.ivs}")
    private String AWS_SECRET_KEY_IVS;
    @Override
    public void createChatRoom() {
        CreateRoomResult result = AWSCloudUtil.createRoom(AWS_ACCESS_KEY_IVS, AWS_SECRET_KEY_IVS, "Hehehe");
        log.error(result.toString());
    }
}
