package com.clone.finalProject.controller;


import com.clone.finalProject.domain.Fword;
import com.clone.finalProject.dto.ChatMessagedResponseDto;
import com.clone.finalProject.repository.FwordRepository;
import com.clone.finalProject.security.UserDetailsImpl;
import com.clone.finalProject.service.CacheService;
import com.clone.finalProject.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatService chatService;


    // 메인페이지 채널 채팅 내역 조회
    @GetMapping("/chatting/main")
    public List<ChatMessagedResponseDto> chatMainGet() {
        List<ChatMessagedResponseDto> chatMessagedResponseDtoList = chatService.chatMainGet();

        return chatMessagedResponseDtoList;
    }


    // 게시글 페이지 채널 채팅 내역 조회
    @GetMapping("/chatting/{pid}")
    public List<ChatMessagedResponseDto> chatMainPost(@PathVariable Long pid) {
        List<ChatMessagedResponseDto> chatMessagedResponseDtoList = chatService.chatMainPost(pid);

        return chatMessagedResponseDtoList;
    }

    // 귓속말 채팅 내역 조회
    @GetMapping("/islogin/chatting")
    public List<ChatMessagedResponseDto> chatMainUser( @AuthenticationPrincipal UserDetailsImpl userDeta) {
        List<ChatMessagedResponseDto> chatMessagedResponseDtoList = chatService.chatMainUser(userDeta.getUid());

        return chatMessagedResponseDtoList;
    }





}
