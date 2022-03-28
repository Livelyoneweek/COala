package com.clone.finalProject.controller;


import com.clone.finalProject.domain.User;
import com.clone.finalProject.dto.answrDto.AnswerResponseDto;
import com.clone.finalProject.security.UserDetailsImpl;
import com.clone.finalProject.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    // answer 생성
    @PostMapping("/islogin/answer/create/{pid}")
    public Long postCreate(@RequestBody AnswerResponseDto answerResponseDto, @AuthenticationPrincipal UserDetailsImpl userDeta) {
        User user = userDeta.getUser();
        Long answerId = answerService.answerCreate(answerResponseDto,user);
        return answerId;
    }

    // answer 조회
    @GetMapping("/answer/get/{pid}")
    public List<AnswerResponseDto> postGet(@PathVariable Long pid) {
        List<AnswerResponseDto> answerResponseDtos = answerService.answerGet(pid);

        return answerResponseDtos;
    }

    // answer 삭제
    @DeleteMapping("/islogin/answer/delete/{answerId}")
    public void postDelete(@PathVariable Long answerId, @AuthenticationPrincipal UserDetailsImpl userDeta) {
        Long uid = userDeta.getUid();
        answerService.answerDelete(uid, answerId);

    }


    // answer 수정
    @PutMapping("/islogin/answer/edit/{answsrId}")
    public void postEdit(@PathVariable Long answerId, @RequestBody AnswerResponseDto answerResponseDto, @AuthenticationPrincipal UserDetailsImpl userDeta){
        Long uid = userDeta.getUid();
        answerService.answerEdit(answerId, answerResponseDto, uid);

    }




}
