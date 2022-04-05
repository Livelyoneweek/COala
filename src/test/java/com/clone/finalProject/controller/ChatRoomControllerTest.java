package com.clone.finalProject.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ChatRoomControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    @DisplayName("메인 채널 채팅 내역조회")
    void getMainMessage() {

        webTestClient.get().uri("/mainchat/get/main")
                .exchange()
                .expectStatus().isOk();

    }

    @Test
    @Order(2)
    @DisplayName("게시글 채널 채팅 내역조회")
    void getPostMessage() {

        webTestClient.get().uri("/postchat/get/"+22)
                .exchange()
                .expectStatus().isOk();

    }

}