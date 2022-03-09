package com.clone.finalProject.controller;


import com.clone.finalProject.dto.ChatMessagedResponseDto;
import com.clone.finalProject.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatService chatService;

    // 메인페이지 채널 채팅 내역 조회
    @GetMapping("/islogin/chatting/main")
    public List<ChatMessagedResponseDto> chatMainGet() {
        List<ChatMessagedResponseDto> chatMessagedResponseDtoList = chatService.chatMainGet();

        return chatMessagedResponseDtoList;
    }


    // 게시글 페이지 채널 채팅 내역 조회
    @GetMapping("/islogin/chatting/{pid}")
    public List<ChatMessagedResponseDto> chatMainPost(@PathVariable Long pid) {
        List<ChatMessagedResponseDto> chatMessagedResponseDtoList = chatService.chatMainPost(pid);

        return chatMessagedResponseDtoList;
    }

    // 귓속말 채팅 내역 조회
    @GetMapping("/islogin/chatting/{uid}")
    public List<ChatMessagedResponseDto> chatMainUser(@PathVariable Long uid) {
        List<ChatMessagedResponseDto> chatMessagedResponseDtoList = chatService.chatMainUser(uid);

        return chatMessagedResponseDtoList;
    }

}
