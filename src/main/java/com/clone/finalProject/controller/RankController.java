package com.clone.finalProject.controller;

import com.clone.finalProject.dto.rankDto.AllRankResponseDto;
import com.clone.finalProject.dto.rankDto.MonthRankResponseDto;
import com.clone.finalProject.dto.rankDto.WeekRankResponseDto;
import com.clone.finalProject.security.UserDetailsImpl;
import com.clone.finalProject.service.RankService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RankController {

    private final RankService rankService;

    // 전체 랭킹 조회
    @GetMapping("/user/get/ranking/total")
    public List<AllRankResponseDto> getRankingTotal() {
        return rankService.getRankingTotal();
    }

    // 주간 랭킹 조회
    @GetMapping("/user/get/ranking/weekend")
    public List<WeekRankResponseDto> getRankingWeekend() {
        return rankService.getRankingWeekend();
    }

    // 월간 랭킹 조회
    @GetMapping("/user/get/ranking/month")
    public List<MonthRankResponseDto> getRankingMonth() {
        return rankService.getRankingMonth();
    }


    //종합 나의 랭킹
    @GetMapping("/islogin/get/myranking/total")
    public AllRankResponseDto getRankingTotalMy(@AuthenticationPrincipal UserDetailsImpl userDeta) {
        long uid = userDeta.getUid();
        AllRankResponseDto allRankResponseDto = rankService.getRankingTotalMy(uid);

        return allRankResponseDto;
    }

    //주간 나의 랭킹
    @GetMapping("/islogin/get/myranking/weekend")
    public WeekRankResponseDto getRankingWeekendMy(@AuthenticationPrincipal UserDetailsImpl userDeta) {
        long uid = userDeta.getUid();
        WeekRankResponseDto weekRankResponseDto = rankService.getRankingWeekendMy(uid);

        return weekRankResponseDto;
    }

    //월간 나의 랭킹
    @GetMapping("/islogin/get/myranking/month")
    public MonthRankResponseDto getRankingMonthMy(@AuthenticationPrincipal UserDetailsImpl userDeta) {
        long uid = userDeta.getUid();
        MonthRankResponseDto monthRankResponseDto = rankService.getRankingMonthMy(uid);

        return monthRankResponseDto;
    }
}
