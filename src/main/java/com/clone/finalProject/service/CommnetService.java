package com.clone.finalProject.service;

import com.clone.finalProject.domain.Answer;
import com.clone.finalProject.domain.Comment;
import com.clone.finalProject.domain.User;
import com.clone.finalProject.dto.commentDto.CommnetResponseDto;
import com.clone.finalProject.exceptionHandler.CustomException;
import com.clone.finalProject.exceptionHandler.ErrorCode;
import com.clone.finalProject.repository.AnswerRepository;
import com.clone.finalProject.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommnetService {

    private final AnswerRepository answerRepository;
    private final CommentRepository commentRepository;


    //commnet 생성
    public Long commentCreate(CommnetResponseDto commnetResponseDto, User user) {
        Answer answer = answerRepository.findByAnswerId(commnetResponseDto.getAnswerId()).orElseThrow(
                ()-> new CustomException(ErrorCode.NOT_FOUND_COMMENT)
        );

        Comment comment = new Comment(commnetResponseDto,answer);
        commentRepository.save(comment);
        log.info("commnet 생성 완료");

        return comment.getCommentId();

    }

    //commnet 삭제
    public void commentDelete(Long uid, Long commentId) {
        Comment comment = commentRepository.findByCommentId(commentId).orElseThrow(
                ()-> new CustomException(ErrorCode.NOT_FOUND_COMMENT)
        );

        if(comment.getUid() == uid){

            commentRepository.deleteById(commentId);
            log.info("comment 삭제 완료 commentId : {}",  commentId);
        }

    }
    //commnet 수정
    @Transactional
    public void commentEdit(Long commentId, CommnetResponseDto commnetResponseDto, Long uid) {
        Comment comment = commentRepository.findByCommentId(commentId).orElseThrow(
                ()-> new CustomException(ErrorCode.NOT_FOUND_COMMENT)
        );
        if(comment.getUid() == uid){

            comment.update(commnetResponseDto);
            log.info("comment 수정 완료 commentId : {}",  commentId);
        }


    }
}
