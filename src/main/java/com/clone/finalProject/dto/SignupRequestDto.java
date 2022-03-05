package com.clone.finalProject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequestDto {
    private String username;
    private String nickname;
    private String career;
    private String password;
    private String passwordCheck;
}
