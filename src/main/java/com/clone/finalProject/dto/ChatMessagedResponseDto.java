package com.clone.finalProject.dto;

import com.clone.finalProject.domain.ChatMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class ChatMessagedResponseDto {

    private String senderName;
    private String message;
    private String opposingUserName;
    private LocalDateTime createdAt;

    public ChatMessagedResponseDto (ChatMessage chatMessage) {
        this.senderName = chatMessage.getSenderName();
        this.message = chatMessage.getMessage();
        this.opposingUserName = chatMessage.getOpposingUserName();
        this.createdAt = chatMessage.getCreatedAt();
    }

}
