package com.clone.finalProject.service;

import com.clone.finalProject.domain.AnswerLike;
import com.clone.finalProject.dto.AnswerLikeResponseDto;
import com.clone.finalProject.repository.AnswerLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class AnswerLikeService {

    private final AnswerLikeRepository answerLikeRepository;


    // 답변 채택 시 없으면 생성, 있으면 삭제하거나 실패
    public HashMap<String, String> answerLike(AnswerLikeResponseDto answerLikeResponseDto) {
        Long uid = answerLikeResponseDto.getUid();
        Long pid = answerLikeResponseDto.getPid();
        Long answerId = answerLikeResponseDto.getAnswerId();
        Long answerUid = answerLikeResponseDto.getAnswerUid();

        HashMap<String, String> result = new HashMap<>();

        if (answerLikeRepository.findbyUidAndPid(uid,pid).isPresent()) {
            AnswerLike answerLike = answerLikeRepository.findbyUidAndPid(uid,pid).orElseThrow(
                    ()-> new NullPointerException("answerLike가 존재하지 않습니다.")
            );

            // 답변 채택이 이미 있는데 같은 답변일 때 (채택 취소)
            if(answerId == answerLike.getAnswerId()) {
                answerLikeRepository.deleteByAnswerId(answerId);
                result.put("status","delete");
                // 답변 채택이 이미 있는데 다른 답변일 때는 실패 처리
            } else {
                result.put("status","false");
            }

        } else {
            AnswerLike answerLike = new AnswerLike(answerLikeResponseDto);

            answerLikeRepository.save(answerLike);
            result.put("status","true");
        }

        return result;
    }
}
