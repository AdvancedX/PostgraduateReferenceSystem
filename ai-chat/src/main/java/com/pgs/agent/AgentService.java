package com.pgs.agent;

public interface AgentService {
    String chat(AgentUserContext context, String conversationId, String message, String requestId);
}
