package com.clone.finalProject.repository;

import com.clone.finalProject.domain.ChatMessage;
import com.clone.finalProject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {

    List<ChatMessage> findTOP50ByChatRoom_AreaOrderByCreatedAtDesc(String area);
    List<ChatMessage> findTOP50ByChatRoom_PidOrderByCreatedAtDesc(Long pid);
    List<ChatMessage> findTOP50ByChatRoom_UidOrderByCreatedAtDesc(Long uid);



}
 