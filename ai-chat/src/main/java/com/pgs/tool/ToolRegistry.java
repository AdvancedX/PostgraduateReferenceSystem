package com.pgs.tool;

import com.fasterxml.jackson.databind.JsonNode;
import com.pgs.agent.AgentUserContext;
import com.pgs.security.AgentPermissionGuard;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class ToolRegistry {
    private final Map<String, AgentTool> tools;
    private final AgentPermissionGuard permissionGuard;

    public ToolRegistry(List<AgentTool> registeredTools, AgentPermissionGuard permissionGuard) {
        this.permissionGuard = permissionGuard;
        Map<String, AgentTool> toolMap = new LinkedHashMap<String, AgentTool>();
        if (registeredTools != null) {
            for (AgentTool tool : registeredTools) {
                AgentTool previous = toolMap.put(tool.name(), tool);
                if (previous != null) {
                    throw new IllegalStateException("Duplicate Agent Tool name: " + tool.name());
                }
            }
        }
        this.tools = Collections.unmodifiableMap(toolMap);
    }

    public AgentTool require(String toolName) {
        AgentTool tool = tools.get(toolName);
        if (tool == null) {
            throw new AgentToolNotFoundException(toolName);
        }
        return tool;
    }

    public List<ToolDefinition> allowedTools(AgentUserContext context) {
        List<ToolDefinition> definitions = new ArrayList<ToolDefinition>();
        for (AgentTool tool : tools.values()) {
            if (permissionGuard.isAllowed(context, tool.requiredPermission())) {
                definitions.add(tool.definition());
            }
        }
        return definitions;
    }

    public ToolExecutionResult execute(String toolName, AgentUserContext context, JsonNode arguments) {
        AgentTool tool = require(toolName);
        permissionGuard.require(context, tool.requiredPermission());
        return tool.execute(context, arguments);
    }
}
