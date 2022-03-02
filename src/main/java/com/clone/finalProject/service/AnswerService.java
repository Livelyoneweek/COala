package com.clone.finalProject.service;

import com.clone.finalProject.domain.Answer;
import com.clone.finalProject.domain.Comment;
import com.clone.finalProject.domain.Post;
import com.clone.finalProject.domain.User;
import com.clone.finalProject.dto.AnswerResponseDto;
import com.clone.finalProject.dto.CommnetResponseDto;
import com.clone.finalProject.dto.PostRequestDto;
import com.clone.finalProject.dto.PostResponseDto;
import com.clone.finalProject.repository.AnswerRepository;
import com.clone.finalProject.repository.CommentRepository;
import com.clone.finalProject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    // answer 생성
    public Long answerCreate(AnswerResponseDto answerResponseDto, User user) {
        Long pid = answerResponseDto.getPid();
        Post post = postRepository.findByPid(pid).orElseThrow(
                ()-> new NullPointerException("post가 존재하지 않습니다.")
        );
        Answer answer = new Answer(answerResponseDto, post);
        answerRepository.save(answer);
        System.out.println("답글 생성 완료 답글 타이틀 : "  );
        return answer.getAnswerId();
    }


    //answer 조회
    public List<AnswerResponseDto> answerGet() {
        List<Answer>answerList = answerRepository.findAllByOrderByCreatedAtDesc();

        //답변 리스트 담는 중
        ArrayList<AnswerResponseDto> answerResponseDtos = new ArrayList<>();
        for (Answer answer  : answerList) {
            Long uid = answer.getPost().getUser().getUid();
            Long pid = answer.getPost().getPid();
            Long answerId = answer.getAnswerId();

            //답변에 댓글 리스트 담는중
            ArrayList<CommnetResponseDto> commnetResponseDtos = new ArrayList<>();
            List<Comment>commentList = commentRepository.findAllByAnswer_answerIdOrderByCreatedAtAsc(answerId);
            for (Comment comment : commentList){
                CommnetResponseDto commnetResponseDto = new CommnetResponseDto(comment);
                commnetResponseDtos.add(commnetResponseDto);
            }

            AnswerResponseDto answerResponseDto = new AnswerResponseDto(answer, uid, commnetResponseDtos);
            answerResponseDtos.add(answerResponseDto);

        }
        return answerResponseDtos;
    }

    //answer 삭제
    @Transactional
    public void answerDelete(Long uid, Long answerId) {

        Answer answer = answerRepository.findByAnswerId(answerId).orElseThrow(
                ()-> new NullPointerException("Answer 가 존재하지 않습니다.")
        );

        if (answer.getPost().getUser().getUid() == uid) {
            //댓글 및 대댓글 추가 시 포스트 밑에 있는 댓글 , 대댓글 부터 삭제 해야 함

            commentRepository.deleteallByAnswer_answerId(answerId);
            System.out.println("답변 삭제 전 댓글 삭제");

            answerRepository.deleteById(answerId);
            System.out.println("Answer 삭제 완료 answerId : " + answerId);
        }

    }

    //answer 수정
    @Transactional
    public void answerEdit(Long answerId, AnswerResponseDto answerResponseDto, Long uid) {

        Answer answer = answerRepository.findByAnswerId(answerId).orElseThrow(
                ()-> new NullPointerException("Answer가 존재하지 않습니다.")
        );

        if (answer.getPost().getUser().getUid() == uid) {
            answer.update(answerResponseDto);
            System.out.println("Answer 수정 완료 answerId :"  + answerId);
        }
    }
}
