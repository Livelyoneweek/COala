package com.clone.finalProject.controller;

import com.clone.finalProject.dto.postDto.PostResponseDto;
import com.clone.finalProject.service.TagsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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
    @GetMapping("/post/get/tag/{tag}")
    public List<PostResponseDto> searchTag(@PathVariable String tag,
                                           @RequestParam("page") int page,
                                           @RequestParam("size") int size,
                                           @RequestParam("sortBy")String sortBy,
                                           @RequestParam("isAsc")boolean isAsc) {
        log.info("tag : {}", tag);
        page = page-1;
        List<PostResponseDto> postResponseDtoList = tagsService.searchTag(tag, page, size, sortBy, isAsc);
        return postResponseDtoList;
    }


}
