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
class TagsControllerTest {

    @Autowired
    private WebTestClient webTestClient;


    @Test
    @Order(1)
    @DisplayName("태그로 게시글 조회")
    void searchTag() {

        webTestClient.get().uri(uriBuilder ->
                        uriBuilder
                                .path("/post/get/tag/"+"코린이")
                                .queryParam("page", "1")
                                .queryParam("size", "10")
                                .queryParam("sortBy", "createdAt")
                                .queryParam("isAsc", "false")
                                .build())
                .exchange()
                .expectStatus().isOk();

    }

}