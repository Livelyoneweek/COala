package com.clone.finalProject.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Alarm extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alarmId;

    @Column(nullable = false)
    private String type;

    @Column
    private Long pid;

    @Column
    private Long answerId;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public Alarm (String type,Long pid, Long answerId, User user) {
        this.type = type;
        this.pid = pid;
        this.answerId = answerId;
        this.user = user;
    }

}
