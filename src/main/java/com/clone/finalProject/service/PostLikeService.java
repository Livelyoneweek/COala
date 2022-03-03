package com.clone.finalProject.service;

import com.clone.finalProject.domain.Post;
import com.clone.finalProject.domain.PostLike;
import com.clone.finalProject.domain.User;
import com.clone.finalProject.dto.AnswerLikeResponseDto;
import com.clone.finalProject.repository.PostLikeRepository;
import com.clone.finalProject.repository.PostRepository;
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


    // 답변 채택 시 있으면 삭제 없으면 생성
    public HashMap<String, String> postLike(AnswerLikeResponseDto answerLikeResponseDto) {

        Long uid =answerLikeResponseDto.getUid();
        Long pid = answerLikeResponseDto.getPid();

        HashMap<String, String> result = new HashMap<>();

        if(postLikeRepository.findByUser_UidAndPost_Pid(uid,pid).isPresent()){
            Long postLikeId = postLikeRepository.findByUser_UidAndPost_Pid(uid,pid).get().getPostLikeId();
            postLikeRepository.deleteById(postLikeId);

            result.put("postLike","false");

        } else {
            User user = userRepository.findById(uid).orElseThrow(
                    ()-> new NullPointerException("User가 존재하지 않습니다.")
            );
            Post post = postRepository.findById(pid).orElseThrow(
                    ()-> new NullPointerException("Post가 존재하지 않습니다.")
            );

            PostLike postLike = new PostLike(user, post);
            postLikeRepository.save(postLike);

            result.put("postLike","true");

        }

        return result;

    }
}
