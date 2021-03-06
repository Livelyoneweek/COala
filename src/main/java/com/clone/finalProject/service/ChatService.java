package com.clone.finalProject.service;

import com.clone.finalProject.domain.ChatMessage;
import com.clone.finalProject.domain.ChatRoom;
import com.clone.finalProject.dto.chatMessageDto.ChatMessageDto;
import com.clone.finalProject.dto.chatMessageDto.ChatMessagedResponseDto;
import com.clone.finalProject.repository.ChatMessageRepository;
import com.clone.finalProject.repository.FwordRepository;
import com.clone.finalProject.repository.UserRepository;
import com.clone.finalProject.security.jwt.JwtDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final JwtDecoder jwtDecoder;
    private final UserRepository userRepository;
    private final CacheService cacheService;
    private final FwordRepository fwordRepository;

    public String getRoomId(String destination) {
        int lastIndex = destination.lastIndexOf('/');
        if (lastIndex != -1)
            return destination.substring(lastIndex + 1);
        else
            return "";
    }

    // 메인페이지 채널 채팅 내역 조회
    public List<ChatMessagedResponseDto> getMainMessage() {

        List<ChatMessage> chatMessageList = chatMessageRepository.findTOP20ByChatRoom_AreaOrderByCreatedAtDesc("main");

        List<ChatMessagedResponseDto> chatMessagedResponseDtoList = new ArrayList<>();
        for(ChatMessage chatMessage : chatMessageList) {
            ChatMessagedResponseDto chatMessagedResponseDto = new ChatMessagedResponseDto(chatMessage);

            chatMessagedResponseDtoList.add(chatMessagedResponseDto);
        }
        Collections.reverse(chatMessagedResponseDtoList);
        return chatMessagedResponseDtoList;
    }

    // 게시글 페이지 채널 채팅 내역 조회
    public List<ChatMessagedResponseDto> getPostMessage(Long pid) {

        List<ChatMessage> chatMessageList = chatMessageRepository.findTOP20ByChatRoom_PidOrderByCreatedAtDesc(pid);

        List<ChatMessagedResponseDto> chatMessagedResponseDtoList = new ArrayList<>();
        for(ChatMessage chatMessage : chatMessageList) {
            ChatMessagedResponseDto chatMessagedResponseDto = new ChatMessagedResponseDto(chatMessage);

            chatMessagedResponseDtoList.add(chatMessagedResponseDto);
        }
        Collections.reverse(chatMessagedResponseDtoList);
        return chatMessagedResponseDtoList;
    }

    // 귓속말 채널 채팅 내역 조회
    public List<ChatMessagedResponseDto> getWhisperMessage(Long uid) {

        List<ChatMessage> chatMessageList = chatMessageRepository.findTOP20ByChatRoom_UidOrderByCreatedAtDesc(uid);

        List<ChatMessagedResponseDto> chatMessagedResponseDtoList = new ArrayList<>();
        for(ChatMessage chatMessage : chatMessageList) {
            ChatMessagedResponseDto chatMessagedResponseDto = new ChatMessagedResponseDto(chatMessage);

            chatMessagedResponseDtoList.add(chatMessagedResponseDto);
        }
        Collections.reverse(chatMessagedResponseDtoList);
        return chatMessagedResponseDtoList;

    }
    ///////////////////////////채팅 메시지 셋업 메소드////////////////////////////////////////////////////////////////////
    public void chatSettingMethod(ChatMessageDto chatMessageDto, String token, ChatRoom chatRoom) {
        chatMessageDto.setCreatedAt(LocalDateTime.now());
        Long uid = 0L;
        String username = "";

        /* 토큰 정보 추출 */
        if (!(String.valueOf(token).equals("Authorization")||String.valueOf(token).equals("null"))) {
            log.info("token : {}",String.valueOf(token));
            String tokenInfo = token.substring(7);
            username = jwtDecoder.decodeUsername(tokenInfo);
//            uid = userRepository.findByUsername(username).get().getUid();
        }

        if(chatMessageDto.getStatus().equals("JOIN")) {
            if(username!=""&&username!="null"){
                log.info("JOIN일때 {}",chatMessageDto.getSenderName());
                chatMessageDto.setMessage(chatMessageDto.getSenderName()+"님이 입장하셨습니다");
                log.info("=== 연결 : {}",chatMessageDto.getPid());
            }

        } else if (chatMessageDto.getStatus().equals("OUT")) {
            if(username!=""&&username!="null"){
                log.info("OUT일때 {}",chatMessageDto.getSenderName());
                chatMessageDto.setMessage( chatMessageDto.getSenderName()+"님이 퇴장하셨습니다");
                log.info("=== 연결끊김 : {}",chatMessageDto.getPid());
            }

        } else {
            uid = chatMessageDto.getUid();
            String career = userRepository.findByUid(uid).get().getCareer();
            chatMessageDto.setCareer(career);

            log.info("비속어 필터링 전 채팅 : {}",chatMessageDto.getMessage());
            // 비속어 필터링 메소드
            chatFilter(chatMessageDto);
            log.info("비속어 필터링 후 채팅 : {}",chatMessageDto.getMessage());

            //채팅 메시지 저장
            ChatMessage chatMessage = new ChatMessage(uid, chatMessageDto, chatRoom);
            chatMessageRepository.save(chatMessage);


        }

    }
    ///////////////////////////////////////////// 비속어 필터링 메소드////////////////////////////////////////////////////

    public void chatFilter(ChatMessageDto chatMessageDto) {
//        cacheService.setFwords();
        String message = chatMessageDto.getMessage().trim();

        StringBuilder newMessage = new StringBuilder();

        StringTokenizer st = new StringTokenizer(message, " ");
        while (st.hasMoreTokens()) {

            StringBuilder sb = new StringBuilder(st.nextToken());
            StringBuilder star = new StringBuilder();

            String fowrds = cacheService.getFwords(String.valueOf(sb));
            if (fowrds != null) {
                for (int i = 0; i < sb.length(); i++) {
                    star.append("*");
                }
                sb.replace(0,sb.length(), String.valueOf(star));
            }

                newMessage.append(sb).append(" ");


        }
        newMessage.deleteCharAt(newMessage.lastIndexOf(" "));

        chatMessageDto.setMessage(String.valueOf(newMessage));

    }

}
