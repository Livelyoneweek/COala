package com.clone.finalProject.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Optional;


@RequiredArgsConstructor
@Repository
public class RedisChatRepository {

    /* Redis CacheKeys */
    public static final String USER_COUNT = "USER_COUNT"; // 채팅룸에 입장한 클라이언트수 저장

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOps;

//

    /* ***채팅방 유저수 조회 */
    public long getUserCount(String destination) {
        return Long.valueOf(
                Optional.ofNullable(valueOps.get(USER_COUNT +"_"+ destination))
                        .orElse("0"));
    }
    /* 채팅방에 입장한 유저수 +1 */
    public long plusUserCount(String destination) {
        return Optional.ofNullable(valueOps.increment(USER_COUNT +"_"+ destination))
                .orElse(0L);
    }

    /* 채팅방에 입장한 유저수 -1 */
    public long minusUserCount(String destination) {
        return Optional.ofNullable(valueOps.decrement(USER_COUNT +"_"+ destination))
                .filter(count -> count > 0)
                .orElse(0L);
    }
}