package com.clone.finalProject.dto.rankDto;

import com.clone.finalProject.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AllRankResponseDto {

    private String username;
    private String nickname;
    private String career;
    private String userImage;
    private String blogUrl;
    private Long point;
    private Long rank;


    //전체 랭킹
    public AllRankResponseDto(User user) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.career = user.getCareer();
        this.point = user.getPoint();
        this.userImage = user.getUserImage();
        this.blogUrl = user.getBlogUrl();
    }

    //전체 랭킹
    public AllRankResponseDto(User user, Long rank) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.career = user.getCareer();
        this.point = user.getPoint();
        this.userImage = user.getUserImage();
        this.blogUrl = user.getBlogUrl();
        this.rank = rank;
    }
}
