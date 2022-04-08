package com.clone.finalProject.domain;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ANSWER_ID")
    private Answer answer;

    public AnswerLike (Long uid, Long pid , Answer answer) {
        this.uid =uid;
        this.pid =pid;
        this.answer =answer;
    }

}
