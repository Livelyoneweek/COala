package com.clone.finalProject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class ChatMessage {

    private String senderName;
    private String message;
    private String status;
    private Long postId;
    private LocalDateTime createdAt;
}
