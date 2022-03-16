package com.clone.finalProject.config;


import com.clone.finalProject.repository.ChatRoomRepository;
import com.clone.finalProject.repository.RedisChatRepository;
import com.clone.finalProject.security.jwt.JwtDecoder;
import com.clone.finalProject.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
//@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class FilterChannelInterceptor implements ChannelInterceptor {

    private final JwtDecoder jwtDecoder;
    private final ChatService chatService;
    private final RedisChatRepository redisChatRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);


        if (StompCommand.CONNECT == headerAccessor.getCommand()) {
                // 유저가 Websocket으로 connect()를 한 뒤 호출됨
                System.out.println("유저 connect 완료");

        }else if(StompCommand.SUBSCRIBE == headerAccessor.getCommand()){

            String destination = chatService.getRoomId(
                    Optional.ofNullable(
                                    (String) message.getHeaders()
                                            .get("simpDestination"))
                            .orElse("error"));

            String sessionId = (String) message.getHeaders()
                    .get("simpSessionId");

            redisChatRepository.setUserEnterInfo(sessionId, destination);

            System.out.println("=== SUBSCRIBE sessionId : " + sessionId);
            System.out.println(("=== SUBSCRIBE destination : " + destination));

            /* 채팅방의 인원수를 +1한다. */
            redisChatRepository.plusUserCount(destination);




        }else if(StompCommand.DISCONNECT == headerAccessor.getCommand()){
            System.out.println("유저 disconnetct");

            String destination = (String) message.getHeaders()
                    .get("simpSessionId");

            String roomId = redisChatRepository.getUserEnterRoomId(destination);

            /* 채팅방의 인원수를 -1한다. */
            redisChatRepository.minusUserCount(roomId);
            System.out.println("=== DISCONNECT sessionId : " + destination);
            System.out.println(("=== DISCONNECT destination : " + destination));
            /* 퇴장한 클라이언트의 roomId 맵핑 정보를 삭제한다. */
            redisChatRepository.removeUserEnterInfo(destination);


        }

//        System.out.println("auth:" + headerAccessor.getNativeHeader("Authorization"));


////        System.out.println(headerAccessor.getHeader("nativeHeaders").getClass());
//        if (StompCommand.CONNECT.equals(headerAccessor.getCommand())) {
//            String jwtToken = headerAccessor.getFirstNativeHeader("Authorization").substring(7);
//
////            System.out.println("juwtToken : " + jwtToken);
//
//            jwtDecoder.isValidToken(jwtToken);
//
//            System.out.println("msg: " + "토큰인증완료?");
//            System.out.println("=================================================================================");
//        }


        //throw new MessagingException("no permission! ");
        return message;
    }
}
