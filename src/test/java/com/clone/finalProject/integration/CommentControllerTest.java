package com.clone.finalProject.integration;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.clone.finalProject.dto.commentDto.CommnetResponseDto;
import com.clone.finalProject.service.CommnetService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Date;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentControllerTest {

    @Autowired
    private CommnetService commnetService;

    @Autowired
    private WebTestClient webTestClient;


    String token = JWT.create()
            .withIssuer("sparta")
            .withClaim("USER_NAME", "choi123")
            // 토큰 만료 일시 = 현재 시간 + 토큰 유효기간)
            .withClaim("EXPIRED_DATE", new Date(System.currentTimeMillis() +60*60*24*3))
            .sign(Algorithm.HMAC256("jwt_secret_!@#$%"));

    @Test
    @Order(1)
    @DisplayName("커맨드생성하기")
    void createComment(){
        CommnetResponseDto commnetResponseDto = CommnetResponseDto.builder()
                .comment("안녕하세요")
                .uid(1L)
                .pid(22L)
                .answerId(20L)
                .build();

        webTestClient.post().uri("/islogin/comment/create")
                .header(HttpHeaders.AUTHORIZATION, "BEARER "+token)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(commnetResponseDto)
                .exchange()
                .expectStatus().isOk();

    }


}
