package com.clone.finalProject.dto.alarmDto;

import lombok.*;


@Getter
@NoArgsConstructor
public class AlarmResponseDto {

    private String status;
    private String postPageUrl;

    public AlarmResponseDto(String status, String postPageUrl) {
        this.status =status;
        this.postPageUrl = postPageUrl;
    }

}
