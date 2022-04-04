package com.clone.finalProject.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.clone.finalProject.dto.postDto.PostRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    String token = JWT.create()
            .withIssuer("sparta")
            .withClaim("USER_NAME", "choi123")
            // 토큰 만료 일시 = 현재 시간 + 토큰 유효기간)
            .withClaim("EXPIRED_DATE", new Date(System.currentTimeMillis() + 60 * 60 * 24 * 3))
            .sign(Algorithm.HMAC256("jwt_secret_!@#$%"));


    String pid;

    @Test
    @Order(1)
    @DisplayName("게시글생성하기")
    void createPost() {

        List<String> tags = new ArrayList<>();
        tags.add("spring");
        tags.add("testCode");

        PostRequestDto postRequestDto = PostRequestDto.builder()
                .postComment("코알라오신여러분환영합니다")
                .postTitle("코알라오신여러분환영합니다")
                .tags(tags)
                .category("Javascript")
                .build();

        byte[] result = webTestClient.post().uri("/islogin/post/create")
                .header(HttpHeaders.AUTHORIZATION, "BEARER " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(postRequestDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult().getResponseBodyContent();

        log.info("result:{}", result);
        String Charsets = new String(result, StandardCharsets.UTF_8);
        log.info("Charsets:{}", Charsets);
        pid = Charsets.toString();
        log.info("pid:{}", pid);

    }

    @Test
    @Order(2)
    @DisplayName("게시글조회하기")
    void getAnswer() {

        webTestClient.get().uri(uriBuilder ->
                        uriBuilder
                                .path("/post/get/nocheck")
                                .queryParam("page", "1")
                                .queryParam("size", "10")
                                .queryParam("sortBy", "createdAt")
                                .queryParam("isAsc", "false")
                                .build())
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.[0].pid").toString().equals(pid);
    }

    @Test
    @Order(3)
    @DisplayName("게시글수정하기")
    void editAnswer() {

        List<String> tags = new ArrayList<>();
        tags.add("nginx");
        tags.add("https");

        PostRequestDto postRequestDto = PostRequestDto.builder()
                .postComment("코알라오신여러분환영합니다22")
                .postTitle("코알라오신여러분환영합니다22")
                .tags(tags)
                .category("Javascript")
                .build();

        webTestClient.put().uri("/islogin/post/edit/" + pid)
                .header(HttpHeaders.AUTHORIZATION, "BEARER "+token)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(postRequestDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.postComment").toString().equals("코알라오신여러분환영합니다22");
    }

    @Test
    @Order(4)
    @DisplayName("게시글삭제하기")
    void deleteAnswer() {

        webTestClient.delete().uri("/islogin/post/delete/" + pid)
                .header(HttpHeaders.AUTHORIZATION, "BEARER "+token)
                .exchange()
                .expectStatus().isOk();
    }




}