package com.clone.finalProject.domain;

import com.clone.finalProject.dto.CommnetResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Comment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String commentTitle;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private Long uid;

    @Column(nullable = false)
    private Long pid;

    @ManyToOne
    @JoinColumn(name = "ANSWER_ID")
    private Answer answer;

    public Comment(CommnetResponseDto commnetResponseDto, Answer answer) {
        this.commentTitle = commnetResponseDto.getCommentTitle();
        this.comment = commnetResponseDto.getComment();
        this.uid = commnetResponseDto.getUid();
        this.pid = commnetResponseDto.getPid();
        this.answer = answer;
    }
//
    public void update(CommnetResponseDto commnetResponseDto) {
        this.commentTitle = commnetResponseDto.getCommentTitle();
        this.comment= commnetResponseDto.getComment();
    }

}
