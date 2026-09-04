package com.pgs.tool;

import com.fasterxml.jackson.databind.JsonNode;
import com.pgs.agent.AgentUserContext;

import java.util.Map;

public interface AgentTool {
    String name();

    String description();

    String requiredPermission();

    Map<String, Object> inputSchema();

    ToolExecutionResult execute(AgentUserContext context, JsonNode arguments);

    default ToolDefinition definition() {
        return new ToolDefinition(name(), description(), inputSchema());
    }
}
