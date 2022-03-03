package com.clone.finalProject.controller;

import com.clone.finalProject.dto.rankDto.AllRankResponseDto;
import com.clone.finalProject.dto.rankDto.MonthRankResponseDto;
import com.clone.finalProject.dto.rankDto.WeekRankResponseDto;
import com.clone.finalProject.service.RankService;
import lombok.RequiredArgsConstructor;
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
    @GetMapping("/user/ranking/month")
    public List<WeekRankResponseDto> weekRank() {
        return rankService.getWeekRank();
    }

    // 월간 랭킹 조회
    @GetMapping("/user/ranking/weekend")
    public List<MonthRankResponseDto> monthRank() {
        return rankService.getMonthRank();
    }
}
