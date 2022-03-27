package com.clone.finalProject.dto.userDto;

import lombok.Getter;

@Getter
public class UserInfoRequestDto {
    private String nickname;
    private String career;
    private String userImage;
    private String blogUrl;
    private String password;
    private String newPassword;
    private String newPasswordCheck;
}
