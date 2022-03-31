package com.clone.finalProject.dto.commentDto;

import com.clone.finalProject.domain.Comment;
import com.clone.finalProject.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommnetResponseDto {

    private Long commentId;
    private String comment;

    public CreateCommnetResponseDto(Comment comment) {
        this.commentId= comment.getCommentId();
        this.comment =comment.getComment();

    }

}
