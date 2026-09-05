package com.pgs.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgs.agent.AgentService;
import com.pgs.agent.AgentUserContext;
import com.pgs.agent.AgentUserContextFactory;
import com.pgs.dto.AgentChatRequest;
import com.pgs.security.AgentAuthenticationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DsController
 */
@RestController
@RequestMapping("/deepSeek")
public class DsController {
    private static final Logger log = LoggerFactory.getLogger(DsController.class);
    private static final Pattern CONVERSATION_ID = Pattern.compile("[A-Za-z0-9._-]{1,64}");
    private static final int MAX_MESSAGE_LENGTH = 4000;

    private final AgentService agentService;
    private final AgentUserContextFactory userContextFactory;
    private final ObjectMapper objectMapper;
    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    public DsController(
            AgentService agentService,
            AgentUserContextFactory userContextFactory,
            ObjectMapper objectMapper) {
        this.agentService = agentService;
        this.userContextFactory = userContextFactory;
        this.objectMapper = objectMapper;
    }

    /**
     * chat page
     */
    @GetMapping()
    public ModelAndView chat(ModelAndView modelAndView) {
        modelAndView.setViewName("main");
        return modelAndView;
    }

    /**
     * chat
     */
    @PostMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter chat(@RequestBody String requestBody) {
        final SseEmitter emitter = new SseEmitter(120_000L);
        final String requestId = UUID.randomUUID().toString();
        final AgentChatRequest request;
        final AgentUserContext context;
        try {
            request = parseRequest(requestBody);
            context = userContextFactory.currentUser();
        } catch (RuntimeException e) {
            sendError(emitter, userMessage(e));
            return emitter;
        }

        executor.execute(() -> {
            try {
                String answer = agentService.chat(
                        context,
                        request.getConversationId(),
                        request.resolvedMessage(),
                        requestId);
                sendMessage(emitter, answer);
                emitter.send("[DONE]");
                emitter.complete();
            } catch (Exception e) {
                log.error("Agent SSE request failed requestId={} userId={} conversationId={}",
                        requestId, context.getUserId(), request.getConversationId(), e);
                sendError(emitter, userMessage(e));
            }
        });
        return emitter;
    }

    @PreDestroy
    public void shutdown() {
        executor.shutdown();
    }

    private AgentChatRequest parseRequest(String requestBody) {
        if (requestBody == null || requestBody.trim().isEmpty()) {
            throw new IllegalArgumentException("消息不能为空");
        }
        try {
            JsonNode root = objectMapper.readTree(requestBody);
            AgentChatRequest request = new AgentChatRequest();
            if (root.isTextual()) {
                request.setMessage(root.asText());
            } else if (root.isObject()) {
                request = objectMapper.treeToValue(root, AgentChatRequest.class);
            } else {
                throw new IllegalArgumentException("请求体格式不正确");
            }
            validate(request);
            return request;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException("请求体不是有效 JSON", e);
        }
    }

    private void validate(AgentChatRequest request) {
        String message = request.resolvedMessage();
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("消息不能为空");
        }
        message = message.trim();
        if (message.length() > MAX_MESSAGE_LENGTH) {
            throw new IllegalArgumentException("消息长度不能超过 " + MAX_MESSAGE_LENGTH + " 个字符");
        }
        request.setMessage(message);

        String conversationId = request.getConversationId();
        if (conversationId == null || conversationId.trim().isEmpty()) {
            conversationId = "default";
        } else {
            conversationId = conversationId.trim();
        }
        if (!CONVERSATION_ID.matcher(conversationId).matches()) {
            throw new IllegalArgumentException("conversationId 格式不正确");
        }
        request.setConversationId(conversationId);
    }

    private String userMessage(Exception e) {
        if (e instanceof AgentAuthenticationException) {
            return "当前登录状态无效，请重新登录后再试。";
        }
        if (e instanceof IllegalArgumentException) {
            return e.getMessage();
        }
        return "AI 服务暂时不可用，请稍后再试。";
    }

    private void sendError(SseEmitter emitter, String message) {
        try {
            sendMessage(emitter, message);
            emitter.send("[DONE]");
            emitter.complete();
        } catch (Exception sendException) {
            emitter.complete();
        }
    }

    private void sendMessage(SseEmitter emitter, String message) throws IOException {
        // JSON 字符串会把换行编码为 \n，避免多行内容被 SSE 客户端当成无前缀行丢弃。
        emitter.send(SseEmitter.event().data(objectMapper.writeValueAsString(message)));
    }
}
