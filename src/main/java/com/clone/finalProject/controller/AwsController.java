package com.clone.finalProject.controller;

import com.clone.finalProject.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class AwsController {

    private final S3Uploader s3Uploader;

    @PostMapping("/images/upload")
    public String upload(@RequestParam("images") MultipartFile multipartFile) throws IOException {
        s3Uploader.upload(multipartFile, "static");
        return "test";
    }

    // 사진 삭제 테스트
    @DeleteMapping("/image/delete")
    public String deleteimage(@RequestParam("path") String fileName) {
        s3Uploader.deleteFile(fileName);
        return "삭제완료";
    }
}
