package com.devang.aichatbot.controller;

import com.devang.aichatbot.model.ChatRequest;
import com.devang.aichatbot.model.ChatResponse;
import com.devang.aichatbot.service.ChatService;

import com.devang.aichatbot.model.ChatMessage;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping
    public ChatResponse chat(@RequestBody ChatRequest request) {

        String userMessage = request.getMessage();

        String botReply =
                chatService.getReply(
                        userMessage,
                        request.getConversationId()
                );

        return new ChatResponse(botReply);
    }

    @GetMapping("/history")
    public List<ChatMessage> getChatHistory() {
        return chatService.getAllChats();
    }

    @GetMapping("/history/{conversationId}")
    public List<ChatMessage> getConversation(
            @PathVariable String conversationId
    ) {
        return chatService.getConversation(conversationId);
    }

}