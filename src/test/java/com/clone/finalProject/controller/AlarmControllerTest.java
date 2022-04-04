package com.clone.finalProject.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
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
class AlarmControllerTest {

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
    @DisplayName("알람조회하기")
    void getAlarm() {

        webTestClient.get().uri("/islogin/get/alarm")
                .header(HttpHeaders.AUTHORIZATION, "BEARER "+token)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON);
    }

//    @Test
//    @Order(2)
//    @DisplayName("알람삭제하기")
//    void deleteAlarm() {
//
//        webTestClient.delete().uri("/islogin/delete/alarm/"+alarmId)
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody().isEmpty();
//    }



//    String Charsets = new String(result, StandardCharsets.UTF_8);
//    String[] results = Charsets.split(":");
//    commentId = results[1]. replaceAll("\\}", "");

}