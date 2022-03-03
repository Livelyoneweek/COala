package com.clone.finalProject.controller;


import com.clone.finalProject.dto.AnswerLikeResponseDto;
import com.clone.finalProject.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    //찜 할 때 있으면 삭제 없으면 생성
    @PostMapping("/islogin/post/like")
    public HashMap<String, String> postLike (AnswerLikeResponseDto answerLikeResponseDto) {
        HashMap<String, String> result = postLikeService.postLike(answerLikeResponseDto);

        return result;
    }

}
