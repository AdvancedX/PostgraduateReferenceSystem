package com.pgs.llm;

import com.pgs.tool.ToolDefinition;

import java.util.List;

public interface DeepSeekClient {
    DeepSeekResponse chat(List<LlmMessage> messages, List<ToolDefinition> tools, String requestId);
}
