package com.clone.finalProject.domain;

import com.clone.finalProject.dto.answrDto.AnswerLikeResponseDto;
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

    @ManyToOne
    @JoinColumn(name = "ANSWER_ID")
    private Answer answer;


    public AnswerLike (AnswerLikeResponseDto answerLikeResponseDto, Answer answer) {
        this.uid =answerLikeResponseDto.getUid();
        this.pid =answerLikeResponseDto.getPid();
        this.answer =answer;
    }

}
