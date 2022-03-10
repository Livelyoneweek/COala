package com.clone.finalProject.controller;

import com.clone.finalProject.domain.Alarm;
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
    @GetMapping("/islogin/alarm")
    public List<Alarm> alarmGet( @AuthenticationPrincipal UserDetailsImpl userDeta) {

        Long uid = userDeta.getUid();
        List<Alarm> alarmList = alarmService.alarmGet(uid);

        return alarmList;
    }


    //알람 메시지 삭제
    @DeleteMapping("/islogin/alarm/{alarmId}")
    public Long alarmDelete(@PathVariable Long alarmId) {
        alarmService.alarmDelete(alarmId);

        return alarmId;
    }

}
