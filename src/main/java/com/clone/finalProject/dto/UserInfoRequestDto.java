package com.clone.finalProject.dto;

import lombok.Getter;

@Getter
public class UserInfoRequestDto {
    private String nickname;
    private String career;
    private String userImage;
    private String password;
    private String newPassword;
    private String newPasswordCheck;
}
