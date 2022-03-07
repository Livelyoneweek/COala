package com.clone.finalProject.controller;

import com.clone.finalProject.domain.User;
import com.clone.finalProject.dto.PostRequestDto;
import com.clone.finalProject.dto.PostResponseDto;
import com.clone.finalProject.security.UserDetailsImpl;
import com.clone.finalProject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // post 생성
    @PostMapping("/islogin/post/write")
    public Long postCreate(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDeta) {
        System.out.println("img : " + postRequestDto.getPostImg());
        User user = userDeta.getUser();
        Long pid = postService.postCreate(postRequestDto,user);

        return pid;
    }

    // post 조회 (답변채택)
    @GetMapping("/post/get/check")
    public List<PostResponseDto> postGet() {
        List<PostResponseDto> postResponseDtos = postService.postGet();

        return postResponseDtos;
    }

    // post 조회 (답변대기)
    @GetMapping("/post/get/nocheck")
    public List<PostResponseDto> postGet2() {
        List<PostResponseDto> postResponseDtos = postService.postGet2();

        return postResponseDtos;
    }

    // post 삭제
    @DeleteMapping("/islogin/post/delete/{pid}")
    public Long postDelete(@PathVariable Long pid, @AuthenticationPrincipal UserDetailsImpl userDeta) {
        Long uid = userDeta.getUid();

        postService.postDelete(pid, uid);
        return pid;
    }


    // post 수정
    @PutMapping("/islogin/post/revice/{pid}")
    public Long postEdit(@PathVariable Long pid, @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDeta){

        Long uid = userDeta.getUid();

        postService.postEdit(pid, postRequestDto, uid);

        return pid;
    }

    //post 게시글 상세 조회
    @GetMapping("/post/detailget/{pid}")
    public PostResponseDto detailPostGet(@PathVariable Long pid) {
        PostResponseDto postResponseDto = postService.detailPostGet(pid);

        return postResponseDto;
    }

    // 태그로 게시글 검색
    @GetMapping("/islogin/post/search")
    public List<PostResponseDto> postTitleGet(@RequestBody PostResponseDto postResponseDto2) {
        List<PostResponseDto> postResponseDtoList = postService.postTitleGet(postResponseDto2);

        return postResponseDtoList;
    }

}
