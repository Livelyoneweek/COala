package com.clone.finalProject.dto;


import com.clone.finalProject.domain.Comment;
import com.clone.finalProject.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommnetResponseDto {

    private Long commentId;
    private Long uid;
    private String nickname;
    private String career;
    private String userImage;
    private String blogUrl;
    private Long pid;
    private Long answerId;
    private String comment;
    private LocalDateTime createdAt;

    public CommnetResponseDto(User user, Comment comment) {
        this.uid = user.getUid();
        this.nickname= user.getNickname();
        this.career= user.getCareer();
        this.userImage= user.getUserImage();
        this.blogUrl= user.getBlogUrl();
        this.pid = comment.getPid();
        this.commentId= comment.getCommentId();
        this.comment =comment.getComment();
        this.createdAt = comment.getCreatedAt();

    }

}
