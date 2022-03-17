package com.clone.finalProject.dto.rankDto;

import com.clone.finalProject.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WeekRankResponseDto {

    private String username;
    private String nickname;
    private String career;
    private String userImage;
    private String blogUrl;
    private Long weekPoint;
    private Long rank;

    // 주간 랭킹
    public WeekRankResponseDto(User user) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.career = user.getCareer();
        this.weekPoint = user.getWeekPoint();
        this.userImage = user.getUserImage();
        this.blogUrl = user.getBlogUrl();

    }


    public WeekRankResponseDto(User user, Long rank) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.career = user.getCareer();
        this.weekPoint = user.getWeekPoint();
        this.userImage = user.getUserImage();
        this.blogUrl = user.getBlogUrl();
        this.rank = rank;
    }
}
