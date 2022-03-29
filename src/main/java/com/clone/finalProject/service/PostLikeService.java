package com.clone.finalProject.service;

import com.clone.finalProject.domain.Post;
import com.clone.finalProject.domain.PostLike;
import com.clone.finalProject.domain.User;
import com.clone.finalProject.dto.answrDto.AnswerLikeResponseDto;
import com.clone.finalProject.exceptionHandler.CustomException;
import com.clone.finalProject.exceptionHandler.ErrorCode;
import com.clone.finalProject.repository.PostLikeRepository;
import com.clone.finalProject.repository.PostRepository;
import com.clone.finalProject.repository.PostTagsRepository;
import com.clone.finalProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostTagsRepository postTagsRepository;


    //관심 등록 할 때 있으면 삭제 없으면 생성
    public HashMap<String, String> postLikeCreate(AnswerLikeResponseDto answerLikeResponseDto) {

        Long uid =answerLikeResponseDto.getUid();
        Long pid = answerLikeResponseDto.getPid();

        HashMap<String, String> result = new HashMap<>();

        // 있는 경우 삭제
        if(postLikeRepository.findByUser_UidAndPost_Pid(uid,pid).isPresent()){
            Long postLikeId = postLikeRepository.findByUser_UidAndPost_Pid(uid,pid).get().getPostLikeId();
            postLikeRepository.deleteById(postLikeId);

            result.put("postLike","false");

        //없으니 생성
        } else {
            User user = userRepository.findById(uid).orElseThrow(
                    ()-> new CustomException(ErrorCode.NOT_FOUND_USER)
            );
            Post post = postRepository.findById(pid).orElseThrow(
                    ()-> new CustomException(ErrorCode.NOT_FOUND_POST)
            );

            PostLike postLike = new PostLike(user, post);
            postLikeRepository.save(postLike);

            result.put("postLike","true");

        }
        return result;
    }


}
