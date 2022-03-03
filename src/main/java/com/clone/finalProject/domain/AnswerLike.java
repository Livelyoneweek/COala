package com.clone.finalProject.domain;

import com.clone.finalProject.dto.AnswerLikeResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class AnswerLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerLikeId;

    @Column(nullable = false)
    private Long uid;

    @Column(nullable = false)
    private Long pid;

    @Column(nullable = false)
    private Long answerId;

    @Column(nullable = false)
    private Long answerUid;

    public AnswerLike (AnswerLikeResponseDto answerLikeResponseDto) {
        this.uid =answerLikeResponseDto.getUid();
        this.pid =answerLikeResponseDto.getPid();
        this.answerId =answerLikeResponseDto.getAnswerId();
        this.answerUid =answerLikeResponseDto.getAnswerUid();
    }

}
