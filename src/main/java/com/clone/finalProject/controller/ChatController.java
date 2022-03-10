package com.clone.finalProject.controller;


import com.clone.finalProject.domain.ChatMessage;
import com.clone.finalProject.domain.ChatRoom;
import com.clone.finalProject.dto.ChatMessageDto;

import com.clone.finalProject.repository.ChatMessageRepository;
import com.clone.finalProject.repository.ChatRoomRepository;
import com.clone.finalProject.repository.RedisChatRepository;
import com.clone.finalProject.repository.UserRepository;
import com.clone.finalProject.security.jwt.JwtDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final JwtDecoder jwtDecoder;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final RedisChatRepository redisChatRepository;
    private final RedisTemplate redisTemplate;

    //메인 페이지 채널
    @MessageMapping("/message")
    @SendTo("/topic/greetings")
    public ChatMessageDto greeting(ChatMessageDto chatMessageDto, @Header("Authorization") String token) throws Exception {

        chatMessageDto.setCreatedAt(LocalDateTime.now());

        //////////////////////////////////////////////////////////////////////////////////////////////////////////
        ChatRoom chatRoom = new ChatRoom();
        if(!chatRoomRepository.findByArea("main").isPresent()) {

            chatRoom = new ChatRoom("main");
            chatRoomRepository.save(chatRoom);
        } else {
            chatRoom = chatRoomRepository.findByArea("main").orElseThrow(
                    ()-> new NullPointerException("chatRoom이 존재하지 않습니다.")
            );
        }

        Long uid = 0L;
        /* 토큰 정보 추출 */
        if (token != null) {
            String tokenInfo = token.substring(7);
            String username = jwtDecoder.decodeUsername(tokenInfo);
            System.out.println("메인 페이지 채널 username : " + username);
            uid = userRepository.findByUsername(username).get().getUid();
        }


        System.out.println("message : " + chatMessageDto.getMessage());
        System.out.println("SenderName : " + chatMessageDto.getSenderName());
        System.out.println("status : " + chatMessageDto.getStatus());
        System.out.println("여기서 끊기는거지?");

        if(chatMessageDto.getStatus().equals("JOIN")) {
            chatMessageDto.setMessage( chatMessageDto.getSenderName()+"님이 입장하셨습니다");

        } else if (chatMessageDto.getStatus().equals("OUT")) {
            chatMessageDto.setMessage( chatMessageDto.getSenderName()+"님이 퇴장하셨습니다");

        } else {
            //채팅 메시지 저장
            ChatMessage chatMessage = new ChatMessage(uid, chatMessageDto, chatRoom);
            chatMessageRepository.save(chatMessage);
        }

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////
        String destination = "greetings";
        chatMessageDto.setUserCount(redisChatRepository.getUserCount(destination));
        System.out.println("USERCOUNT : " + chatMessageDto.getUserCount());

        Thread.sleep(500); // simulated delay
        return chatMessageDto;

    }

    //게시글 페이지 채널
    @MessageMapping("/message1")
    public void greeting2(ChatMessageDto chatMessageDto, @Header("Authorization") String token) throws Exception {

        chatMessageDto.setCreatedAt(LocalDateTime.now());

        //////////////////////////////////////////////////////////////////////////////////////////////////////////
        ChatRoom chatRoom = new ChatRoom();
        if(!chatRoomRepository.findByPid(chatMessageDto.getPid()).isPresent()) {

            chatRoom = new ChatRoom("post",chatMessageDto.getPid());
            chatRoomRepository.save(chatRoom);
        }


        Long uid = 0L;
        /* 토큰 정보 추출 */
        if (token != null) {
            String tokenInfo = token.substring(7);
            String username = jwtDecoder.decodeUsername(tokenInfo);
            System.out.println("게시글 채널 username : " + username);
            uid = userRepository.findByUsername(username).get().getUid();
        }

        System.out.println("message : " + chatMessageDto.getMessage());
        System.out.println("SenderName : " + chatMessageDto.getSenderName());
        System.out.println("status : " + chatMessageDto.getStatus());


        if(chatMessageDto.getStatus().equals("JOIN")) {
            chatMessageDto.setMessage( chatMessageDto.getSenderName()+"님이 입장하셨습니다");

        } else if (chatMessageDto.getStatus().equals("OUT")) {
            chatMessageDto.setMessage( chatMessageDto.getSenderName()+"님이 퇴장하셨습니다");

        } else {
            //채팅 메시지 저장
            ChatMessage chatMessage = new ChatMessage(uid, chatMessageDto, chatRoom);
            chatMessageRepository.save(chatMessage);
        }

        //////////////////////////////////////////////////////////////////////////////////////////////////////////

        String channel = String.valueOf(chatMessageDto.getPid());


        Thread.sleep(500); // simulated delay

        simpMessagingTemplate.convertAndSend("/topic/greetings"+channel ,chatMessageDto);
    }

    //유저 개인 귓속말 채널
    @MessageMapping("/user")
    public void greeting3(ChatMessageDto chatMessageDto,@Header("Authorization") String token) throws Exception {
        chatMessageDto.setCreatedAt(LocalDateTime.now());

        //////////////////////////////////////////////////////////////////////////////////////////////////////////
        Long senderUid = userRepository.findByUsername(chatMessageDto.getSenderName()).get().getUid();
        Long opposingUid = userRepository.findByUsername(chatMessageDto.getOpposingUserName()).get().getUid();

        ChatRoom chatRoom = new ChatRoom();
        if(!chatRoomRepository.findByUid(senderUid).isPresent()) {

            chatRoom = new ChatRoom("private",senderUid);
            chatRoomRepository.save(chatRoom);
        }

        ChatRoom chatRoom2 = new ChatRoom();
        if(!chatRoomRepository.findByUid(opposingUid).isPresent()) {

            chatRoom2 = new ChatRoom("private",opposingUid);
            chatRoomRepository.save(chatRoom2);
        }

        /* 토큰 정보 추출 */
        if (token != null) {
            String tokenInfo = token.substring(7);
            String username = jwtDecoder.decodeUsername(tokenInfo);
            System.out.println("귓속말 채널 username : " + username);
        }

        if(chatMessageDto.getStatus().equals("JOIN")) {
            chatMessageDto.setMessage( chatMessageDto.getSenderName()+"님이 입장하셨습니다");

        } else if (chatMessageDto.getStatus().equals("OUT")) {
            chatMessageDto.setMessage( chatMessageDto.getSenderName()+"님이 퇴장하셨습니다");

        } else {
            //채팅 메시지 저장
            ChatMessage chatMessage = new ChatMessage(senderUid, chatMessageDto, chatRoom);
            chatMessageRepository.save(chatMessage);

            ChatMessage chatMessage2 = new ChatMessage(opposingUid, chatMessageDto, chatRoom2);
            chatMessageRepository.save(chatMessage2);
        }



        String channel = chatMessageDto.getSenderName();
        String channel2 = chatMessageDto.getOpposingUserName();

        Thread.sleep(500); // simulated delay
        simpMessagingTemplate.convertAndSend("/queue/user"+channel ,chatMessageDto);
        simpMessagingTemplate.convertAndSend("/queue/user"+channel2 ,chatMessageDto);

    }


}
