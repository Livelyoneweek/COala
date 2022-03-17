package com.clone.finalProject.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@RestController
public class TxtReadController {

    //욕설 리스트 디비에 넣기 위한 컨트롤러
    // txt파일을 읽고 한줄씩 내려가면서 값을 디비에 넣음


    @GetMapping("/read/txt")
    public String readTxt() throws IOException {

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources";

        String filename = "\\fword_list.txt";

        BufferedReader br = new BufferedReader(new FileReader(projectPath+filename));

        String str;

        while ((str = br.readLine()) != null) {
            System.out.println(str);
        }
        br.close();

        return "ok";
    }


}
