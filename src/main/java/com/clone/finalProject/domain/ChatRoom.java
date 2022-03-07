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
    private Long uid;

    public ChatRoom (String area) {
        this.area=area;
    }

    public ChatRoom (String area, Long pid) {
        this.area=area;
        this.pid =pid;
    }

    public ChatRoom (Long uid, String area) {
        this.area=area;
        this.uid =uid;
    }




}
