package com.clone.finalProject.dto.userDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IsloginResponseDto {

    private String username;
    private String nickname;
    private Long uid;
    private String career;
    private String userImage;
    private String blogUrl;

    //로그인 유지
    public IsloginResponseDto(String username, String nickname, Long uid, String career) {
        this.username = username;
        this.nickname = nickname;
        this.uid = uid;
        this.career = career;

    }

    //회원정보
    public IsloginResponseDto(String username, String nickname, Long uid, String career, String userImage, String blogUrl) {
        this.username = username;
        this.nickname = nickname;
        this.uid = uid;
        this.career = career;
        this.userImage = userImage;
        this.blogUrl = blogUrl;
    }
}
