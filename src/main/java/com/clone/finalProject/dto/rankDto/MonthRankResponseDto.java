package com.clone.finalProject.dto.rankDto;

import com.clone.finalProject.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MonthRankResponseDto {
    private String username;
    private String nickname;
    private String career;
    private Long monthPoint;

    // 월간 랭킹
    public MonthRankResponseDto(User user) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.career = user.getCareer();
        this.monthPoint = user.getMonthPoint();

    }
}
