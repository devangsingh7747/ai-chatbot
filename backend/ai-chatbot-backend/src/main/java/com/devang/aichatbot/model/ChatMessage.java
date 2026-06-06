package com.devang.aichatbot.model;

import jakarta.persistence.*;

@Entity
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userMessage;

    private String botReply;

    private String conversationId;

    public ChatMessage() {
    }

    public ChatMessage(
            String conversationId,
            String userMessage,
            String botReply
    ) {
        this.conversationId = conversationId;
        this.userMessage = userMessage;
        this.botReply = botReply;
    }

    public Long getId() {
        return id;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getBotReply() {
        return botReply;
    }

    public void setBotReply(String botReply) {
        this.botReply = botReply;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }
}