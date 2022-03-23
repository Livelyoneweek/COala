package com.clone.finalProject.dto;

import com.clone.finalProject.domain.Alarm;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
public class AlarmPageResponseDto {

    private Long alarmId;
    private String type;
    private Long pid;
    private Long answerId;
    private Long uid;
    private String postTitle;
    private LocalDateTime createdAt;

    public AlarmPageResponseDto(Alarm alarm, String postTitle) {
        this.alarmId = alarm.getAlarmId();
        this.type = alarm.getType();
        this.pid = alarm.getPid();
        this.answerId=alarm.getAnswerId();
        this.uid=alarm.getUser().getUid();
        this.createdAt=alarm.getCreatedAt();
        this.postTitle=postTitle;
    }

}
