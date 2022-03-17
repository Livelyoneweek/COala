package com.clone.finalProject.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class TagsResponseDto {
    private Long pid;
    private Long uid;
    private String title;
    private String postComment;
    private String nickname;
    private String postImg;
    private List<String> tag;
    private int postLikeCount;
    private int createdAt;
}
