package com.clone.finalProject.controller;

import com.clone.finalProject.dto.RedisDto;
import com.clone.finalProject.repository.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired RedisRepository redisRepository;

    @GetMapping("/test/get")
    public String get(){

        return redisRepository.findAll().toString();

    }

    @PostMapping("/test/post")
    public String set(@RequestBody RedisDto redisDto){

        return redisRepository.save(redisDto).toString();
    }

}
