package com.pgs.tool;

import com.fasterxml.jackson.databind.JsonNode;
import com.pgs.agent.AgentUserContext;
import com.pgs.security.AgentPermissionGuard;
import com.pgs.security.AgentPermissionDeniedException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ToolRegistryTest {
    private final AgentPermissionGuard guard = new AgentPermissionGuard();

    @Test
    void registersAndFindsTool() {
        AgentTool tool = tool("query_school", "school:schoolinfo:list");
        ToolRegistry registry = new ToolRegistry(Collections.singletonList(tool), guard);
        assertEquals(tool, registry.require("query_school"));
        assertThrows(AgentToolNotFoundException.class, () -> registry.require("missing"));
    }

    @Test
    void rejectsDuplicateToolNames() {
        assertThrows(
                IllegalStateException.class,
                () -> new ToolRegistry(
                        Arrays.asList(tool("same", "a:b:c"), tool("same", "x:y:z")),
                        guard));
    }

    @Test
    void filtersDefinitionsByExplicitContextPermissions() {
        ToolRegistry registry = new ToolRegistry(
                Arrays.asList(
                        tool("query_school", "school:schoolinfo:list"),
                        tool("query_score", "score:score:list")),
                guard);
        AgentUserContext context = new AgentUserContext(
                2L,
                "student",
                Collections.<String>emptySet(),
                Collections.singleton("school:schoolinfo:list"));

        assertEquals(1, registry.allowedTools(context).size());
        assertEquals("query_school", registry.allowedTools(context).get(0).getFunction().getName());
    }

    @Test
    void rechecksPermissionAtExecutionEvenForUnprotectedToolImplementation() {
        ToolRegistry registry = new ToolRegistry(
                Collections.singletonList(tool("query_score", "score:score:list")),
                guard);
        AgentUserContext context = new AgentUserContext(
                2L,
                "student",
                Collections.<String>emptySet(),
                Collections.singleton("school:schoolinfo:list"));

        assertThrows(
                AgentPermissionDeniedException.class,
                () -> registry.execute("query_score", context, null));
    }

    private AgentTool tool(String name, String permission) {
        return new AgentTool() {
            @Override
            public String name() {
                return name;
            }

            @Override
            public String description() {
                return name;
            }

            @Override
            public String requiredPermission() {
                return permission;
            }

            @Override
            public Map<String, Object> inputSchema() {
                return ToolSchemas.objectSchema(Collections.<String, Object>emptyMap());
            }

            @Override
            public ToolExecutionResult execute(AgentUserContext context, JsonNode arguments) {
                return ToolExecutionResult.success(null);
            }
        };
    }
}
