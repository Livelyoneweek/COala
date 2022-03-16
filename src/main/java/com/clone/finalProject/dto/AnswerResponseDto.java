package com.clone.finalProject.dto;

import com.clone.finalProject.domain.Answer;

import com.clone.finalProject.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerResponseDto {
    private Long answerId;
    private Long uid;
    private String nickname;
    private String career;
    private String userImage;
    private String blogUrl;
    private Long pid;
    private String answerTitle;
    private String answerComment;
    private String answerImg;
    private LocalDateTime createdAt;
    private String status;
    private List<CommnetResponseDto> commnetResponseDtoList;


    public AnswerResponseDto(Answer answer, User user, String status, List<CommnetResponseDto> commnetResponseDtoList) {
        this.answerId = answer.getAnswerId();
        this.pid= answer.getPost().getPid();
        this.answerTitle = answer.getAnswerTitle();
        this.answerComment= answer.getAnswerComment();
        this.answerImg= answer.getAnswerImg();
        this.createdAt = answer.getCreatedAt();
        this.uid= user.getUid();
        this.nickname= user.getNickname();
        this.career= user.getCareer();
        this.userImage= user.getUserImage();
        this.blogUrl= user.getBlogUrl();
        this.status = status;
        this.commnetResponseDtoList = commnetResponseDtoList;

    }

}
