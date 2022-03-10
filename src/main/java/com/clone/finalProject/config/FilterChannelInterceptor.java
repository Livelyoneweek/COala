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
//                System.out.println("===========================================");
//                System.out.println("full message:" + message);
//                System.out.println("full channel:" + channel);
//                System.out.println("===========================================");
            String destination = chatService.getRoomId(
                    Optional.ofNullable(
                            (String) message.getHeaders()
                                    .get("simpDestination")).orElse("error"));
            System.out.println("=== destination : " + destination + "===");
            redisChatRepository.plusUserCount(destination);
                /*
            채팅방에 들어온 클라이언트 sessionId를 roomId와 맵핑해 놓는다.
            (나중에 특정 세션이 어떤 채팅방에 들어가 있는지 알기 위함)
            */
            String sessionId = (String) message.getHeaders().get("simpSessionId");
            System.out.println("===sessionId : " + sessionId+ "===");

        }else if(StompCommand.DISCONNECT == headerAccessor.getCommand()){
            // 유저가 Websocket으로 disconnect() 를 한 뒤 호출됨 or 세션이 끊어졌을 때 발생함(페이지 이동~ 브라우저 닫기 등)
            System.out.println("===========================================");
                System.out.println("full message:" + message);
                System.out.println("full channel:" + channel);
                System.out.println("===========================================");
            String destination = "greetings";
            System.out.println("===DISCONNECT : " + destination + "===");

            /* 채팅방의 인원수를 -1한다. */
            redisChatRepository.minusUserCount(destination);
            System.out.println("유저 disconnetct");
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
