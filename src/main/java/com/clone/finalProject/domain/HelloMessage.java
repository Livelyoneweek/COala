package com.clone.finalProject.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class HelloMessage {

    private String name;
    private String message;
    private LocalDateTime createdAt;

    public HelloMessage(String name) {
        this.name = name;
    }



}
