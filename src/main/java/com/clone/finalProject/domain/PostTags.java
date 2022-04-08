package com.clone.finalProject.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Setter
public class PostTags extends Timestamped{

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long postTagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TAG_ID")
    private Tags tags;

    public PostTags (Post post, Tags tags) {
        this.post=post;
        this.tags=tags;
    }


}
