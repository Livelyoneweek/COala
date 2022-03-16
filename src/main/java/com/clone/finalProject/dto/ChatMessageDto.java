package com.clone.finalProject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class ChatMessageDto implements Serializable {
    //    public class ChatMessageDto implements Serializable {
//    private static final long serialVersionUID = 6494678977089006639L;

    private String senderName;
    private String message;
    private String status;
    private String area;
    private Long pid;
    private LocalDateTime createdAt;
    private String opposingUserName;

    private long userCount;
    private String roomId;
}
