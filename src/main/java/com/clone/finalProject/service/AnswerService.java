package com.clone.finalProject.service;

import com.clone.finalProject.domain.*;
import com.clone.finalProject.dto.alarmDto.AlarmResponseDto;
import com.clone.finalProject.dto.answrDto.AnswerResponseDto;
import com.clone.finalProject.dto.answrDto.CreateAnswerResponseDto;
import com.clone.finalProject.dto.commentDto.CommnetResponseDto;
import com.clone.finalProject.exceptionHandler.CustomException;
import com.clone.finalProject.exceptionHandler.ErrorCode;
import com.clone.finalProject.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final AnswerLikeRepository answerLikeRepository;
    private final UserRepository userRepository;
    private final AlarmRepository alarmRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    // answer 생성
    @Transactional
    public CreateAnswerResponseDto createAnswer(AnswerResponseDto answerResponseDto, User user) {
        Long pid = answerResponseDto.getPid();
        Post post = postRepository.findByPid(pid).orElseThrow(
                ()-> new CustomException(ErrorCode.NOT_FOUND_POST)
        );
        Answer answer = new Answer(answerResponseDto, post);
        answerRepository.save(answer);
        log.info("답글 생성 완료 답글 타이틀 : {}", answer.getAnswerTitle());
        log.info("답글 생성 완료 답글 Id : {}", answer.getAnswerId());

        //생성되었을떄 포스트 유저 컬럼으로 Alarm 객체 저장
        Alarm alarm = new Alarm("AnswerCreate",pid,answer.getAnswerId(),post.getUser());
        alarmRepository.save(alarm);

        // 답변 생성 된걸 포스트 주인한테 알림

        String postPageUrl = "/" + "detail" +"/" + pid;
        AlarmResponseDto alarmResponseDto = new AlarmResponseDto("AnswerCreate" , postPageUrl);

        simpMessagingTemplate.convertAndSend("/queue/user" +"/" +post.getUser().getNickname() ,alarmResponseDto);

        CreateAnswerResponseDto createAnswerResponseDto = new CreateAnswerResponseDto(answer, user);
        return createAnswerResponseDto;
    }


    //answer 조회
    public List<AnswerResponseDto> getAnswer(Long pid) {

        List<Answer>answerList = answerRepository.findAllByPost_PidOrderByCreatedAtDesc(pid);

        //답변 리스트 담는 중
        ArrayList<AnswerResponseDto> answerResponseDtos = new ArrayList<>();
        for (Answer answer  : answerList) {
            User user = userRepository.findByUid(answer.getUid()).orElseThrow(
                    ()-> new CustomException(ErrorCode.NOT_FOUND_USER)
            );
            Long answerId = answer.getAnswerId();

            String status = "false";
            if(answerLikeRepository.findByAnswer_AnswerId(answerId).isPresent()){
                status ="true";
            }

            //답변에 댓글 리스트 담는중
            ArrayList<CommnetResponseDto> commnetResponseDtos = new ArrayList<>();
            List<Comment>commentList = commentRepository.findAllByAnswer_answerIdOrderByCreatedAtAsc(answerId);

            for (Comment comment : commentList){

                User commentUser = userRepository.findByUid(comment.getUid()).orElseThrow(
                        ()-> new CustomException(ErrorCode.NOT_FOUND_USER)
                );

                CommnetResponseDto commnetResponseDto = new CommnetResponseDto(commentUser,comment,answerId);
                commnetResponseDtos.add(commnetResponseDto);
            }

            AnswerResponseDto answerResponseDto = new AnswerResponseDto(answer, user, status, commnetResponseDtos);
            answerResponseDtos.add(answerResponseDto);

        }
        return answerResponseDtos;
    }

    //answer 삭제
    @Transactional
    public void deleteAnswer(Long uid, Long answerId) {

        Answer answer = answerRepository.findByAnswerId(answerId).orElseThrow(
                ()-> new CustomException(ErrorCode.NOT_FOUND_ANSWER)
        );

        if (answer.getUid() == uid) {
            //댓글 및 대댓글 추가 시 포스트 밑에 있는 댓글 , 대댓글 부터 삭제 해야 함

            commentRepository.deleteAllByAnswer_answerId(answerId);
            log.info("답변 삭제 전 댓글 삭제");
            
            //
            answerLikeRepository.deleteByAnswer_AnswerId(answerId);
            log.info("답변 삭제 전 답변채택 삭제");

            answerRepository.deleteById(answerId);
            log.info("Answer 삭제 완료 answerId : {}",  answerId);
        }

    }

    //answer 수정
    @Transactional
    public void editAnswer(Long answerId, AnswerResponseDto answerResponseDto, Long uid) {
        log.info("답변 수정 answerId {} ", answerId);
        Answer answer = answerRepository.findByAnswerId(answerId).orElseThrow(
                ()-> new CustomException(ErrorCode.NOT_FOUND_ANSWER)
        );

        if (answer.getUid() == uid) {
            answer.update(answerResponseDto);
            log.info("Answer 수정 완료 answerId :{}", answerId);
        }
    }
}
