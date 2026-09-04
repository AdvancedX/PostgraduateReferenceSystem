package com.pgs.llm;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LlmMessageSerializationTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void serializesDeepSeekToolCallingFieldNames() throws Exception {
        ToolCall call = new ToolCall(
                "call-1",
                "function",
                new FunctionCall("query_school", "{\"region\":\"北京\"}"));
        JsonNode assistant = objectMapper.readTree(objectMapper.writeValueAsString(
                LlmMessage.assistant(null, Collections.singletonList(call))));
        JsonNode tool = objectMapper.readTree(objectMapper.writeValueAsString(
                LlmMessage.tool("call-1", "{\"success\":true}")));

        assertTrue(assistant.has("tool_calls"));
        assertFalse(assistant.has("toolCalls"));
        assertEquals("query_school", assistant.path("tool_calls").path(0).path("function").path("name").asText());
        assertEquals("call-1", tool.path("tool_call_id").asText());
        assertFalse(tool.has("toolCallId"));
    }
}
