package com.clone.finalProject.controller;

import com.clone.finalProject.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AwsController {

    private final S3Uploader s3Uploader;

    @PostMapping("/images/upload")
    public HashMap<String,String> uploadImage(@RequestParam("images") MultipartFile multipartFile) throws IOException {
        log.info("이미지 전송 중");

        String imgUrl = "";
        String fileName = "";
        HashMap<String,String> imgInfo = s3Uploader.uploadImage(multipartFile, "static");
        imgUrl = imgInfo.get("img");

        log.info("이미지 url : {}", imgUrl );

        fileName = imgInfo.get("fileName");

        HashMap<String,String> imageUrl = new HashMap<>();
        imageUrl.put("url",imgUrl);

        return imageUrl;
    }

    // 사진 삭제 테스트
    @DeleteMapping("/image/delete")
    public String deleteImageFile(@RequestParam("path") String fileName) {
        s3Uploader.deleteImageFile(fileName);
        return "삭제완료";
    }
}

//    // 사진 삭제 테스트
//    @DeleteMapping("/image")
//    public void deleteimage(@RequestParam String fileName) {
//        s3Uploader.deleteFile(fileName);
//    }


