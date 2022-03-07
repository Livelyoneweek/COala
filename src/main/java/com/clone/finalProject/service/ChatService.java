package com.clone.finalProject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatService {


    /* destination정보에서 roomId 추출 */
    public String getRoomId(String destination) {
        int lastIndex = destination.lastIndexOf('/');
        if (lastIndex != -1)
            return destination.substring(lastIndex + 1);
        else
            return "";
    }
}
