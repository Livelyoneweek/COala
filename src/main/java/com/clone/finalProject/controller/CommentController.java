package com.clone.finalProject.controller;

import com.clone.finalProject.domain.User;
import com.clone.finalProject.dto.AnswerResponseDto;
import com.clone.finalProject.dto.CommnetResponseDto;
import com.clone.finalProject.security.UserDetailsImpl;
import com.clone.finalProject.service.CommnetService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommnetService commnetService;

    //commnet 생성
    @PostMapping("/islogin/comment/write")
    public Long commentCreate(@RequestBody CommnetResponseDto commnetResponseDto, @AuthenticationPrincipal UserDetailsImpl userDeta) {
        System.out.println("댓글 작성 진행");
        System.out.println(" 댓글 title : " +commnetResponseDto.getCommentTitle());

        User user = userDeta.getUser();
        Long commentId = commnetService.commentCreate(commnetResponseDto, user);

        return commentId;
    }

    // commnet 삭제
    @DeleteMapping("/islogin/comment/delete/commentId")
    public void postDelete(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDeta) {
        Long uid = userDeta.getUid();
        commnetService.commentDelete(uid, commentId);

    }

    // commnet 수정
    @PutMapping("/islogin/comment/revice/commentId")
    public void postEdit(@PathVariable Long commentId, @RequestBody CommnetResponseDto commnetResponseDto, @AuthenticationPrincipal UserDetailsImpl userDeta){
        Long uid = userDeta.getUid();
        commnetService.commentEdit(commentId, commnetResponseDto, uid);

    }



}
