package com.clone.finalProject.dto;


import com.clone.finalProject.domain.Answer;
import com.clone.finalProject.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommnetResponseDto {

    private Long commentId;
    private Long uid;
    private Long pid;
    private Long answerId;
    private String commentTitle;
    private String comment;

    public CommnetResponseDto(Comment comment) {
        this.uid = comment.getUid();
        this.pid = comment.getPid();
        this.commentId= comment.getCommentId();
        this.commentTitle =comment.getCommentTitle();
        this.comment =comment.getComment();

    }

}
