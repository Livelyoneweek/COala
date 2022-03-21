package com.clone.finalProject.controller;

import com.clone.finalProject.dto.PostResponseDto;
import com.clone.finalProject.service.TagsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class TagsController {
    private final TagsService tagsService;

    public TagsController(TagsService tagsService) {
        this.tagsService = tagsService;
    }


    //태그 조회
    @GetMapping("/tag/search/{tag}")
    public List<PostResponseDto> searchTag(@PathVariable String tag) {
        log.info("tag : {}", tag);
        List<PostResponseDto> postResponseDtoList = tagsService.searchTag(tag);
        return postResponseDtoList;
    }


}
