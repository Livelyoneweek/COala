package com.clone.finalProject.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@RedisHash("RedisDto")
public class RedisDto {

    @Id
    String id;
    String name;
    String age;

    List<String> tag;
}
