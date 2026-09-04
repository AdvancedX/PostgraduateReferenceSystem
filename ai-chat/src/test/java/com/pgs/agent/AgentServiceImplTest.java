package com.pgs.agent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgs.llm.DeepSeekClient;
import com.pgs.llm.DeepSeekResponse;
import com.pgs.llm.FunctionCall;
import com.pgs.llm.LlmMessage;
import com.pgs.llm.ToolCall;
import com.pgs.tool.ToolDefinition;
import com.pgs.tool.ToolExecutionResult;
import com.pgs.tool.ToolRegistry;
import com.pgs.tool.ToolSchemas;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AgentServiceImplTest {
    @Test
    void executesToolCallAndReturnsSecondRoundAnswer() {
        DeepSeekClient client = mock(DeepSeekClient.class);
        ToolRegistry registry = mock(ToolRegistry.class);
        ObjectMapper objectMapper = new ObjectMapper();
        ConversationMemory memory = new ConversationMemory();
        AgentServiceImpl service = new AgentServiceImpl(client, registry, memory, objectMapper);
        AgentUserContext context = new AgentUserContext(
                2L,
                "student",
                Collections.<String>emptySet(),
                Collections.singleton("school:schoolinfo:list"));
        List<ToolDefinition> tools = Collections.singletonList(new ToolDefinition(
                "query_school",
                "school query",
                ToolSchemas.objectSchema(Collections.<String, Object>emptyMap())));
        when(registry.allowedTools(context)).thenReturn(tools);
        when(registry.execute(eq("query_school"), eq(context), any()))
                .thenReturn(ToolExecutionResult.success(Collections.singletonMap("school", "测试大学")));

        ToolCall call = new ToolCall(
                "call-1",
                "function",
                new FunctionCall("query_school", "{\"region\":\"北京\"}"));
        AtomicInteger round = new AtomicInteger();
        when(client.chat(anyList(), eq(tools), eq("request-1"))).thenAnswer(invocation -> {
            if (round.getAndIncrement() == 0) {
                return new DeepSeekResponse(LlmMessage.assistant(null, Collections.singletonList(call)));
            }
            List<LlmMessage> messages = invocation.getArgument(0);
            assertTrue(messages.stream().anyMatch(message -> "tool".equals(message.getRole())));
            return new DeepSeekResponse(LlmMessage.assistant("查询结果是测试大学。"));
        });

        String answer = service.chat(context, "conversation-1", "查询北京院校", "request-1");

        assertEquals("查询结果是测试大学。", answer);
        verify(client, times(2)).chat(anyList(), eq(tools), eq("request-1"));
        verify(registry).execute(eq("query_school"), eq(context), any());
        assertEquals(2, memory.load(2L, "conversation-1").size());
        assertEquals(0, memory.load(3L, "conversation-1").size());
    }

    @Test
    void stopsAfterMaximumToolRounds() {
        DeepSeekClient client = mock(DeepSeekClient.class);
        ToolRegistry registry = mock(ToolRegistry.class);
        AgentServiceImpl service = new AgentServiceImpl(
                client, registry, new ConversationMemory(), new ObjectMapper());
        AgentUserContext context = new AgentUserContext(
                2L,
                "student",
                Collections.<String>emptySet(),
                Collections.singleton("school:schoolinfo:list"));
        ToolCall call = new ToolCall(
                "call-loop",
                "function",
                new FunctionCall("query_school", "{}"));
        when(registry.allowedTools(context)).thenReturn(Collections.<ToolDefinition>emptyList());
        when(registry.execute(eq("query_school"), eq(context), any()))
                .thenReturn(ToolExecutionResult.success(Collections.emptyMap()));
        when(client.chat(anyList(), anyList(), eq("request-loop")))
                .thenReturn(new DeepSeekResponse(
                        LlmMessage.assistant(null, Collections.singletonList(call))));

        assertThrows(
                AgentLoopLimitException.class,
                () -> service.chat(context, "conversation-loop", "继续调用", "request-loop"));
        verify(client, times(AgentConstants.MAX_TOOL_ROUNDS))
                .chat(anyList(), anyList(), eq("request-loop"));
    }
}
