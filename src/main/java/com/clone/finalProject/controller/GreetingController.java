package com.clone.finalProject.controller;


import com.clone.finalProject.domain.Greeting;
import com.clone.finalProject.domain.HelloMessage;
import com.clone.finalProject.dto.MessageDto;
import com.clone.finalProject.dto.ChatMessage;
import com.clone.finalProject.security.jwt.JwtDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Controller
public class GreetingController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message")
    @SendTo("/topic/greetings")
    public ChatMessage greeting(ChatMessage chatMessage) throws Exception {
        System.out.println("message : " + chatMessage.getMessage());
        System.out.println("SenderName : " + chatMessage.getSenderName());

//        /* 토큰 정보 추출 */
//        String tokenInfo = token.substring(7);
//        System.out.println("tokenInfo : " +tokenInfo);
//        String username = JwtDecoder.decodeUsername(tokenInfo);



        chatMessage.setCreatedAt(LocalDateTime.now());
        Thread.sleep(500); // simulated delay
        return chatMessage;

    }


    @MessageMapping("/hello2")
    @SendTo("/queue/greetings2")
    public Greeting greeting2(HelloMessage message) throws Exception {
        Thread.sleep(500); // simulated delay
        return new Greeting("Message, " + message.getName());
    }

    @MessageMapping("/hello3")
    public void greeting3(HelloMessage message) throws Exception {
        LocalDateTime now = LocalDateTime.now();
        message.setCreatedAt(now);

        MessageDto messageDto = new MessageDto();
        messageDto.setContent(message.getName());
        messageDto.setCreatedAt(now);

        String test3 = "3";
        Thread.sleep(500); // simulated delay
        simpMessagingTemplate.convertAndSend("/queue/greetings"+test3  ,messageDto);
    }


}
