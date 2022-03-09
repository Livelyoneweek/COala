package com.clone.finalProject.dto;

import com.clone.finalProject.domain.Post;
import com.clone.finalProject.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private Long uid;
    private Long pid;
    private String nickname;
    private String career;
    private String userImage;
    private String blogUrl;
    private String postTitle;
    private String postComment;
    private String postImg;
    private Long postLikeCount;
    private String status;
    private String category;
    private List<String> tag;


    public PostResponseDto (Post post, User user, Long postLikeCount, List<String> tag) {
        this.pid= post.getPid();
        this.postTitle = post.getPostTitle();
        this.postComment= post.getPostComment();
        this.postImg= post.getPostImg();
        this.category = post.getCategory();
        this.status= post.getStatus();
        this.uid= user.getUid();
        this.nickname= user.getNickname();
        this.career= user.getCareer();
        this.userImage= user.getUserImage();
        this.blogUrl= user.getBlogUrl();
        this.postLikeCount = postLikeCount;
        this.tag = tag;

    }

}
