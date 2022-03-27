package com.clone.finalProject.service;

import com.clone.finalProject.domain.*;
import com.clone.finalProject.dto.alarmDto.AlarmResponseDto;
import com.clone.finalProject.dto.answrDto.AnswerLikeResponseDto;
import com.clone.finalProject.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
@Service
public class AnswerLikeService {

    private final AnswerLikeRepository answerLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AlarmRepository alarmRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final AnswerRepository answerRepository;
    private final PointRepository pointRepository;


    // 답변 채택 시 없으면 생성, 있으면 삭제하거나 실패
    @Transactional
    public HashMap<String, String> answerLike(AnswerLikeResponseDto answerLikeResponseDto) {
        Long uid = answerLikeResponseDto.getUid();
        log.info("uid : {}", uid);
        Long pid = answerLikeResponseDto.getPid();
        log.info("pid : {}", pid);
        Long answerId = answerLikeResponseDto.getAnswerId();
        log.info("answerId : {}", answerId);
        Long answerUid = answerLikeResponseDto.getAnswerUid();
        log.info("answerUid : {}", answerUid);

        Answer answer = answerRepository.findByAnswerId(answerId).orElseThrow(
                ()-> new NullPointerException("answer 존재하지 않습니다.")
        );

        HashMap<String, String> result = new HashMap<>();

        if (answerLikeRepository.findByUidAndPid(uid,pid).isPresent()) {
            AnswerLike answerLike = answerLikeRepository.findByUidAndPid(uid,pid).orElseThrow(
                    ()-> new NullPointerException("answerLike가 존재하지 않습니다.")
            );

            // 답변 채택이 이미 있는데 같은 답변일 때 (채택 취소) ,, 채택취소 못하게 수정
            if(answerId.equals(answerLike.getAnswer().getAnswerId()) ) {
//                answerLikeRepository.deleteByAnswerId(answerId);
//
//                Post post = postRepository.findById(pid).orElseThrow(
//                        ()-> new NullPointerException("post가 존재하지 않습니다.")
//                );
//                post.checkUpdate("noCheck");

                result.put("status","false");

//                // 포인트 점수 추가 업데이트
//                User user =userRepository.findByUid(answerUid).orElseThrow(
//                        ()-> new NullPointerException("User가 존재하지 않습니다.")
//                );
//                Long point =user.getPoint();
//                Long weekPoint = user.getWeekPoint();
//                Long monthPoint = user.getMonthPoint();
//
//                point -= 50;
//                weekPoint -= 50;
//                monthPoint -= 50;
//
//                user.userPointUpdate(point, weekPoint, monthPoint);
//                userRepository.save(user);


                // 답변 채택이 이미 있는데 다른 답변일 때는 실패 처리
            } else {
                result.put("status","false");
            }

            //답변 채택 없어서 생성함
        } else {
            AnswerLike answerLike = new AnswerLike(answerLikeResponseDto, answer);

            answerLikeRepository.save(answerLike);

            Post post = postRepository.findById(pid).orElseThrow(
                    ()-> new NullPointerException("Post가 존재하지 않습니다.")
            );
            post.checkUpdate("selection");
            result.put("status","true");

            // 포인트 점수 추가 업데이트
            User user =userRepository.findByUid(answerUid).orElseThrow(
                    ()-> new NullPointerException("User가 존재하지 않습니다.")
            );
            Long point =user.getPoint();
            Long weekPoint = user.getWeekPoint();
            Long monthPoint = user.getMonthPoint();

            point += 50;
            weekPoint += 50;
            monthPoint += 50;

            user.userPointUpdate(point, weekPoint, monthPoint);
            userRepository.save(user);

            Point pointEntity = new Point(post.getUser().getUid(), user);
            pointRepository.save(pointEntity);

            //생성되었을떄 포스트 유저 컬럼으로 Alarm 객체 저장
            Alarm alarm = new Alarm("AnswerChoose",pid,answerId ,post.getUser());
            alarmRepository.save(alarm);

            // 답변 채택된걸 답변 주인한테 알림
            String postPageUrl = "/" + "detail" +"/" + pid;
            AlarmResponseDto alarmResponseDto = new AlarmResponseDto("AnswerChoose" , postPageUrl);
            simpMessagingTemplate.convertAndSend("/queue/user"+"/"+userRepository.findByUid(answerUid).get().getNickname() ,alarmResponseDto);

        }

        return result;
    }
}
