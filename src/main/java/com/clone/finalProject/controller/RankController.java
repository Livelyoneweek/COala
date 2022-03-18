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
    @GetMapping("/user/ranking/total")
    public List<AllRankResponseDto> totalRank() {
        return rankService.getAllRank();
    }

    // 주간 랭킹 조회
    @GetMapping("/user/ranking/weekend")
    public List<WeekRankResponseDto> weekRank() {
        return rankService.getWeekRank();
    }

    // 월간 랭킹 조회
    @GetMapping("/user/ranking/month")
    public List<MonthRankResponseDto> monthRank() {
        return rankService.getMonthRank();
    }


    //종합 나의 랭킹
    @GetMapping("/islogin/ranking/total")
    public AllRankResponseDto myTotalRank(@AuthenticationPrincipal UserDetailsImpl userDeta) {
        long uid = userDeta.getUid();
        AllRankResponseDto allRankResponseDto = rankService.getAllRankMy(uid);

        return allRankResponseDto;
    }

    //주간 나의 랭킹
    @GetMapping("/islogin/ranking/weekend")
    public WeekRankResponseDto myWeekRank(@AuthenticationPrincipal UserDetailsImpl userDeta) {
        long uid = userDeta.getUid();
        WeekRankResponseDto weekRankResponseDto = rankService.getWeekRankMy(uid);

        return weekRankResponseDto;
    }

    //주간 나의 랭킹
    @GetMapping("/islogin/ranking/month")
    public MonthRankResponseDto myMonthRank(@AuthenticationPrincipal UserDetailsImpl userDeta) {
        long uid = userDeta.getUid();
        MonthRankResponseDto monthRankResponseDto = rankService.getMonthRankMy(uid);

        return monthRankResponseDto;
    }


}
