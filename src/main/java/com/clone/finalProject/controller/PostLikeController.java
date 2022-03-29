package com.clone.finalProject.controller;

import com.clone.finalProject.dto.answrDto.AnswerLikeResponseDto;
import com.clone.finalProject.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    //관심 등록 할 때 있으면 삭제 없으면 생성
    @PostMapping("/islogin/post/create/like")
    public HashMap<String, String> postLikeCreate (@RequestBody AnswerLikeResponseDto answerLikeResponseDto) {
        HashMap<String, String> result = postLikeService.postLikeCreate(answerLikeResponseDto);

        return result;
    }

}
