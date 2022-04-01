
package com.clone.finalProject.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//                .allowedOrigins("https://www.co-ala.com")
//                .allowedOrigins("https://co-ala.com")
//                .allowedOrigins("http://co-ala.com.s3-website.ap-northeast-2.amazonaws.com")
//                .allowedOrigins("http://d2drmli0uyajuu.cloudfront.net")
//                .allowedOrigins("http://localhost:3000")
                .allowedOrigins("*")
//                .allowCredentials(true)
                .allowedHeaders("*")
                .exposedHeaders(HttpHeaders.AUTHORIZATION)
                .allowedMethods("*");
//                .allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE", "PATCH");


    }
}