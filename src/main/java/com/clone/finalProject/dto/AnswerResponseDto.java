package com.clone.finalProject.dto;

import com.clone.finalProject.domain.Answer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerResponseDto {
    private Long answerId;
    private Long uid;
    private Long pid;
    private String answerTitle;
    private String answerComment;
    private String answerImg;
    private boolean answerLike;
    private List<CommnetResponseDto> commnetResponseDtoList;


    public AnswerResponseDto(Answer answer, Long uid,boolean answerLike, List<CommnetResponseDto> commnetResponseDtoList) {
        this.pid= answer.getPost().getPid();
        this.answerTitle = answer.getAnswerTitle();
        this.answerComment= answer.getAnswerComment();
        this.answerImg= answer.getAnswerImg();
        this.uid= uid;
        this.answerLike = answerLike;
        this.commnetResponseDtoList = commnetResponseDtoList;

    }

}
