package com.clone.finalProject.service;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@Slf4j // 스프링 부트에서 로그를 남기는 방법 중 가장 편하게 사용되는 어노테이션
@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름

    // 전달 받은 데이터를 바로 S3에 업로드하는 메소드
    // 1. 사전 준비 - 메타데이터와 파일명 생성
    // 2. S3에 전달 받은 파일 업로드
    // 3. S3에 저장된 파일 이름과 주소 반환

    // 파라미터로 multipartFile(업로드하려는 파일)과 dirName(이 파일을 업로드하고 싶은 S3 버킷의 폴더 이름)을 받는다.
    public HashMap<String, String> upload(MultipartFile multipartFile, String dirName) throws IOException {

        // 0. 이미지 파일인지 체크
        isImage(multipartFile);

        // 1. 사전 준비
        // 1-1 메타데이터 생성
        // InputStream을 통해 Byte만 전달되고 해당 파일에 대한 정보가 없기 때문에 파일의 정보를 담은 메타데이터가 필요하다.
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());
        metadata.setContentLength(multipartFile.getSize());

        // 1-2 S3에 저장할 파일명 생성
        // UUID 사용 이유 : 이름이 같은 파일들이 서로 덮어쓰지 않고 구분될 수 있도록
        String fileName = createFileName(multipartFile);
        String uploadImageName = dirName + "/" + UUID.randomUUID() + fileName;

        // 2. s3로 업로드
        amazonS3Client.putObject(new PutObjectRequest(bucket,uploadImageName, multipartFile.getInputStream(),metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        // S3에 업로드한 이미지의 주소를 받아온다.
        String uploadImageUrl = amazonS3Client.getUrl(bucket, uploadImageName).toString();


        // 4. S3에 저장된 파일 이름과 주소 반환
        HashMap<String, String> imgInfo = new HashMap<>();
        imgInfo.put("fileName", uploadImageName);
        imgInfo.put("img",uploadImageUrl);
        return imgInfo;
    }

    // 파일 삭제하기
    public void deleteFile(String fileName) {
        amazonS3Client.deleteObject(bucket, fileName);
    }


    // 처음 사용했던 방법 - 전달 받은 데이터를 서버에 저장하는 과정을 거쳐 업로드하기기
    // 1. 전달받은 파일을 서버에 저장, 즉 업로드를 위한 임시 파일 저장
    // 2. S3에 서버의 임시 파일 업로드
    // 3. 서버의 임시 파일 삭제
    // 4. S3에 저장된 파일 이름과 주소 반환
    public HashMap<String, String> saveAndupload(MultipartFile multipartFile, String dirName) throws IOException {

        // 0. 이미지 파일인지 체크
        isImage(multipartFile);

        // 1. 서버에 임시 파일 저장
        // 1-1 서버에 임시 파일을 저장할 폴더가 없으면 새로 생성
        File Folder = new File(System.getProperty("user.dir")+"/tempFileFolder"); // '/'으로 적든 '\\'으로 적든 같다.
        if (!Folder.exists()) {
            Folder.mkdir();
        }

        // 1-2 파일 객체 생성 (경로는 현재 프로젝트가 위치하는 곳으로)
        File tempFile = new File(System.getProperty("user.dir") + "/tempFileFolder/" + multipartFile.getOriginalFilename());

        // 1-3 파일 존재여부 체크, 없으면 빈 파일 생성
        if (!tempFile.exists()) {
            tempFile.createNewFile();
        }

        // 1-4 tempFile에 내용을 작성할 수 있는 FileOutputStream 생성
        // fileOutPutStream의 'append 디폴트 값이 false'이므로 이미 내용이 존재하는 파일이라면 기존 내용이 지워진다.
        FileOutputStream fos = new FileOutputStream(tempFile);

        // 1-5 가져온 multipartFile의 내용으로 채워넣기
        fos.write(multipartFile.getBytes());

        // 1-6 다 사용한 FileOutputStream 닫기
        fos.close();


        // 2. S3에 파일 업로드
        // 2-1 S3에 저장할 파일명 생성
        // UUID 사용 이유 : 이름이 같은 파일들이 서로 덮어쓰지 않고 구분될 수 있도록
        String fileName = createFileName(multipartFile);
        String uploadImageName = dirName + "/" + UUID.randomUUID() + fileName;

        // 2-2 s3로 업로드
        amazonS3Client.putObject(bucket, uploadImageName, tempFile);
        // S3에 업로드한 이미지의 주소를 받아온다.
        String uploadImageUrl = amazonS3Client.getUrl(bucket, uploadImageName).toString();

        // 3. 임시 파일 삭제
        if (tempFile.exists()) {
            tempFile.delete();
        }

        // 4. S3에 저장된 파일 이름과 주소 반환
        // 해시맵으로 반환
        HashMap<String, String> imgInfo = new HashMap<>();
        imgInfo.put("fileName", uploadImageName);
        imgInfo.put("img",uploadImageUrl);
        return imgInfo;

    }


    // 파일 이름 생성 메소드
    private String createFileName(MultipartFile multipartFile) {

        String name = multipartFile.getOriginalFilename();
        String ext = "";

        // 확장자와 파일명 분리
        if (name.contains(".")) {
            int position = name.lastIndexOf(".");
            ext = name.substring(position+1);
            name = name.substring(0,position);
        }

        // 파일 이름의 길이가 길면 100자로 자르기 (디비의 varchar(255) 제한에 걸리지 않으려고)
        if (name.length()>100){
            name = name.substring(0,100);
        }

        // S3에 저장할 파일 이름 생성
        String fileName = !ext.equals("")?name+"."+ext:name;

        return fileName;
    }

    // 이미지 파일인지 확인하는 메소드
    private void isImage(MultipartFile multipartFile) throws IOException {

        // tika를 이용해 파일 MIME 타입 체크
        // 파일명에 .jpg 식으로 붙는 확장자는 없앨 수도 있고 조작도 가능하므로 MIME 타입을 체크하는 것이 좋다.
        Tika tika = new Tika();
        String mimeType = tika.detect(multipartFile.getInputStream());

        // MIME타입이 이미지가 아니면 exception 발생생
        if (!mimeType.startsWith("image/")) {
            throw new IllegalArgumentException("업로드하려는 파일이 이미지 파일이 아닙니다.");
        }
    }
}
