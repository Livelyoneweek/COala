package com.clone.finalProject.dto.postDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
