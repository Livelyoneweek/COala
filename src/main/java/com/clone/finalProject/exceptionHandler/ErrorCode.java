package com.clone.finalProject.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    NOT_IMAGES(HttpStatus.BAD_REQUEST,"400", "업로드하려는 파일이 이미지 파일이 아닙니다."),

    NOT_FOUND_USER(HttpStatus.NOT_FOUND,"404", "사용자가 존재하지 않습니다."),
    NOT_FOUND_ANSWER(HttpStatus.NOT_FOUND,"404", "답변이 존재하지 않습니다."),
    NOT_FOUND_ANSWERLIKE(HttpStatus.NOT_FOUND,"404", "답변이 존재하지 않습니다."),
    NOT_FOUND_POST(HttpStatus.NOT_FOUND,"404", "답변이 존재하지 않습니다."),
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND,"404", "댓글이 존재하지 않습니다."),
    NOT_FOUND_TAG(HttpStatus.NOT_FOUND,"404", "태그가 존재하지 않습니다."),

    DUPLICATE_ID(HttpStatus.CONFLICT,"409","중복된 아이디입니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT,"409","중복된 닉네임입니다.");


    private final HttpStatus httpStatus;
    private final String status;
    private final String message;
}
