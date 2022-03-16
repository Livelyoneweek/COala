package com.clone.finalProject.service;

import com.clone.finalProject.domain.Alarm;
import com.clone.finalProject.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AlarmService {

    private final AlarmRepository alarmRepository;

    // 알람 메시지 조회
    public List<Alarm> alarmGet(Long uid) {

        List<Alarm> alarmList = alarmRepository.findAllByUser_UidOrderByCreatedAt(uid);

        return alarmList;
    }

    // 알람 메시지 삭제
    public void alarmDelete(Long alarmId) {

        alarmRepository.deleteById(alarmId);
    }
}
