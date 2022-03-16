package com.clone.finalProject.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Setter
public class PostTags {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long postTagId;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "TAG_ID")
    private Tags tags;


}
