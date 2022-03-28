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
    public HashMap<String, String> postLike (@RequestBody AnswerLikeResponseDto answerLikeResponseDto) {
        HashMap<String, String> result = postLikeService.postLike(answerLikeResponseDto);

        return result;
    }

//    // 관심 글  조회
//    @GetMapping("/islogin/post/like/get/{uid}")
//    public List<PostResponseDto> postLikeGet (@PathVariable Long uid,
//                                              @PathVariable String postTitle,
//                                              @RequestParam("page") int page,
//                                              @RequestParam("size") int size,
//                                              @RequestParam("sortBy")String sortBy,
//                                              @RequestParam("isAsc")boolean isAsc) {
//
//        page = page -1;
//        List<PostResponseDto> postResponseDtoList = postLikeService.postLikeGet(uid,page, size, sortBy, isAsc);
//
//        return postResponseDtoList;
//    }

}
