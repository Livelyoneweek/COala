package com.clone.finalProject.controller;


import com.clone.finalProject.dto.AnswerLikeResponseDto;
import com.clone.finalProject.service.AnswerLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class AnswerLikeController {

    private final AnswerLikeService answerLikeService;

    // 답변 채택 시 없으면 생성, 있으면 삭제하거나 실패
    @PostMapping("/islogin/answer/like")
    public HashMap<String,String> answerLike(@RequestBody AnswerLikeResponseDto answerLikeResponseDto) {
        HashMap<String,String> result = answerLikeService.answerLike(answerLikeResponseDto);

        return result;
    }


}