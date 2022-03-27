package com.clone.finalProject.domain;

import com.clone.finalProject.dto.answrDto.AnswerResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Answer extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @Column(nullable = false)
    private String answerTitle;

    @Column(nullable = false)
    private String answerComment;

    @Column
    private String answerImg;

    @Column
    private Long uid;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post post;

    public Answer(AnswerResponseDto answerResponseDto, Post post) {
        this.answerTitle = answerResponseDto.getAnswerTitle();
        this.answerComment = answerResponseDto.getAnswerComment();
        this.answerImg = answerResponseDto.getAnswerImg();
        this.uid =answerResponseDto.getUid();
        this.post = post;
    }
//
    public void update(AnswerResponseDto answerResponseDto) {
        this.answerTitle = answerResponseDto.getAnswerTitle();
        this.answerComment= answerResponseDto.getAnswerComment();
        this.answerImg= answerResponseDto.getAnswerImg();
    }

}
