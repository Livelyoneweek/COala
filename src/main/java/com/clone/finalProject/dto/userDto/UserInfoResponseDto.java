package com.clone.finalProject.dto.userDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserInfoResponseDto {

    boolean result;

    public UserInfoResponseDto(boolean checkedresult) {
        this.result = checkedresult;
    }
}
