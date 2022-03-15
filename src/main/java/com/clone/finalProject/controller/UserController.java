package com.clone.finalProject.controller;

import com.clone.finalProject.domain.User;
import com.clone.finalProject.dto.*;
import com.clone.finalProject.security.UserDetailsImpl;
import com.clone.finalProject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    //회원가입
    @PostMapping("/user/signup")
    public SignupResponseDto registerUser(@RequestBody SignupRequestDto requestDto) {

        System.out.println("username :" + requestDto.getUsername());
        System.out.println("nickname :" + requestDto.getNickname());
        System.out.println("password :" + requestDto.getPassword());
        System.out.println("passwordCheck :" + requestDto.getPasswordCheck());

        userService.registerUser(requestDto);
        return new SignupResponseDto(true);
    }

    //로그인 유지 확인
    @PostMapping("/islogin/user")
    public IsloginResponseDto islogin(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        System.out.println("username : " + user.getUsername());
        System.out.println("nickName : " + user.getNickname());
        System.out.println("nickName : " + user.getUid());
        System.out.println("nickName : " + user.getCareer());
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
    @PostMapping("/islogin/user/getinfo/uid")
    public IsloginResponseDto getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        System.out.println("username : " + user.getUsername());
        System.out.println("nickName : " + user.getNickname());
        return new IsloginResponseDto(user.getUsername(),user.getNickname(),user.getUid(),user.getCareer(), user.getUserImage());
    }

//    회원 정보 수정
    @PutMapping("/islogin/user/getinfo/{uid}")
    public UserInfoResponseDto updateUserInfo(@PathVariable Long uid, @RequestBody UserInfoRequestDto userInfoRequestDto){
        System.out.println("nickname :" + userInfoRequestDto.getNickname());
        System.out.println("career :" + userInfoRequestDto.getCareer());
        System.out.println("userimage_url :" + userInfoRequestDto.getUserImage());

        userService.updateUserInfo(uid, userInfoRequestDto);
        return new UserInfoResponseDto(true);
    }

    //    회원 비밀번호 수정
    @PutMapping("/islogin/user/password/{uid}")
    public UserInfoResponseDto updatePassword(@PathVariable Long uid, @RequestBody UserInfoRequestDto userInfoRequestDto){
        userService.updatePassword(uid, userInfoRequestDto);
        return new UserInfoResponseDto(true);
    }

}