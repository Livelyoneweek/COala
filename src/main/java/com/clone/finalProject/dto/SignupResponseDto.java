package com.clone.finalProject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SignupResponseDto {
    boolean result;

    public SignupResponseDto(boolean checkedresult){
        this.result = checkedresult;
    }
}