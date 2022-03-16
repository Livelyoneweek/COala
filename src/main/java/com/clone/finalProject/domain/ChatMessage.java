package com.clone.finalProject.domain;

import com.clone.finalProject.dto.ChatMessageDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class ChatMessage extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatMessageId;

    @Column(nullable = false)
    private Long uid;

    @Column
    private String message;

    @Column(nullable = false)
    private String senderName;

    @Column
    private String opposingUserName;

    @ManyToOne
    @JoinColumn(name = "CHATROOM_ID")
    private ChatRoom chatRoom;

    public ChatMessage (Long uid, ChatMessageDto chatMessageDto, ChatRoom chatRoom) {
        this.uid= uid;
        this.message=chatMessageDto.getMessage();
        this.senderName=chatMessageDto.getSenderName();
        this.opposingUserName=chatMessageDto.getOpposingUserName();
        this.chatRoom=chatRoom;
    }



}
