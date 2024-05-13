package com.example.awsproject.Controller;

import com.example.awsproject.service.ChatService;
import com.example.awsproject.service.StreamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/stream")
public class StreamController {

    @Autowired
    private StreamService streamService;

    @Autowired
    private ChatService chatService;

    @GetMapping("/getstream/{username}")
    public ResponseEntity<String> getStream(@PathVariable("username") String username){
        boolean response = streamService.checkLive(username);
        log.error(String.valueOf(response));
        return ResponseEntity.status(200).body("hi");
    }

    @GetMapping("/chat")
    public ResponseEntity<String> makeChat(){
        chatService.createChatRoom();
        return ResponseEntity.status(200).body("dasd");
    }
}
