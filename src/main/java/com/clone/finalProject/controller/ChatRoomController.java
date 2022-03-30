package com.clone.finalProject.controller;

import com.clone.finalProject.dto.chatMessageDto.ChatMessagedResponseDto;
import com.clone.finalProject.security.UserDetailsImpl;
import com.clone.finalProject.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.serializer.DefaultDeserializer;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatService chatService;

    // 메인페이지 채널 채팅 내역 조회
    @GetMapping("/mainchat/get/main")
    public List<ChatMessagedResponseDto> getMainMessage() {
        List<ChatMessagedResponseDto> chatMessagedResponseDtoList = chatService.getMainMessage();

        return chatMessagedResponseDtoList;
    }

    // 게시글 페이지 채널 채팅 내역 조회
    @GetMapping("/postchat/get/{pid}")
    public List<ChatMessagedResponseDto>getPostMessage(@PathVariable Long pid) {
        List<ChatMessagedResponseDto> chatMessagedResponseDtoList = chatService.getPostMessage(pid);

        return chatMessagedResponseDtoList;
    }

    // 귓속말 채팅 내역 조회
    @GetMapping("/islogin/chatting")
    public List<ChatMessagedResponseDto> getWhisperMessage( @AuthenticationPrincipal UserDetailsImpl userDeta) {
        List<ChatMessagedResponseDto> chatMessagedResponseDtoList = chatService.getWhisperMessage(userDeta.getUid());

        return chatMessagedResponseDtoList;
    }


}
