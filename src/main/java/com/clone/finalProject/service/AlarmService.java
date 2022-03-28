package com.clone.finalProject.service;

import com.clone.finalProject.domain.Alarm;
import com.clone.finalProject.dto.alarmDto.AlarmPageResponseDto;
import com.clone.finalProject.repository.AlarmRepository;
import com.clone.finalProject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final PostRepository postRepository;

    // 알람 메시지 조회
    public List<AlarmPageResponseDto> alarmGet(Long uid) {


        List<Alarm> alarmList = alarmRepository.findAllByUser_UidOrderByCreatedAt(uid);

        List<AlarmPageResponseDto> alarmPageResponseDtoList = new ArrayList<>();
        for (Alarm alarm : alarmList) {

            if (!(postRepository.findByPid(alarm.getPid()).isPresent())) {
                log.info("pid:{}",alarm.getPid());
            }

            String postTitle = postRepository.findByPid(alarm.getPid()).get().getPostTitle();

            AlarmPageResponseDto alarmPageResponseDto = new AlarmPageResponseDto(alarm,postTitle);
            alarmPageResponseDtoList.add(alarmPageResponseDto);
        }

        return alarmPageResponseDtoList;
    }

    // 알람 메시지 삭제
    public void alarmDelete(Long alarmId) {

        alarmRepository.deleteById(alarmId);
    }
}
