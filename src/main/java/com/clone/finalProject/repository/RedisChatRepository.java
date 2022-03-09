package com.clone.finalProject.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Optional;


@RequiredArgsConstructor
@Repository
public class RedisChatRepository {

    /* Redis CacheKeys */
    private static final String CHAT_ROOMS = "CHAT_ROOM"; // 채팅룸 저장
    public static final String USER_COUNT = "USER_COUNT"; // 채팅룸에 입장한 클라이언트수 저장
    public static final String USER_MCOUNT = "USER_MCOUNT"; // 채팅룸 퇴장
    public static final String USER_TCOUNT = "USER_MCOUNT"; // 총 인원
    public static final String ENTER_INFO = "ENTER_INFO"; // 채팅룸에 입장한 클라이언트의 sessionId와 채팅룸 id를 맵핑한 정보 저장
    public static final String USER_NICKNAME = "USER_NICKNAME"; // 채팅룸에 입장한 유저 닉네임

//    @Resource(name = "redisTemplate")
//    private HashOperations<String, String, ChatRoomDto> hashOpsChatRoom;
//    @Resource(name = "redisTemplate")
//    private HashOperations<String, String, ChatRoomDto> hashOpsChatList;
    @Resource(name = "redisTemplate")
    private HashOperations<String, String, String> hashOpsEnterInfo;
    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOps;

//

    /* ***채팅방 유저수 조회 ======수정 필요=======*/
    public long getUserCount(String destination) {
        return Long.valueOf(
                Optional.ofNullable(valueOps.get(USER_TCOUNT +"_"+ destination))
                        .orElse("0"));
    }
    /* 채팅방에 입장한 유저수 +1 */
    public long plusUserCount(String destination) {
        return Optional.ofNullable(valueOps.increment(USER_COUNT +"_"+ destination))
                .orElse(0L);
    }

    /* 채팅방에 입장한 유저수 -1 */
    public long minusUserCount(String destination) {
        return Optional.ofNullable(valueOps.decrement(USER_MCOUNT + destination))
                .filter(count -> count > 0)
                .orElse(0L);
    }

    /* 유저 세션으로 입장해 있는 채팅방 ID 조회 */
    public String getUserEnterRoomId(String sessionId) {
        return hashOpsEnterInfo.get(ENTER_INFO, sessionId);
    }

}