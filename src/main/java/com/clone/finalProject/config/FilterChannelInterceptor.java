package com.clone.finalProject.config;


import com.clone.finalProject.security.jwt.JwtDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class FilterChannelInterceptor implements ChannelInterceptor {

    private final JwtDecoder jwtDecoder;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        System.out.println("full message:" + message);
        switch (headerAccessor.getCommand()) {
            case CONNECT:
                // 유저가 Websocket으로 connect()를 한 뒤 호출됨
                System.out.println("유저 connect 완료");
                break;
            case DISCONNECT:
                // 유저가 Websocket으로 disconnect() 를 한 뒤 호출됨 or 세션이 끊어졌을 때 발생함(페이지 이동~ 브라우저 닫기 등)
                System.out.println("유저 disconnetct");
                break;
            default:
                break;
        }

        System.out.println("auth:" + headerAccessor.getNativeHeader("Authorization"));

        System.out.println(headerAccessor.getHeader("nativeHeaders").getClass());
        if (StompCommand.CONNECT.equals(headerAccessor.getCommand())) {
            String jwtToken = headerAccessor.getFirstNativeHeader("Authorization").substring(7);

            System.out.println("juwtToken : " + jwtToken);

            jwtDecoder.isValidToken(jwtToken);

            System.out.println("msg: " + "토큰인증완료?");
        }


        //throw new MessagingException("no permission! ");
        return message;
    }
}
