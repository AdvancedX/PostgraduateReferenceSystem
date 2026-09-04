package com.pgs.llm;

import java.util.Collections;
import java.util.List;

public class DeepSeekResponse {
    private final LlmMessage assistantMessage;

    public DeepSeekResponse(LlmMessage assistantMessage) {
        this.assistantMessage = assistantMessage;
    }

    public LlmMessage getAssistantMessage() {
        return assistantMessage;
    }

    public String getContent() {
        return assistantMessage == null ? null : assistantMessage.getContent();
    }

    public List<ToolCall> getToolCalls() {
        if (assistantMessage == null || assistantMessage.getToolCalls() == null) {
            return Collections.emptyList();
        }
        return assistantMessage.getToolCalls();
    }

    public boolean hasToolCalls() {
        return !getToolCalls().isEmpty();
    }
}
