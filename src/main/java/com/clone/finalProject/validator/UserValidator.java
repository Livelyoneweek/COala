package com.clone.finalProject.validator;

import com.clone.finalProject.dto.userDto.SignupRequestDto;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UserValidator {

    public static boolean validateUserRegister(SignupRequestDto signupRequestDto){
        String username = signupRequestDto.getUsername();
        String nickname = signupRequestDto.getNickname();
        String password = signupRequestDto.getPassword();
        String passwordCheck = signupRequestDto.getPasswordCheck();

        String patternId = "^[a-zA-Z0-9]{4,10}$"; //아이디: 영대소문자,숫자 4-10이내
        String patternNick = "^[a-zA-Z0-9가-힣]{2,8}$"; //닉네임: 영대소문자한글,숫자 2-8자 이내
        String patternpw= "^[a-zA-Z0-9]{4,10}$"; //비밀번호: 영대소문,숫자,특수문자 4-8이내

        //아이디 검사
        if(username == null || !Pattern.matches(patternId, username)){
            throw new IllegalArgumentException("아이디는 영소문자,숫자 포함하여 4~10자 이내로 입력해주세요.");
        }

        //닉네임 검사
        if (nickname == null || !Pattern.matches(patternNick, nickname)) {
            throw new IllegalArgumentException("닉네임은 문자,숫자 포함 4-10자 이내로 입력해주세요.");
        }

        //비밀번호 확인
        if (password == null || !Pattern.matches(patternpw, password)) {
            throw new IllegalArgumentException("비밀번호는 숫자,문자 포함하여 4~10자 이내로 입력해주세요.");
        }

        if (!password.equals(passwordCheck)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return true;
    }
}
