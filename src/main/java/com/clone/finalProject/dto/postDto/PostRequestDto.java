package com.clone.finalProject.dto.postDto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class PostRequestDto {
    private Long uid;
    private String postTitle;
    private String postComment;
    private String postImg;
    private String category;
    private List<String> tags;


}
