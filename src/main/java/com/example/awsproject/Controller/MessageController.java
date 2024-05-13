package com.example.awsproject.Controller;

import com.example.awsproject.Model.Message;
import com.example.awsproject.Model.OutputMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@Slf4j
public class MessageController {
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public OutputMessage send(Message message) throws Exception {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        log.info(message.toString());
        return new OutputMessage(message.getFrom(), message.getText(), time);
    }
}
