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
    private Long weekPoint;

    // 주간 랭킹
    public WeekRankResponseDto(User user) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.career = user.getCareer();
        this.weekPoint = user.getWeekPoint();

    }
}
