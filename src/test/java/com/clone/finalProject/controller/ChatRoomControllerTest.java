package com.clone.finalProject.controller;

//import com.clone.finalProject.domain.ChatMessage;
//import com.clone.finalProject.domain.ChatRoom;
//import com.clone.finalProject.dto.chatMessageDto.ChatMessageDto;
//import com.clone.finalProject.dto.chatMessageDto.ChatMessagedResponseDto;
//import com.clone.finalProject.repository.ChatMessageRepository;
//import com.clone.finalProject.repository.ChatRoomRepository;
//import com.clone.finalProject.service.ChatService;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.transaction.Transactional;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//class ChatRoomControllerTest {
//
//    @Autowired
//    ChatService chatService;
//
//    @Autowired
//    ChatMessageRepository chatMessageRepository;
//
//    @Autowired
//    ChatRoomRepository chatRoomRepository;
//
//    ChatRoom chatRoom = new ChatRoom();
//
//    public Long uid;
//    public String message;
//    public String senderName;
//    public String opposingUserName;
//    public String career;
//
//    @Test
//    @Order(1)
//    @DisplayName("메인 채팅 저장")
//    void CreateChatMessage() {
//
//        //given
//        chatRoom = chatRoomRepository.findByArea("main").orElseThrow(
//                ()-> new NullPointerException("chatRoom이 존재하지 않습니다.")
//        );
//
//        uid = 1L;
//        message = "채팅테스트입니다채팅테스트입니다";
//        senderName = "sender";
//        opposingUserName = "null";
//        career = "1년차 이내";
//
//        ChatMessageDto chatMessageDto = ChatMessageDto.builder()
//                .uid(uid)
//                .message(message)
//                .senderName(senderName)
//                .opposingUserName(opposingUserName)
//                .career(career)
//                .status("test")
//                .build();
//
//        //when
//        chatService.chatSettingMethod(chatMessageDto, "Authorization", chatRoom);
//
//        //then
//        ChatMessage chatMessage = chatMessageRepository.findByMessageContains(message);
//
//        assertEquals(uid,chatMessage.getUid());
//        assertEquals(message,chatMessage.getMessage());
//        assertEquals(senderName,chatMessage.getSenderName());
//        assertEquals(opposingUserName,chatMessage.getOpposingUserName());
//        assertEquals(career,chatMessage.getCareer());
//    }
//
//    @Test
//    @Order(2)
//    @DisplayName("메인페이지 채널 채팅 내역 조회")
//    void getMainMessage(){
//        //given
//
//        //when
//        List<ChatMessagedResponseDto> chatMessagedResponseDtoList = chatService.getMainMessage();
//
//        //then
//        int last = chatMessagedResponseDtoList.size()-1;
//
//        assertEquals(message,chatMessagedResponseDtoList.get(last).getMessage());
//        assertEquals(career,chatMessagedResponseDtoList.get(last).getCareer());
//        assertEquals(senderName,chatMessagedResponseDtoList.get(last).getSenderName());
//        assertEquals(opposingUserName,chatMessagedResponseDtoList.get(last).getOpposingUserName());
//    }
//
//    @Test
//    @Order(3)
//    @DisplayName("test 채팅 삭제")
//    @Transactional
//    void deleteTestMessage(){
//        chatMessageRepository.deleteByMessageContains(message);
//
//    }
//}