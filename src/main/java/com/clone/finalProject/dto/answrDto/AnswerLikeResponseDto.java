package com.clone.finalProject.dto.answrDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerLikeResponseDto {
    private Long uid;
    private Long pid;
    private Long answerId;
    private Long answerUid;



}
