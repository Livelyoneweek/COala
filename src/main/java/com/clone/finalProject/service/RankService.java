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
    public List<AllRankResponseDto> getRankingTotal() {
        List<User> allRank = userRepository.findTop10ByOrderByPointDesc();

        List<AllRankResponseDto> allrankResponseDtos = new ArrayList<>();
        for (User user : allRank) {
            AllRankResponseDto rankResponseDto = new AllRankResponseDto(user);
            allrankResponseDtos.add(rankResponseDto);
        }
        return allrankResponseDtos;
    }

    //주간 랭킹 조회
    public List<WeekRankResponseDto> getRankingWeekend() {
        List<User> allRank = userRepository.findTop10ByOrderByWeekPointDesc();

        List<WeekRankResponseDto> weekRankResponseDtos = new ArrayList<>();
        for (User user : allRank) {
            WeekRankResponseDto rankResponseDto = new WeekRankResponseDto(user);
            weekRankResponseDtos.add(rankResponseDto);
        }
        return weekRankResponseDtos;
    }

    //월간 랭킹 조회
    public List<MonthRankResponseDto> getRankingMonth() {
        List<User> allRank = userRepository.findTop10ByOrderByMonthPointDesc();

        List<MonthRankResponseDto> monthRankResponseDtos = new ArrayList<>();
        for (User user : allRank) {
            MonthRankResponseDto monthRankResponseDto = new MonthRankResponseDto(user);
            monthRankResponseDtos.add(monthRankResponseDto);
        }
        return monthRankResponseDtos;
    }

    //종합 나의 랭킹 조회
    public AllRankResponseDto getRankingTotalMy(Long uid) {
        List<User> allRank = userRepository.findAllByOrderByPointDesc();

        User user = userRepository.findByUid(uid).get();
        Long rank = 0L;

        for (int i = 0; i<allRank.size(); i++) {
            if (allRank.get(i).getUid().equals(uid)) {
                rank = (long) (1 + i);
                break;
            }
        }
        AllRankResponseDto allRankResponseDto = new AllRankResponseDto(user, rank);

        return allRankResponseDto;
    }

    //주간 나의 랭킹 조회
    public WeekRankResponseDto getRankingWeekendMy(long uid) {
        List<User> allRank = userRepository.findAllByOrderByWeekPointDesc();

        User user = userRepository.findByUid(uid).get();
        Long rank = 0L;

        for (int i = 0; i<allRank.size(); i++) {
            if (allRank.get(i).getUid().equals(uid)) {
                rank = (long) (1 + i);
                break;
            }
        }
        WeekRankResponseDto weekRankResponseDto = new WeekRankResponseDto(user, rank);

        return weekRankResponseDto;
    }
    //월간 나의 랭킹 조회
    public MonthRankResponseDto getRankingMonthMy(long uid) {

        List<User> allRank = userRepository.findAllByOrderByMonthPointDesc();

        User user = userRepository.findByUid(uid).get();
        Long rank = 0L;

        for (int i = 0; i<allRank.size(); i++) {
            if (allRank.get(i).getUid().equals(uid)) {
                rank = (long) (1 + i);
                break;
            }
        }
        MonthRankResponseDto monthRankResponseDto = new MonthRankResponseDto(user, rank);

        return monthRankResponseDto;
    }
}
