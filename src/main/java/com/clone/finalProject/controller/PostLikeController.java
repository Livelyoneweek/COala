package com.clone.finalProject.controller;


import com.clone.finalProject.dto.AnswerLikeResponseDto;
import com.clone.finalProject.dto.PostResponseDto;
import com.clone.finalProject.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    //관심 등록 할 때 있으면 삭제 없으면 생성
    @PostMapping("/islogin/post/like")
    public HashMap<String, String> postLike (@RequestBody AnswerLikeResponseDto answerLikeResponseDto) {
        HashMap<String, String> result = postLikeService.postLike(answerLikeResponseDto);

        return result;
    }

    // 관심 글  조회
    @GetMapping("/islogin/post/like/get/{uid}")
    public List<PostResponseDto> postLikeGet (@PathVariable Long uid ) {
        List<PostResponseDto> postResponseDtoList = postLikeService.postLikeGet(uid);

        return postResponseDtoList;
    }

}
