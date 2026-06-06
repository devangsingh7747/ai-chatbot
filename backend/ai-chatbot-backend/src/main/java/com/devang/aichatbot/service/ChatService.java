package com.devang.aichatbot.service;

import com.devang.aichatbot.model.ChatMessage;
import com.devang.aichatbot.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    public String getReply(String message, String conversationId) {

        String reply;

        String lower = message.toLowerCase();

        if (lower.contains("java")) {
            reply = "Java is a powerful object-oriented programming language used for backend, enterprise applications, Android development, and more.";
        }
        else if (lower.contains("spring")) {
            reply = "Spring Boot simplifies Java backend development by providing ready-to-use configurations and embedded servers.";
        }
        else if (lower.contains("react")) {
            reply = "React is a JavaScript library used to build fast and interactive user interfaces.";
        }
        else {
            reply = "I am still learning. Please ask something about Java, Spring Boot, or React.";
        }

        if (conversationId == null || conversationId.isEmpty()) {
            conversationId = UUID.randomUUID().toString();
        }

        ChatMessage chatMessage =
                new ChatMessage(
                        conversationId,
                        message,
                        reply
                );

        chatRepository.save(chatMessage);

        return reply;
    }

    public List<ChatMessage> getAllChats() {
        return chatRepository.findAllByOrderByIdAsc();
    }

    public List<ChatMessage> getConversation(String conversationId) {
        return chatRepository.findByConversationIdOrderByIdAsc(conversationId);
    }

    public List<String> getAllConversationIds() {
        return chatRepository.findAllConversationIds();
    }

}