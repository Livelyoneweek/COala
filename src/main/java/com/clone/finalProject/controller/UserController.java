package com.clone.finalProject.controller;

import com.clone.finalProject.domain.User;
import com.clone.finalProject.dto.userDto.*;
import com.clone.finalProject.security.UserDetailsImpl;
import com.clone.finalProject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("/user/signup")
    public SignupResponseDto registerUser(@RequestBody SignupRequestDto requestDto) {

        log.info("username :{}", requestDto.getUsername());
        log.info("nickname :{}", requestDto.getNickname());
        log.info("password :{}", requestDto.getPassword());
        log.info("passwordCheck :{}", requestDto.getPasswordCheck());

        userService.registerUser(requestDto);
        return new SignupResponseDto(true);
    }

    //로그인 유지 확인
    @PostMapping("/islogin/user")
    public IsloginResponseDto islogin(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        log.info("username : {}", user.getUsername());
        log.info("nickName : {}", user.getNickname());
        log.info("nickName : {}", user.getUid());
        log.info("nickName : {}", user.getCareer());
        return new IsloginResponseDto(user.getUsername(),user.getNickname(),user.getUid(),user.getCareer(),user.getUserImage());
    }

    //아이디 중복 확인
    @PostMapping("/user/signup/username")
    public boolean usernameCheck(@RequestBody SignupRequestDto signupRequestDto) {
        return userService.usernameCheck(signupRequestDto.getUsername());
    }
    //닉네임 중복 확인
    @PostMapping("/user/signup/nickname")
    public boolean emailCheck(@RequestBody SignupRequestDto signupRequestDto) {
        return userService.nickName(signupRequestDto.getNickname());
    }

    //회원정보 조회
    @PostMapping("/islogin/user/get/{uid}")
    public IsloginResponseDto getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        log.info("username : {}", user.getUsername());
        log.info("nickName : {}", user.getNickname());
        return new IsloginResponseDto(user.getUsername(),user.getNickname(),user.getUid(),user.getCareer(), user.getUserImage());
    }

//    회원 정보 수정
    @PutMapping("/islogin/user/edit/{uid}")
    public UserInfoResponseDto updateUserInfo(@PathVariable Long uid, @RequestBody UserInfoRequestDto userInfoRequestDto){
        log.info("nickname :{}", userInfoRequestDto.getNickname());
        log.info("career :{}", userInfoRequestDto.getCareer());
        log.info("userimage_url {}",  userInfoRequestDto.getUserImage());

        userService.updateUserInfo(uid, userInfoRequestDto);
        return new UserInfoResponseDto(true);
    }

    //    회원 비밀번호 수정
    @PutMapping("/islogin/user/edit/pwd/{uid}")
    public UserInfoResponseDto updatePassword(@PathVariable Long uid, @RequestBody UserInfoRequestDto userInfoRequestDto){
        UserInfoResponseDto userInfoResponseDto = userService.updatePassword(uid, userInfoRequestDto);
        return userInfoResponseDto;
    }

}