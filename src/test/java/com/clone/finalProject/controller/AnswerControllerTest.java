package com.clone.finalProject.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.clone.finalProject.dto.answrDto.AnswerResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AnswerControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    String token = JWT.create()
            .withIssuer("sparta")
            .withClaim("USER_NAME", "choi123")
            // 토큰 만료 일시 = 현재 시간 + 토큰 유효기간)
            .withClaim("EXPIRED_DATE", new Date(System.currentTimeMillis() +60*60*24*3))
            .sign(Algorithm.HMAC256("jwt_secret_!@#$%"));

    String answerId;


    @Test
    @Order(1)
    @DisplayName("답변생성하기")
    void createAnswer() {

        AnswerResponseDto answerResponseDto = AnswerResponseDto.builder()
                .uid(1L)
                .pid(22L)
                .answerTitle("Answer확인용입니다")
                .answerComment("AnswerCommnet확인용입니다")
                .build();

        byte[] result = webTestClient.post().uri("/islogin/answer/create/22")
                .header(HttpHeaders.AUTHORIZATION, "BEARER "+token)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(answerResponseDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.answerId").exists()
                .returnResult().getResponseBody();

        log.info("result:{}",result);
        String Charsets = new String(result, StandardCharsets.UTF_8);
        log.info("Charsets:{}",Charsets);
        answerId= Charsets.substring(12,14);
        log.info("answerId:{}",answerId);

    }

    @Test
    @Order(2)
    @DisplayName("답변조회하기")
    void getAnswer() {

        webTestClient.get().uri("/answer/get/"+answerId)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.answerId").toString().equals(answerId);
    }


    @Test
    @Order(3)
    @DisplayName("답변수정하기")
    void editAnswer() {

        AnswerResponseDto answerResponseDto = AnswerResponseDto.builder()
                .answerTitle("Answer확인용입니다2")
                .answerComment("Answer확인용입니다2")
                .build();

        webTestClient.put().uri("/islogin/answer/edit/" + answerId)
                .header(HttpHeaders.AUTHORIZATION, "BEARER "+token)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(answerResponseDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.answerComment").toString().equals("Answer확인용입니다2");
    }


    @Test
    @Order(4)
    @DisplayName("답변삭제하기")
    void deleteAnswer() {

        webTestClient.delete().uri("/islogin/answer/delete/" + answerId)
                .header(HttpHeaders.AUTHORIZATION, "BEARER "+token)
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();
    }


}