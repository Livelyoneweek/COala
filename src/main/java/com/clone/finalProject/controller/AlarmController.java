package com.clone.finalProject.controller;

import com.clone.finalProject.dto.alarmDto.AlarmPageResponseDto;
import com.clone.finalProject.security.UserDetailsImpl;
import com.clone.finalProject.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;

    //알람 메시지 조회
    @GetMapping("/islogin/get/alarm")
    public List<AlarmPageResponseDto> alarmGet(@AuthenticationPrincipal UserDetailsImpl userDeta) {

        Long uid = userDeta.getUid();
        List<AlarmPageResponseDto> alarmPageResponseDtoList = alarmService.alarmGet(uid);

        return alarmPageResponseDtoList;
    }

    //알람 메시지 삭제
    @DeleteMapping("/islogin/delete/alarm/{alarmId}")
    public Long alarmDelete(@PathVariable Long alarmId) {
        alarmService.alarmDelete(alarmId);

        return alarmId;
    }

}
