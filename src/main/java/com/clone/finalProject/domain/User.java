package com.clone.finalProject.domain;

import com.clone.finalProject.dto.SignupRequestDto;
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


    public User(SignupRequestDto signupRequestDto, String encodePassword ) {
        this.username = signupRequestDto.getUsername();
        this.nickname = signupRequestDto.getNickname();
        this.password = encodePassword;
    }
}
