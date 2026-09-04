package com.pgs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgs.agent.AgentService;
import com.pgs.agent.AgentUserContext;
import com.pgs.agent.AgentUserContextFactory;
import org.junit.jupiter.api.Test;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class DsControllerTest {
    @Test
    void encodesMultilineAnswerAsOneJsonSseDataLine() throws Exception {
        AgentService agentService = mock(AgentService.class);
        AgentUserContextFactory contextFactory = mock(AgentUserContextFactory.class);
        AgentUserContext context = new AgentUserContext(
                1L, "admin", Collections.<String>emptySet(), Collections.singleton("*:*:*"));
        when(contextFactory.currentUser()).thenReturn(context);
        when(agentService.chat(eq(context), eq("test-conversation"), eq("查询分数"), anyString()))
                .thenReturn("first line\nsecond line");

        DsController controller = new DsController(agentService, contextFactory, new ObjectMapper());
        try {
            MockMvc mockMvc = standaloneSetup(controller)
                    .addFilter(new CharacterEncodingFilter("UTF-8", true))
                    .build();
            MvcResult initial = mockMvc.perform(post("/deepSeek/chat")
                            .contentType("application/json")
                            .accept("text/event-stream")
                            .content("{\"conversationId\":\"test-conversation\",\"message\":\"查询分数\"}"))
                    .andExpect(request().asyncStarted())
                    .andReturn();

            MvcResult completed = mockMvc.perform(asyncDispatch(initial))
                    .andExpect(status().isOk())
                    .andReturn();
            String body = new String(completed.getResponse().getContentAsByteArray(), StandardCharsets.UTF_8);

            assertTrue(body.contains("data:\"first line\\nsecond line\""), body);
            assertFalse(body.contains("\nsecond line"), body);
            assertTrue(body.contains("data:[DONE]"), body);
        } finally {
            controller.shutdown();
        }
    }
}
