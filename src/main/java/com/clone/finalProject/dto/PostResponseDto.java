package com.clone.finalProject.dto;

import com.clone.finalProject.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private Long uid;
    private Long pid;
    private String postTitle;
    private String postComment;
    private String postImg;
    private Long postLikeCount;
    private String status;


    public PostResponseDto (Post post, Long uid, Long postLikeCount) {
        this.pid= post.getPid();
        this.postTitle = post.getPostTitle();
        this.postComment= post.getPostComment();
        this.postImg= post.getPostImg();
        this.status= post.getStatus();
        this.uid= uid;
        this.postLikeCount = postLikeCount;

    }

}
