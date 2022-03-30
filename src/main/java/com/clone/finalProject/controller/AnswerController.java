package com.clone.finalProject.controller;

import com.clone.finalProject.domain.User;
import com.clone.finalProject.dto.answrDto.AnswerResponseDto;
import com.clone.finalProject.dto.answrDto.CreateAnswerResponseDto;
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
    public CreateAnswerResponseDto createAnswer(@RequestBody AnswerResponseDto answerResponseDto, @AuthenticationPrincipal UserDetailsImpl userDeta) {
        User user = userDeta.getUser();
        CreateAnswerResponseDto createAnswerResponseDto = answerService.createAnswer(answerResponseDto,user);
        return createAnswerResponseDto;
    }

    // answer 조회
    @GetMapping("/answer/get/{pid}")
    public List<AnswerResponseDto> getAnswer(@PathVariable Long pid) {
        List<AnswerResponseDto> answerResponseDtos = answerService.getAnswer(pid);

        return answerResponseDtos;
    }

    // answer 삭제
    @DeleteMapping("/islogin/answer/delete/{answerId}")
    public void deleteAnswer(@PathVariable Long answerId, @AuthenticationPrincipal UserDetailsImpl userDeta) {
        Long uid = userDeta.getUid();
        answerService.deleteAnswer(uid, answerId);

    }


    // answer 수정
    @PutMapping("/islogin/answer/edit/{answerId}")
    public void editAnswer(@PathVariable Long answerId, @RequestBody AnswerResponseDto answerResponseDto, @AuthenticationPrincipal UserDetailsImpl userDeta){
        Long uid = userDeta.getUid();
        answerService.editAnswer(answerId, answerResponseDto, uid);

    }

}
