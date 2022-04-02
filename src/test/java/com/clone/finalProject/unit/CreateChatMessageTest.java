package com.clone.finalProject.unit;

import com.clone.finalProject.domain.ChatMessage;
import com.clone.finalProject.domain.ChatRoom;
import com.clone.finalProject.dto.chatMessageDto.ChatMessageDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class CreateChatMessageTest {
    ChatRoom chatRoom = new ChatRoom();

    @Nested
    @DisplayName("ChatRoom 생성 test")
    class CreateChatroom{

        @Test
        @Order(1)
        @DisplayName("chatroom 생성 1")
        void creatChatroom(){

            //given
            Long chatRoomId =350L;
            Long uid = 350L;
            Long pid = 350L;
            String area = "Main";

            //when
            chatRoom = new ChatRoom(area);

            //then
            assertNull(chatRoom.getChatRoomId());
            assertEquals(uid, chatRoom.getUid());
            assertEquals(area, chatRoom.getArea());

        }

        @Test
        @Order(2)
        @DisplayName("chatroom 생성 2")
        void creatChatroom2(){

            //given
            Long chatRoomId =350L;
            Long uid = 350L;
            Long pid = 350L;
            String area = "Main";

            //when
            ChatRoom chatRoom2 = new ChatRoom(area,pid);

            //then
            assertNull(chatRoom2.getChatRoomId());
            assertEquals(pid, chatRoom2.getPid());
            assertEquals(area, chatRoom2.getArea());

        }

    }

    @Nested
    @DisplayName("Chatmessage 생성 test")
    class CreateChatmessage{

        @Test
        @Order(3)
        @DisplayName("chatmessage 생성 1")
        void createChatmessage() {

            //given
            Long chatMessageId =350L;
            Long uid = 350L;
            String message = "안녕하세요";
            String senderName = "senser";
            String opposingUserName = "null";
            String career = "1년차 이내";

            ChatMessageDto chatMessageDto = ChatMessageDto.builder()
                    .message(message)
                    .senderName(senderName)
                    .opposingUserName(opposingUserName)
                    .career(career)
                    .build();

            //when
            ChatMessage chatMessage = new ChatMessage(uid, chatMessageDto,chatRoom);

            //then
            assertNull(chatMessage.getChatMessageId());
            assertEquals(uid,chatMessage.getUid());
            assertEquals(message,chatMessage.getMessage());
            assertEquals(senderName,chatMessage.getSenderName());
            assertEquals(opposingUserName,chatMessage.getOpposingUserName());
            assertEquals(chatRoom,chatMessage.getChatRoom());
            assertEquals(career, chatMessage.getCareer());

        }

    }

}