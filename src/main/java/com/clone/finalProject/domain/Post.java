package com.clone.finalProject.domain;

import com.clone.finalProject.dto.PostRequestDto;
import com.clone.finalProject.dto.SignupRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Post extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;

    @Column(nullable = false)
    private String postTitle;

    @Column(nullable = false)
    private String postComment;

    @Column
    private String postImg;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public Post(PostRequestDto postRequestDto, User user) {
        this.postTitle = postRequestDto.getPostTitle();
        this.postComment = postRequestDto.getPostComment();
        this.postImg = postRequestDto.getPostImg();
        this.user = user;
    }

    public void update(PostRequestDto postRequestDto) {
        this.postTitle = postRequestDto.getPostTitle();
        this.postComment= postRequestDto.getPostComment();
    }

}
