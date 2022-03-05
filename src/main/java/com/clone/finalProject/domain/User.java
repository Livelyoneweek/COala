package com.clone.finalProject.domain;

import com.clone.finalProject.dto.SignupRequestDto;
import com.clone.finalProject.dto.UserInfoRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column
    private String career;

    @Column
    private String userImage;

    @Column
    private Long point;

    @Column
    private Long weekPoint;

    @Column
    private Long monthPoint;


    // 회원가입
    public User(SignupRequestDto signupRequestDto, String encodePassword ) {
        this.username = signupRequestDto.getUsername();
        this.nickname = signupRequestDto.getNickname();
        this.career = signupRequestDto.getCareer();
        this.password = encodePassword;
    }
    // 유저 정보 수정
    public void userInfoUpdate(UserInfoRequestDto userInfoRequestDto){
        this.nickname = userInfoRequestDto.getNickname();
        this.career = userInfoRequestDto.getCareer();
        this.userImage = userInfoRequestDto.getUserImage();
    }

    // 유저 패스워드 수정
    public void userPasswordUpdate(String encodePassword){
        this.password = encodePassword;
    }
}
