package com.clone.finalProject.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Setter
public class Tags {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long tagId;

    @Column
    private String tagName;

}
