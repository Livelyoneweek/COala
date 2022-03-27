package com.clone.finalProject.controller;

import com.clone.finalProject.domain.User;
import com.clone.finalProject.dto.postDto.PostRequestDto;
import com.clone.finalProject.dto.postDto.PostResponseDto;
import com.clone.finalProject.security.UserDetailsImpl;
import com.clone.finalProject.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // post 생성
    @PostMapping("/islogin/post/create")
    public Long postCreate(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDeta) {
        log.info("img : {}", postRequestDto.getPostImg());
        log.info("img : {}",postRequestDto.getPostImg());

        User user = userDeta.getUser();
        Long pid = postService.postCreate(postRequestDto,user);

        return pid;
    }

    // post 조회 (답변채택)
    @GetMapping("/post/get/check")
    public List<PostResponseDto> postCheckGet(@RequestParam("page") int page,
                                              @RequestParam("size") int size,
                                              @RequestParam("sortBy")String sortBy,
                                              @RequestParam("isAsc")boolean isAsc) {

        page = page -1;
        List<PostResponseDto> postResponseDtos = postService.postCheckGet(page, size, sortBy, isAsc);

        return postResponseDtos;
    }

    // post 조회 (답변대기)
    @GetMapping("/post/get/nocheck")
    public List<PostResponseDto> postWaitGet(@RequestParam("page") int page,
                                             @RequestParam("size") int size,
                                             @RequestParam("sortBy")String sortBy,
                                             @RequestParam("isAsc")boolean isAsc) {
        page = page -1;
        List<PostResponseDto> postResponseDtos = postService.postWaitGet(page, size, sortBy, isAsc);

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
    @PutMapping("/islogin/post/edit/{pid}")
    public Long postEdit(@PathVariable Long pid, @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDeta){
        Long uid = userDeta.getUid();
        postService.postEdit(pid, postRequestDto, uid);
        return pid;
    }

    //post 게시글 상세 조회
    @GetMapping("/post/get/detail/{pid}")
    public PostResponseDto detailPostGet(@PathVariable Long pid) {
        PostResponseDto postResponseDto = postService.detailPostGet(pid);

        return postResponseDto;
    }

    // 타이틀로 게시글 검색
    @GetMapping("/post/get/title/{postTitle}")
    public List<PostResponseDto> postTitleGet(@PathVariable String postTitle,
                                              @RequestParam("page") int page,
                                              @RequestParam("size") int size,
                                              @RequestParam("sortBy")String sortBy,
                                              @RequestParam("isAsc")boolean isAsc) {
        page = page -1;
        List<PostResponseDto> postResponseDtoList = postService.postTitleGet(postTitle,page, size, sortBy, isAsc);

        return postResponseDtoList;
    }

    // 카테고리로 게시글 검색
    @GetMapping("/post/get/category/{category}")
    public List<PostResponseDto> postCategoryGet(@PathVariable String category,
                                                 @RequestParam("page") int page,
                                                 @RequestParam("size") int size,
                                                 @RequestParam("sortBy")String sortBy,
                                                 @RequestParam("isAsc")boolean isAsc) {
        log.info("category : {}", category);
        page = page -1;
        List<PostResponseDto> postResponseDtoList = postService.postCategoryGet(category,page, size, sortBy, isAsc);

        return postResponseDtoList;
    }

    // post 관심글 조회
    @GetMapping("/islogin/post/get/like")
    public List<PostResponseDto> postLikeGet(@AuthenticationPrincipal UserDetailsImpl userDeta,
                                             @RequestParam("page") int page,
                                             @RequestParam("size") int size,
                                             @RequestParam("sortBy")String sortBy,
                                             @RequestParam("isAsc")boolean isAsc) {
        Long uid = userDeta.getUid();
        page = page -1;
        List<PostResponseDto> postResponseDtos = postService.postLikeGet(uid,page, size, sortBy, isAsc);

        return postResponseDtos;
    }


}
