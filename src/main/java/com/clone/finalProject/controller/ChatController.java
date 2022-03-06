package com.clone.finalProject.controller;//package com.clone.finalProject.controller;
//
//
//import com.clone.finalProject.domain.ChatRoom;
//import com.clone.finalProject.dto.ChatMessageDto;
//
//import com.clone.finalProject.repository.ChatRoomRepository;
//import com.clone.finalProject.security.jwt.JwtDecoder;
//import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//
//import java.time.LocalDateTime;
//
//@RequiredArgsConstructor
//@Controller
//public class ChatController {
//
//    private final SimpMessagingTemplate simpMessagingTemplate;
//    private final JwtDecoder jwtDecoder;
//    private final ChatRoomRepository chatRoomRepository;
//
//    //메인 페이지 채널
//    @MessageMapping("/message")
//    @SendTo("/topic/greetings")
//    public ChatMessageDto greeting(ChatMessageDto chatMessageDto, @Header("Authorization") String token) throws Exception {
//
//        if(!chatRoomRepository.findByArea("main").isPresent()) {
//
//            ChatRoom chatRoom = new ChatRoom("main");
//            chatRoomRepository.save(chatRoom);
//        }
//
//
//
//
//        chatMessageDto.setCreatedAt(LocalDateTime.now());
//
//        System.out.println("message : " + chatMessageDto.getMessage());
//        System.out.println("SenderName : " + chatMessageDto.getSenderName());
//        System.out.println("status : " + chatMessageDto.getStatus());
//
//        if(chatMessageDto.getStatus().equals("JOIN")) {
//            chatMessageDto.setMessage( chatMessageDto.getSenderName()+"님이 입장하셨습니다");
//        }
//
//        if(chatMessageDto.getStatus().equals("OUT")) {
//            chatMessageDto.setMessage( chatMessageDto.getSenderName()+"님이 퇴장하셨습니다");
//        }
//
//        /* 토큰 정보 추출 */
//        if (token != null) {
//            String tokenInfo = token.substring(7);
//            String username = jwtDecoder.decodeUsername(tokenInfo);
//            System.out.println("메인 페이지 채널 username : " + username);
//        }
//
//
//        Thread.sleep(500); // simulated delay
//        return chatMessageDto;
//
//    }
//
//    //게시글 페이지 채널
//    @MessageMapping("/message1")
//
//    public void greeting2(ChatMessageDto chatMessageDto, @Header("Authorization") String token) throws Exception {
//
//        chatMessageDto.setCreatedAt(LocalDateTime.now());
//
//
//        System.out.println("message : " + chatMessageDto.getMessage());
//        System.out.println("SenderName : " + chatMessageDto.getSenderName());
//        System.out.println("status : " + chatMessageDto.getStatus());
//
//        if(chatMessageDto.getStatus().equals("JOIN")) {
//            chatMessageDto.setMessage( chatMessageDto.getSenderName()+"님이 입장하셨습니다");
//        }
//
//        if(chatMessageDto.getStatus().equals("OUT")) {
//            chatMessageDto.setMessage( chatMessageDto.getSenderName()+"님이 퇴장하셨습니다");
//        }
//
//
//        /* 토큰 정보 추출 */
//        if (token != null) {
//            String tokenInfo = token.substring(7);
//            String username = jwtDecoder.decodeUsername(tokenInfo);
//            System.out.println("게시글 채널 username : " + username);
//        }
//
//        String channel = String.valueOf(chatMessageDto.getPid());
////        String channel = "1";
//        Thread.sleep(500); // simulated delay
//
//        simpMessagingTemplate.convertAndSend("/topic/greetings"+channel ,chatMessageDto);
//    }
//
//    //유저 개인 귓속말 채널
//    @MessageMapping("/user")
//    public void greeting3(ChatMessageDto chatMessageDto,@Header("Authorization") String token) throws Exception {
//        chatMessageDto.setCreatedAt(LocalDateTime.now());
//
//        /* 토큰 정보 추출 */
//        if (token != null) {
//            String tokenInfo = token.substring(7);
//            String username = jwtDecoder.decodeUsername(tokenInfo);
//            System.out.println("귓속말 채널 username : " + username);
//        }
//
//        String channel = chatMessageDto.getSenderName();
//        String channel2 = chatMessageDto.getOpposingUserName();
//
//        Thread.sleep(500); // simulated delay
//        simpMessagingTemplate.convertAndSend("/queue/user"+channel ,chatMessageDto);
//        simpMessagingTemplate.convertAndSend("/queue/user"+channel2 ,chatMessageDto);
//
//    }
//
//
//}
