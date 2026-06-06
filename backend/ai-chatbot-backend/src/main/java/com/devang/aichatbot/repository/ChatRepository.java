package com.devang.aichatbot.repository;

import com.devang.aichatbot.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ChatRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByOrderByIdAsc();
    List<ChatMessage> findByConversationIdOrderByIdAsc(String conversationId);

    @Query("""
       SELECT DISTINCT c.conversationId
       FROM ChatMessage c
       WHERE c.conversationId IS NOT NULL
       """)
    List<String> findAllConversationIds();
}