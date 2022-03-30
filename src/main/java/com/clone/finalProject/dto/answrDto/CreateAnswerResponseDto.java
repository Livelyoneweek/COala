package com.clone.finalProject.dto.answrDto;

import com.clone.finalProject.domain.Answer;
import com.clone.finalProject.domain.User;
import com.clone.finalProject.dto.commentDto.CommnetResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAnswerResponseDto {
    private Long answerId;
    private String nickname;
    private String userImage;

    public CreateAnswerResponseDto(Answer answer, User user) {
        this.answerId = answer.getAnswerId();
        this.userImage= user.getUserImage();
        this.nickname= user.getNickname();

    }

}
