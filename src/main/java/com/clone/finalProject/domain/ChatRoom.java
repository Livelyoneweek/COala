package com.clone.finalProject.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class ChatRoom extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomId;

    @Column(nullable = false)
    private String area;

    @Column
    private Long pid;

    @Column
    private Long userCount;

    public ChatRoom (String area) {
        this.area=area;
    }



}
