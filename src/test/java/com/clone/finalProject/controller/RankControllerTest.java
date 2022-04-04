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
class RankControllerTest {

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
    @DisplayName("전체 랭킹 조회")
    void getAnswer() {

        webTestClient.get().uri("/user/get/ranking/total")
                .exchange()
                .expectStatus().isOk();

    }

    @Test
    @Order(2)
    @DisplayName("주간 랭킹 조회")
    void getRankingWeekend() {

        webTestClient.get().uri("/user/get/ranking/weekend")
                .exchange()
                .expectStatus().isOk();

    }

    @Test
    @Order(3)
    @DisplayName("월간 랭킹 조회")
    void getRankingMonth() {

        webTestClient.get().uri("/user/get/ranking/month")
                .exchange()
                .expectStatus().isOk();

    }

    @Test
    @Order(4)
    @DisplayName("종합 나의 랭킹")
    void getRankingTotalMy() {

        webTestClient.get().uri("/islogin/get/myranking/total")
                .header(HttpHeaders.AUTHORIZATION, "BEARER "+token)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.uid").toString().equals("1");
    }

    @Test
    @Order(5)
    @DisplayName("종합 나의 랭킹")
    void getRankingWeekendMy() {

        webTestClient.get().uri("/islogin/get/myranking/weekend")
                .header(HttpHeaders.AUTHORIZATION, "BEARER "+token)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.uid").toString().equals("1");
    }

    @Test
    @Order(6)
    @DisplayName("월간 나의 랭킹")
    void getRankingMonthMy() {

        webTestClient.get().uri("/islogin/get/myranking/month")
                .header(HttpHeaders.AUTHORIZATION, "BEARER "+token)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.uid").toString().equals("1");
    }

}