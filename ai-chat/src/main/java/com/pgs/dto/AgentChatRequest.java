package com.pgs.dto;

public class AgentChatRequest {
    private String conversationId;
    private String message;
    private String question;

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String resolvedMessage() {
        return message != null ? message : question;
    }
}
