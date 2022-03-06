package com.clone.finalProject.repository;

import com.clone.finalProject.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {


}
 