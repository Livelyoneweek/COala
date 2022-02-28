package com.clone.finalProject.controller;

import com.clone.finalProject.domain.User;
import com.clone.finalProject.dto.IsloginResponseDto;
import com.clone.finalProject.dto.SignupRequestDto;
import com.clone.finalProject.dto.SignupResponseDto;
import com.clone.finalProject.security.UserDetailsImpl;
import com.clone.finalProject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class
UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("/user/signup")
    public SignupResponseDto registerUser(@RequestBody SignupRequestDto requestDto) {
        userService.registerUser(requestDto);
        return new SignupResponseDto(true);
    }

    //로그인 유지 확인
    @PostMapping("/user/islogin")
    public IsloginResponseDto islogin(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        System.out.println("username : " + user.getUsername());
        System.out.println("nickName : " + user.getNickname());
        return new IsloginResponseDto(user.getUsername(),user.getNickname(),user.getUid());
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
        return new IsloginResponseDto(user.getUsername(),user.getNickname(),user.getUid());
    }




}