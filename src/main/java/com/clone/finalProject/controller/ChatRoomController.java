package com.clone.finalProject.controller;


import com.clone.finalProject.domain.Fword;
import com.clone.finalProject.dto.ChatMessagedResponseDto;
import com.clone.finalProject.repository.FwordRepository;
import com.clone.finalProject.security.UserDetailsImpl;
import com.clone.finalProject.service.CacheService;
import com.clone.finalProject.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatService chatService;
    private final CacheService cacheService;
    private final FwordRepository fwordRepository;

    // 메인페이지 채널 채팅 내역 조회
    @GetMapping("/chatting/main")
    public List<ChatMessagedResponseDto> chatMainGet() {
        List<ChatMessagedResponseDto> chatMessagedResponseDtoList = chatService.chatMainGet();

        return chatMessagedResponseDtoList;
    }


    // 게시글 페이지 채널 채팅 내역 조회
    @GetMapping("/chatting/{pid}")
    public List<ChatMessagedResponseDto> chatMainPost(@PathVariable Long pid) {
        List<ChatMessagedResponseDto> chatMessagedResponseDtoList = chatService.chatMainPost(pid);

        return chatMessagedResponseDtoList;
    }

    // 귓속말 채팅 내역 조회
    @GetMapping("/islogin/chatting")
    public List<ChatMessagedResponseDto> chatMainUser( @AuthenticationPrincipal UserDetailsImpl userDeta) {
        List<ChatMessagedResponseDto> chatMessagedResponseDtoList = chatService.chatMainUser(userDeta.getUid());

        return chatMessagedResponseDtoList;
    }


    //내장 캐시로 욕설 디비담는 테스트 진행 중
    @GetMapping("/test/pls")
    public HashMap<Integer,String> testpls() {
        HashMap<Integer,String> fowrds = cacheService.getCacheData("key");
        return fowrds;
    }

    // 욕설 test
    @Transactional
    @GetMapping("/test/pls2")
    public void test22(){

//        List<Fword>fwordList = fwordRepository.findAll();
//
//        for (Fword fword : fwordList) {
//            String st= fword.getFWord().trim();
//            fword.update(st);
//        }
//        System.out.println("공백 없애기 완료");

        HashMap<Integer,String> fowrds= cacheService.getCacheData("key");

//        전체 출력문
//        for (Map.Entry<Integer, String> entrySet : fowrds.entrySet()) {
//            System.out.println(entrySet.getKey() + " : " + entrySet.getValue());
//        }


    }


}
