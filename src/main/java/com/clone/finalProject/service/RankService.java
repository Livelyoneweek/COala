package com.clone.finalProject.service;

import com.clone.finalProject.domain.User;
import com.clone.finalProject.dto.rankDto.AllRankResponseDto;
import com.clone.finalProject.dto.rankDto.MonthRankResponseDto;
import com.clone.finalProject.dto.rankDto.WeekRankResponseDto;
import com.clone.finalProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RankService {

    private final UserRepository userRepository;

    //전체 랭킹 조회
    public List<AllRankResponseDto> getAllRank() {
        List<User> allRank = userRepository.findTop10ByOrderByPointDesc();

        List<AllRankResponseDto> allrankResponseDtos = new ArrayList<>();
        for (User user : allRank) {
            AllRankResponseDto rankResponseDto = new AllRankResponseDto(user);
            allrankResponseDtos.add(rankResponseDto);
        }
        return allrankResponseDtos;
    }

    //주간 랭킹 조회
    public List<WeekRankResponseDto> getWeekRank() {
        List<User> allRank = userRepository.findTop10ByOrderByWeekPointDesc();

        List<WeekRankResponseDto> weekRankResponseDtos = new ArrayList<>();
        for (User user : allRank) {
            WeekRankResponseDto rankResponseDto = new WeekRankResponseDto(user);
            weekRankResponseDtos.add(rankResponseDto);
        }
        return weekRankResponseDtos;
    }

    //월간 랭킹 조회
    public List<MonthRankResponseDto> getMonthRank() {
        List<User> allRank = userRepository.findTop10ByOrderByMonthPointDesc();

        List<MonthRankResponseDto> monthRankResponseDtos = new ArrayList<>();
        for (User user : allRank) {
            MonthRankResponseDto monthRankResponseDto = new MonthRankResponseDto(user);
            monthRankResponseDtos.add(monthRankResponseDto);
        }
        return monthRankResponseDtos;
    }

}
