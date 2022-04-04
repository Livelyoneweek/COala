package com.clone.finalProject.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.clone.finalProject.dto.answrDto.AnswerLikeResponseDto;
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
class PostLikeControllerTest {

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
    @DisplayName("관심 등록 및 삭제")
    void createAnswer() {

        AnswerLikeResponseDto answerLikeResponseDto = AnswerLikeResponseDto.builder()
                .uid(1L)
                .pid(22L)
                .build();

        webTestClient.post().uri("/islogin/post/create/like")
                .header(HttpHeaders.AUTHORIZATION, "BEARER " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(answerLikeResponseDto)
                .exchange()
                .expectStatus().isOk();

    }
}