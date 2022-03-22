
package com.clone.finalProject.controller;


import com.clone.finalProject.domain.ChatMessage;
import com.clone.finalProject.domain.ChatRoom;
import com.clone.finalProject.dto.ChatMessageDto;

import com.clone.finalProject.repository.ChatMessageRepository;
import com.clone.finalProject.repository.ChatRoomRepository;
import com.clone.finalProject.repository.RedisChatRepository;
import com.clone.finalProject.repository.UserRepository;
import com.clone.finalProject.security.jwt.JwtDecoder;
import com.clone.finalProject.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.transaction.Transactional;
import java.time.LocalDateTime;



@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final JwtDecoder jwtDecoder;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final RedisChatRepository redisChatRepository;
    private final ChatService chatService;


    //////////////////////////////////메인 페이지 채널///////////////////////////////////////////////////////////////
    @MessageMapping("/message")
    @SendTo("/topic/greetings")
    public ChatMessageDto greeting(ChatMessageDto chatMessageDto, @Header("Authorization") String token) throws Exception {
        log.info("채팅테스트:{}",chatMessageDto.getMessage());

        log.info("채팅 헤더 확인:{}",token);

        Thread.sleep(500); // simulated delay

        ChatRoom chatRoom = new ChatRoom();

        // 채팅방있는지 확인 후 없으면 생성, 있으면 채팅방 변수에 할당해놓음 -> 채팅 저장 시에 사용할 예정
        if(!(chatRoomRepository.findByArea("main").isPresent())) {
            chatRoom = new ChatRoom("main");
            chatRoomRepository.save(chatRoom);
        } else {
            chatRoom = chatRoomRepository.findByArea("main").orElseThrow(
                    ()-> new NullPointerException("chatRoom이 존재하지 않습니다.")
            );
        }
        //채팅 메시지 셋업 메소드
        chatService.chatSettingMethod(chatMessageDto, token, chatRoom);

        String destination = "greetings";
        log.info("=== channel : {}",destination);
//        chatMessageDto.setUserCount(redisChatRepository.getUserCount(destination));
        return chatMessageDto;
    }


    //////////////////////////////////게시글 페이지 채널///////////////////////////////////////////////////////////////
    @Transactional
    @MessageMapping("/message1")
    public void greeting2(ChatMessageDto chatMessageDto, @Header("Authorization") String token) throws Exception {

        Thread.sleep(500); // simulated delay

        ChatRoom chatRoom = new ChatRoom();
        // 채팅방있는지 확인 후 없으면 생성, 있으면 채팅방 변수에 할당해놓음 -> 채팅 저장 시에 사용할 예정
        if(!(chatRoomRepository.findByPid(chatMessageDto.getPid()).isPresent())) {
            chatRoom = new ChatRoom("post",chatMessageDto.getPid());
            chatRoomRepository.save(chatRoom);
        } else {
            chatRoom = chatRoomRepository.findByPid(chatMessageDto.getPid()).orElseThrow(
                    ()-> new NullPointerException("chatRoom이 존재하지 않습니다.")
            );
        }
        //채팅 메시지 셋업 메소드
        chatService.chatSettingMethod(chatMessageDto, token, chatRoom);

        String destination = String.valueOf(chatMessageDto.getPid());
        log.info("=== channel : {}",destination);
//        chatMessageDto.setUserCount(redisChatRepository.getUserCount(destination));

        simpMessagingTemplate.convertAndSend("/topic/greetings"+"/"+destination ,chatMessageDto);
    }

    //////////////////////////////////유저 개인 귓속말 채널///////////////////////////////////////////////////////////////
    @MessageMapping("/user")
    public void greeting3(ChatMessageDto chatMessageDto,@Header("Authorization") String token) throws Exception {
        chatMessageDto.setCreatedAt(LocalDateTime.now());

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
            log.info("귓속말 채널 username : {}", username);
        }

        if(chatMessageDto.getStatus().equals("JOIN")) {
            chatMessageDto.setMessage( chatMessageDto.getSenderName()+"님이 입장하셨습니다");

        } else if (chatMessageDto.getStatus().equals("OUT")) {
            chatMessageDto.setMessage( chatMessageDto.getSenderName()+"님이 퇴장하셨습니다");

        } else {

            // 비속어 필터링 메소드
            chatService.chatFilter(chatMessageDto);

            //채팅 메시지 저장
            ChatMessage chatMessage = new ChatMessage(senderUid, chatMessageDto, chatRoom);
            chatMessageRepository.save(chatMessage);

            ChatMessage chatMessage2 = new ChatMessage(opposingUid, chatMessageDto, chatRoom2);
            chatMessageRepository.save(chatMessage2);
        }

        String channel = chatMessageDto.getSenderName();
        String channel2 = chatMessageDto.getOpposingUserName();

        Thread.sleep(500); // simulated delay
        simpMessagingTemplate.convertAndSend("/queue/user" +"/"+channel ,chatMessageDto);
        simpMessagingTemplate.convertAndSend("/queue/user" +"/"+channel2 ,chatMessageDto);

    }

}
