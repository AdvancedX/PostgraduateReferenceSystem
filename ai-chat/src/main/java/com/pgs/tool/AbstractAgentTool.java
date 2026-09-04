package com.pgs.tool;

import com.fasterxml.jackson.databind.JsonNode;
import com.pgs.agent.AgentUserContext;
import com.pgs.security.AgentPermissionGuard;

public abstract class AbstractAgentTool implements AgentTool {
    private final AgentPermissionGuard permissionGuard;

    protected AbstractAgentTool(AgentPermissionGuard permissionGuard) {
        this.permissionGuard = permissionGuard;
    }

    @Override
    public final ToolExecutionResult execute(AgentUserContext context, JsonNode arguments) {
        permissionGuard.require(context, requiredPermission());
        if (arguments == null || !arguments.isObject()) {
            throw new AgentToolArgumentException("Tool 参数必须是 JSON object");
        }
        return doExecute(arguments);
    }

    protected abstract ToolExecutionResult doExecute(JsonNode arguments);

    protected String optionalText(JsonNode arguments, String fieldName) {
        JsonNode value = arguments.get(fieldName);
        if (value == null || value.isNull()) {
            return null;
        }
        if (!value.isTextual()) {
            throw new AgentToolArgumentException(fieldName + " 必须是字符串");
        }
        String text = value.asText().trim();
        return text.isEmpty() ? null : text;
    }

    protected int limit(JsonNode arguments) {
        JsonNode value = arguments.get("limit");
        if (value == null || value.isNull()) {
            return 20;
        }
        if (!value.canConvertToInt()) {
            throw new AgentToolArgumentException("limit 必须是整数");
        }
        int limit = value.intValue();
        if (limit < 1 || limit > 100) {
            throw new AgentToolArgumentException("limit 必须在 1 到 100 之间");
        }
        return limit;
    }
}
