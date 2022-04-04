package com.clone.finalProject.unit;

import com.clone.finalProject.domain.ChatMessage;
import com.clone.finalProject.domain.ChatRoom;
import com.clone.finalProject.dto.chatMessageDto.ChatMessageDto;
import com.clone.finalProject.dto.chatMessageDto.ChatMessagedResponseDto;
import com.clone.finalProject.repository.ChatMessageRepository;
import com.clone.finalProject.repository.ChatRoomRepository;
import com.clone.finalProject.service.ChatService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GetChatMessageTest {

    @Autowired
    ChatService chatService;

    @Autowired
    ChatMessageRepository chatMessageRepository;

    @Autowired
    ChatRoomRepository chatRoomRepository;

    ChatRoom chatRoom = new ChatRoom();

    public Long uid = 1L;
    public String message = "즐거운주말보내세요";
    public String senderName = "코알라choi";
    public String opposingUserName = "null";
    public String career= "1년차 이내";

    @Test
    @Order(1)
    @DisplayName("메인 채팅 저장")
    void CreateChatMessage() {

        //given
        chatRoom = chatRoomRepository.findByArea("main").orElseThrow(
                ()-> new NullPointerException("chatRoom이 존재하지 않습니다.")
        );


        ChatMessageDto chatMessageDto = ChatMessageDto.builder()
                .uid(uid)
                .message(message)
                .senderName(senderName)
                .opposingUserName(opposingUserName)
                .career(career)
                .status("test")
                .build();

        //when
        chatService.chatSettingMethod(chatMessageDto, "Authorization", chatRoom);

        //then
        ChatMessage chatMessage = chatMessageRepository.findByMessageContains(message);

        assertEquals(uid,chatMessage.getUid());
        assertEquals(message,chatMessage.getMessage());
        assertEquals(senderName,chatMessage.getSenderName());
        assertEquals(opposingUserName,chatMessage.getOpposingUserName());
        assertEquals(career,chatMessage.getCareer());
    }

    @Test
    @Order(2)
    @DisplayName("메인페이지 채널 채팅 내역 조회")
    void getMainMessage(){
        //given

        //when
        List<ChatMessagedResponseDto> chatMessagedResponseDtoList = chatService.getMainMessage();

        //then
        int last = chatMessagedResponseDtoList.size()-1;

        assertEquals(message,chatMessagedResponseDtoList.get(last).getMessage());
        assertEquals(career,chatMessagedResponseDtoList.get(last).getCareer());
        assertEquals(senderName,chatMessagedResponseDtoList.get(last).getSenderName());
        assertEquals(opposingUserName,chatMessagedResponseDtoList.get(last).getOpposingUserName());
    }

//    @Test
//    @Order(3)
//    @DisplayName("test 채팅 삭제")
//    @Transactional
//    void deleteTestMessage(){
//        chatMessageRepository.deleteAllByMessageContainsAndUid("즐거운주말보내세요",1L);
//    }

}
