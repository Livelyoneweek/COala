
package com.clone.finalProject.controller;


import com.clone.finalProject.domain.ChatMessage;
import com.clone.finalProject.domain.ChatRoom;
import com.clone.finalProject.dto.ChatMessageDto;

import com.clone.finalProject.repository.ChatMessageRepository;
import com.clone.finalProject.repository.ChatRoomRepository;
import com.clone.finalProject.repository.RedisChatRepository;
import com.clone.finalProject.repository.UserRepository;
import com.clone.finalProject.security.jwt.JwtDecoder;
import com.clone.finalProject.service.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.StringTokenizer;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final JwtDecoder jwtDecoder;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final RedisChatRepository redisChatRepository;
    private final CacheService cacheService;

    //메인 페이지 채널
    @MessageMapping("/message")
    @SendTo("/topic/greetings")
    public ChatMessageDto greeting(ChatMessageDto chatMessageDto) throws Exception {
        chatMessageDto.setCreatedAt(LocalDateTime.now());
        Thread.sleep(500); // simulated delay
        //////////////////////////////////////////////////////////////////////////////////////////////////////////
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

        Long uid = 0L;

        /* 토큰 정보 추출 */
//        if (token != null) {
//            String tokenInfo = token.substring(7);
//            String username = jwtDecoder.decodeUsername(tokenInfo);
//            System.out.println("메인 페이지 채널 username : " + username);
//            uid = userRepository.findByUsername(username).get().getUid();
//        }

        if(chatMessageDto.getStatus().equals("JOIN")) {
            chatMessageDto.setMessage( chatMessageDto.getSenderName()+"님이 입장하셨습니다");
            chatMessageDto.setUserCount(redisChatRepository.getUserCount("greetings"));
            System.out.println("=== 연결이다 : " + chatMessageDto.getMessage());

        } else if (chatMessageDto.getStatus().equals("OUT")) {
            chatMessageDto.setMessage( chatMessageDto.getSenderName()+"님이 퇴장하셨습니다");
            chatMessageDto.setUserCount(redisChatRepository.getUserCount("greetings"));
            System.out.println("===== 끊겼다 : " + chatMessageDto.getMessage());

        } else {
            uid = chatMessageDto.getUid();
            String career = userRepository.findByUid(uid).get().getCareer();
            chatMessageDto.setCareer(career);

            System.out.println("비속어 필터링 전 채팅 : " + chatMessageDto.getMessage());
            // 비속어 필터링 메소드
            chatFilter(chatMessageDto);
            System.out.println("비속어 필터링 후 채팅 : " + chatMessageDto.getMessage());

            //채팅 메시지 저장
            ChatMessage chatMessage = new ChatMessage(uid, chatMessageDto, chatRoom);
            chatMessageRepository.save(chatMessage);
        }
//        chatService.createChatRoom(chatMessageDto);
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////
        String destination = "greetings";
        chatMessageDto.setUserCount(redisChatRepository.getUserCount(destination));
        System.out.println(" ====== USERCOUNT : " + chatMessageDto.getUserCount());

        return chatMessageDto;

    }

    //  @Header("Authorization") String token
    //게시글 페이지 채널
    @Transactional
    @MessageMapping("/message1")
    public void greeting2(ChatMessageDto chatMessageDto) throws Exception {
        System.out.println("getPid :" + chatMessageDto.getPid());
        System.out.println("message : " + chatMessageDto.getMessage());
        System.out.println("SenderName : " + chatMessageDto.getSenderName());
        System.out.println("status : " + chatMessageDto.getStatus());

        Thread.sleep(500); // simulated delay

        chatMessageDto.setCreatedAt(LocalDateTime.now());

        //////////////////////////////////////////////////////////////////////////////////////////////////////////
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

        Long uid = 0L;
        /* 토큰 정보 추출 */
//        if (token != null) {
//            String tokenInfo = token.substring(7);
//            String username = jwtDecoder.decodeUsername(tokenInfo);
//            System.out.println("게시글 채널 username : " + username);
//            uid = userRepository.findByUsername(username).get().getUid();
//        }

        if(chatMessageDto.getStatus().equals("JOIN")) {
            chatMessageDto.setMessage(chatMessageDto.getSenderName()+"님이 입장하셨습니다");

        } else if (chatMessageDto.getStatus().equals("OUT")) {
            chatMessageDto.setMessage(chatMessageDto.getSenderName()+"님이 퇴장하셨습니다");

        } else {

            uid = chatMessageDto.getUid();
            String career = userRepository.findByUid(uid).get().getCareer();
            chatMessageDto.setCareer(career);

            // 비속어 필터링 메소드
            chatFilter(chatMessageDto);

            //채팅 메시지 저장
            ChatMessage chatMessage = new ChatMessage(uid, chatMessageDto, chatRoom);
            chatMessageRepository.save(chatMessage);
        }

        //////////////////////////////////////////////////////////////////////////////////////////////////////////

        String channel = String.valueOf(chatMessageDto.getPid());
        System.out.println(" === channel : " + channel + " === ");

        String destination = String.valueOf(chatMessageDto.getPid());
        chatMessageDto.setUserCount(redisChatRepository.getUserCount(destination));

        System.out.println(" ====== USERCOUNT : " + chatMessageDto.getUserCount());

        simpMessagingTemplate.convertAndSend("/topic/greetings"+"/"+channel ,chatMessageDto);
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

            // 비속어 필터링 메소드
            chatFilter(chatMessageDto);


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


    // 비속어 필터링 메소드
    private void chatFilter(ChatMessageDto chatMessageDto) {

        //비속어 해쉬맵 가져옴
        HashMap<Integer,String> fowrds = cacheService.getCacheData("key");

        //유저 메시지 공백 제거
        String message = chatMessageDto.getMessage().trim();

        // 새로운 메시지 생성
        StringBuilder newMessage = new StringBuilder();

        StringTokenizer st = new StringTokenizer(message," ");

        //반복문 돌면서 욕 필터 후 *로 치환하여 새로운 메시지 작성
        while(st.hasMoreTokens()){
            StringBuilder sb = new StringBuilder(st.nextToken());
            StringBuilder star = new StringBuilder();
            if(fowrds.containsValue(sb)){
                for(int i=0; i<sb.length(); i++) {
                    star.append("*");
                }

                sb.replace(0,sb.length(), String.valueOf(star));

            }
            newMessage.append(sb).append(" ");
        }

        chatMessageDto.setMessage(String.valueOf(newMessage));
    }

}
